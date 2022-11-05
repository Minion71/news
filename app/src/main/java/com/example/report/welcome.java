package com.example.report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();

        TextView tx;
            tx = findViewById(R.id.text1);
            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome);
            tx.setAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }