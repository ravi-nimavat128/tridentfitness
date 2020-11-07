package apps.tridentfitness.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apps.tridentfitness.getset.SelectItem;
import apps.tridentfitness.R;

public class DragAdapter extends ArrayAdapter<SelectItem> {
    final int INVALID_ID = -1;

    public interface Listener {
        void onGrab(int position, RelativeLayout row);
    }
    final Listener listener;
    final Map<SelectItem, Integer> mIdMap = new HashMap<>();
    public DragAdapter(Context context, List<SelectItem> list, Listener listener) {
        super(context, 0, list);
        this.listener = listener;

        for (int i = 0; i < list.size(); ++i) {
            mIdMap.put(list.get(i), i);
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Context context = getContext();
        final SelectItem data = getItem(position);
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.cell_dragdrop, null);
        }
        final RelativeLayout row = (RelativeLayout) view.findViewById(
                R.id.lytPattern);
        TextView txt_detail = view.findViewById(R.id.img_detail);
        ImageView imageView = view.findViewById(R.id.img_jumping);

        txt_detail.setText(data.getName());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Glide.with(context)
                .load( "file:///android_asset/Exercise_img/" +data.getImage())
                .into(imageView);
        ImageView btn_drag = view.findViewById(R.id.btn_drag);

        btn_drag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                listener.onGrab(position, row);
                return false;
            }
        });

        return view;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        SelectItem item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }
}
