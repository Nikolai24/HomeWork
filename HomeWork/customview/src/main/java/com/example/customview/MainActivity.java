package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        customView = findViewById(R.id.customView);

        customView.setListener((x, y) -> textView.setText("Нажаты координаты [" + x+ ";" + y + "]"));
    }
}