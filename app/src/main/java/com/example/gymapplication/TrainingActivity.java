package com.example.gymapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class TrainingActivity extends AppCompatActivity implements PlanDetailsDialog.PassPlan {
    public static final String TRAINING_KEY = "training";

    private TextView txtTrainingName, txtLongDesc;
    private Button btnAddToPlan;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainning);

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            Training training = intent.getParcelableExtra(TRAINING_KEY);
            if (training != null) {
                txtTrainingName.setText(training.getName());
                txtLongDesc.setText(training.getLongDesc());
                Glide.with(this)
                        .asBitmap()
                        .load(training.getImgUrl())
                        .into(image);

                btnAddToPlan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PlanDetailsDialog planDetailsDialog = new PlanDetailsDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(TRAINING_KEY, training);
                        planDetailsDialog.setArguments(bundle);
                        planDetailsDialog.show(getSupportFragmentManager(), "plan details");
                    }
                });
            }
        }
    }

    private void initView() {
        txtTrainingName = findViewById(R.id.txtTrainingName);
        txtLongDesc = findViewById(R.id.txtLongDesc);
        btnAddToPlan = findViewById(R.id.btnAddToPlan);
        image = findViewById(R.id.imgTraining);
    }

    @Override
    public void getPlan(Plan plan) {
        if (Utils.getInstance(this).addPlan(plan)) {
            Toast.makeText(this, "Add successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PlansActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}