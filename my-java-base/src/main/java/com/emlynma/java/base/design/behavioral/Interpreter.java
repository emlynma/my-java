package com.emlynma.java.base.design.behavioral;

import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    // 抽象表达式
    interface Expression {
        int interpret(Context context);
    }

    // 终结符表达式
    static class NumberExpression implements Expression {
        private int number;

        public NumberExpression(int number) {
            this.number = number;
        }

        @Override
        public int interpret(Context context) {
            return number;
        }
    }

    // 非终结符表达式
    static class AddExpression implements Expression {
        private Expression leftExpression;
        private Expression rightExpression;

        public AddExpression(Expression leftExpression, Expression rightExpression) {
            this.leftExpression = leftExpression;
            this.rightExpression = rightExpression;
        }

        @Override
        public int interpret(Context context) {
            int leftValue = leftExpression.interpret(context);
            int rightValue = rightExpression.interpret(context);
            return leftValue + rightValue;
        }
    }

    // 上下文
    static class Context {
        private Map<String, Integer> variables;

        public Context() {
            variables = new HashMap<>();
        }

        public void setVariable(String variable, int value) {
            variables.put(variable, value);
        }

        public int getVariable(String variable) {
            return variables.get(variable);
        }
    }

    public static void main(String[] args) {
        Context context = new Context();
        context.setVariable("a", 10);
        context.setVariable("b", 5);

        Expression expression = new AddExpression(
                new NumberExpression(context.getVariable("a")),
                new NumberExpression(context.getVariable("b"))
        );

        int result = expression.interpret(context);
        System.out.println("Result: " + result); // Output: Result: 15
    }

}
