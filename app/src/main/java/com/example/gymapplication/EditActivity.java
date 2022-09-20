package com.example.gymapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements PlanAdapter.RemovePlan {

    private TextView txtDay;
    private RecyclerView recyclerView;
    private Button btnAddMorePlans;
    PlanAdapter planAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();

        Intent intent = getIntent();
        if (null != intent) {
            String day = intent.getStringExtra("day");
            if (null != day) {
                txtDay.setText(day);
                ArrayList<Plan> plans = Utils.getInstance(this).getPlansByDay(day);
                planAdapter = new PlanAdapter(this);
                planAdapter.setType("edit");
                recyclerView.setAdapter(planAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                planAdapter.setPlans(plans);
            }
        }

        btnAddMorePlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPlanIntent = new Intent(EditActivity.this, AllTrainingActivities.class);
                startActivity(addPlanIntent);
            }
        });
    }

    private void initView() {
        txtDay = findViewById(R.id.txtDay);
        recyclerView = findViewById(R.id.recView);
        btnAddMorePlans = findViewById(R.id.addMorePlan);
    }

    @Override
    public void onRemovePlanResult(Plan plan) {
        if (Utils.getInstance(this).removePlan(plan)){
            Toast.makeText(this,"Remove successfully",Toast.LENGTH_SHORT).show();
            planAdapter.setPlans(Utils.getInstance(this).getPlansByDay(plan.getDay()));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PlansActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}