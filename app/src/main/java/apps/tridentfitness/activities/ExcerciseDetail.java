package apps.tridentfitness.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;

import apps.tridentfitness.getset.ExceciseGetSet;
import apps.tridentfitness.utilHelper.SqliteHelper;
import apps.tridentfitness.R;
import apps.tridentfitness.adapter.ExceciseAdapter;

public class ExcerciseDetail extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;
    ArrayList<ExceciseGetSet> exceciseGetSetArrayList;
    public static Typeface abeezee_regular;
    String link;
    ExceciseAdapter adapter;
    ListView listView;
    String id, url;
    SqliteHelper sqliteHelper;
    private String TAG = "ExcerciseDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_excercise_detail);
        getIntents();
        inite();
        getList();
    }

    public void getIntents() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        id = intent.getStringExtra("id");
    }

    private void inite() {
        playerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_fragment);
        playerFragment.initialize(getString(R.string.youtube_key), this);
        playerFragment.setRetainInstance(true);
        listView = findViewById(R.id.listview);
    }

    private void getList() {
        exceciseGetSetArrayList = new ArrayList<>();
        try {
            sqliteHelper = new SqliteHelper(ExcerciseDetail.this);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Log.e(TAG, "getList: " + "select * from steps where exercise_id=" + id + ";");
            Cursor cur = db1.rawQuery("select * from steps where exercise_id=" + id + ";", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        ExceciseGetSet getset = new ExceciseGetSet();
                        String step = cur.getString(cur.getColumnIndex("step"));
                        getset.setExcecisestep(step);
                        exceciseGetSetArrayList.add(getset);
                    } while (cur.moveToNext());
                }
            }
            cur.close();
            db1.close();
        } catch (Exception e) {
            Log.e(TAG, "getList: " + e.getCause());
        }
        adapter = new ExceciseAdapter(ExcerciseDetail.this, exceciseGetSetArrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        mPlayer = youTubePlayer;
        link = url;
        //Enables automatic control of orientation
        mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        //Show full screen in landscape mode always
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
        //System controls will appear automatically
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
        if (!b) {
            try {
                mPlayer.loadVideo(link.substring(link.indexOf("=") + 1));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("IngradientActivity", "onInitializationSuccess: " + e.getMessage());
            }
        } else {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        mPlayer = null;
    }
}
