package apps.tridentfitness.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apps.tridentfitness.R;
import apps.tridentfitness.Utils.SharedPrefreances;

public class ProfileFragment extends Fragment {
    TextView txt_name,txt_profile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_activity, container, false);
        txt_name = view.findViewById(R.id.txt_name);
        txt_profile = view.findViewById(R.id.txt_profile);

        txt_name.setText(SharedPrefreances.getSharedPreferenceString(getContext(),"name"));
        txt_profile.setText("+91 " + SharedPrefreances.getSharedPreferenceString(getContext(),"mobile"));
        return view;
    }

}
