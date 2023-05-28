package com.example.firebaseinit;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StudentListingActivity extends AppCompatActivity {

    RecyclerView jrvstudent;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ClsStudent> arrayListStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_listing);
        // Hide the title bar by default, asociate objects
        // Java with xmlw objects and initialize the ArrayList

        getSupportActionBar().hide();
        jrvstudent = findViewById(R.id.rvstudent);
        arrayListStudents = new ArrayList<>();
        //Manejador de errores
        jrvstudent.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        jrvstudent.setHasFixedSize(true);
        LoadData();


    }
    private void LoadData(){
        db.collection("Students")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                ClsStudent objstudents = new ClsStudent();
                                objstudents.setId(document.getString("Id"));
                                objstudents.setFullName(document.getString("FullName"));
                                objstudents.setMajor(document.getString("Major"));
                                objstudents.setSemester(document.getString("Semester"));
                                objstudents.setCheckBox(document.getString("CheckBox"));
                                arrayListStudents.add(objstudents);
                            }
                            AdapterStudent addstudent = new AdapterStudent(arrayListStudents);
                            jrvstudent.setAdapter(addstudent);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void Back(View view){
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }

};
