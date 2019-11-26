package com.training.day1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class AboutActivity extends AppCompatActivity {

    // API Process
    private RestProcess rest_class;

    //Local Variable

    private WebView wvAbout;

    //Declare ArrayList
    ArrayList<HashMap<String, String>> dataAbout_arrayList = new ArrayList<HashMap<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        rest_class = new RestProcess();
        wvAbout = (WebView) findViewById(R.id.wv_about);

        getAbout(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Menambahkan item2 ke dalam class
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.karyawan){
            startActivity(new Intent(AboutActivity.this, EmployeeActivity.class));
        }else if(item.getItemId() == R.id.office){
            startActivity(new Intent(AboutActivity.this, OfficeActivity.class));
        }else if(item.getItemId() == R.id.logout){
            startActivity(new Intent(AboutActivity.this, LoginActivity.class));
            finish();

        }
        return true;
    }


    private void getAbout(final View view){
        HashMap<String, String> apiData = new HashMap<String, String>();
        apiData = rest_class.apiSetting();
        AsyncHttpClient client = new AsyncHttpClient();
        String base_url;
        base_url = apiData.get("str_ws_addr")+ "/about_apps";
        client.setBasicAuth(apiData.get("str_ws_user"), apiData.get("str_ws_pass"));
        client.post(base_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp_content ="";
                try {
                    resp_content = new String(responseBody, "UTF-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                try{
                    displayAbout(view, resp_content);
                }
                catch (Throwable t){
                    Toast.makeText(AboutActivity.this, "Koneksi Gagal 1", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AboutActivity.this, "Koneksi Gagal 2", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void displayAbout(View view, String resp_content){
        try {
            dataAbout_arrayList = rest_class.getJsonData(resp_content);
            if (dataAbout_arrayList.get(0).get("var_result").equals("1")){
                String width = " width=\"100%\" ";

                String strHtml = dataAbout_arrayList.get(1).get("about_apps");
                int firstIndex = strHtml.indexOf("<img");
                strHtml = strHtml.substring(0, firstIndex+4) + width + strHtml.substring(firstIndex+4, strHtml.length());

                wvAbout.loadData(strHtml, "text/html", null);
                Toast.makeText(AboutActivity.this, "Koneksi Berhasil 1", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(AboutActivity.this, "Koneksi Gagal 3", Toast.LENGTH_LONG).show();
            }
        }catch(JSONException e){
            Toast.makeText(AboutActivity.this, "Koneksi Gagal 8", Toast.LENGTH_LONG).show();
        }
    }
}
