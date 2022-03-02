package com.darkbeast0106.peoplerestclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import com.darkbeast0106.peoplerestclient.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.adatok.setMovementMethod(new ScrollingMovementMethod());
        RequestTask task = new RequestTask(binding.adatok);
        task.execute();
    }
}