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
    private OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(Item item, int position) {
            startEditActivity(item, position);
        }
    };

    interface OnItemClickListener {
        void onItemClick(Item item, int position);
    }

    private void startEditActivity(Item item, int position) {
        Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
        intent.putExtra("name", item.getName());
        intent.putExtra("contact", item.getContact());
        intent.putExtra("position", position);
        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitialData();
        adapter = new DataAdapter(this.items, listener);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
                String name = array[0];
                String contact = array[1];
                String action = array[2];
                if (action.length() == 12) {
                    items.add(new Item(name, contact, R.drawable.phone));
                }
                else if (action.length() == 5) {
                    items.add(new Item(name, contact, R.drawable.email));
                }
                else if (action.length() == 4) {
                    int position = Integer.parseInt(array[3]);
                    items.get(position).setName(name);
                    items.get(position).setContact(contact);
                }
                else{
                    int position = Integer.parseInt(array[3]);
                    items.remove(position);
                }
                adapter.setItems(items);
            }
            else{
                Toast toast = Toast.makeText(this, "Список контактов не изменился",Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}