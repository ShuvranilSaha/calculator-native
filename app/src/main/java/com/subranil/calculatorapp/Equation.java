package com.subranil.calculatorapp;

import java.util.ArrayList;

class Equation extends ArrayList<String> {

     String getText() {
        String text = "";
        for (String s : this)
            text += s + " ";
        return text;
    }

     void setText(String text) {
        while (size() > 0)
            removeLast();
        if (text.length() > 0) {
            String[] sa = text.split(" ");
            for (String s : sa)
                add(s);
        }
    }

     void attachToLast(char c) {
        if (size() == 0)
            add("" + c);
        else
            set(size() - 1, getLast() + c);
    }

     void detachFromLast() {
        if (getLast().length() > 0) {
            set(size() - 1, getLast().substring(0, getLast().length() - 1));
        }
    }

     void removeLast() {
        if (size() > 0)
            remove(size() - 1);
    }

     String getLast() {
        return getRecent(0);
    }

     char getLastChar() {
        String s = getLast();
        if (s.length() > 0)
            return s.charAt(s.length() - 1);
        return ' ';
    }

     String getRecent(int indexFromLast) {
        if (size() <= indexFromLast)
            return "";
        return get(size() - indexFromLast - 1);
    }

     boolean isNumber(int i) {
        String s = getRecent(i);
        if (s != null && s.length() > 0) {
            char c = s.charAt(0);
            return isRawNumber(i) || c == 'π' || c == 'e' || c == ')' || c == '!';
        }
        return false;
    }

     boolean isOperator(int i) {
        String s = getRecent(i);
        if (s != null && s.length() == 1) {
            char c = s.charAt(0);
            return c == '/' || c == '*' || c == '-' || c == '+' || c == '^';
        }
        return false;
    }

     boolean isRawNumber(int i) {
        String s = getRecent(i);
        if (s != null && s.length() > 0)
            return Character.isDigit(s.charAt(0)) || (s.charAt(0) == '-' && isStartCharacter(i + 1) &&
                    (s.length() == 1 || Character.isDigit(s.charAt(1))));
        return false;
    }

     boolean isStartCharacter(int i) {
        String s = getRecent(i);
        if (s != null && s.length() > 0) {
            char c = s.charAt(0);
            if (s.length() > 1 && c == '-')
                c = s.charAt(1);
            if ( c == '/' || c == '*' || c == '-' || c == '+' || c == '^')
                return true;
        }
        if (s.equals(""))
            return true;
        return false;
    }
}
