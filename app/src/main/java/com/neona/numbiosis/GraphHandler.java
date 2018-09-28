package com.neona.numbiosis;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.Arrays;

public class GraphHandler{

    private String funcao;
    private GraphView graph;

    private double menorY, maiorY;

    private LineGraphSeries<DataPoint> series_funcao;
    private PointsGraphSeries<DataPoint> series_x;
    private PointsGraphSeries<DataPoint> series_raiz;

    public GraphHandler(String funcao, GraphView graph){
        this.setFuncao(funcao);
        this.setGraph(graph);
    }

    public void initSerieFuncao(){
        initSerieFuncao(Raiz.getMenorX(),Raiz.getMaiorX());
    }

    public void initSerieFuncao(double inferior, double superior){
        setSeries_funcao(criaSerieFuncao(inferior,superior, getFuncao()));
        getSeries_funcao().setColor(Color.RED);
    }

    public void initSerieRaizes(){
        setSeriesX(criaSerieRaizes());
        getSeriesX().setShape(PointsGraphSeries.Shape.POINT);
        getSeriesX().setColor(Color.BLUE);
        getSeriesX().setSize(8f);
    }

    public void initSerieRaizFinal(double raiz){
        setSeriesRaiz(new PointsGraphSeries<>(new DataPoint[]{new DataPoint(raiz,0)}));
        getSeriesRaiz().setShape(PointsGraphSeries.Shape.POINT);
        getSeriesRaiz().setColor(Color.RED);
        getSeriesRaiz().setSize(15f);

        graph.setTitle("Raiz encontrada: "+raiz);
    }

    public void initGraph(){
        // Adiciono as series
        if(getSeries_funcao() != null)
            getGraph().addSeries(getSeries_funcao());
        if(getSeriesX() != null)
            getGraph().addSeries(getSeriesX());
        if(getSeriesRaiz() != null)
            getGraph().addSeries(getSeriesRaiz());

        // permite apenas scroll
        //getGraph().getViewport().setScrollable(true);
        //getGraph().getViewport().setScrollableY(true);

        // permite zoom e scroll
        getGraph().getViewport().setScalable(true);
        getGraph().getViewport().setScalableY(true);

        // impoe limites
        double menorX = Raiz.getMenorX() - 1;
        double maiorX = Raiz.getMaiorX() + 1;

        getGraph().getViewport().setMinimalViewport(menorX,maiorX,menorY,maiorY);

        getGraph().getViewport().setXAxisBoundsManual(true);
        getGraph().getViewport().setYAxisBoundsManual(true);


        graph.getViewport().setOnXAxisBoundsChangedListener(new Viewport.OnXAxisBoundsChangedListener() {
            double minX_ant = graph.getViewport().getMinX(true);
            double maxX_ant = graph.getViewport().getMaxX(true);
            double minDx, maxDx;
            double E = 0.2;

            @Override
            public void onXAxisBoundsChanged(double minX, double maxX, Reason reason) {
                if(reason == Reason.SCALE) {
                    minDx = Math.abs(minX_ant - minX);
                    maxDx = Math.abs(maxX_ant - maxX);

                    if(minDx > maxDx){
                        if (minDx < E)
                            minX = minX_ant;

                        minX_ant = minX;
                    }else{
                        if (maxDx < E)
                            maxX = maxX_ant;

                        maxX_ant = maxX;
                    }
                    graph.getViewport().setMinX(minX);
                    graph.getViewport().setMaxX(maxX);
                }
            }
        });


    }

    private LineGraphSeries<DataPoint> criaSerieFuncao(double menorX, double maiorX, String funcao){
        int inf = (int)Math.ceil(menorX) - 1;
        int sup = (int)Math.ceil(maiorX) + 1;
        int qtd_pontos = (Math.abs(inf) + Math.abs(sup)) * 10;
        Argument arg_x = new Argument("x");
        Expression _f = new Expression(funcao, arg_x);
        double x = inf;
        double y;
        int cont = 0;
        qtd_pontos++;

        // impondo um limite a qtd de pontos para calcular
        if(qtd_pontos > 501) qtd_pontos = 501;

        menorY = -1.0;
        maiorY =  1.0;

        DataPoint[] dataPoints = new DataPoint[qtd_pontos];
        DataPoint[] dataPoints1;

        for(int i = 0; i < qtd_pontos; i++){
            arg_x.setArgumentValue(x);
            y = _f.calculate();

            if(!Double.isInfinite(y) && !Double.isNaN(y)){
                dataPoints[cont++] = new DataPoint(x,y);
                if(y < menorY) menorY = y;
                else if(y > maiorY) maiorY = y;
            }

            x += 0.1d;
        }

        if(cont != qtd_pontos){
            dataPoints1 = Arrays.copyOf(dataPoints, cont);
            return new LineGraphSeries<>(dataPoints1);
        }

        return new LineGraphSeries<>(dataPoints);
    }

    private PointsGraphSeries<DataPoint> criaSerieRaizes(){
        DataPoint[] ar = Arrays.copyOfRange(Raiz.getDataPointsX(),0,Raiz.getNumeroIteracoes() - 1);
        insertionSort(ar, ar.length);

        return new PointsGraphSeries<>(ar);
    }

    private void insertionSort(DataPoint[] tabela, int nElem){
        int i,j;
        DataPoint aux;

        for (i = 1; i < nElem ; i++) {
            aux = tabela[i];

            // Move cada elemento maior do que aux uma posicao adiante
            for (j = i - 1; (j >= 0) && (aux.getX() < tabela[j].getX()); j--) {
                tabela[j + 1] = tabela[j];
            }

            // Posiciono aux na posicao correta
            tabela[j + 1] = aux;
        }
    }


    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public GraphView getGraph() {
        return graph;
    }

    public void setGraph(GraphView graph) {
        this.graph = graph;
    }

    public LineGraphSeries<DataPoint> getSeries_funcao() {
        return series_funcao;
    }

    public void setSeries_funcao(LineGraphSeries<DataPoint> series_funcao) {
        this.series_funcao = series_funcao;
    }

    public PointsGraphSeries<DataPoint> getSeriesX() {
        return series_x;
    }

    public void setSeriesX(PointsGraphSeries<DataPoint> series_x) {
        this.series_x = series_x;
    }

    public PointsGraphSeries<DataPoint> getSeriesRaiz() {
        return series_raiz;
    }

    public void setSeriesRaiz(PointsGraphSeries<DataPoint> series_raiz) {
        this.series_raiz = series_raiz;
    }
}
