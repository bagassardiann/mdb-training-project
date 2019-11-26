package com.training.day1;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    String varUsername, varPassword;
    RestProcess restProcess;
    ArrayList<HashMap<String, String>> arrayLogin = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getSupportActionBar().hide();
        restProcess = new RestProcess();

        final EditText editTextUsername = findViewById(R.id.editTextUsername);
        final EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin), buttonLupaPassword = findViewById(R.id.buttonLupPass);


        buttonLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent lupas = new Intent(LoginActivity.this, LupaPassword.class);
                startActivity(lupas);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                varUsername = editTextUsername.getText().toString();
                varPassword = editTextPassword.getText().toString();

                loginProcess(view);
//                if (varUsername.length() >=5 && varPassword.length() >=4){
//                    Intent login = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(login);
//                }else if(varPassword.length()==0){
//                    Toast.makeText(LoginActivity.this, "Password harap diisi", Toast.LENGTH_LONG).show();
//                }else if(varUsername.length()==0){
//                    Toast.makeText(LoginActivity.this, "Username harap diisi", Toast.LENGTH_LONG).show();
//                }else if(varUsername.length() < 5 || varPassword.length() < 4){
//                    Toast.makeText(LoginActivity.this, "Username atau Password kurang panjang", Toast.LENGTH_LONG).show();
//                }

            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    private void loginProcess(final View view){
        HashMap<String, String> apiData;
        apiData = restProcess.apiSetting();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String base_url;

        base_url = apiData.get("str_ws_addr")+"/auth";
        params.put("var_cell_phone", varUsername);
        params.put("var_password", varPassword);

        client.setBasicAuth(apiData.get("str_ws_user"), apiData.get("str_ws_pass"));
        client.post(base_url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String resp_content = "";
                try {
                    resp_content = new String (responseBody, "UTF-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }try {
                    displayLogin(view, resp_content);
                }catch (Throwable t){
                    Toast.makeText(LoginActivity.this, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(LoginActivity.this, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void displayLogin(View view, String resp_content){
        try {
            arrayLogin = restProcess.getJsonData(resp_content);
            if (arrayLogin.get(0).get("var_result").equals("1")){
                Intent main_intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main_intent);
                finish();
            }else if(arrayLogin.get(0).get("var_result").equals("0")){
                Toast.makeText(LoginActivity.this, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e){
            Toast.makeText(LoginActivity.this, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
        }
    }

}
