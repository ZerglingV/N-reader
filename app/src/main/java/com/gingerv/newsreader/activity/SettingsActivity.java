package com.gingerv.newsreader.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.gingerv.newsreader.R;

public class SettingsActivity extends AppCompatActivity {
    private RadioGroup languageGroup;
    private SeekBar fontSizeBar;
    private SwitchCompat darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        loadSettings();

        // back button
        ImageButton buttonBack = findViewById(R.id.button_settings_back);
        buttonBack.setOnClickListener(v -> this.finish());

        TextView exampleTextView = findViewById(R.id.example_text_instance);
        exampleTextView.setTextSize(getResources().getInteger(R.integer.MIN_FONT_SIZE) + fontSizeBar.getProgress() * 2);

        // fontSizeBar's value changed
        fontSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                exampleTextView.setTextSize(getResources().getInteger(R.integer.MIN_FONT_SIZE) + progress * 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // save button
        Button buttonSave = findViewById(R.id.button_settings_save);
        buttonSave.setOnClickListener(v -> saveSettings());
    }

    private void loadSettings() {
        // settings items
        languageGroup = findViewById(R.id.radioGroup_language);
        fontSizeBar = findViewById(R.id.seekBar_font_size);
        darkModeSwitch = findViewById(R.id.switch_dark_mode);
        // loading
        SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
        languageGroup.check(sharedPreferences.getInt("language", R.id.radioButton_chinese));
        fontSizeBar.setProgress(sharedPreferences.getInt("fontSize", 1));
        darkModeSwitch.setChecked(sharedPreferences.getBoolean("darkMode", false));
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
        editor.putInt("language", languageGroup.getCheckedRadioButtonId());
        editor.putInt("fontSize", fontSizeBar.getProgress());
        editor.putBoolean("darkMode", darkModeSwitch.isChecked());
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}