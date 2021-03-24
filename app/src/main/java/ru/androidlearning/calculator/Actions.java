package ru.androidlearning.calculator;

public enum Actions {
    PLUS("+"),
    MINUS("-"),
    MULTIPLE("×"),
    DIVIDE("÷"),
    PERCENTS("%");
    private final String action;

    Actions(String action) {
        this.action = action;
    }

    public String getActionChar() {
        return action;
    }
}
