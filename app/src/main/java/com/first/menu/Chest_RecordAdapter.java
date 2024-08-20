package com.first.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Chest_RecordAdapter extends RecyclerView.Adapter<Chest_RecordAdapter.RecordViewHolder> {
    private final Context context;
    private static List<Chest_Record> chestRecordList;

    public Chest_RecordAdapter(Context context, List<Chest_Record> chestRecordList) {
        this.context = context;
        Chest_RecordAdapter.chestRecordList = chestRecordList;

    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chest_item_record,parent,false);
        return new RecordViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Chest_RecordAdapter.RecordViewHolder holder,int position) {
        Chest_Record record = chestRecordList.get(position);

        holder.KneeView.setText(record.knee);
        holder.DiamondView.setText(record.diamond);
        holder.PushUpView.setText(record.pushUp);
        holder.WideView.setText(record.WideArm);
        holder.ClapView.setText(record.Clap);
        holder.InclineView.setText(record.Incline);
        holder.DeclineView.setText(record.Decline);
        holder.HinduView.setText(record.Hindu);
        holder.MedicineView.setText(record.Medicine);
        holder.BurpeesView.setText(record.Burpees);
        holder.TotalVIew.setText(record.ChestTotal);
    }

    @Override
    public int getItemCount() {
        return chestRecordList.size();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {

        public TextView KneeView;
        public TextView DiamondView;
        public TextView PushUpView;
        public TextView WideView;
        public TextView ClapView;
        public TextView InclineView;
        public TextView DeclineView;
        public TextView HinduView;
        public TextView MedicineView;
        public TextView BurpeesView;
        public TextView TotalVIew;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            KneeView = itemView.findViewById(R.id.knee_cb);
            DiamondView = itemView.findViewById(R.id.diamond_cb);
            PushUpView = itemView.findViewById(R.id.pushUp_cb);
            WideView = itemView.findViewById(R.id.Wide_cb);
            ClapView = itemView.findViewById(R.id.Clap_cb);
            InclineView = itemView.findViewById(R.id.Incline_cb);
            DeclineView = itemView.findViewById(R.id.Decline_cb);
            HinduView = itemView.findViewById(R.id.Hindu_cb);
            MedicineView = itemView.findViewById(R.id.Medicine_cb);
            BurpeesView = itemView.findViewById(R.id.Burpees_cb);
            TotalVIew = itemView.findViewById(R.id.total_view);
        }
    }
}
