package apps.tridentfitness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import apps.tridentfitness.R;
import apps.tridentfitness.activities.DetailActivity;
import apps.tridentfitness.getset.HomePageCategoryGetset;

public class HomePageCategoryAdapter extends RecyclerView.Adapter<HomePageCategoryAdapter.ItemHolder> {
    ArrayList<HomePageCategoryGetset> data;
    private Context context;
    String category;
   // private OnItemClickListener listener;
    private String TAG = "OfferAdapter";
    public OnLoadMoreListener onLoadMoreListener;
    RecyclerView recyclerView;
    HomePageCategoryAdapter.OnItemClickListenerNew mItemClickListener;
    public HomePageCategoryAdapter(RecyclerView recyclerView, ArrayList<HomePageCategoryGetset> data, Context context) {
        this.context = context;
        this.data = data;
        this.category = category;
       // this.listener = listener;
//        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.recyclerView = recyclerView;
    }

    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_home_page, parent, false);
        ItemHolder myViewHolder = new ItemHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        //holder.txt_cal.setText(data.get(position).getCalories() + " " + "Calories");
       // holder.txt_exercise.setText(data.get(position).getTotexercise() + " " + "Exercise");
       // holder.txt_time.setText(data.get(position).getMinuts() + " " + "Minutes");
        holder.txt_title.setText(data.get(position).getName());
      //  holder.txt_type.setText(data.get(position).getTyps());

        Glide.with(context).load(data.get(position).getImage()).into(  holder.img_excercise);


        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                intent.putExtra("id", "" + homePageCategoryList.get(position).getId());
                intent.putExtra("description", "" + homePageCategoryList.get(position).getDescription());
                intent.putExtra("time", "" + homePageCategoryList.get(position).getMinuts());
                intent.putExtra("cal", "" + homePageCategoryList.get(position).getCalories());
                intent.putExtra("tot_ex", "" + homePageCategoryList.get(position).getTotexercise());
                intent.putExtra("type", "" + homePageCategoryList.get(position).getTyps());
                intent.putExtra("name", "" + homePageCategoryList.get(position).getName());
                intent.putExtra("image", "" + homePageCategoryList.get(position).getImage());*/


                context.startActivity(new Intent(context, DetailActivity.class).putExtra("id",data.get(position).getId())
                        .putExtra("description",data.get(position).getDescription())
                        .putExtra("time",data.get(position).getMinuts())
                        .putExtra("cal",data.get(position).getCalories())
                        .putExtra("tot_ex",data.get(position).getTotexercise())
                        .putExtra("type",data.get(position).getTyps())
                        .putExtra("name",data.get(position).getName())
                        .putExtra("image",data.get(position).getImage())


                );
            }
        });
       /* if (data.get(position).getImage().equals("one")) {
            holder.img_exercise.setBackground(context.getResources().getDrawable(R.drawable.one));
        } else if (data.get(position).getImage().equals("two")) {
            holder.img_exercise.setBackground(context.getResources().getDrawable(R.drawable.two));
        } else if (data.get(position).getImage().equals("three")) {
            holder.img_exercise.setBackground(context.getResources().getDrawable(R.drawable.three));
        } else if (data.get(position).getImage().equals("foure")) {
            holder.img_exercise.setBackground(context.getResources().getDrawable(R.drawable.four));
        } else if (data.get(position).getImage().equals("five")) {
            holder.img_exercise.setBackground(context.getResources().getDrawable(R.drawable.five));
        } else if (data.get(position).getImage().equals("six")) {
            holder.img_exercise.setBackground(context.getResources().getDrawable(R.drawable.six));
        }*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_exercise, txt_time, txt_cal, txt_type;
        ImageView img_excercise;
        RelativeLayout details;
        ItemHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            img_excercise = itemView.findViewById(R.id.img_excercise);
            details = itemView.findViewById(R.id.details);

        }

        public void bind(final HomePageCategoryGetset item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(HomePageCategoryGetset item);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnItemClickListener(final HomePageCategoryAdapter.OnItemClickListenerNew mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListenerNew {

        void onItemClick(int position);
    }
    
    

}