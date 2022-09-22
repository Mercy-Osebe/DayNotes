package com.example.daynotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> notes =new ArrayList<>();
    static ArrayAdapter <String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.add_mote_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.add_note){
            Intent intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
                intent.putExtra("noteId",position);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("You want to delete this?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences2=getApplicationContext().getSharedPreferences("com.example.daynotes", Context.MODE_PRIVATE);
                                HashSet<String> set1=new HashSet<>(MainActivity.notes);//generate set from arraylist
                                sharedPreferences2.edit().putStringSet("notes1",set1).apply();

                            }
                        })
                        .setNegativeButton("no",null)
                        .show();
                return true;
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();


        SharedPreferences sharedPreferences2=getApplicationContext().getSharedPreferences("com.example.daynotes", Context.MODE_PRIVATE);
        HashSet<String> set1 = (HashSet<String>) sharedPreferences2.getStringSet("notes1",null);

        if(set1==null){
            notes.add("New item");
        }
        else{
            notes=new ArrayList<>(set1);
        }
        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);


    }
}