package com.example.gymapplication;

import static com.example.gymapplication.TrainingActivity.TRAINING_KEY;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class PlanDetailsDialog extends DialogFragment {

    private Button btnDismiss, btnAdd;
    private TextView txtName;
    private EditText edtTxtMinutes;
    private Spinner spinnerDay;

    public interface PassPlan {
        void getPlan(Plan plan);
    }

    private PassPlan passPlan;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_plan_details, null);
        initView(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Enter Details");

        Bundle bundle = getArguments();
        if (null != bundle) {
            Training training = bundle.getParcelable(TRAINING_KEY);
            if (training != null) {
                txtName.setText(training.getName());
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String day = spinnerDay.getSelectedItem().toString();
                        int minutes = Integer.valueOf(edtTxtMinutes.getText().toString());
                        Plan plan = new Plan(training, minutes, day, false);
                        try {
                            passPlan = (PassPlan) getActivity();
                            passPlan.getPlan(plan);
                            dismiss();
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                            dismiss();
                        }
                    }
                });
            }
        }

        return builder.create();
    }

    private void initView(View view) {
        btnAdd = view.findViewById(R.id.btnAdd);
        btnDismiss = view.findViewById(R.id.btnDismiss);
        txtName = view.findViewById(R.id.txtName);
        edtTxtMinutes = view.findViewById(R.id.edtTxtMinutes);
        spinnerDay = view.findViewById(R.id.spinnerDays);
    }
}

