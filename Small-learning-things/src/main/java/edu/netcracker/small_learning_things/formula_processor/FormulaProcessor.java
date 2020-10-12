package edu.netcracker.small_learning_things.formula_processor;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings({"ConstantConditions", "unchecked"})
public class FormulaProcessor {
    private final Map<Integer, Function<Map<String, Object>, Object>> formulaMap;
    private final Map<Class<? extends Number>, Function<Number, Number>> convertNumberResultMap;

    @SneakyThrows
    public FormulaProcessor() {
        formulaMap = new HashMap<>();
        convertNumberResultMap = new HashMap<>();
        convertNumberResultMap.put(Float.class, Number::floatValue);
        convertNumberResultMap.put(Long.class, Number::longValue);
        convertNumberResultMap.put(Integer.class, Number::intValue);
        convertNumberResultMap.put(Double.class, Number::doubleValue);


        String formula1 = "settlement.revenueShareAmount * charge.chargeCostMny.amount / 100 * 100";
        Integer formulaId1 = 1;
        String formula2 = "charge.chargeCostMny.amount * charge.chargeCostMny.moneyMultiplier";
        Integer formulaId2 = 2;
        String formula3 = "charge.chargeType";
        Integer formulaId3 = 3;
        String formula4 = "150.55";
        Integer formulaId4 = 4;
        String formula5 = "charge.chargeEndDat";
        Integer formulaId5 = 5;
        formulaMap.put(formulaId1, createFormula(formula1, Class.forName("java.lang.Long")));
        formulaMap.put(formulaId2, createFormula(formula2, Double.class));
        formulaMap.put(formulaId3, createFormula(formula3, Class.forName("java.lang.Integer")));
        formulaMap.put(formulaId4, createFormula(formula4, Float.class));
        formulaMap.put(formulaId5, createFormula(formula5, ZonedDateTime.class));

        Map<String, Object> objectMap = new HashMap<>();
        SettlementORM settlement = new SettlementORM()
                .setRevenueShareAmount(100);
        RBMCharge charge = new RBMCharge()
                .setChargeCostMny(new Money()
                        .setAmount(25.555F)
                        .setMoneyMultiplier(1000))
                .setChargeType(159)
                .setChargeEndDat(ZonedDateTime.now().minusDays(2));

        objectMap.put("settlement", settlement);
        objectMap.put("charge", charge);

        final Object x1 = formulaMap.get(1).apply(objectMap);
        System.out.println(x1 + " " + x1.getClass().equals(Long.class));
        final Object x2 = formulaMap.get(2).apply(objectMap);
        System.out.println(x2 + " " + x2.getClass().equals(Double.class));
        final Object x3 = formulaMap.get(3).apply(objectMap);
        System.out.println(x3 + " " + x3.getClass().equals(Class.forName("java.lang.Integer")));
        final Object x4 = formulaMap.get(4).apply(objectMap);
        System.out.println(x4 + " " + x4.getClass().equals(Float.class));
        final Object x5 = formulaMap.get(5).apply(objectMap);
        System.out.println(x5 + " " + x5.getClass().equals(ZonedDateTime.class));
    }

    public static void main(String[] args) {
        new FormulaProcessor();
    }

    public FormulaType type(String contender) {
        if (Operation.isOperation(contender)) {
            return FormulaType.OPERATION;
        }
        boolean isVar = contender.chars()
                .reduce((left, right) -> (isPartOfVariable(right) ? 1 : 0) & left)
                .orElseThrow(() -> new RuntimeException("Empty string provided!")) == 1;
        return isVar ? FormulaType.VARIABLE : FormulaType.CONSTANT;
    }

    private boolean isPartOfVariable(int right) {
        return Character.isLetter(right) || '.' == right;
    }

    private Function<Map<String, Object>, Object> createFormula(String formula, Class<?> returnType) {
        Map<FormulaType, Pair<Function<String, Object>, ArrayDeque<Object>>> map = new HashMap<>();
        String[] parts = formula.split(" ");

        final int formulaPartsLength = parts.length;

        ArrayDeque<Object> constants = new ArrayDeque<>(formulaPartsLength);
        ArrayDeque<Object> operations = new ArrayDeque<>(formulaPartsLength);
        ArrayDeque<Object> variables = new ArrayDeque<>(formulaPartsLength);
        ArrayDeque<Object> types = new ArrayDeque<>(formulaPartsLength);

        map.put(FormulaType.OPERATION, Pair.of(this::processOperation, operations));
        map.put(FormulaType.CONSTANT, Pair.of(this::processConstant, constants));
        map.put(FormulaType.VARIABLE, Pair.of(this::processVariable, variables));

        int i = 0;
        while (i < formulaPartsLength) {
            final FormulaType type = type(parts[i]);
            types.offer(type);
            Pair<Function<String, Object>, ArrayDeque<Object>> pair = map.get(type);
            ArrayDeque<Object> queue = pair.getSecond();
            queue.offer(pair.getFirst().apply(parts[i]));
            i++;
        }

        return nameObjectMap -> {
            int j = 1;

            Object objValue;
            Number value;
            Operation operation;
            Number secondOperand;

            objValue = getValue((FormulaType) types.poll(), constants, variables, nameObjectMap);
            if (formulaPartsLength == 1) {
                return objValue;
            }
            value = (Number) objValue;
            while (j < formulaPartsLength) {
                types.poll();
                operation = (Operation) operations.poll();
                FormulaType formulaType = (FormulaType) types.poll();
                secondOperand = (Number) getValue(formulaType, constants, variables, nameObjectMap);
                value = operation.getOperation().apply(value, secondOperand);
                j += 2;
            }

            return convertNumberResultMap.get(returnType).apply(value);
        };
    }

    private Object getValue(FormulaType formulaType, ArrayDeque<Object> constants, ArrayDeque<Object> variables, Map<String, Object> nameObjectMap) {
        switch (formulaType) {
            case CONSTANT:
                return constants.poll();
            case VARIABLE:
                return ((Function<Map<String, Object>, Object>) Objects.requireNonNull(variables.poll())).apply(nameObjectMap);
        }
        throw new RuntimeException("Wrong formula type!");
    }

    private Object processOperation(String operation) {
        return Operation.getOperation(operation);
    }

    private Number processConstant(String constant) {
        return Float.parseFloat(constant);
    }

    private Function<Map<String, Object>, Object> processVariable(String variable) {
        return values -> {
            String[] tokens = variable.split("\\.");
            Object object = values.get(tokens[0]);
            Field field;
            int i = 0;
            while (i < tokens.length - 1) {
                try {
                    field = object.getClass().getDeclaredField(tokens[i + 1]);
                    field.setAccessible(true);
                    object = field.get(object);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException("No field with name: " + tokens[i + 1] + " in class: " + object.getClass().getSimpleName());
                }
                i++;
            }
            return object;
        };
    }
}
