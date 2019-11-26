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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeActivity extends AppCompatActivity {

    private RestProcess rest_class;
    ListView list_employee;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> datakaryawan_arraylist = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        rest_class = new RestProcess();
        list_employee = (ListView) findViewById(R.id.list_employee);

        getDataKaryawan(null);
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
        if (item.getItemId() == R.id.office){
            startActivity(new Intent(EmployeeActivity.this, OfficeActivity.class));
        }else if(item.getItemId() == R.id.tentang){
            startActivity(new Intent(EmployeeActivity.this, AboutActivity.class));
        }else if(item.getItemId() == R.id.logout){
            startActivity(new Intent(EmployeeActivity.this, LoginActivity.class));
            finish();

        }
        return true;
    }

    private void getDataKaryawan(final View view){
        HashMap<String, String> apiData = new HashMap<String, String>();
        apiData = rest_class.apiSetting();
        AsyncHttpClient client = new AsyncHttpClient();
        String base_url;

        base_url = apiData.get("str_ws_addr")+ "/employee";

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
                    displayDataKaryawan(view, resp_content);
                }catch (Throwable t){
                    Toast.makeText(EmployeeActivity.this, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(EmployeeActivity.this, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayDataKaryawan(View view, String resp_content){
        try {
            datakaryawan_arraylist = rest_class.getJsonData(resp_content);
            if (datakaryawan_arraylist.get(0).get("var_result").equals("1")){
                datakaryawan_arraylist.remove(0);
                adapter = new ListAdapter(EmployeeActivity.this, datakaryawan_arraylist, 1);
                list_employee.setAdapter(adapter);
                Toast.makeText(EmployeeActivity.this, "Koneksi Berhasil!", Toast.LENGTH_LONG).show();

                list_employee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(EmployeeActivity.this, DetailEmployee.class);
                        intent.putExtra("nama", datakaryawan_arraylist.get(position).get("employee_name"));
                        intent.putExtra("nip", datakaryawan_arraylist.get(position).get("nomor_induk_pegawai"));
                        intent.putExtra("address", datakaryawan_arraylist.get(position).get("address"));
                        intent.putExtra("gender", datakaryawan_arraylist.get(position).get("gender"));
                        intent.putExtra("url_poto", datakaryawan_arraylist.get(position).get("base_url"));
                        intent.putExtra("tempat_lahir", datakaryawan_arraylist.get(position).get("tempat_lahir"));
                        intent.putExtra("tanggal_lahir", datakaryawan_arraylist.get(position).get("tanggal_lahir"));
                        intent.putExtra("goldar", datakaryawan_arraylist.get(position).get("gol_darah"));
                        intent.putExtra("agama", datakaryawan_arraylist.get(position).get("agama"));
                        intent.putExtra("stat_pkwn", datakaryawan_arraylist.get(position).get("status_perkawinan"));
                        intent.putExtra("kewarganegaraan", datakaryawan_arraylist.get(position).get("kewarganegaraan"));
                        intent.putExtra("berlaku_hingga", datakaryawan_arraylist.get(position).get("berlaku_hingga"));
                        intent.putExtra("tempat_buat", datakaryawan_arraylist.get(position).get("tempat_buat"));
                        intent.putExtra("tanggal_buat", datakaryawan_arraylist.get(position).get("tanggal_buat"));
                        startActivity(intent);
                    }
                });


                
            }else{
                Toast.makeText(EmployeeActivity.this, "Koneksi Gagal!", Toast.LENGTH_LONG).show();

            }
        }catch (JSONException e){
            Toast.makeText(EmployeeActivity.this, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
        }
    }

}
