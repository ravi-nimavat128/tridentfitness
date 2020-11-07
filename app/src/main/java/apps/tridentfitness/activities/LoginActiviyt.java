package apps.tridentfitness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import apps.tridentfitness.Network.ApiUtils;
import apps.tridentfitness.Network.WebApi;
import apps.tridentfitness.R;
import apps.tridentfitness.Responses.LoginResponse;
import apps.tridentfitness.Utils.SharedPrefreances;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActiviyt extends AppCompatActivity {
    TextView btn_login;
    EditText edit_email,edit_password;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        progressDialog = new ProgressDialog(LoginActiviyt.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        edit_password = findViewById(R.id.edit_password);
        edit_email = findViewById(R.id.edit_email);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_email.getText().toString().isEmpty()){
                    edit_email.setError("Enter mobile number");
                }else if (edit_password.getText().toString().isEmpty()){
                    edit_password.setError("Enter password");
                }else {
                    progressDialog.show();
                    WebApi webApi = ApiUtils.getClient().create(WebApi.class);
                    Call<LoginResponse> call = webApi.login(edit_email.getText().toString(),edit_password.getText().toString(),SharedPrefreances.getSharedPreferenceString(getApplicationContext(),"token"));
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            progressDialog.dismiss();
                            if (response.body().getStatus() == 1){
                                Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                SharedPrefreances.setSharedPreferenceString(getApplicationContext(),"id",response.body().getUserId());
                                SharedPrefreances.setSharedPreferenceString(getApplicationContext(),"name",response.body().getName());
                                SharedPrefreances.setSharedPreferenceString(getApplicationContext(),"mobile",response.body().getMobileNo());
                                SharedPrefreances.setSharedPreferenceString(getApplicationContext(),"login","1");

                                Intent intent = new Intent(LoginActiviyt.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.w("server ",t.toString());
                            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
