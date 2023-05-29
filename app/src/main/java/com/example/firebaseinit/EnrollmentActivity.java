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
import android.widget.TextView;
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
import java.util.UUID;

public class EnrollmentActivity extends AppCompatActivity {

    EditText jetenrollmentCode, jetStudentId, jetClassId;
    TextView jtvStudentFullName, jtvClassName;
    CheckBox jcheckBox;

    String  studentCode, enrollmentCode, studentFullName, classCode, className;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        getSupportActionBar().hide();

        jetenrollmentCode = findViewById(R.id.etenrollmentCode);
        jetStudentId = findViewById(R.id.etStudentId);
        jetClassId = findViewById(R.id.etClassId);
        jtvStudentFullName = findViewById(R.id.tvStudentFullName);
        jtvClassName = findViewById(R.id.tvClassName);
        jcheckBox = findViewById(R.id.checkBox);
        enrollmentCode = "";
    }

    // Search Method begins

    private void Search_id(){
        enrollmentCode = jetenrollmentCode.getText().toString();
        if(enrollmentCode.isEmpty()) {
            Toast.makeText(this, "Enrollment code required to make a search", Toast.LENGTH_SHORT).show();
            jetenrollmentCode.requestFocus();
        }else{
            db.collection("Enrollments")
                    .whereEqualTo("enrollmentCode",enrollmentCode)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    enrollmentCode = document.getId();
                                    jetStudentId.setText(document.getString("ClassCode"));
                                    jetClassId.setText(document.getString("ClassCode"));
                                    jtvStudentFullName.setText(document.getString("StudentFullname"));
                                    jtvClassName.setText(document.getString("ClassName"));
                                    jcheckBox.setChecked(Objects.equals(document.getString("EnrollmentCheckBox"), "Yes"));
                                    Toast.makeText(EnrollmentActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(EnrollmentActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }





    // End Search method


    //Public Search Method
    public void Search(View view){
        Search_id();
    }


    // End Method



    // Add method begins here

    public void Add(View view){
        enrollmentCode = jetenrollmentCode.getText().toString();
        studentCode=jetStudentId.getText().toString();
        classCode = jetClassId.getText().toString();


        if(  enrollmentCode.isEmpty() || studentCode.isEmpty() || classCode.isEmpty()){
            Toast.makeText(this, "All of the fields are required", Toast.LENGTH_SHORT).show();
            jetenrollmentCode.requestFocus();
        }else{
            // Create e a new user with a first and last name
            Map<String, Object> enrollment = new HashMap<>();
            enrollment.put("enrollmentCode", enrollmentCode);
            enrollment.put("ClassCode", classCode);
            enrollment.put("StudentCode", studentCode);
            enrollment.put("StudentFullname", jtvStudentFullName.getText().toString());
            enrollment.put("ClassName",jtvClassName.getText().toString());
            enrollment.put("EnrollmentCheckBox", "Yes");

            // Add a new document with a generated ID
            db.collection("Enrollments")
                    .add(enrollment)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(EnrollmentActivity.this, "Created Document", Toast.LENGTH_SHORT).show();
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



    // Method ends here

    //Start Private methods Clear Fields

    private void Clear_fields(){
        jetenrollmentCode.setText("");
        jetStudentId.setText("");
        jetClassId.setText("");
        jtvStudentFullName.setText("");
        jtvClassName.setText("");
        jcheckBox.setChecked(false);
        classCode = "";
        jetenrollmentCode.requestFocus();
    }

    public void Clear(View view){
        Clear_fields();
    }

    //End Method




    // Find Method Begins here


    private void Find_id(){
        studentCode = jetStudentId.getText().toString();
        classCode = jetClassId.getText().toString();

        if(studentCode.isEmpty()) {
            Toast.makeText(this, "Class code  & Student Code are required to make a search", Toast.LENGTH_SHORT).show();
            jetenrollmentCode.requestFocus();
        }else{
            db.collection("Students")
                    .whereEqualTo("Id",studentCode)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    studentCode = document.getId();
                                    jtvStudentFullName.setText(document.getString("FullName"));
                                    Toast.makeText(EnrollmentActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(EnrollmentActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            db.collection("Classes")
                    .whereEqualTo("ClassCode",classCode)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    classCode = document.getId();
                                    jtvClassName.setText(document.getString("ClassName"));
                                    Toast.makeText(EnrollmentActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(EnrollmentActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }





    // End Find Method


    //Public Search Method
    public void Find(View view){
        Find_id();
    }


    // End Method


    // Anulate Method Starts Here

    // Anulate Method

    public void Anulate(View view){
        if(!enrollmentCode.equals("")){
            enrollmentCode = jetenrollmentCode.getText().toString();
            studentCode = jetStudentId.getText().toString();
            classCode = jetClassId.getText().toString();
            studentFullName = jtvStudentFullName.getText().toString();
            className = jtvClassName.getText().toString();

            if (enrollmentCode.isEmpty() || studentCode.isEmpty() || classCode.isEmpty() || studentFullName.isEmpty() || className.isEmpty()) {
                Toast.makeText(this, "All of the fields are required", Toast.LENGTH_SHORT).show();
                jetenrollmentCode.requestFocus();
            } else {
                // Create e a new user with a first and last name
                Map<String, Object> enrollment = new HashMap<>();
                enrollment.put("enrollmentCode", enrollmentCode);
                enrollment.put("ClassCode", classCode);
                enrollment.put("StudentCode", studentCode);
                enrollment.put("StudentFullname", jtvStudentFullName.getText().toString());
                enrollment.put("ClassName",jtvClassName.getText().toString());
                enrollment.put("EnrollmentCheckBox", "No");


                db.collection("Enrollments").document(enrollmentCode)
                        .set(enrollment)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EnrollmentActivity.this, "Student correctly updated.....", Toast.LENGTH_SHORT).show();
                                Clear_fields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EnrollmentActivity.this, "Student could not be updated....", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else{
            Toast.makeText(this, "First you got to search...", Toast.LENGTH_SHORT).show();
            jetenrollmentCode.requestFocus();
        }
    }






    // Anulate Mehod Ends Here


    //Start Back method


    public void Back(View view){
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }

    //End Back Method


    // Begin Consult Method

    public void General_Consult(View view){
        Intent generalIntent = new Intent(this, EnrollmentListingActivity.class);
        startActivity(generalIntent);
    }

    //End Consult Method

}