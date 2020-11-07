package apps.tridentfitness.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.R;

public class CustomItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private ArrayList<detailpageGetSet> selectItemArrayList;
    private final LayoutInflater inflater;
    private CustomButtonListener listener;
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    // The native app install ad view type.

    private static final int AD_VIEW_TYPE = 1;

    public void setCustomButtonListener(CustomButtonListener listener) {
        this.listener = listener;
    }

    public CustomItemAdapter(Context mcontext, ArrayList<detailpageGetSet> selectItemArrayList) {
        this.mcontext = mcontext;
        this.selectItemArrayList = selectItemArrayList;
        inflater = LayoutInflater.from(mcontext);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName;
        public ImageView imageView, img_arrow;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            textViewItemName = view.findViewById(R.id.txt_detail);
            imageView = view.findViewById(R.id.img_excercise);
            img_arrow = view.findViewById(R.id.img_arrow);
        }

        /*public void bind(final detailpageGetSet item, final WorkoutListAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }*/
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case AD_VIEW_TYPE:
                if (mcontext.getString(R.string.show_admmob_ads).equals("yes")) {
                    View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_admob, parent, false);
                    return new ViewHolderAdMob(bannerView);
                } else if (mcontext.getString(R.string.show_admmob_ads).equals("yes")){
                    View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_admob_facebook, parent, false);
                    return new FacebookBannerViewHolder(bannerView);
                }

            case MENU_ITEM_VIEW_TYPE:

            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detailpage_excercise, parent, false);
                return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case AD_VIEW_TYPE:
                break;

            case MENU_ITEM_VIEW_TYPE:

            default: {
                detailpageGetSet selectItem = selectItemArrayList.get(position);
                ((ItemViewHolder) holder).textViewItemName.setText(selectItem.getName());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Glide.with(mcontext)
                        .load("file:///android_asset/Exercise_img/" + selectItem.getImage())
                        .into(((ItemViewHolder) holder).imageView);
                String[] array = mcontext.getResources().getStringArray(R.array.androidcolors);
                final int count = (position) % 4;
                if (count == 0) {
                    ((ItemViewHolder) holder).imageView.setBackgroundColor(Color.parseColor(array[0]));
                } else if (count == 1) {
                    ((ItemViewHolder) holder).imageView.setBackgroundColor(Color.parseColor(array[1]));
                } else if (count == 2) {
                    ((ItemViewHolder) holder).imageView.setBackgroundColor(Color.parseColor(array[2]));
                } else if (count == 3) {
                    ((ItemViewHolder) holder).imageView.setBackgroundColor(Color.parseColor(array[3]));
                }
            }
            applyClickEvents(((ItemViewHolder) holder), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object recyclerViewItem = selectItemArrayList.get(position);
        if (recyclerViewItem instanceof detailpageGetSet) {
            return MENU_ITEM_VIEW_TYPE;
        }
        return AD_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return selectItemArrayList.size();
    }

    private void applyClickEvents(ItemViewHolder holder, final int i) {
        holder.img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnImageDisplayclick(i);
            }
        });
    }
}
