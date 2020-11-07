package apps.tridentfitness.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import apps.tridentfitness.R;

import static apps.tridentfitness.activities.MainActivity.changeStatsBarColor;

public class Privecy extends AppCompatActivity {
    private ImageView ib_back;
    WebView web;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privecy);
        inite();
        setAboutUscontent();
        changeStatsBarColor(Privecy.this);
    }

    private void inite() {
        web = findViewById(R.id.web);
        ib_back = findViewById( R.id.ib_back);
        TextView txt_header = findViewById(R.id.txt_header);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setAboutUscontent() {
        web.loadUrl("file:///android_asset/"+getString(R.string.privecy_filename));
    }
}
