package ru.androidlearning.calculator;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class CalculatorProcessor implements Parcelable {
    private String mainDisplayString;
    private String historyDisplay1String;
    private String historyDisplay2String;
    private String historyDisplay3String;
    private String firstNumber;
    private String secondNumber;
    private boolean isPointPressed;
    private boolean isActionButtonPressed;

    public CalculatorProcessor() {
        mainDisplayString = "";
        historyDisplay1String = "";
        historyDisplay2String = "";
        historyDisplay3String = "";
        firstNumber = "";
        secondNumber = "";
        isPointPressed = false;
        isActionButtonPressed = false;
    }


    public CalculatorProcessor(Parcel in) {
        mainDisplayString = in.readString();
        historyDisplay1String = in.readString();
        historyDisplay2String = in.readString();
        historyDisplay3String = in.readString();
        int isPointPressedInt = in.readInt();
        isPointPressed = (isPointPressedInt == 1);
    }

    public static final Creator<CalculatorProcessor> CREATOR = new Creator<CalculatorProcessor>() {
        @Override
        public CalculatorProcessor createFromParcel(Parcel source) {
            return new CalculatorProcessor(source);
        }

        @Override
        public CalculatorProcessor[] newArray(int size) {
            return new CalculatorProcessor[size];
        }
    };

    public String getMainDisplayString() {
        return mainDisplayString;
    }

    public String getHistoryDisplay1String() {
        return historyDisplay1String;
    }

    public String getHistoryDisplay2String() {
        return historyDisplay2String;
    }

    public String getHistoryDisplay3String() {
        return historyDisplay3String;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mainDisplayString);
        dest.writeString(historyDisplay1String);
        dest.writeString(historyDisplay2String);
        dest.writeString(historyDisplay3String);
        dest.writeInt(isPointPressed ? 1 : 0); //writeBoolean не совместим со старыми API, поэтому ради совместимости делаю через этот метод

    }

    @SuppressLint("DefaultLocale")
    public String clickOnNumber(int number) {
        if (mainDisplayString.equals("0")) {
            mainDisplayString = "";
        }
        mainDisplayString = String.format("%s%s", mainDisplayString, String.format("%d", number));
        return mainDisplayString;
    }

    public String clickOnAC() {
        mainDisplayString = "";
        isPointPressed = false;
        isActionButtonPressed = false;
        return mainDisplayString;
    }

    public String clickOnBackspace() {
        if (!mainDisplayString.isEmpty()) {
            if (mainDisplayString.endsWith(".")) {
                isPointPressed = false;
            } else if (checkEndOfLineContainAction()) {
                isActionButtonPressed = false;
            }
            mainDisplayString = mainDisplayString.substring(0, mainDisplayString.length() - 1);
        }
        return mainDisplayString;
    }

    public String clickOnPoint() {
        if (!isPointPressed) {
            isPointPressed = true;
            if (mainDisplayString.isEmpty() || mainDisplayString.equals("-")) {
                mainDisplayString += "0";
            }
            mainDisplayString = String.format("%s%s", mainDisplayString, ".");
        }
        return mainDisplayString;
    }

    public String clickOnAction(Actions action) {
        if (mainDisplayString.isEmpty()) {
            if (action == Actions.MINUS) {
                mainDisplayString = action.getActionChar();
            }
            isActionButtonPressed = false;
        } else {
            if (isActionButtonPressed) {
                if (checkEndOfLineContainAction()) {
                    mainDisplayString = mainDisplayString.substring(0, mainDisplayString.length() - 1) + action.getActionChar();
                }
            } else {
                if (!mainDisplayString.equals("-")) {
                    firstNumber = String.format("%s", mainDisplayString);
                    System.out.println(firstNumber);
                    mainDisplayString = String.format("%s%s", mainDisplayString, action.getActionChar());
                    isActionButtonPressed = true;
                } else {
                    if (action != Actions.MINUS) {
                        mainDisplayString = "";
                    }
                }
            }
        }
        return mainDisplayString;
    }

    private boolean checkEndOfLineContainAction() {
        return (mainDisplayString.endsWith("+") || mainDisplayString.endsWith("-") || mainDisplayString.endsWith("×") || mainDisplayString.endsWith("÷") || mainDisplayString.endsWith("%"));
    }


}
