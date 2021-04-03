package ru.androidlearning.calculator;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RequiresApi;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;


public class CalculatorProcessor implements Parcelable {
    private final static String CHECK_ON_NUMBER_REGEX = "[-0-9.]+$";
    private String mainDisplayString;
    private String historyDisplay1String;
    private String historyDisplay2String;
    private String historyDisplay3String;
    private String firstNumberStr;
    private String secondNumberStr;
    private boolean isFirstNumberPointPressed;
    private boolean isSecondNumberPointPressed;
    private boolean isFirstNumberEntered;
    private Actions currentAction;

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private final DecimalFormat decimalFormat = new DecimalFormat("#.####", symbols);

    public CalculatorProcessor() {
        mainDisplayString = "";
        historyDisplay1String = "";
        historyDisplay2String = "";
        historyDisplay3String = "";
        firstNumberStr = "";
        secondNumberStr = "";
        isFirstNumberPointPressed = false;
        isSecondNumberPointPressed = false;
        currentAction = Actions.EMPTY;
        isFirstNumberEntered = false;
    }


    public CalculatorProcessor(Parcel in) {
        mainDisplayString = in.readString();
        historyDisplay1String = in.readString();
        historyDisplay2String = in.readString();
        historyDisplay3String = in.readString();
        firstNumberStr = in.readString();
        secondNumberStr = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isFirstNumberPointPressed = in.readBoolean();
            isSecondNumberPointPressed = in.readBoolean();
            isFirstNumberEntered = in.readBoolean();
        }

        currentAction = Actions.valueOf(in.readString());
    }

    public static final Creator<CalculatorProcessor> CREATOR = new Creator<CalculatorProcessor>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
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
        dest.writeString(firstNumberStr);
        dest.writeString(secondNumberStr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(isFirstNumberPointPressed);
            dest.writeBoolean(isSecondNumberPointPressed);
            dest.writeBoolean(isFirstNumberEntered);
        }

        dest.writeString(currentAction.getActionChar());
    }

    @SuppressLint("DefaultLocale")
    public String clickOnNumber(int number) {
        if (!isFirstNumberEntered) {
            if (firstNumberStr.equals("0")) {
                firstNumberStr = "";
            }
            firstNumberStr = String.format("%s%s", firstNumberStr, String.format("%d", number));
        } else {
            if (secondNumberStr.equals("0")) {
                secondNumberStr = "";
            }
            secondNumberStr = String.format("%s%s", secondNumberStr, String.format("%d", number));
        }
        mainDisplayString = getFormattedResult();
        return mainDisplayString;
    }

    public String clickOnAC() {
        mainDisplayString = "";
        firstNumberStr = "";
        secondNumberStr = "";
        isFirstNumberPointPressed = false;
        isSecondNumberPointPressed = false;
        isFirstNumberEntered = false;
        currentAction = Actions.EMPTY;
        return mainDisplayString;
    }

    public String clickOnBackspace() {
        if (isFirstNumberEntered) {
            if (!secondNumberStr.isEmpty()) {
                if (secondNumberStr.endsWith(".")) {
                    isSecondNumberPointPressed = false;
                }
                secondNumberStr = secondNumberStr.substring(0, secondNumberStr.length() - 1);
            } else {
                currentAction = Actions.EMPTY;
                isFirstNumberEntered = false;
            }
        } else {
            if (!firstNumberStr.isEmpty()) {
                if (firstNumberStr.endsWith(".")) {
                    isFirstNumberPointPressed = false;
                }
                firstNumberStr = firstNumberStr.substring(0, firstNumberStr.length() - 1);
            }
        }

        mainDisplayString = getFormattedResult();
        return mainDisplayString;
    }

    public String clickOnPoint() {
        if (isFirstNumberEntered) {
            if (!isSecondNumberPointPressed) {
                isSecondNumberPointPressed = true;
                if (secondNumberStr.isEmpty()) {
                    secondNumberStr += "0";
                }
                secondNumberStr = String.format("%s%s", secondNumberStr, ".");
            }
        } else {
            if (!isFirstNumberPointPressed) {
                isFirstNumberPointPressed = true;
                if (firstNumberStr.isEmpty() || firstNumberStr.equals("-")) {
                    firstNumberStr += "0";
                }
                firstNumberStr = String.format("%s%s", firstNumberStr, ".");
            }
        }
        mainDisplayString = getFormattedResult();
        return mainDisplayString;
    }

    public String clickOnAction(Actions action) {
        if (firstNumberStr.isEmpty()) {
            if (action == Actions.MINUS) {
                firstNumberStr = action.getActionChar();
            }
            currentAction = Actions.EMPTY;
        } else if (firstNumberStr.equals("-")) {
            firstNumberStr = "";
        } else {
            isFirstNumberEntered = true;
            if (currentAction == Actions.EMPTY) {
                currentAction = action;
            }
        }
        mainDisplayString = getFormattedResult();
        return mainDisplayString;
    }


    public String clickOnEquals(boolean isPercents) {
        if (isFirstNumberEntered && currentAction != Actions.EMPTY && !secondNumberStr.isEmpty()) {
            float result;
            //System.out.println(firstNumberStr);
            float firstNumber = Float.parseFloat(firstNumberStr);
            float secondNumber = Float.parseFloat(secondNumberStr);

            if (isPercents) {
                mainDisplayString += "%";
                switch (currentAction) {
                    case PLUS:
                        result = firstNumber * (1 + secondNumber / 100);
                        break;
                    case MINUS:
                        result = firstNumber * (1 - secondNumber / 100);
                        break;
                    case MULTIPLE:
                        result = (firstNumber * secondNumber) / 100;
                        break;
                    case DIVIDE:
                        result = (firstNumber / secondNumber) * 100;
                        break;
                    default:
                        result = 0;
                }
            } else {
                switch (currentAction) {
                    case PLUS:
                        result = firstNumber + secondNumber;
                        break;
                    case MINUS:
                        result = firstNumber - secondNumber;
                        break;
                    case MULTIPLE:
                        result = firstNumber * secondNumber;
                        break;
                    case DIVIDE:
                        result = firstNumber / secondNumber;
                        break;
                    default:
                        result = 0;
                }
            }

            secondNumberStr = "";
            firstNumberStr = decimalFormat.format(result);
            currentAction = Actions.EMPTY;
            isFirstNumberEntered = false;
            isFirstNumberPointPressed = firstNumberStr.contains(".");
            isSecondNumberPointPressed = false;
            //System.out.println(firstNumberStr);
            //System.out.println(isFirstNumberPointPressed);
            historyDisplay3String = historyDisplay2String;
            historyDisplay2String = historyDisplay1String;
            historyDisplay1String = mainDisplayString;

        }
        mainDisplayString = getFormattedResult();
        if (!Pattern.matches(CHECK_ON_NUMBER_REGEX, firstNumberStr)) { //fix divide on zero
            firstNumberStr = "";
        }
        return mainDisplayString;
    }

    private String getFormattedResult() {
        return String.format("%s%s%s", firstNumberStr, currentAction.getActionChar(), secondNumberStr);
    }


}
