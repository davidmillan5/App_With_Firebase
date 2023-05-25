package com.example.firebaseinit;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentActivity extends AppCompatActivity {

    EditText jetId,jetFullName, jetMajor, jetSemester;
    CheckBox jcCheckBox;
    String id, fullName, major, semester, id_document;

    FirebaseFirestore db = FirebaseFirestore.getInstance();



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
        id_document = "";


    }
    // Add method begins here
    public void Add(View view){
        id=jetId.getText().toString();
        fullName = jetFullName.getText().toString();
        major = jetMajor.getText().toString();
        semester = jetSemester.getText().toString();

        if(id.isEmpty() || fullName.isEmpty() || major.isEmpty() || semester.isEmpty()){
            Toast.makeText(this, "All of the fields are required", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }else{
            // Create e a new user with a first and last name
            Map<String, Object> student = new HashMap<>();
            student.put("Id", id);
            student.put("FullName", fullName);
            student.put("Major", major);
            student.put("Semester", semester);
            student.put("CheckBox", "Yes");

            // Add a new document with a generated ID
            db.collection("Students")
                    .add(student)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(StudentActivity.this, "Created Document", Toast.LENGTH_SHORT).show();
                            Clear_fields();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }


    }

// Search Method Begins Here
    private void Search_id(){
        id=jetId.getText().toString();
        if(id.isEmpty()){
            Toast.makeText(this, "Id required to make a search", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }else{
            db.collection("Students")
                    .whereEqualTo("Id",id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    id_document = document.getId();
                                    jetFullName.setText(document.getString("FullName"));
                                    jetMajor.setText(document.getString("Major"));
                                    jetSemester.setText(document.getString("Semester"));
                                    if(document.getString("CheckBox").equals("Yes")){
                                        jcCheckBox.setChecked(true);
                                    }else{
                                        jcCheckBox.setChecked(false);
                                    }

                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(StudentActivity.this, "Id Founded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //Public Search Method
    public void Search(View view){
        Search_id();
    }


    // General Consult Method Begins

    public void General_Consult(View view){
        Intent generalIntent = new Intent(this, StudentListingActivity.class);
        startActivity(generalIntent);
    }






    // General Consult Method Ends


    // Clear fields method
    private void Clear_fields(){
        jetId.setText("");
        jetSemester.setText("");
        jetMajor.setText("");
        jetFullName.setText("");
        jcCheckBox.setChecked(false);
        id_document = "";
        jetId.requestFocus();
    }

    public void Clear(View view){
        Clear_fields();
    }


    // Edit Method
    public void Edit(View view) {
        if (!id_document.equals("")) {
            id = jetId.getText().toString();
            fullName = jetFullName.getText().toString();
            major = jetMajor.getText().toString();
            semester = jetSemester.getText().toString();

            if (id.isEmpty() || fullName.isEmpty() || major.isEmpty() || semester.isEmpty()) {
                Toast.makeText(this, "All of the fields are required", Toast.LENGTH_SHORT).show();
                jetId.requestFocus();
            } else {
                // Create e a new user with a first and last name
                Map<String, Object> student = new HashMap<>();
                student.put("Id", id);
                student.put("FullName", fullName);
                student.put("Major", major);
                student.put("Semester", semester);
                if(jcCheckBox.isChecked()){
                    student.put("CheckBox", "Yes");
                }else{
                    student.put("CheckBox","No");
                }

                db.collection("Students").document(id_document)
                        .set(student)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(StudentActivity.this, "Student correctly updated.....", Toast.LENGTH_SHORT).show();
                                Clear_fields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(StudentActivity.this, "Student could not be updated....", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else{
            Toast.makeText(this, "Random comment", Toast.LENGTH_SHORT).show();
        }
    }

    // Back

    public void Back(View view){
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }

    // Delete
    public void Delete(View view){
        if(!id_document.equals("")){
            db.collection("Students").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Clear_fields();
                            Toast.makeText(StudentActivity.this,"Student Delete sucesfully...",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentActivity.this,"Error while deleting...",Toast.LENGTH_SHORT).show();
                        }
                    });

        }else{
            Toast.makeText(this, "You Should check before deleting", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }
    }

    public void Anulate(View view){
        if(!id_document.equals("")){
            id = jetId.getText().toString();
            fullName = jetFullName.getText().toString();
            major = jetMajor.getText().toString();
            semester = jetSemester.getText().toString();

            if (id.isEmpty() || fullName.isEmpty() || major.isEmpty() || semester.isEmpty()) {
                Toast.makeText(this, "All of the fields are required", Toast.LENGTH_SHORT).show();
                jetId.requestFocus();
            } else {
                // Create e a new user with a first and last name
                Map<String, Object> student = new HashMap<>();
                student.put("Id", id);
                student.put("FullName", fullName);
                student.put("Major", major);
                student.put("Semester", semester);
                student.put("CheckBox", "No");

                db.collection("Students").document(id)
                        .set(student)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(StudentActivity.this, "Student correctly updated.....", Toast.LENGTH_SHORT).show();
                                Clear_fields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(StudentActivity.this, "Student could not be updated....", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else{
            Toast.makeText(this, "First you got to search...", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }
    }


}
