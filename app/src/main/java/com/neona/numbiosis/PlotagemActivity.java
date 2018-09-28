package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;

import java.text.NumberFormat;

public class PlotagemActivity extends AppCompatActivity {

    GraphHandler handler;
    String funcao;
    double raiz;
    ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotagem);

        Intent intent = getIntent();
        try {
            funcao = intent.getExtras().getString("funcao");

            String titulo = "f(x) = " + funcao;
            setTitle(titulo);

            final GraphView graph = (GraphView) findViewById(R.id.graph); // instanciamos o gráfico
            graph.removeAllSeries();
            graph.computeScroll();
            graph.setTitle("");
            graph.getGridLabelRenderer().setLabelVerticalWidth(80);

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf,nf));

            scaleGestureDetector = new ScaleGestureDetector(graph.getContext(),new Gesture());

            View.OnTouchListener touchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    graph.onTouchEvent(event);
                    return scaleGestureDetector.onTouchEvent(event);
                }
            };
            graph.setOnTouchListener(touchListener);

            // Plota o grafico
            handler = new GraphHandler(funcao, graph);
            handler.initSerieFuncao();
            boolean raiz_encontrada = intent.getBooleanExtra("raiz_ok", true);
            if(raiz_encontrada) {
                raiz = intent.getExtras().getDouble("raiz");
                handler.initSerieRaizes();
                handler.initSerieRaizFinal(raiz);
            }
            handler.initGraph();

        }catch(NullPointerException ex){
            Toast.makeText(getApplicationContext(),"Ocorreu um erro.", Toast.LENGTH_SHORT).show();
        }
    }

    private class Gesture extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }
    }

}
