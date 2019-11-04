package com.subranil.calculatorapp;

class Calculator {
    private MainActivity context;
    private EquationHelper equationHelper;
    private Equation eq;

    Calculator(MainActivity context) {
        this.context = context;
        this.equationHelper = new EquationHelper();
        this.eq = new Equation();
        update();
    }

    String getText() {
        return eq.getText();
    }

    void setText(String text) {
        eq.setText(text);
        update();
    }

    void decimal() {
        if (!Character.isDigit(eq.getLastChar()))
            digit('0');
        if (!eq.getLast().contains("."))
            eq.attachToLast('.');
        update();
    }

    void delete() {
        if (eq.getLast().length() > 1 && (eq.isRawNumber(0) || eq.getLast().charAt(0) == '-'))
            eq.detachFromLast();
        else
            eq.removeLast();
        update();
    }

    void digit(char digit) {
        if (eq.isRawNumber(0))
            eq.attachToLast(digit);
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("" + digit);
        }
        update();
    }

    void equal() {
        String s;
        try {
            s = equationHelper.formatNumber(equationHelper.solveProblem(getText()));
        } catch (Exception e) {
            s = "Error";
        }
        context.displayPrimaryToLeft(s);
        context.displaySecondary("");
        eq = new Equation();
        if (!s.contains("∞") && !s.contains("Infinity") && !s.contains("NaN"))
            eq.add(s);
    }

    void numOpNum(char operator) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (operator != '-' || (eq.isOperator(0) && eq.isStartCharacter(1)))
            while (eq.isOperator(0))
                eq.removeLast();
        if (operator == '-' || !eq.isStartCharacter(0))
            eq.add("" + operator);
        update();
    }

    private void update() {
        context.displayPrimaryToRight(getText());
        try {
            context.displaySecondary(
                    equationHelper.formatNumber(equationHelper.solveProblem(getText()))
            );
        } catch (Exception e) {
            context.displaySecondary("");
        }
    }
}
