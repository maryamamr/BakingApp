package com.example.maryam.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maryam.bakingapp.R;
import com.example.maryam.bakingapp.model.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    final private StepsItemClickListener mOnClickListener;
    private List<Step> steps;
    private Context context;

    public StepsAdapter(Context context, List<Step> steps, StepsItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.steps = steps;
        this.context = context;
    }

    public interface StepsItemClickListener {
        void onStepItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.steps_list_item, parent, false);
        return new StepsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.stepTv.setText(steps.get(position).getShortDescription());
    }

    public Step getStepIndex(int index) {
        return steps.get(index);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView stepTv;

        public ViewHolder(View itemView) {
            super(itemView);
            stepTv = itemView.findViewById(R.id.steps_tv);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onStepItemClick(clickedPosition);
        }
    }
}
