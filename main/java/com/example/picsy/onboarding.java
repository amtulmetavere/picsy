package com.example.picsy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.picsy.MainActivity;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;
import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;
public class onboarding extends AppCompatActivity {

    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);


        fragmentManager = getSupportFragmentManager();

        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnBoarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,paperOnboardingFragment);
        fragmentTransaction.commit();




        paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {

                startActivity(new Intent(onboarding.this, generateimage.class));
                finish();
            }
        });
    }

    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {



        PaperOnboardingPage scr1 = new PaperOnboardingPage("Welcome to Picsy","Discover the power of effortless image editing and background removal.", Color.parseColor("#F8F8FF"), R.drawable.c, R.drawable.space);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Generate Stunning Images","Create eye-catching visuals with just a tap. Our advanced image generation tool lets you bring your ideas to life effortlessly.",Color.parseColor("#F8F8FF") , R.drawable.b, R.drawable.space);
        PaperOnboardingPage scr3 = new PaperOnboardingPage(" Get Started Instantly","Ready to transform your images? Dive into a world of creativity.\n Swipe right to experience the magic! \uD83D\uDC49", Color.parseColor("#F8F8FF"), R.drawable.d, R.drawable.space);



        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }
}