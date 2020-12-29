package com.example.secondhomework;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Arrays;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            TextView randomArray = (TextView) findViewById(R.id.randomArray);
            int[] array = arguments.getIntArray("array");
            String intArrayString = Arrays.toString(array);
            randomArray.setText("Сгенерированный набор чисел равен " + intArrayString);
        }
    }
    public void onCancelClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
    public void onButton1Click(View v) {
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            int[] array = arguments.getIntArray("array");
            int sum = 0;
            for (int value : array) {
                sum += value;
            }
            double result = sum * 1.0 / array.length;
            sendMessage("Среднее арифметическое равно " + result );
        }
    }
    public void onButton2Click(View v) {
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            int[] array = arguments.getIntArray("array");
            int sum = 0;
            for (int value : array) {
                sum += value;
            }
            sendMessage("Сумма всех чисел равна " + sum);
        }
    }
    public void onButton3Click(View v) {
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            int[] array = arguments.getIntArray("array");
            int p = array.length / 2;
            int sum = 0;
            for (int i = 0; i < p; i++){
                sum += array[i];
            }
            int raz = 0;
            for (int i = p; i < array.length; i++){
                raz -= array[i];
            }
            double result = sum * 1.0 / raz;
            sendMessage("Результат равен " + result);
        }
    }
    private void sendMessage(String message){
        Intent data = new Intent();
        data.putExtra(MainActivity.ACCESS_MESSAGE, message);
        setResult(RESULT_OK, data);
        finish();
    }
}