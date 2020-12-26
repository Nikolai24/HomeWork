package com.example.fourthhomework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    private List<Item> items = new ArrayList<>();
    private DataAdapter adapter;
    private  DataAdapter.OnItemClickListener listener = this::startEditActivity;
    private  RecyclerView recyclerView;

    private void startEditActivity(Item item, int position) {
        Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
        intent.putExtra("Item", item);
        intent.putExtra("position", position);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitialData();
        adapter = new DataAdapter(this.items, listener);
        recyclerView = findViewById(R.id.recyclerview);
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
        items.add(new Item("Aaron Bennet", "+375333333333", "phone"));
        items.add(new Item("Alex Blackwell", "ex@ex.com", "email"));
        items.add(new Item("Alicia Malcolm", "ex@ex.com", "email"));
        items.add(new Item("Amelia Earhart", "+375333333333", "phone"));
        items.add(new Item("Antonio Banderas", "+375333333333", "phone"));
        items.add(new Item("Bailey Richards", "ex@ex.com", "email"));
        items.add(new Item("Bob Cobb", "+375333333333", "phone"));
        items.add(new Item("Brian Eno", "ex@ex.com","email"));
        items.add(new Item("Brooke Wilson", "ex@ex.com", "email"));
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            items.add(data.getParcelableExtra("Item"));
            adapter.setItems(items);
        } else if(requestCode==2 && resultCode == RESULT_OK){
            Item item = data.getParcelableExtra("Item");
            int position = data.getIntExtra("position", 0);
            if (item.getImage().equals("delete")) {
                items.remove(position);
            }
            else{
                items.set(position, item);
            }
            adapter.setItems(items);
        } else{
            Toast toast = Toast.makeText(this, "Список контактов не изменился",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("items", (ArrayList<? extends Parcelable>) items);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        items = savedInstanceState.getParcelableArrayList("items");
        adapter = new DataAdapter(items, listener);
        recyclerView.setAdapter(adapter);
    }
}