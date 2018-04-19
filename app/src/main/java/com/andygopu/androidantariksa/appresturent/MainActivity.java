package com.andygopu.androidantariksa.appresturent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText email, pass;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.editTextEmail);
        pass = (EditText) findViewById(R.id.editTextPass);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void signupClick(View view) {
        final String email_Text = email.getText().toString().trim();
        final String pass_Text = pass.getText().toString().trim();

        if (!TextUtils.isEmpty(email_Text) && !TextUtils.isEmpty(pass_Text)) {
            mAuth.createUserWithEmailAndPassword(email_Text, pass_Text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user = mDatabase.child(user_id);
                        current_user.child("Name").setValue(email_Text);
                        Toast.makeText(MainActivity.this, "Sign up Successful", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void signinClick(View view) {
        Intent signinIntent = new Intent(MainActivity.this,
                SigninActivity.class);
        startActivity(signinIntent);
    }
}
