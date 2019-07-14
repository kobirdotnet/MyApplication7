package com.example.myapplication;

import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
     Button add_data;
     EditText add_name;

     ArrayList<String>listItem;
     ArrayAdapter adapter;

     ListView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        add_data = findViewById(R.id.add_data);
        add_name = findViewById(R.id.add_name);

        userlist = findViewById(R.id.user_list);

        viewData();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = userlist.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, ""+text,Toast.LENGTH_LONG).show();
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_name.getText().toString();
                if (!name.equals("") && db.insertData(name)){
                    Toast.makeText(MainActivity.this,"Data added",Toast.LENGTH_LONG).show();
                    add_name.setText("");
                    listItem.clear();
                    viewData();
                }else {
                    Toast.makeText(MainActivity.this,"Data not added",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void viewData() {

        Cursor cursor = db.viewData();

        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data to show",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listItem);
            userlist.setAdapter(adapter);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu,menu);
//
//        MenuItem searchItem = menu.findItem(R.id.item_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<String> userlist = new ArrayList<>();
//
//                for (String user : listItem){
//                    if (user.toLowerCase().contains(newText.toLowerCase())){
//                       userlist.add(user);
//                    }
//                }
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
//                        android.R.layout.simple_list_item_1, userlist);
//                userlist.setAdapter(adapter);
//
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
}
