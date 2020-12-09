package com.example.fourthhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditContactActivity extends AppCompatActivity {
    String name = "Username not set";
    String contact = "Username not set";
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

    public void onButtonClick(View view) {
        EditText editName = findViewById(R.id.edit_name);
        name = editName.getText().toString();
        EditText editContact = findViewById(R.id.edit_contact);
        contact = editContact.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("contact", contact);
        intent.putExtra("position", position);
        intent.putExtra("operation", "edit");
        startActivity(intent);
    }
    public void delete(View v) {
        name = "delete";
        contact = "delete";
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("contact", contact);
        intent.putExtra("position", position);
        intent.putExtra("operation", "delete");
        startActivity(intent);
    }
}