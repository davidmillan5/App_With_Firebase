package com.example.firebaseinit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide default text bar

        getSupportActionBar().hide();


    }
    public void Students(View view){
        Intent intStudents = new Intent(this, StudentActivity.class);
        startActivity(intStudents);
    }

    public void Classes(View view){
        Intent intClasses = new Intent(this, ClassesActivity.class);
        startActivity(intClasses);
    }

    public void Enrollments(View view){
        Intent intEnrollments = new Intent(this, EnrollmentActivity.class);
        startActivity(intEnrollments);
    }
}