package com.andygopu.androidantariksa.appresturent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ItemOrderActivity extends AppCompatActivity {

    private String food_key=null;
    private TextView name,price,desc;
    private Button order;
    private DatabaseReference mDataRef;
    private ImageView imageView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_order);

        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price);
        order = findViewById(R.id.ordernow);

        mDataRef = FirebaseDatabase.getInstance().getReference().child("item");
        food_key = getIntent().getExtras().getString("FoodId");
        mAuth = FirebaseAuth.getInstance();

        mDataRef.child(food_key).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String item_name=(String)dataSnapshot.child("name").getValue();
                String item_decs=(String)dataSnapshot.child("desc").getValue();
                String item_price=(String)dataSnapshot.child("price").getValue();
                String item_image=(String)dataSnapshot.child("image").getValue();

                name.setText(item_name);
                desc.setText(item_decs);
                price.setText(item_price);
                Picasso.with(ItemOrderActivity.this).load(item_image).into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
