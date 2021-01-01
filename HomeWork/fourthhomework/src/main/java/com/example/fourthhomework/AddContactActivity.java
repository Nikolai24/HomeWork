package com.example.fourthhomework;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddContactActivity extends AppCompatActivity {
    private String image = "phone";
    private EditText editName;
    private EditText editContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        editName = findViewById(R.id.edit_name);
        editContact = findViewById(R.id.edit_contact);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.phone_number:
                if (checked){
                    editContact.setHint("Phone number");
                    image = "phone";
                }
                break;
            case R.id.email:
                if (checked){
                    editContact.setHint("Email");
                    image = "email";
                }
                break;
        }
    }

    public void onCancelClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onButtonClick(View v) {
        String name = editName.getText().toString();
        String contact = editContact.getText().toString();
        Item item = new Item(name, contact, image);
        sendResult(item);
    }

    private void sendResult(Item item){
        Intent data = new Intent();
        data.putExtra("Item", item);
        setResult(RESULT_OK, data);
        finish();
    }
}