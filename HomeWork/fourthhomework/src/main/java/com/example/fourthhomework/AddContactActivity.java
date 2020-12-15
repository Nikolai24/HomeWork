package com.example.fourthhomework;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddContactActivity extends AppCompatActivity {
    String[] array = {"", "", "phone_number"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        EditText contact = findViewById(R.id.edit_contact);
        switch(view.getId()) {
            case R.id.phone_number:
                if (checked){
                    contact.setHint("Phone number");
                    array[2] = "phone_number";
                }
                break;
            case R.id.email:
                if (checked){
                    contact.setHint("Email");
                    array[2] = "email";
                }
                break;
        }
    }

    public void onCancelClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onButtonClick(View v) {
        EditText editName = findViewById(R.id.edit_name);
        array[0] = editName.getText().toString();
        EditText editContact = findViewById(R.id.edit_contact);
        array[1] = editContact.getText().toString();
        Intent data = new Intent();
        data.putExtra(MainActivity.ACCESS_MESSAGE, array);
        setResult(RESULT_OK, data);
        finish();
    }
}