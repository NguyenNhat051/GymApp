package com.example.gymapplication;

import static com.example.gymapplication.TrainingActivity.TRAINING_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {
    private ArrayList<Training> trainingList = new ArrayList<>();
    private Context context;

    public TrainingAdapter(Context context) {
        this.context = context;
    }

    public void setTrainingList(ArrayList<Training> trainingList) {
        this.trainingList = trainingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(trainingList.get(position).getName());
        holder.txtShortDesc.setText(trainingList.get(position).getShortDesc());
        Glide.with(context)
                .asBitmap()
                .load(trainingList.get(position).getImgUrl())
                .into(holder.image);

        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(context, TrainingActivity.class);
            intent.putExtra(TRAINING_KEY, trainingList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return trainingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView parent;
        private final TextView txtName;
        private final TextView txtShortDesc;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            txtName = itemView.findViewById(R.id.txtName);
            txtShortDesc = itemView.findViewById(R.id.txtDescription);
            image = itemView.findViewById(R.id.imageTraining);
        }
    }
}
