package com.example.firebaseinit;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {

    EditText jetId,jetFullName, jetMajor, jetSemester;
    CheckBox jcCheckBox;
    String id, fullName, major, semester;

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


    }

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

    private void Clear_fields(){
        jetId.setText("");
        jetSemester.setText("");
        jetMajor.setText("");
        jetFullName.setText("");
        jcCheckBox.setChecked(false);
        jetId.requestFocus();
    }

}