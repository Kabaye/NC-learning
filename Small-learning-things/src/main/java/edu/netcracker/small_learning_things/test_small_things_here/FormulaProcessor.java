package edu.netcracker.small_learning_things.test_small_things_here;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("ConstantConditions")
public class FormulaProcessor {
    private final Map<Integer, Function<Map<String, Object>, Number>> formulaMap;
    private final Map<Class<? extends Number>, Function<Number, Number>> convertResultMap;

    public FormulaProcessor() {
        formulaMap = new HashMap<>();
        convertResultMap = new HashMap<>();
        convertResultMap.put(Float.class, Number::floatValue);
        convertResultMap.put(Long.class, Number::longValue);
        convertResultMap.put(Integer.class, Number::intValue);
        convertResultMap.put(Double.class, Number::doubleValue);


        String formula1 = "settlement.revenueShareAmount * charge.chargeCostMny.amount / 100 * 100";
        Integer formulaId1 = 1;
        String formula2 = "charge.chargeCostMny.amount * charge.chargeCostMny.moneyMultiplier";
        Integer formulaId2 = 2;
        formulaMap.put(formulaId1, createFormula(formula1, Long.class));
        formulaMap.put(formulaId2, createFormula(formula2, Double.class));

        Map<String, Object> objectMap = new HashMap<>();
        SettlementORM settlement = new SettlementORM()
                .setRevenueShareAmount(100);
        RBMCharge charge = new RBMCharge()
                .setChargeCostMny(new Money()
                        .setAmount(25.555F)
                        .setMoneyMultiplier(1000));

        objectMap.put("settlement", settlement);
        objectMap.put("charge", charge);

        final Number x = formulaMap.get(1).apply(objectMap);
        System.out.println(x + " " + x.getClass().equals(Long.class));
        final Number x1 = formulaMap.get(2).apply(objectMap);
        System.out.println(x1 + " " + x1.getClass().equals(Double.class));
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

    private Function<Map<String, Object>, Number> createFormula(String formula, Class<? extends Number> returnType) {
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
            int j = 3;

            Number value;
            Operation operation;
            Number secondOperand;

            value = getValue((FormulaType) types.poll(), constants, variables, nameObjectMap);
            types.poll();
            operation = (Operation) operations.poll();
            secondOperand = getValue((FormulaType) types.poll(), constants, variables, nameObjectMap);
            value = operation.getOperation().apply(value, secondOperand);
            while (j < formulaPartsLength) {
                types.poll();
                operation = (Operation) operations.poll();
                FormulaType formulaType = (FormulaType) types.poll();
                secondOperand = getValue(formulaType, constants, variables, nameObjectMap);
                value = operation.getOperation().apply(value, secondOperand);
                j += 2;
            }

            return convertResultMap.get(returnType).apply(value);
        };
    }

    private Number getValue(FormulaType formulaType, ArrayDeque<Object> constants, ArrayDeque<Object> variables, Map<String, Object> nameObjectMap) {
        switch (formulaType) {
            case CONSTANT:
                return (Number) constants.poll();
            case VARIABLE:
                return ((Function<Map<String, Object>, Number>) Objects.requireNonNull(variables.poll())).apply(nameObjectMap);
        }
        throw new RuntimeException("Wrong formula type!");
    }

    private Object processOperation(String operation) {
        return Operation.getOperation(operation);
    }

    private Number processConstant(String constant) {
        return Float.parseFloat(constant);
    }

    private Function<Map<String, Object>, Number> processVariable(String variable) {
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
            return (Number) object;
        };
    }
}
