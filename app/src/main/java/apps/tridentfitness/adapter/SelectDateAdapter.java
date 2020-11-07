package apps.tridentfitness.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apps.tridentfitness.getset.CompletgetSet;
import apps.tridentfitness.R;


public class SelectDateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mcontext;
    private ArrayList<CompletgetSet> selectdateList;

    public SelectDateAdapter(Context context, ArrayList<CompletgetSet> selectdateList) {
        mcontext = context;
        this.selectdateList = selectdateList;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_time, txt_calories, txt_total_time;
        ImageView image;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            txt_name = view.findViewById(R.id.txt_name);
            txt_time = view.findViewById(R.id.txt_time);
            txt_calories = view.findViewById(R.id.txt_calories);
            image = view.findViewById(R.id.image);
            txt_total_time = view.findViewById(R.id.txt_total_time);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_selectdate_items, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        CompletgetSet completgetSet = selectdateList.get(i);
        ((ItemViewHolder) holder).txt_name.setText(completgetSet.getWorkout_name());
        ((ItemViewHolder) holder).txt_time.setText(completgetSet.getCurrent_time());
        ((ItemViewHolder) holder).txt_calories.setText(completgetSet.getCalories() + " " + "Calories");
        ((ItemViewHolder) holder).txt_total_time.setText(completgetSet.getTotal_time() + " " + "Minutes");
    }

    @Override
    public int getItemCount() {
        return selectdateList.size();
    }
}
