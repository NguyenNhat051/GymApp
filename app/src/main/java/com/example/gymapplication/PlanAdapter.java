package com.example.gymapplication;

import static com.example.gymapplication.TrainingActivity.TRAINING_KEY;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    public interface RemovePlan {
        void onRemovePlanResult(Plan plan);
    }

    private RemovePlan removePlan;

    private ArrayList<Plan> plans = new ArrayList<>();
    private Context mContext;
    private String type = "";

    public PlanAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(plans.get(position).getTraining().getName());
        holder.txtTime.setText(String.valueOf(plans.get(position).getMinutes()));
        holder.txtDescription.setText(plans.get(position).getTraining().getLongDesc());
        Glide.with(mContext)
                .asBitmap()
                .load(plans.get(position).getTraining().getImgUrl())
                .into(holder.imageTraining);
        if (plans.get(position).isAccomplished()) {
            holder.emptyCircle.setVisibility(View.GONE);
            holder.checkedCircle.setVisibility(View.VISIBLE);
        } else {
            holder.checkedCircle.setVisibility(View.GONE);
            holder.emptyCircle.setVisibility(View.VISIBLE);
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TrainingActivity.class);
                intent.putExtra(TRAINING_KEY, plans.get(holder.getAdapterPosition()).getTraining());
                mContext.startActivity(intent);
            }
        });

        if (type.equals("edit")) {
            holder.emptyCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                            .setTitle("Finished")
                            .setMessage("Have you finished" + plans.get(holder.getAdapterPosition()).getTraining().getName() + "?")
                            .setNegativeButton("No", (dialogInterface, i) -> {
                            })
                            .setPositiveButton("Yes", ((dialogInterface, i) -> {
                                plans.get(holder.getAdapterPosition()).setAccomplished(true);
                                Utils.getInstance(mContext).updatePlan(plans);
                                notifyDataSetChanged();
                            }));
                    builder.create().show();
                }
            });

            holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                            .setTitle("Delete !")
                            .setMessage("Are you sure to delete" + plans.get(holder.getAdapterPosition()).getTraining().getName() + "?")
                            .setNegativeButton("No", (dialogInterface, i) -> {
                            })
                            .setPositiveButton("Yes", ((dialogInterface, i) -> {
                                try {
                                    removePlan = (RemovePlan) mContext;
                                    removePlan.onRemovePlanResult(plans.get(holder.getAdapterPosition()));
                                } catch (ClassCastException e) {
                                    e.printStackTrace();
                                }
                            }));
                    builder.create().show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTime, txtName, txtDescription;
        private MaterialCardView parent;
        private ImageView imageTraining, emptyCircle, checkedCircle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTime = itemView.findViewById(R.id.txtTime);
            txtName = itemView.findViewById(R.id.txtName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            parent = itemView.findViewById(R.id.parent);
            imageTraining = itemView.findViewById(R.id.trainingImage);
            emptyCircle = itemView.findViewById(R.id.emptyCircle);
            checkedCircle = itemView.findViewById(R.id.checkedCircle);
        }
    }

}
