package com.example.firebaseinit;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class ClassesActivity extends AppCompatActivity {

    EditText jetClassCode, jetNameClass, jetCreditClass, jetProfessorName;

    CheckBox jccheckBoxProfessor;

    String classCode, nameClass, creditClass, professorName, classCode_id;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        getSupportActionBar().hide();
        jetClassCode = findViewById(R.id.etclassCode);
        jetCreditClass = findViewById(R.id.etCreditClass);
        jetNameClass = findViewById(R.id.etNameClass);
        jetProfessorName = findViewById(R.id.etProfessorName);
        classCode_id = "";

    }


    // Add method begins here

    public void Add(View view){
        classCode=jetClassCode.getText().toString();
        nameClass=jetNameClass.getText().toString();
        creditClass=jetCreditClass.getText().toString();
        professorName=jetProfessorName.getText().toString();

        if(classCode.isEmpty() || nameClass.isEmpty() || creditClass.isEmpty() || professorName.isEmpty()){
            Toast.makeText(this, "All of the fields are required", Toast.LENGTH_SHORT).show();
            jetClassCode.requestFocus();
        }else{
            // Create e a new user with a first and last name
            Map<String, Object> classunit = new HashMap<>();
            classunit.put("ClassCode", classCode);
            classunit.put("ClassName", nameClass);
            classunit.put("Credits", creditClass);
            classunit.put("ProfessorName", professorName);
            classunit.put("checkBoxProfessor", "Yes");

            // Add a new document with a generated ID
            db.collection("Classes")
                    .add(classunit)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(ClassesActivity.this, "Created Document", Toast.LENGTH_SHORT).show();
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
    //End Add Method

    private void Clear_fields(){
        jetClassCode.setText("");
        jetNameClass.setText("");
        jetCreditClass.setText("");
        jetProfessorName.setText("");
        jccheckBoxProfessor.setChecked(false);
        classCode_id = "";
        jetClassCode.requestFocus();
    }

    public void Clear(View view){
        Clear_fields();
    }

    //End Method

    // Search Method

    private void Search_id(){
        classCode=jetClassCode.getText().toString();
        if(classCode.isEmpty()){
            Toast.makeText(this, "Id required to make a search", Toast.LENGTH_SHORT).show();
            jetClassCode.requestFocus();
        }else{
            db.collection("Students")
                    .whereEqualTo("classCode",classCode)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    classCode_id = document.getId();
                                    jetNameClass.setText(document.getString("NameClass"));
                                    jetCreditClass.setText(document.getString("CreditClass"));
                                    jetProfessorName.setText(document.getString("ProfessorName"));
                                    if(document.getString("CheckBox").equals("Yes")){
                                        jccheckBoxProfessor.setChecked(true);
                                    }else{
                                        jccheckBoxProfessor.setChecked(false);
                                    }

                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(ClassesActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //Public Search Method
    public void Search(View view){
        Search_id();
    }


    // End Method

    public void Back(View view){
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }



}