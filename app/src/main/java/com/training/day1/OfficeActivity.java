package com.training.day1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.training.day1.ListAdapter.SHARED_PREFS;

public class OfficeActivity extends AppCompatActivity {

    private RestProcess rest_class;
    ListView list_office;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> dataoffice_arraylist = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);

        rest_class = new RestProcess();
        list_office = (ListView) findViewById(R.id.list_office);
        getDataOffice(null);

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
            startActivity(new Intent(OfficeActivity.this, EmployeeActivity.class));
        }else if(item.getItemId() == R.id.tentang){
            startActivity(new Intent(OfficeActivity.this, AboutActivity.class));
        }else if(item.getItemId() == R.id.logout){
            startActivity(new Intent(OfficeActivity.this, LoginActivity.class));
            finish();

        }
        return true;
    }

    private void getDataOffice(final View view){
        HashMap<String, String> apiData = new HashMap<String, String>();
        apiData = rest_class.apiSetting();
        AsyncHttpClient client = new AsyncHttpClient();
        String base_url;

        base_url = apiData.get("str_ws_addr")+ "/office";

        client.setBasicAuth(apiData.get("str_ws_user"), apiData.get("str_ws_pass"));
        client.post(base_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp_content= "";
                try {
                    resp_content = new String(responseBody, "UTF-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                try {
                    displayOffice(view, resp_content);
                }catch (Throwable t){
                    Toast.makeText(OfficeActivity.this, "Koneksi Gagal 1!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(OfficeActivity.this, "Koneksi Gagal 2!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayOffice(View view, String resp_content){
        try {
            dataoffice_arraylist = rest_class.getJsonData(resp_content);
            if (dataoffice_arraylist.get(0).get("var_result").equals("1")){
                dataoffice_arraylist.remove(0);
                adapter = new ListAdapter(OfficeActivity.this, dataoffice_arraylist, 2);
                list_office.setAdapter(adapter);
                Toast.makeText(OfficeActivity.this, "Koneksi Berhasil!", Toast.LENGTH_LONG).show();

                list_office.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(OfficeActivity.this, DetailOffice.class);
                        intent.putExtra("namaKantor", dataoffice_arraylist.get(position).get("office_name"));
                        intent.putExtra("telpKantor", dataoffice_arraylist.get(position).get("cell_phone"));
                        intent.putExtra("emailKantor", dataoffice_arraylist.get(position).get("email"));
                        intent.putExtra("addKantor", dataoffice_arraylist.get(position).get("office_address"));
                        intent.putExtra("descKantor", dataoffice_arraylist.get(position).get("office_description"));
                        intent.putExtra("imageKantor", dataoffice_arraylist.get(position).get("base_url"));
                        intent.putExtra("location_gps", dataoffice_arraylist.get(position).get("location_gps"));

                        startActivity(intent);
                    }
                });

            }else{
                Toast.makeText(OfficeActivity.this, "Koneksi Gagal 3!", Toast.LENGTH_LONG).show();

            }
        }catch (JSONException e){
            Toast.makeText(OfficeActivity.this, "Koneksi Gagal 4!", Toast.LENGTH_LONG).show();
        }
    }

}

