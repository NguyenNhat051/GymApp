package com.example.gymapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AllTrainingActivities extends AppCompatActivity {

    private RecyclerView trainingRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_training_activities);

        trainingRecView = findViewById(R.id.trainingRecView);
        TrainingAdapter adapter = new TrainingAdapter(this);
        trainingRecView.setLayoutManager(new GridLayoutManager(this, 2));
        trainingRecView.setAdapter(adapter);
        adapter.setTrainingList(Utils.getInstance(this).getTrainings());
    }
}