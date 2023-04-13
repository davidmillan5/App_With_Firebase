package com.example.firebaseinit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {

    EditText jetId,jetFullName, jetMajor, jetSemester;
    CheckBox jcCheckBox;
    String id, fullName, major, semester;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        getSupportActionBar().hide();
        jetId = findViewById(R.id.etId);
        jetFullName = findViewById(R.id.etFullName);
        jetMajor = findViewById(R.id.etMajor);
        jetSemester = findViewById(R.id.etSemester);
        jcCheckBox = findViewById(R.id.checkBox);


    }

    public void Add(View view){
        id=jetId.getText().toString();
        fullName = jetFullName.getText().toString();
        major = jetMajor.getText().toString();
        semester = jetSemester.getText().toString();

        if(id.isEmpty() || fullName.isEmpty() || major.isEmpty() || semester.isEmpty()){
            Toast.makeText(this, "All of the fields are required", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }


    }


}