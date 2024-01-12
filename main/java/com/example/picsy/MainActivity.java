package com.example.picsy;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout generateImageLayout = findViewById(R.id.generateImageLayout);
        LinearLayout removeBgLayout = findViewById(R.id.removeBgLayout);
        LinearLayout imageIntoTextLayout = findViewById(R.id.imageIntoTextLayout);
        LinearLayout editImageLayout = findViewById(R.id.editImageLayout);


        generateImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, generateimage.class));
            }
        });

        removeBgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, removebg.class));
            }
        });
        imageIntoTextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, generateimage.class));
            }
        });

        editImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, removebg.class));
            }
        });
    }
}
