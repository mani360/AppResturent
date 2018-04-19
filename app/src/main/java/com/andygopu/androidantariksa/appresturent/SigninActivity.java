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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {

    private EditText email,pass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email=findViewById(R.id.etSigninEmail);
        pass=findViewById(R.id.etSigninPass);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("users");
        signin=findViewById(R.id.btnSignin);
    }

    public void signinBtnClick(View view) {
        final String email_text=email.getText().toString().trim();
        final String pass_text=pass.getText().toString().trim();
        if(!TextUtils.isEmpty(email_text) && !TextUtils.isEmpty(pass_text)){
            mAuth.signInWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(SigninActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        checkUserExist();

                }
            });
        }
    }

    private void checkUserExist() {
       final String user_id=mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    Intent menuIntent=new Intent(SigninActivity.this,MenuActivity.class);
                    startActivity(menuIntent);
                    Toast.makeText(SigninActivity.this, "Menu", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SigninActivity.this, "Error", Toast.LENGTH_LONG).show();

            }
        });
    }
}
