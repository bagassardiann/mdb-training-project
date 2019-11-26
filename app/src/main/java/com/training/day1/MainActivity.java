 package com.training.day1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;

 public class MainActivity extends AppCompatActivity {

     GridLayout mainGrid;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mainGrid = findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

    }

     private void setSingleEvent (GridLayout mainGrid){
         for (int i = 0;i<mainGrid.getChildCount();i++){
             CardView cardView = (CardView)mainGrid.getChildAt(i);
             final int finalI = i;
             cardView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (finalI == 0 ) //open activity one
                     {
                         Intent intent = new Intent(MainActivity.this, OfficeActivity.class);
                         startActivity(intent);
                     }
                     else if (finalI == 1 ) //open activity two
                     {
                         Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                         startActivity(intent);
                     }
                     else if (finalI == 2 ) //open activity three
                     {
                         Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                         startActivity(intent);
                     }
                     else if (finalI == 3 ) //open activity 4
                     {
                         Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                         startActivity(intent);
                         finish();
                     }
                 }
             });
         }
     }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        //Menambahkan item2 ke dalam class
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.optionmenu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item){
//        if (item.getItemId() == R.id.dataKaryawan){
//            startActivity(new Intent(MainActivity.this, EmployeeActivity.class));
//        }else if(item.getItemId() == R.id.tentangApps){
//            startActivity(new Intent(MainActivity.this, AboutActivity.class));
//        }else if(item.getItemId() == R.id.office){
//            startActivity(new Intent(MainActivity.this, OfficeActivity.class));
//        }else if(item.getItemId() == R.id.keluar){
//            moveTaskToBack(false);
//            onDestroy();
//            finish();
//
//        }
//        return true;
//    }




}
