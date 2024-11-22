package com.Plantze.tracker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAddActivityAdapter extends RecyclerView.Adapter<MainAddActivityAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder{
        Button selectDateButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selectDateButton=itemView.findViewById(R.id.calendar);

            selectDateButton.setOnClickListener(v->{
                int position=getAdapterPosition();
                if (position !=RecyclerView.NO_POSITION){
                    listener.onSelectDateButtonClick(position);
                }
            });
        }


    }
    private OnItemClickListener listener;
    public MainAddActivityAdapter(OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onSelectDateButtonClick(int position);
    }


    @NonNull
    @Override
    public MainAddActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAddActivityAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
