package ru.androidlearning.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mainDisplay;
    TextView historyDisplay1;
    TextView historyDisplay2;
    TextView historyDisplay3;

    CalculatorProcessor calculatorProcessor = new CalculatorProcessor();
    private final static String keyCalculatorProcessor = "CalculatorProcessor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findDisplays();
        initListeners();
        //Log.d("Orientation", String.valueOf(getResources().getConfiguration().orientation));
    }


    protected void findDisplays() {
        mainDisplay = findViewById(R.id.mainDisplay);
        historyDisplay1 = findViewById(R.id.historyDisplay1);
        historyDisplay2 = findViewById(R.id.historyDisplay2);
        historyDisplay3 = findViewById(R.id.historyDisplay3);
    }

    private void initListeners() {
        //set listeners on number buttons:
        int[] numberButtonsIds = new int[]{R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
        for (int i = 0; i < numberButtonsIds.length; i++) {
            int finalI = i;
            findViewById(numberButtonsIds[finalI]).setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnNumber(finalI)));
        }


        findViewById(R.id.button_point).setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnPoint()));

        findViewById(R.id.button_plus).setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnAction(Actions.PLUS)));
        findViewById(R.id.button_minus).setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnAction(Actions.MINUS)));
        findViewById(R.id.button_multiple).setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnAction(Actions.MULTIPLE)));
        findViewById(R.id.button_divide).setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnAction(Actions.DIVIDE)));

        findViewById(R.id.button_ac).setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnAC()));
        findViewById(R.id.button_backspace).setOnClickListener(v -> mainDisplay.setText(calculatorProcessor.clickOnBackspace()));

        findViewById(R.id.button_equals).setOnClickListener(v -> {
            mainDisplay.setText(calculatorProcessor.clickOnEquals(false));
            historyDisplay1.setText(calculatorProcessor.getHistoryDisplay1String());
            historyDisplay2.setText(calculatorProcessor.getHistoryDisplay2String());
            historyDisplay3.setText(calculatorProcessor.getHistoryDisplay3String());
        });
        findViewById(R.id.button_percents).setOnClickListener(v -> {
            mainDisplay.setText(calculatorProcessor.clickOnEquals(true));
            historyDisplay1.setText(calculatorProcessor.getHistoryDisplay1String());
            historyDisplay2.setText(calculatorProcessor.getHistoryDisplay2String());
            historyDisplay3.setText(calculatorProcessor.getHistoryDisplay3String());
        });
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