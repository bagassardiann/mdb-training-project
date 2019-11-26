package com.training.day1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LupaPassword extends AppCompatActivity {
    String varEtPassBaru, varEtKonPassBaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        final EditText etPassBaru = findViewById(R.id.etPassBaru);
        final EditText etKonPassBaru = findViewById(R.id.etKonPassBaru);
        Button btnGantiPass = findViewById(R.id.btnGantiPass);

        btnGantiPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                varEtPassBaru = etPassBaru.getText().toString();
                varEtKonPassBaru = etKonPassBaru.getText().toString();

                if(varEtPassBaru.length() == 0 || varEtKonPassBaru.length() == 0 ) {
                    Toast.makeText(LupaPassword.this, "Password harap diisi", Toast.LENGTH_LONG).show();
                }else if(varEtPassBaru.length() < 4){
                    Toast.makeText(LupaPassword.this, "Password kurang dari 4 angka", Toast.LENGTH_LONG).show();
                }else if(varEtPassBaru.equals(varEtKonPassBaru)){
                    Intent login = new Intent(LupaPassword.this, MainActivity.class);
                    startActivity(login);
                }else{
                    Toast.makeText(LupaPassword.this, "Password tidak match", Toast.LENGTH_LONG).show();
                }

//                if(etPassBaru.length() > 5)

//                if (varPassBaru.length() >=5 && varPassword.length() >=4){
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
}
