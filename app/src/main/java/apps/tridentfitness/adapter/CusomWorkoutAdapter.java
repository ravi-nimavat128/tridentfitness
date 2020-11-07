package apps.tridentfitness.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import apps.tridentfitness.getset.CusomItem;
import apps.tridentfitness.R;

public class CusomWorkoutAdapter extends BaseAdapter {

    private Context mcontext;
    private ArrayList<CusomItem> cusomItemArrayList;
    private final LayoutInflater inflater;


    public CusomWorkoutAdapter(Context context, ArrayList<CusomItem> cusomItemArrayList) {
        mcontext = context;
        this.cusomItemArrayList = cusomItemArrayList;
        inflater = LayoutInflater.from(mcontext);
        Log.e("", "ChefRecipeListAdapter: " + this.cusomItemArrayList);
    }

    @Override
    public int getCount() {
        return cusomItemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return cusomItemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.cell_cusomworkout, viewGroup, false);
        }
        TextView txt_detail = view.findViewById(R.id.txt_detail);
        TextView num_excecise = view.findViewById(R.id.num_excecise);
        TextView num_time = view.findViewById(R.id.num_time);
        TextView num_calories = view.findViewById(R.id.num_calories);
        TextView txt_text = view.findViewById(R.id.txt_text);
        ImageView imageView = view.findViewById(R.id.img_text);

        CusomItem cusomItem = cusomItemArrayList.get(i);
        String value = cusomItem.getDetail();
        txt_detail.setText(cusomItem.getDetail());
        txt_text.setText(String.valueOf(value.charAt(0)));
        num_excecise.setText(cusomItem.getNum_excecise()+" "+"Excecise" );
        num_time.setText(cusomItem.getNum_time()+" "+ "Minutes");
        num_calories.setText(cusomItem.getNum_calories()+" "+"Calories");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Glide.with(mcontext).asBitmap().load(stream.toByteArray()).into(imageView);
        String[] array = mcontext.getResources().getStringArray(R.array.androidcolors);
        int count = (i) % 4;
        if (count == 0) {
            imageView.setBackgroundColor(Color.parseColor(array[0]));
        } else if (count == 1) {
            imageView.setBackgroundColor(Color.parseColor(array[1]));
        } else if (count == 2) {
            imageView.setBackgroundColor(Color.parseColor(array[2]));
        } else if (count == 3) {
            imageView.setBackgroundColor(Color.parseColor(array[3]));
        }
        return view;
    }
}
