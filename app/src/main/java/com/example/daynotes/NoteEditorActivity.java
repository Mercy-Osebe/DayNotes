package com.example.daynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent  intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            editText.append("nunya");
        });
        Intent intent=getIntent();
        noteId=intent.getIntExtra("noteId",-1);
        if(noteId != -1 ){
            editText.setText(MainActivity.notes.get(noteId));

        }
        else{//no shared preferences here we do not want to save an empty item
            MainActivity.notes.add("");
            noteId=MainActivity.notes.size()-1;

            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences2=getApplicationContext().getSharedPreferences("com.example.daynotes", Context.MODE_PRIVATE);
                HashSet<String> set1=new HashSet<>(MainActivity.notes);//generate set from arraylist

                sharedPreferences2.edit().putStringSet("notes1",set1).apply();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}