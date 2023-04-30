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

}