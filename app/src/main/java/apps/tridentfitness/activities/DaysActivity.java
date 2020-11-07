package apps.tridentfitness.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import apps.tridentfitness.R;
import apps.tridentfitness.fragments.DietFraagment;
import apps.tridentfitness.fragments.HomeFragment;
import apps.tridentfitness.fragments.ProfileFragment;
import apps.tridentfitness.fragments.SettingFragment;

public class DaysActivity extends AppCompatActivity {


    RecyclerView recycle_view;
    RelativeLayout layout_monday;
    List<ClientModel> list = new ArrayList<>();
    ImageView img_workout,img_diet,img_profile,img_setting;
    RelativeLayout layout_workout,layout_diet,layout_profile,layout_setting;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.days_account);


        img_workout = (ImageView)findViewById(R.id.img_workout);
        img_diet = (ImageView)findViewById(R.id.img_diet);
        img_profile = (ImageView)findViewById(R.id.img_profile);
        img_setting = (ImageView)findViewById(R.id.img_setting);

        //recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
      //  layout_monday = (RelativeLayout)findViewById(R.id.layout_monday);
        layout_diet = (RelativeLayout)findViewById(R.id.layout_diet);
        layout_profile = (RelativeLayout)findViewById(R.id.layout_profile);
        layout_workout = (RelativeLayout)findViewById(R.id.layout_workout);
        layout_setting = (RelativeLayout)findViewById(R.id.layout_setting);
        pushFragment(new HomeFragment(),"home");

        layout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_setting.getDrawable().getConstantState().equals(img_workout.getContext().getDrawable(R.drawable.setting_gray).getConstantState())){
                    img_setting.setImageResource(R.drawable.setting_blue);
                    img_diet.setImageResource(R.drawable.diet_gray);
                    img_profile.setImageResource(R.drawable.profile_gray);
                    img_workout.setImageResource(R.drawable.workout_gray);
                }else {
                    img_setting.setImageResource(R.drawable.setting_gray);
                    img_diet.setImageResource(R.drawable.diet_gray);
                    img_profile.setImageResource(R.drawable.profile_gray);
                    img_workout.setImageResource(R.drawable.workout_gray);
                }

                pushFragment(new SettingFragment(),"setting");


            }
        });
        layout_profile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (img_profile.getDrawable().getConstantState().equals(img_workout.getContext().getDrawable(R.drawable.profile_gray).getConstantState())){
                    img_profile.setImageResource(R.drawable.profile_blue);
                    img_setting.setImageResource(R.drawable.setting_gray);
                    img_diet.setImageResource(R.drawable.diet_gray);
                    img_workout.setImageResource(R.drawable.workout_gray);
                }else {
                    img_setting.setImageResource(R.drawable.setting_gray);
                    img_diet.setImageResource(R.drawable.diet_gray);
                    img_profile.setImageResource(R.drawable.profile_gray);
                    img_workout.setImageResource(R.drawable.workout_gray);

                }
                pushFragment(new ProfileFragment(),"profile");


            }
        });


        layout_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_workout.getDrawable().getConstantState().equals(img_workout.getContext().getDrawable(R.drawable.workout_gray).getConstantState())){
                    img_workout.setImageResource(R.drawable.workout_blue);
                    img_setting.setImageResource(R.drawable.setting_gray);
                    img_diet.setImageResource(R.drawable.diet_gray);
                    img_profile.setImageResource(R.drawable.profile_gray);
                }else {
                    img_workout.setImageResource(R.drawable.workout_gray);
                    img_setting.setImageResource(R.drawable.setting_gray);
                    img_diet.setImageResource(R.drawable.diet_gray);
                    img_profile.setImageResource(R.drawable.profile_gray);
                }

                pushFragment(new HomeFragment(),"home");
            }
        });

        layout_diet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (img_diet.getDrawable().getConstantState().equals(img_diet.getContext().getDrawable(R.drawable.diet_gray).getConstantState())){
                    img_diet.setImageResource(R.drawable.diet_blue);
                    img_setting.setImageResource(R.drawable.setting_gray);
                    img_profile.setImageResource(R.drawable.profile_gray);
                    img_workout.setImageResource(R.drawable.workout_gray);

                }else {
                    img_diet.setImageResource(R.drawable.diet_gray);
                    img_setting.setImageResource(R.drawable.setting_gray);
                    img_profile.setImageResource(R.drawable.profile_gray);
                    img_workout.setImageResource(R.drawable.workout_gray);

                }

                pushFragment(new DietFraagment(),"diet");

            }
        });





    }


    private boolean pushFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.feed_in, R.anim.feed_out)
                    .replace(R.id.fragment_container, fragment, tag)
                    //.addToBackStack("fragment")
                    .commit();
            return true;
        }
        return false;
    }


}
