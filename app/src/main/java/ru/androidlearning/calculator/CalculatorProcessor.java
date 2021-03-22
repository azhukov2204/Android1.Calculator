package ru.androidlearning.calculator;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class CalculatorProcessor implements Parcelable {
    private String mainDisplayString;
    private String historyDisplay1String;
    private String historyDisplay2String;
    private String historyDisplay3String;
    private boolean isPointPressed;

    public CalculatorProcessor() {
        mainDisplayString = "";
        historyDisplay1String = "";
        historyDisplay2String = "";
        historyDisplay3String = "";
        isPointPressed = false;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public CalculatorProcessor(Parcel in) {
        mainDisplayString = in.readString();
        historyDisplay1String = in.readString();
        historyDisplay2String = in.readString();
        historyDisplay3String = in.readString();
        isPointPressed = in.readBoolean();
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
        return mainDisplayString;
    }

    public String clickOnBackspace() {
        if (!mainDisplayString.equals("")) {
            if ( mainDisplayString.toCharArray()[mainDisplayString.length()-1] =='.') {
                isPointPressed = false;
            }
            mainDisplayString = mainDisplayString.substring(0, mainDisplayString.length()-1);
        }
        return  mainDisplayString;
    }

    public String clickOnPoint() {
        if (!isPointPressed) {
            isPointPressed = true;
            if (mainDisplayString.isEmpty()) {
                mainDisplayString = "0";
            }
            mainDisplayString = String.format("%s%s", mainDisplayString, ".");
        }
        return mainDisplayString;
    }


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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mainDisplayString);
        dest.writeString(historyDisplay1String);
        dest.writeString(historyDisplay2String);
        dest.writeString(historyDisplay3String);
        dest.writeBoolean(isPointPressed);

    }
}
