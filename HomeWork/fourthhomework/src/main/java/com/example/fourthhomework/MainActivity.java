package com.example.fourthhomework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    static final String ACCESS_MESSAGE="ACCESS_MESSAGE";
    private static  final int REQUEST_ACCESS_TYPE=1;
    private final List<Item> items = new ArrayList<>();
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitialData();
        adapter = new DataAdapter(this, this.items);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            String name = arguments.getString("name");
            String contact = arguments.getString("contact");
            int position = arguments.getInt("position");
            String operation = arguments.getString("operation");
            if (operation.length() == 4) {
                items.get(position).setName(name);
                items.get(position).setContact(contact);
                adapter.notifyItemChanged(position);
            }
            else{
                items.remove(position);
                adapter.notifyItemRemoved(position);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setInitialData(){
        items.add(new Item("Aaron Bennet", "+375333333333", R.drawable.phone));
        items.add(new Item("Alex Blackwell", "ex@ex.com", R.drawable.email));
        items.add(new Item("Alicia Malcolm", "ex@ex.com", R.drawable.email));
        items.add(new Item("Amelia Earhart", "+375333333333", R.drawable.phone));
        items.add(new Item("Antonio Banderas", "+375333333333", R.drawable.phone));
        items.add(new Item("Bailey Richards", "ex@ex.com", R.drawable.email));
        items.add(new Item("Bob Cobb", "+375333333333", R.drawable.phone));
        items.add(new Item("Brian Eno", "ex@ex.com", R.drawable.email));
        items.add(new Item("Brooke Wilson", "ex@ex.com", R.drawable.email));
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_ACCESS_TYPE){
            if(resultCode==RESULT_OK){
                String[] array = data.getStringArrayExtra(ACCESS_MESSAGE);
                String s = array[2];
                if (s.length() == 12) {
                    this.items.add(new Item(array[0], array[1], R.drawable.phone));
                    adapter.notifyItemInserted(this.items.size() - 1);
                }
                else{
                    this.items.add(new Item(array[0], array[1], R.drawable.email));
                    adapter.notifyItemInserted(this.items.size() - 1);
                }
            }
            else{
                Toast toast = Toast.makeText(this, "Контакт не добавлен",Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
