package com.subranil.calculatorapp;

import java.math.RoundingMode;
import java.text.DecimalFormat;

class EquationHelper {
     EquationHelper() {
    }

     String solveProblem(String s) {
        s = solveBasicProblem(s, " ^ ", " ^ ");
        s = solveBasicProblem(s, " * ", " / ");
        s = solveBasicProblem(s, " + ", " - ");
        return s;
    }

    private String solveBasicProblem(String s, String op1, String op2) {
        s = " " + s + " ";
        while (s.contains(op1) || s.contains(op2)) {
            String operator;
            if (s.indexOf(op2) < s.indexOf(op1))
                operator = op2;
            else
                operator = op1;
            if (!s.contains(op1))
                operator = op2;
            if (!s.contains(op2))
                operator = op1;
            String s1 = s.substring(0, s.indexOf(operator));
            String s2 = s.substring(s.indexOf(operator) + 3);
            double d1 = Double.parseDouble(s1.substring(s1.lastIndexOf(' ') + 1));
            double d2 = Double.parseDouble(s2.substring(0, s2.indexOf(' ')));
            s1 = s1.substring(0, s1.lastIndexOf(' ')).trim();
            s2 = s2.substring(s2.indexOf(' '), s2.length()).trim();
            double answer = 0;
            switch (operator) {
                case " / ":
                    answer = d1 / d2;
                    break;
                case " * ":
                    answer = d1 * d2;
                    break;
                case " - ":
                    answer = d1 - d2;
                    break;
                case " + ":
                    answer = d1 + d2;
                    break;
                case " ^ ":
                    answer = Math.pow(d1, d2);
                    break;
            }
            s = " " + s1 + " " + answer + " " + s2 + " ";
        }
        return s.trim();
    }

     String formatNumber(String s) {
        if (s.contains("∞") || s.contains("Infinity") || s.contains("NaN"))
            return s;
        DecimalFormat df = new DecimalFormat("#.########E0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String output = df.format(Double.parseDouble(s));
        if (Math.abs(Double.parseDouble(output.substring(output.indexOf("E") + 1))) < 8) {
            df.applyPattern("#.########");
            output = df.format(Double.parseDouble(s));
        }
        return output;
    }

}
