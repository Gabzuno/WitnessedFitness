package com.first.menu.BMI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.first.menu.Credentials.RecordBMI;
import com.first.menu.R;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {
    private final Context context;
    private static List<RecordBMI> recordList;
    private final OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener{
        void onDeleteClick(String recordKey);
    }

    public RecordAdapter(Context context, List<RecordBMI> recordList, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        RecordAdapter.recordList = recordList;
        this.onDeleteClickListener = onDeleteClickListener;

    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.record_item,parent,false);
        return new RecordViewHolder(view, onDeleteClickListener);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.RecordViewHolder holder,int position) {
        RecordBMI record = recordList.get(position);

        holder.genderTextView.setText(record.gender);
        holder.dateTextView.setText(record.date);
        holder.timeTextView.setText(record.time);
        holder.bmiTextView.setText(record.bmi);
        holder.heightTextView.setText(record.height+" cm");
        holder.weightTextView.setText(record.weight+" kg");
        holder.BMICat.setText(record.category);
        holder.ageTextView.setText(record.age);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTextView;
        public TextView BMICat;
        public TextView timeTextView;
        public TextView bmiTextView;
        public TextView heightTextView;
        public TextView weightTextView;
        public TextView genderTextView;
        public TextView ageTextView;
        public Button deleteValue;
        public RecordViewHolder(@NonNull View itemView, OnDeleteClickListener onDeleteClickListener) {
            super(itemView);

            genderTextView = itemView.findViewById(R.id.gender_textview);
            dateTextView = itemView.findViewById(R.id.date_textview);
            timeTextView = itemView.findViewById(R.id.time_textview);
            bmiTextView = itemView.findViewById(R.id.bmi_textview);
            heightTextView = itemView.findViewById(R.id.height_textview);
            weightTextView = itemView.findViewById(R.id.weight_textview);
            BMICat = itemView.findViewById(R.id.bmiCategory);
            deleteValue = itemView.findViewById(R.id.delete_value);
            ageTextView = itemView.findViewById(R.id.age_textview);

            deleteValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onDeleteClickListener.onDeleteClick(recordList.get(position).getRecordKey());
                    }
                }
            });
        }
    }
}
