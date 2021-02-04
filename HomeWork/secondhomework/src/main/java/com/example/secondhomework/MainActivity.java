package com.example.secondhomework;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static final String AGE_KEY = "AGE";
    static final String ACCESS_MESSAGE="ACCESS_MESSAGE";
    private static  final int REQUEST_ACCESS_TYPE=1;

    TextView textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
    }
    public void onClick(View view) {
        int n = 5 + (int) ( Math.random() * 20 );
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int[] array = new int[n];
        int randomNumber;
        for (int i = 0; i < n; i++){
            randomNumber = (int) ( Math.random() * 100 );
            if (!hashSet.contains(randomNumber)) {
                array[i] = randomNumber;
                hashSet.add(randomNumber);
            } else {
                i--;
            }
        }
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("array", array);
        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_ACCESS_TYPE){
            if(resultCode==RESULT_OK){
                String accessMessage = data.getStringExtra(ACCESS_MESSAGE);
                textView.setText(accessMessage);
            }
            else{
                textView.setText("Ошибка доступа");
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}