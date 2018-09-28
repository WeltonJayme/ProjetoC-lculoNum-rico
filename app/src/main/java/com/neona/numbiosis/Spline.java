package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Spline extends AppCompatActivity implements  View.OnClickListener {
    EditText x0, x1, x2, x3, x4, fx0, fx1, fx2, fx3, fx4;
    String x0S, x1S, x2S, x3S, x4S, fx0S, fx1S, fx2S, fx3S, fx4S;//variaveis para captação dos dados introduzidos pelo usuário
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spline);//tela que estamos usando

        Button button = (Button) findViewById(R.id.btn_calcular_spline);//instanciamos o botão da tela
        button.setOnClickListener((View.OnClickListener ) this); //colocamos ele pra ser "escutado"

        Button btn_help_spline = (Button) findViewById(R.id.btn_help_spline);
        btn_help_spline.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View view) {
        x0 = (EditText) findViewById(R.id.editText_x0_spline);
        x1 = (EditText) findViewById(R.id.editText_x1_spline);
        x2 = (EditText) findViewById(R.id.editText_x2_spline);
        x3 = (EditText) findViewById(R.id.editText_x3_spline);
        x4 = (EditText) findViewById(R.id.editText_x4_spline);
        fx0 = (EditText) findViewById(R.id.editText_fx0_spline);
        fx1 = (EditText) findViewById(R.id.editText_fx1_spline);
        fx2 = (EditText) findViewById(R.id.editText_fx2_spline);
        fx3 = (EditText) findViewById(R.id.editText_fx3_spline);
        fx4 = (EditText) findViewById(R.id.editText_fx4_spline);

        if(x0.getText().toString().equals("")){
            x0.setText("0");
        }
        if(fx0.getText().toString().equals("")){
            fx0.setText("3");
        }
        if(x1.getText().toString().equals("")){
            x1.setText("0.5");
        }
        if(fx1.getText().toString().equals("")){
            fx1.setText("1.8616");
        }
        if(x2.getText().toString().equals("")){
            x2.setText("1.0");
        }
        if(fx2.getText().toString().equals("")){
            fx2.setText("-0.5571");
        }
        if(x3.getText().toString().equals("")){
            x3.setText("1.5");
        }
        if(fx3.getText().toString().equals("")){
            fx3.setText("-4.1987");
        }
        if(x4.getText().toString().equals("")){
            x4.setText("2.0");
        }
        if(fx4.getText().toString().equals("")){
            fx4.setText("-9.0536");
        }

        switch(view.getId()){
            case R.id.btn_calcular_spline://caso o click seja no botão calcular
                x0S = x0.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx0S = fx0.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0
                x1S = x1.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx1S = fx1.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0
                x2S = x2.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx2S = fx2.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0
                x3S = x3.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx3S = fx3.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0
                x4S = x4.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx4S = fx4.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0

                double[] x = new double[5];
                double[] y = new double[5];
                double[] resultado;

                x[0] = Double.parseDouble(x0S);
                y[0] = Double.parseDouble(fx0S);
                x[1] = Double.parseDouble(x1S);
                y[1] = Double.parseDouble(fx1S);
                x[2] = Double.parseDouble(x2S);
                y[2] = Double.parseDouble(fx2S);
                x[3] = Double.parseDouble(x3S);
                y[3] = Double.parseDouble(fx3S);
                x[4] = Double.parseDouble(x4S);
                y[4] = Double.parseDouble(fx4S);

                resultado = Raiz.Spline(x, y);

                Intent intent = new Intent(this, EquacoesSpline.class);
                intent.putExtra("resposta", resultado);
                intent.putExtra("x", x);
                startActivity(intent);
                break;

            case R.id.btn_help_spline:
                Intent intent1 = new Intent(this, HelpFalsaPosicao.class); //TROCAR ESSA TELA AINDA*********************************************************************
                startActivity(intent1);
                break;
        }
    }
}
