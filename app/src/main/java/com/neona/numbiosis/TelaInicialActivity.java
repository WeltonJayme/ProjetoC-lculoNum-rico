package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TelaInicialActivity extends AppCompatActivity implements  View.OnClickListener {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        Button btnMuller = (Button) findViewById(R.id.btn_muller);
        btnMuller.setOnClickListener((View.OnClickListener ) this);

        Button btnFalsaPosicao = (Button) findViewById(R.id.btn_falsa_posição);
        btnFalsaPosicao.setOnClickListener((View.OnClickListener ) this);

        Button btnSecante = (Button) findViewById(R.id.btn_secante);
        btnSecante.setOnClickListener((View.OnClickListener ) this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_muller:
                Intent it = new Intent(this, MullerActivity.class);
                startActivity(it);
                break;
            case R.id.btn_falsa_posição:
                Intent it2 = new Intent(this, FalsaPosicao.class);
                startActivity(it2);
                break;
            case R.id.btn_secante:
                Intent it3 = new Intent(this, Secante.class);
                startActivity(it3);
                break;

        }
    }
}
