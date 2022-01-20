package com.hust.httpconnection;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class UserDetail extends AppCompatActivity {

    String baseURL = "https://lebavui.github.io";

    ImageView background, avatar;
    TextView name, website, email, phone, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        background = findViewById(R.id.imageViewBackground);
        avatar = findViewById(R.id.imageViewAvatar);

        name = findViewById(R.id.textViewName);
        website = findViewById(R.id.textViewWebsite);
        email = findViewById(R.id.textViewEmail);
        phone = findViewById(R.id.textViewPhone);
        address = findViewById(R.id.textViewAddress);

        name.setText(getIntent().getStringExtra("name"));
        website.setText(getIntent().getStringExtra("website"));
        email.setText(getIntent().getStringExtra("email"));
        phone.setText(getIntent().getStringExtra("phone"));
        address.setText(getIntent().getStringExtra("address"));

        try {
            Picasso.get()
                    .load(baseURL + getIntent().getStringExtra("background"))
                    .into(background);

            Picasso.get()
                    .load(baseURL + getIntent().getStringExtra("avatar"))
                    .into(avatar);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}