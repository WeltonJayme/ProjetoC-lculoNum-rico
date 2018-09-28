package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EquacoesSpline extends AppCompatActivity {
    EditText eq1, eq2, eq3;
    String eq1S, eq2S, eq3S;
    double[] equações;
    double x[];
    //System.out.printf("s[%d] = %.5f(x-%.2f)^3 + %.5f(x-%.2f)^2 + %.5f(x-%.2f) + %.5f \n", i, a1[i], x[i], b[i], x[i], c[i], x[i], y[i]);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equacoes_spline);//tela que estamos usando

        Intent intent = getIntent();

        eq1  = (EditText) findViewById(R.id.equacao1);
        eq2  = (EditText) findViewById(R.id.equacao2);
        eq3  = (EditText) findViewById(R.id.equacao3);

        equações = intent.getExtras().getDoubleArray("resposta");
        x = intent.getExtras().getDoubleArray("x");
        eq1S = "s[1] = "+ equações[0] +"(x-"+ x[1] +")^3 + " + equações[3] +"(x-"+ x[1] +")^2 + "+ equações[6] +"(x-"+ x[1] +") + " +equações[9];
        eq2S = "s[2] = "+ equações[1] +"(x-"+ x[2] +")^3 + " + equações[4] +"(x-"+ x[2] +")^2 + "+ equações[7] +"(x-"+ x[2] +") + " +equações[10];
        eq3S = "s[3] = "+ equações[2] +"(x-"+ x[3] +")^3 + " + equações[5] +"(x-"+ x[3] +")^2 + "+ equações[8] +"(x-"+ x[3] +") + " +equações[11];

        eq1.setText(eq1S);
        eq2.setText(eq2S);
        eq3.setText(eq3S);

    }
}
