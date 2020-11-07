package apps.tridentfitness.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import apps.tridentfitness.R;
import apps.tridentfitness.activities.AllWorkOutActivity;
import apps.tridentfitness.activities.MainActivity;
import apps.tridentfitness.activities.WorkoutActivity;

public class HomeFragment extends Fragment {
    RelativeLayout layout_monday,layout_tuesday,layout_wednesday,layout_thursday,layout_friday,layout_sat,layout_sundasy;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        layout_monday = (RelativeLayout)view.findViewById(R.id.layout_monday);
        layout_tuesday = view.findViewById(R.id.layout_tuesday);
        layout_wednesday = view.findViewById(R.id.layout_wednesday);
        layout_thursday = view.findViewById(R.id.layout_thursday);
        layout_friday = view.findViewById(R.id.layout_friday);
        layout_sat = view.findViewById(R.id.layout_sat);
        layout_sundasy = view.findViewById(R.id.layout_sundasy);

        layout_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("data","2");
                startActivity(intent);
            }
        });

        layout_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("data","3");
                startActivity(intent);
            }
        });

        layout_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("data","4");
                startActivity(intent);
            }
        });

        layout_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("data","5");
                startActivity(intent);
            }
        });


        layout_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("data","6");
                startActivity(intent);
            }
        });

        layout_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("data","7");
                startActivity(intent);
            }
        });

        layout_sundasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("data","1");
                startActivity(intent);
            }
        });

        return view;
    }

}
