package com.example.yazlab3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;

public class FirmaAdapter extends RecyclerView.Adapter<FirmaAdapter.FirmamViewHolder> {
    Context context;
    ArrayList<Firmam> firmamArrayList;
    LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemClickListener MonItemClickListener;

    public FirmaAdapter(Context context, ArrayList<Firmam> firmamArrayList, OnItemClickListener OnItemClickListener) {
        this.context = context;
        this.firmamArrayList = firmamArrayList;
        this.MonItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public FirmamViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.carditem, null);
        return new FirmamViewHolder(view, MonItemClickListener);
    }

    @Override
    public void onBindViewHolder(FirmamViewHolder firmamViewHolder, final int i) {
        final Firmam firmalarim = firmamArrayList.get(i);
        firmamViewHolder.title.setText(firmalarim.getName());
        firmamViewHolder.content.setText(firmalarim.getSure());
        firmamViewHolder.location.setText(firmalarim.getLokasyon());
        firmamViewHolder.time.setText(firmalarim.getType());

    }

    @Override
    public int getItemCount() {
        return firmamArrayList.size();
    }

    public void setOnItemClickListener(FirmaAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public class FirmamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, content, time,location;
        OnItemClickListener onItemClickListener;

        public FirmamViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            location = itemView.findViewById(R.id.location);
            time = itemView.findViewById(R.id.time);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}
