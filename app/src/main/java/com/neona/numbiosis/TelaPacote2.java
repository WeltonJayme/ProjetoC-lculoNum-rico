package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TelaPacote2 extends AppCompatActivity implements  View.OnClickListener {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacote2);

        Button btn_Spline = (Button) findViewById(R.id.btn_spline);
        btn_Spline.setOnClickListener((View.OnClickListener ) this);

        //Button btnFalsaPosicao = (Button) findViewById(R.id.btn_falsa_posição);
        //btnFalsaPosicao.setOnClickListener((View.OnClickListener ) this);

        //Button btnSecante = (Button) findViewById(R.id.btn_secante);
        //btnSecante.setOnClickListener((View.OnClickListener ) this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //case R.id.btn_muller:
            //    Intent it = new Intent(this, MullerActivity.class);
            //    startActivity(it);
            //    break;
            //case R.id.btn_falsa_posição:
            //    Intent it2 = new Intent(this, FalsaPosicao.class);
            //    startActivity(it2);
            //    break;
            case R.id.btn_spline:
                Intent it3 = new Intent(this, Spline.class);
                startActivity(it3);
                break;

        }
    }
}
