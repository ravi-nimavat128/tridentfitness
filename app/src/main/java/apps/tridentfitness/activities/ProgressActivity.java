package apps.tridentfitness.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import apps.tridentfitness.R;
import apps.tridentfitness.fragments.CalenderFragment;
import apps.tridentfitness.fragments.WeightFragment;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt_calender, txt_weight, txt_calender1, txt_weight1;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_progress);
        init();

    }

    private void init() {
        txt_calender = findViewById(R.id.txt_calender);
        txt_calender1 = findViewById(R.id.txt_calender1);
        txt_weight = findViewById(R.id.txt_weight);
        txt_weight1 = findViewById(R.id.txt_weight1);
        container = findViewById(R.id.container);
        calenderFrag();
        txt_calender.setOnClickListener(this);
        txt_weight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.txt_calender:
                txt_calender1.setVisibility(View.VISIBLE);
                txt_weight.setVisibility(View.VISIBLE);
                txt_calender.setVisibility(View.GONE);
                txt_weight1.setVisibility(View.GONE);
                calenderFrag();

                break;
            case R.id.txt_weight:
                txt_calender1.setVisibility(View.GONE);
                txt_weight1.setVisibility(View.VISIBLE);
                txt_weight.setVisibility(View.GONE);
                txt_calender.setVisibility(View.VISIBLE);
                weightFrag();

                break;
        }

    }

    private void calenderFrag() {
        Fragment fragment;
        fragment = new CalenderFragment();
        if (getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.container)).commit();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void weightFrag() {

        Fragment fragment;
        fragment = new WeightFragment();
        if (getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.container)).commit();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1) {
                finish();
            } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
