package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    String f = "x^3 - 6*x^2 -x + 30";
    double raiz;
    double  a = -3,
            b = 0,
            c = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTeste = (Button) findViewById(R.id.btn_teste);
        btnTeste.setOnClickListener((View.OnClickListener ) this);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        /**
         * ----------------------------------------------------
         * Plotando o grafico:
         *      1: Chamo um dos metodos na classe Raiz e encontro a raiz da funcao
         *      2: Chamo os metodos da classe GraphHandler na ordem abaixo
         *----------------------------------------------------
         * */

        //raiz = Raiz.secante(f,a,b,Raiz.TOL,Raiz.N);
        //raiz = Raiz.falsaPosicao(f,a,b,Raiz.TOL,Raiz.N);
        raiz = Raiz.muller(f,a,b,c,Raiz.TOL,Raiz.N);

        GraphHandler handler = new GraphHandler(f, graph);
        handler.initSerieFuncao();
        handler.initSerieRaizes();
        handler.initSerieRaizFinal(raiz);
        handler.initGraph();
        //----------------------------------------------------


        // USADO SO PARA PRINTAR NO Logcat/Terminal E ACOMPANHAR OS VALORES
        //String resumo = "Raiz: "+raiz+"  MenorX: "+Raiz.getMenorX()+"  MaiorX: "+Raiz.getMaiorX();
        //Log.d("resumo",resumo);
        //Log.d("resumo","Iteracoes: "+Raiz.getNumeroIteracoes());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_teste:
                Intent it = new Intent(this, TelaInicialActivity.class);
                startActivity(it);
                break;
        }
    }
}
