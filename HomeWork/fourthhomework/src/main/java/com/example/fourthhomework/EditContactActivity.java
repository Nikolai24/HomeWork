package com.example.fourthhomework;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EditContactActivity extends AppCompatActivity {
    String name = "Name not set";
    String contact = "Contact not set";
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        EditText nameView = findViewById(R.id.edit_name);
        EditText contactView = findViewById(R.id.edit_contact);
        TextView textView = findViewById(R.id.text);
        if(getIntent().hasExtra("name") && getIntent().hasExtra("contact") && getIntent().hasExtra("position")){
            name = getIntent().getStringExtra("name");
            contact = getIntent().getStringExtra("contact");
            position = getIntent().getIntExtra("position", 0);
        }
        nameView.setText(name);
        contactView.setText(contact);
        textView.setText(String.valueOf(position+1));
    }

    String[] array = {"name", "contact", "operation", "position"};

    public void onButtonClick(View view) {
        EditText editName = findViewById(R.id.edit_name);
        array[0] = editName.getText().toString();
        EditText editContact = findViewById(R.id.edit_contact);
        array[1] = editContact.getText().toString();
        array[2] = "edit";
        array[3] = String.valueOf(position);
        Intent data = new Intent();
        data.putExtra(MainActivity.ACCESS_MESSAGE, array);
        setResult(RESULT_OK, data);
        finish();
    }

    public void delete(View view) {
        EditText editName = findViewById(R.id.edit_name);
        array[0] = editName.getText().toString();
        EditText editContact = findViewById(R.id.edit_contact);
        array[1] = editContact.getText().toString();
        array[2] = "delete";
        array[3] = String.valueOf(position);
        Intent data = new Intent();
        data.putExtra(MainActivity.ACCESS_MESSAGE, array);
        setResult(RESULT_OK, data);
        finish();
    }
}