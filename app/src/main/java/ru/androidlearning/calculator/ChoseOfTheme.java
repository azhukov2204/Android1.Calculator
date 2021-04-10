package ru.androidlearning.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.os.Bundle;
import android.widget.RadioButton;

public class ChoseOfTheme extends AppCompatActivity {

    private RadioButton dayThemeRButton, nightThemeRButton, defaultThemeRButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_of_theme);

        findElements();
        initListeners();
        setActiveRButton();
    }

    private void findElements() {
        dayThemeRButton = findViewById(R.id.dayThemeRButton);
        nightThemeRButton = findViewById(R.id.nightThemeRButton);
        defaultThemeRButton = findViewById(R.id.defaultThemeRButton);
    }

    private void initListeners() {
        dayThemeRButton.setOnClickListener(v -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO));
        nightThemeRButton.setOnClickListener(v -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES));
        defaultThemeRButton.setOnClickListener(v -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM));
        findViewById(R.id.comeBackButton).setOnClickListener(v -> finish());
    }

    private void setActiveRButton() {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                nightThemeRButton.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                dayThemeRButton.setChecked(true);
                break;
            default:
                defaultThemeRButton.setChecked(true);
                break;
        }
    }

}