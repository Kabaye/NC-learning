package edu.netcracker.small_learning_things.test_small_things_here;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class Tester {
    public static void main(String[] args) {
        String formula = "settlement.revenueShareAmount * charge.chargeCostMny.amount / 100 * 100";

        createFormula(formula);


        Map<FormulaType, Pair<BiFunction<Map<String, Object>, String, Object>, ArrayDeque<Object>>> map = new HashMap<>();

        String[] parts = formula.split(" ");

        ArrayDeque<Object> constants = new ArrayDeque<>(parts.length);
        ArrayDeque<Object> operations = new ArrayDeque<>(parts.length);
        ArrayDeque<Object> variables = new ArrayDeque<>(parts.length);

        map.put(FormulaType.OPERATION, Pair.of(Tester::processOperation, operations));
        map.put(FormulaType.CONSTANT, Pair.of(Tester::processConstant, constants));
        map.put(FormulaType.VARIABLE, Pair.of(Tester::processVariable, variables));

        Map<String, Object> objectMap = new HashMap<>();
        SettlementORM settlement = new SettlementORM()
                .setRevenueShareAmount(100);
        RBMCharge charge = new RBMCharge()
                .setChargeCostMny(new Money().setAmount(55.5555F));

        objectMap.put("settlement", settlement);
        objectMap.put("charge", charge);


        int i = 0;
        while (i < parts.length) {
            final FormulaType type = type(parts[i]);
            Pair<BiFunction<Map<String, Object>, String, Object>, ArrayDeque<Object>> pair = map.get(type);
            ArrayDeque<Object> queue = pair.getSecond();
            queue.offer(pair.getFirst().apply(objectMap, parts[i]));
            i++;
        }

        i = 3;

        Number value;

        Number firstOperand = (Number) constants.poll();
        Operation operation = (Operation) operations.poll();
        Number secondOperand = (Number) constants.poll();

        value = Objects.requireNonNull(operation).getOperation().apply(firstOperand, secondOperand);
        while (i < parts.length) {
            secondOperand = (Number) constants.poll();
            operation = (Operation) operations.poll();
            value = Objects.requireNonNull(operation).getOperation().apply(value, secondOperand);
            i += 2;
        }

        System.out.println(value);

    }

    public static FormulaType type(String contender) {
        if (Operation.isOperation(contender)) {
            return FormulaType.OPERATION;
        }
        boolean isVar = contender.chars()
                .reduce((left, right) -> (isPartOfVariable(right) ? 1 : 0) & left)
                .orElseThrow(() -> new RuntimeException("Empty string provided!")) == 1;
        return isVar ? FormulaType.VARIABLE : FormulaType.CONSTANT;
    }

    private static boolean isPartOfVariable(int right) {
        return Character.isLetter(right) || '.' == right;
    }

    private static Object processOperation(Map<String, Object> values, String operation) {
        return Operation.getOperation(operation);
    }

    private static Number processConstant(Map<String, Object> values, String constant) {
        return Float.parseFloat(constant);
    }

    @SneakyThrows
    private static Number processVariable(Map<String, Object> values, String variable) {
        String[] tokens = variable.split("\\.");
        Object object = values.get(tokens[0]);
        Field field;
        int i = 0;
        while (i < tokens.length - 1) {
            field = object.getClass().getDeclaredField(tokens[i + 1]);
            field.setAccessible(true);
            object = field.get(object);
            i++;
        }
        return (Number) object;
    }

    private static void createFormula(String formula) {
    }
}
