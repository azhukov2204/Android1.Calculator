package ru.androidlearning.calculator;

public enum Actions {
    PLUS("+"),
    MINUS("-"),
    MULTIPLE("×"),
    DIVIDE("÷"),
    EMPTY("");
    private final String action;

    Actions(String action) {
        this.action = action;
    }

    public String getActionChar() {
        return action;
    }
}
