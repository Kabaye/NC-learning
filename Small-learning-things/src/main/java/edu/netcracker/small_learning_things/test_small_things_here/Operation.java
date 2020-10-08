package edu.netcracker.small_learning_things.test_small_things_here;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

/**
 * @author svku0919
 * @version 08.10.2020
 */
@AllArgsConstructor
public enum Operation {
    ADDITION("+", (x, y) -> x.floatValue() + y.floatValue()),
    SUBTRACTION("-", (x, y) -> x.floatValue() - y.floatValue()),
    MULTIPLICATION("*", (x, y) -> x.floatValue() * y.floatValue()),
    DIVISION("/", (x, y) -> x.floatValue() / y.floatValue());

    @Getter
    private final String operationSymbol;
    @Getter
    private final BiFunction<Number, Number, Number> operation;

    public static Operation getOperation(String operationSymbol) {
        if (ADDITION.operationSymbol.equals(operationSymbol)) {
            return ADDITION;
        } else if (SUBTRACTION.operationSymbol.equals(operationSymbol)) {
            return SUBTRACTION;
        } else if (MULTIPLICATION.operationSymbol.equals(operationSymbol)) {
            return MULTIPLICATION;
        } else if (DIVISION.operationSymbol.equals(operationSymbol)) {
            return DIVISION;
        }
        throw new RuntimeException("The operation symbol does not match! Check the provided formula");
    }

    public static boolean isOperation(String operationSymbol) {
        return ADDITION.operationSymbol.equals(operationSymbol) ||
                SUBTRACTION.operationSymbol.equals(operationSymbol) ||
                MULTIPLICATION.operationSymbol.equals(operationSymbol) ||
                DIVISION.operationSymbol.equals(operationSymbol);

    }
}
