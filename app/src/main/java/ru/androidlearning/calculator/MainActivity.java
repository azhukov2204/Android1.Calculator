package ru.androidlearning.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.locks.Condition;

public class MainActivity extends AppCompatActivity {
    TextView mainDisplay;
    TextView historyDisplay1;
    TextView historyDisplay2;
    TextView historyDisplay3;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonPoint;
    Button buttonEquals;
    Button buttonPlus;
    Button buttonMinus;
    Button buttonMultiple;
    Button buttonDivide;
    Button buttonPercents;
    Button buttonAC;
    Button buttonBackspace;

    CalculatorProcessor calculatorProcessor = new CalculatorProcessor();

    private final static String keyCalculatorProcessor = "CalculatorProcessor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape);
        } else {
            setContentView(R.layout.activity_main);
        }

        findAllElements();
        initListeners();
        //Log.d("Orientation", String.valueOf(getResources().getConfiguration().orientation));

    }


    protected void findAllElements() {
        mainDisplay = findViewById(R.id.mainDisplay);
        historyDisplay1 = findViewById(R.id.historyDisplay1);
        historyDisplay2 = findViewById(R.id.historyDisplay2);
        historyDisplay3 = findViewById(R.id.historyDisplay3);
        button0 = findViewById(R.id.button_0);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        buttonPoint = findViewById(R.id.button_point);
        buttonEquals = findViewById(R.id.button_equals);
        buttonPlus = findViewById(R.id.button_plus);
        buttonMinus = findViewById(R.id.button_minus);
        buttonMultiple = findViewById(R.id.button_multiple);
        buttonDivide = findViewById(R.id.button_divide);
        buttonPercents = findViewById(R.id.button_percents);
        buttonAC = findViewById(R.id.button_ac);
        buttonBackspace = findViewById(R.id.button_backspace);
    }

    private void initListeners() {
        button0.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(0)));


        button1.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(1)));
        button2.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(2)));
        button3.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(3)));
        button4.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(4)));
        button5.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(5)));
        button6.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(6)));
        button7.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(7)));
        button8.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(8)));
        button9.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(9)));
        buttonPoint.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnPoint()));
        buttonEquals.setOnClickListener(v -> {

        });
        buttonPlus.setOnClickListener(v -> {

        });
        buttonMinus.setOnClickListener(v -> {

        });
        buttonMultiple.setOnClickListener(v -> {

        });
        buttonDivide.setOnClickListener(v -> {

        });
        buttonPercents.setOnClickListener(v -> {

        });
        buttonAC.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnAC()));
        buttonBackspace.setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnBackspace()));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(keyCalculatorProcessor, calculatorProcessor);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculatorProcessor = savedInstanceState.getParcelable(keyCalculatorProcessor);
        setDisplaysInformation();
    }

    private void setDisplaysInformation() {
        mainDisplay.setText(calculatorProcessor.getMainDisplayString());
        historyDisplay1.setText(calculatorProcessor.getHistoryDisplay1String());
        historyDisplay2.setText(calculatorProcessor.getHistoryDisplay2String());
        historyDisplay3.setText(calculatorProcessor.getHistoryDisplay3String());
    }

}