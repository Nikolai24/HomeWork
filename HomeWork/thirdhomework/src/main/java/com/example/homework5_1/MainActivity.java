package com.example.homework5_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view) {
        Intent intent = new Intent(this, TableLayout1.class);
        startActivity(intent);
    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, LinearLayout1.class);
        startActivity(intent);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(this, LinearLayout2.class);
        startActivity(intent);
    }

    public void onClick4(View view) {
        Intent intent = new Intent(this, ConstraintLayout2.class);
        startActivity(intent);
    }

    public void onClick5(View view) {
        Intent intent = new Intent(this, RelativeLayout2.class);
        startActivity(intent);
    }

    public void onClick6(View view) {
        Intent intent = new Intent(this, LinearLayout3.class);
        startActivity(intent);
    }

    public void onClick7(View view) {
        Intent intent = new Intent(this, ConstraintLayout3.class);
        startActivity(intent);
    }
    public void onClick8(View view) {
        Intent intent = new Intent(this, RelativeLayout3.class);
        startActivity(intent);
    }

    public void onClick9(View view) {
        Intent intent = new Intent(this, LinearLayout4.class);
        startActivity(intent);
    }

    public void onClick10(View view) {
        Intent intent = new Intent(this, ConstraintLayout4.class);
        startActivity(intent);
    }
    public void onClick11(View view) {
        Intent intent = new Intent(this, RelativeLayout4.class);
        startActivity(intent);
    }

    public void onClick12(View view) {
        Intent intent = new Intent(this, ConstraintLayout5.class);
        startActivity(intent);
    }
}