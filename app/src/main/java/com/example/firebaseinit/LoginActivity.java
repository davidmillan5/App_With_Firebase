package com.example.firebaseinit;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText jetUserId, jetPassword;

    String user, password;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Hide default text bar
        getSupportActionBar().hide();
        jetUserId = findViewById(R.id.etUserId);
        jetPassword = findViewById(R.id.etPassword);





    }

    // Login Method

    public void LoginVerification(View view){
            user = jetUserId.getText().toString();
            password = jetPassword.getText().toString();
            if(user.isEmpty() || password.isEmpty() ){
                Toast.makeText(this, "Id & Password required to make a search", Toast.LENGTH_SHORT).show();
                jetUserId.requestFocus();
            }else{
                db.collection("Users")
                        .whereEqualTo("userId",user)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        user = document.getId();
                                    }
                                    Main(view);
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                    Toast.makeText(LoginActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
    }


    public void Main(View view){
        Intent intMain = new Intent(this, MainActivity.class);
        startActivity(intMain);
    }


}