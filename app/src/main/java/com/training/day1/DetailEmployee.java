package com.training.day1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employee);

        TextView namaEmployee = findViewById(R.id.nameEmployee);
        TextView nipEmployee = findViewById(R.id.nipEmployee);
        TextView alamatEmployee = findViewById(R.id.alamatEmployee);
        TextView genderEmployee = findViewById(R.id.genderEmployee);
        ImageView potoEmployee = findViewById(R.id.potoEmployee);
        TextView tempLahirEmployee = findViewById(R.id.tempLahirEmployee);
        TextView tangLahirEmployee = findViewById(R.id.tangLahirEmployee);
        TextView goldarEmployee = findViewById(R.id.goldarEmployee);
        TextView agamaEmployee = findViewById(R.id.agamaEmployee);
        TextView statKawinEmployee = findViewById(R.id.statKawinEmployee);
        TextView kwgEmployee = findViewById(R.id.kwgEmployee);
        TextView blkHingga = findViewById(R.id.blkHingga);
        TextView tempBuat = findViewById(R.id.tempBuat);
        TextView tangBuat = findViewById(R.id.tangBuat);


//        Intent intent = getIntent();
//        String nama, nip, address, gender, url_poto, tempat_lahir,
//                tanggal_lahir, goldar, agama, stat_pkwn, kewarganegaraan,
//                berlaku_hingga, tempat_buat, tanggal_buat;
//        nama = intent.getExtras().getString("nama");
//        nip = intent.getExtras().getString("nip");
//        address = intent.getExtras().getString("address");
//        gender = intent.getExtras().getString("gender");
//        url_poto = intent.getExtras().getString("url_poto");
//        tempat_lahir = intent.getExtras().getString("tempat_lahir");
//        tanggal_lahir = intent.getExtras().getString("tanggal_lahir");
//        goldar = intent.getExtras().getString("goldar");
//        agama = intent.getExtras().getString("agama");
//        stat_pkwn = intent.getExtras().getString("stat_pkwn");
//        kewarganegaraan = intent.getExtras().getString("kewarganegaraan");
//        berlaku_hingga = intent.getExtras().getString("berlaku_hingga");
//        tempat_buat = intent.getExtras().getString("tempat_buat");
//        tanggal_buat = intent.getExtras().getString("tanggal_buat");


        Intent intent = getIntent();

        namaEmployee.setText(intent.getExtras().getString("nama"));
        nipEmployee.setText(intent.getExtras().getString("nip"));
        alamatEmployee.setText(intent.getExtras().getString("address"));
        genderEmployee.setText(intent.getExtras().getString("gender"));
        Picasso.get().load(intent.getExtras().getString("url_poto"))
                .placeholder(R.drawable.ic_launcher_background).fit().into(potoEmployee);
        tempLahirEmployee.setText(intent.getExtras().getString("tempat_lahir"));
        tangLahirEmployee.setText(intent.getExtras().getString("tanggal_lahir"));
        goldarEmployee.setText(intent.getExtras().getString("goldar"));
        agamaEmployee.setText(intent.getExtras().getString("agama"));
        statKawinEmployee.setText(intent.getExtras().getString("stat_pkwn"));
        kwgEmployee.setText(intent.getExtras().getString("kewarganegaraan"));
        blkHingga.setText(intent.getExtras().getString("berlaku_hingga"));
        tempBuat.setText(intent.getExtras().getString("tempat_buat"));
        tangBuat.setText(intent.getExtras().getString("tanggal_buat"));
    }
}
