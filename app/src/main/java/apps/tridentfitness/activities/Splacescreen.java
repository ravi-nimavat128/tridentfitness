package apps.tridentfitness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import apps.tridentfitness.Network.ApiUtils;
import apps.tridentfitness.Network.WebApi;
import apps.tridentfitness.R;
import apps.tridentfitness.Responses.CommonResponse;
import apps.tridentfitness.Utils.SharedPrefreances;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splacescreen extends AppCompatActivity {

    RelativeLayout layout_get_started;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splacescreen);
        Log.w("token",SharedPrefreances.getSharedPreferenceString(getApplicationContext(),"token"));
        //startHeavyProcessing();
        progressDialog = new ProgressDialog(Splacescreen.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");

        layout_get_started = findViewById(R.id.layout_get_started);
        layout_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                if (SharedPrefreances.getSharedPreferenceString(Splacescreen.this,"login").equalsIgnoreCase("1")){
                    WebApi webApi = ApiUtils.getClient().create(WebApi.class);
                    Call<CommonResponse> call = webApi.check_device(SharedPrefreances.getSharedPreferenceString(getApplicationContext(),"mobile"),
                            SharedPrefreances.getSharedPreferenceString(getApplicationContext(),"token"));
                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            progressDialog.dismiss();
                            if (response.body().getStatus() == 1){
                                Intent i = new Intent(Splacescreen.this, DaysActivity.class);
                                // i.putExtra("data", result);
                                startActivity(i);
                                finish();
                            }else {
                                SharedPrefreances.setSharedPreferenceString(getApplicationContext(),"login","0");
                                Intent i = new Intent(Splacescreen.this, LoginActiviyt.class);
                                // i.putExtra("data", result);
                                startActivity(i);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Server error",Toast.LENGTH_SHORT).show();

                        }
                    });

                }else {
                    progressDialog.dismiss();
                    Intent i = new Intent(Splacescreen.this, LoginActiviyt.class);
                    // i.putExtra("data", result);
                    startActivity(i);
                    finish();
                }




            }
        });






    }
    /*private void startHeavyProcessing() {
        new LongOperation().execute("");
    }
    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //some heavy processing resulting in a Data String
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "whatever result you have";
        }

        @Override
        protected void onPostExecute(String result) {

            if (SharedPrefreances.getSharedPreferenceString(Splacescreen.this,"login").equalsIgnoreCase("1")){
                Intent i = new Intent(Splacescreen.this, MainActivity.class);
                // i.putExtra("data", result);
                startActivity(i);
                finish();
            }else {
                Intent i = new Intent(Splacescreen.this, LoginActiviyt.class);
                // i.putExtra("data", result);
                startActivity(i);
                finish();
            }

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
*/




}
