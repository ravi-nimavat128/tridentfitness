package apps.tridentfitness.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import apps.tridentfitness.R;

import static apps.tridentfitness.activities.MainActivity.changeStatsBarColor;

public class TermOfUse extends AppCompatActivity {
    private ImageView ib_back;
    private WebView web;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_of_use);
        inite();
        setAboutUscontent();
        changeStatsBarColor(TermOfUse.this);
    }

    private void inite() {
        web = findViewById(R.id.web);
        ib_back = findViewById(R.id.ib_back);
        TextView txt_header = findViewById(R.id.txt_header);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setAboutUscontent() {
        web.loadUrl("file:///android_asset/" + getString(R.string.term_filename));
    }
}
