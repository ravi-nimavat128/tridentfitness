package apps.tridentfitness.adapter;

import android.annotation.SuppressLint;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import apps.tridentfitness.activities.AllWorkOutActivity;
import apps.tridentfitness.activities.ExcerciseDetail;
import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.R;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.ItemHolder> {
    ArrayList<detailpageGetSet> data;
    private Context mcontext;
    String category;
    //private OnItemClickListener listener;
    private String TAG = "OfferAdapter";

    RecyclerView recyclerView;
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    WorkoutListAdapter.OnItemClickListener mItemClickListener;
    // The native app install ad view type.
    private static final int AD_VIEW_TYPE = 1;

    public WorkoutListAdapter(RecyclerView recyclerView, ArrayList<detailpageGetSet> data, Context context) {
        mcontext = context;
        this.data = data;
        this.category = category;
      //  this.listener = listener;
//        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.recyclerView = recyclerView;
    }

    @Override
    public WorkoutListAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* switch (viewType) {
            case AD_VIEW_TYPE:
                if (mcontext.getString(R.string.show_admmob_ads).equals("yes")) {
                    View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_admob, parent, false);
                    return new ViewHolderAdMob(bannerView);
                } else if (mcontext.getString(R.string.show_facebook_ads).equals("yes")){
                    View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_admob_facebook, parent, false);
                    return new FacebookBannerViewHolder(bannerView);
                }

            case MENU_ITEM_VIEW_TYPE :

            default:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detailpage_excercise, parent, false);
                ItemHolder myViewHolder = new ItemHolder(itemView);
                return myViewHolder;
        }*/

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detailpage_excercise, parent, false);
        ItemHolder myViewHolder = new ItemHolder(itemView);


        return myViewHolder;

    }




    @Override
    public void onBindViewHolder(@NonNull final WorkoutListAdapter.ItemHolder holder, @SuppressLint("RecyclerView") final int position) {


        int viewType = getItemViewType(position);

        switch (viewType) {
            case AD_VIEW_TYPE:
                break;

            case MENU_ITEM_VIEW_TYPE:

            default: {
                ((ItemHolder) holder).test.setText(data.get(position).getName());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Glide.with(mcontext).load(data.get(position).getImage()).into(((ItemHolder) holder).img_excercise);
                //  Glide.with(mcontext).load("file:///android_asset/Exercise_img/" + data.get(position).getImage()).into(((ItemHolder) holder).img_excercise);
            }
        }
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    /*            Intent intent = new Intent(AllWorkOutActivity.this, ExcerciseDetail.class);
                intent.putExtra("id", allExercise.get(position).getId());
                intent.putExtra("url", allExercise.get(position).getUrl());
                startActivity(intent);
                */

                mcontext.startActivity(new Intent(mcontext,ExcerciseDetail.class).putExtra("id",data.get(position).getId()).putExtra("url",data.get(position).getUrl()));
            }
        });


        // holder.txt_time.setText(productList.get(position).getCreatedAt());
       /* holder.txt_company_name.setText(productList.get(position).getCompany_name());
        holder.txt_company_address.setText(productList.get(position).getNagar_palika());*/
    }


    @Override
    public int getItemViewType(int position) {
        Object recyclerViewItem = data.get(position);
        if (recyclerViewItem instanceof detailpageGetSet) {
            return MENU_ITEM_VIEW_TYPE;
        }
        return AD_VIEW_TYPE;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView test;
        ImageView img_excercise;
        RelativeLayout details;
        ItemHolder(View itemView) {
            super(itemView);
            test = itemView.findViewById(R.id.txt_detail);
            img_excercise = itemView.findViewById(R.id.img_excercise);
            details = itemView.findViewById(R.id.details);

        }


    }



    public void setOnItemClickListener(final WorkoutListAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }
    

}