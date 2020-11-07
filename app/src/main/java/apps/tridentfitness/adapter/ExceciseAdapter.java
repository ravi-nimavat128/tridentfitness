package apps.tridentfitness.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import apps.tridentfitness.getset.ExceciseGetSet;
import apps.tridentfitness.R;

public class ExceciseAdapter extends BaseAdapter {
    private Context mcontext;
    private ArrayList<ExceciseGetSet> exceciseGetSetArrayList;
    private final LayoutInflater inflater;

    public ExceciseAdapter(Context context, ArrayList<ExceciseGetSet> exceciseGetSetArrayList) {
        mcontext = context;
        this.exceciseGetSetArrayList = exceciseGetSetArrayList;
        inflater = LayoutInflater.from(mcontext);
        Log.e("", "ChefRecipeListAdapter: " + this.exceciseGetSetArrayList);
    }

    @Override
    public int getCount() {
        return exceciseGetSetArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return exceciseGetSetArrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.cell_excercise_step, viewGroup, false);
        }
        TextView txt_excecisestep = view.findViewById(R.id.txt_excecisestep);
        TextView txt_pos = view.findViewById(R.id.txt_pos);
        ExceciseGetSet exceciseGetSet = exceciseGetSetArrayList.get(i);
        txt_excecisestep.setText(exceciseGetSet.getExcecisestep());
        if ((i + 1) < 10) {
            txt_pos.setText("0" + (i + 1));
        } else {
            txt_pos.setText("" + (i + 1));
        }
        return view;
    }
}
