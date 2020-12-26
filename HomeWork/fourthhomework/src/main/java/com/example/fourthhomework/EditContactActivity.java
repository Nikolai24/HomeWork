package com.example.fourthhomework;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditContactActivity extends AppCompatActivity {
    private int position;
    private EditText editName;
    private EditText editContact;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        editName = findViewById(R.id.edit_name);
        editContact = findViewById(R.id.edit_contact);
        TextView textView = findViewById(R.id.text);
        if(getIntent().hasExtra("Item") && getIntent().hasExtra("position")){
            item = getIntent().getParcelableExtra("Item");
            position = getIntent().getIntExtra("position", 0);
            editName.setText(item.getName());
            editContact.setText(item.getContact());
            textView.setText(String.valueOf(position+1));
        }
    }

    public void onButtonClick(View view) {
        item.setName(editName.getText().toString());
        item.setContact(editContact.getText().toString());
        sendResult(item, position);
    }

    public void delete(View view) {
        item.setImage("delete");
        sendResult(item, position);
    }

    private void sendResult(Item item, int position){
        Intent data = new Intent();
        data.putExtra("Item", item);
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}