package apps.tridentfitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.R;

public class DetailPageAdapter extends BaseAdapter {
    Context context;
    LayoutInflater mInflater;
    ArrayList<detailpageGetSet> data;

    public DetailPageAdapter(Context context, ArrayList<detailpageGetSet> data) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cell_detailpage_excercise, parent, false);
        }
        TextView textViewItemName = convertView.findViewById(R.id.txt_detail);
        ImageView img_excercise;
        textViewItemName.setText(data.get(position).getName());
        return convertView;
    }
}