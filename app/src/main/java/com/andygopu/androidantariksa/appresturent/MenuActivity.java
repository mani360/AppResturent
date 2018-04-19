package com.andygopu.androidantariksa.appresturent;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView foodList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth.AuthStateListener authState;

    private FoodAdapter foodAdapter;
    private ListView listView;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Recyclerview adapter
        foodList = findViewById(R.id.recyclerV);
        foodList.setHasFixedSize(true);
        foodList.setLayoutManager(new LinearLayoutManager(this));

        //send query to database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDataRef = mDatabase.getReference().child("item");

        authState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent signinIntent = new Intent(MenuActivity.this, MainActivity.class);
                    signinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(signinIntent);
                }
            }
        };
    }

    FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
            .setQuery(mDataRef, Food.class)
            .build();


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authState);

        FirebaseRecyclerAdapter<Food, FoodViewHolder> FBRA = new
                FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {

                    @Override
                    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.menu_item, parent, false);
                        Toast.makeText(MenuActivity.this, "create view called", Toast.LENGTH_LONG).show();
                        return new FoodViewHolder(view);

                    }

                    @Override
                    protected void onBindViewHolder(@NonNull FoodViewHolder viewHolder,
                                                    int position, @NonNull Food model) {
                        viewHolder.setName(model.getName());
                        viewHolder.setDesc(model.getDesc());
                        viewHolder.setPrice(model.getPrice());
                        viewHolder.setImage(getApplicationContext(), model.getImage());
                        Toast.makeText(MenuActivity.this, "Bind view called", Toast.LENGTH_LONG).show();
                        final String food_key = getRef(position).getKey().toString();
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent itemOrder=new Intent(MenuActivity.this,ItemOrderActivity.class);
                                itemOrder.putExtra("FoodId",food_key);
                                startActivity(itemOrder);
                            }
                        });
                    }

                };
        foodList.setAdapter(FBRA);


    }


    public class FoodViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FoodViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        public void setName(String name) {
            TextView food_name = mView.findViewById(R.id.tvName);
            food_name.setText(name);
        }

        public void setDesc(String desc) {
            TextView food_desc = mView.findViewById(R.id.tvDesc);
            food_desc.setText(desc);
        }

        public void setPrice(String price) {
            TextView food_price = mView.findViewById(R.id.tvPrice);
            food_price.setText(price);
        }

        public void setImage(Context context, String image) {
            ImageView food_image = mView.findViewById(R.id.imageItem);
            Picasso.with(context).load(image).into(food_image);
        }
    }

}
