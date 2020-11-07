package apps.tridentfitness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import apps.tridentfitness.R;
import apps.tridentfitness.Responses.DietResponse;
import apps.tridentfitness.activities.Splacescreen;

public class DietAdapter  extends RecyclerView.Adapter<DietAdapter.ViewHolder> {

    List<DietResponse.Result> productList;

    Context context;

    public DietAdapter(Context context, List<DietResponse.Result> productList) {
        this.context = context;
        this.productList = productList;

    }


    @NonNull
    @Override
    public DietAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_item_layout, null);
        DietAdapter.ViewHolder viewHolder = new DietAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DietAdapter.ViewHolder holder, final int position) {

        holder.txt_diet_name.setText(productList.get(position).getDietName());
        holder.diet_cal.setText("Pr. " + productList.get(position).getQty());
        holder.txt_cal.setText(productList.get(position).getCalorie() + " Cal");
        holder.txt_food_name.setText(productList.get(position).getName());


        Glide.with(context)
                .load(productList.get(position).getDietFoodImage())
                .into(holder.img_pro);


        //holder.txt_teacher.setText(productList.get(position).get());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_diet_name, diet_cal,txt_food_name,txt_cal;
        de.hdodenhof.circleimageview.CircleImageView img_pro;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pro = itemView.findViewById(R.id.img_pro);
            txt_diet_name = (TextView) itemView.findViewById(R.id.txt_diet_name);
            diet_cal = (TextView) itemView.findViewById(R.id.diet_cal);
            txt_food_name = itemView.findViewById(R.id.txt_food_name);
            txt_cal = itemView.findViewById(R.id.txt_cal);

        }

        @Override
        public void onClick(View view) {
            Intent mIntent = new Intent(context, Splacescreen.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }
    }
}

