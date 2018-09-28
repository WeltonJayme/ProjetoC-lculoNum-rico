package com.neona.numbiosis;

import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import org.mariuszgromada.math.mxparser.*;

public class Raiz {

    private static DataPoint[] dpsX;
    private static DataPoint[][] secantes;
    private static double menorX;
    private static double maiorX;
    private static int nIteracoes;

    private static final double EPSILON = Float.MIN_NORMAL;
    public static final double TOL = 1e-5;
    public static final int N = 100;

    public static double muller(String funcao, double x0, double x1, double x2, double tol, int n) throws ArithmeticException{
        double  x3 = 0,
                fx0,fx1,fx2,
                h0,h1,
                d0,d1,
                a,b,c,
                disc,
                erro;

        Argument arg_x = new Argument("x");
        Expression _f = new Expression(funcao, arg_x);

        // inicializar maior e menor
        menorX = x0 < x1 ? x0 : x1;
        menorX = x2 < menorX ? x2 : menorX;
        maiorX = x0 > x1 ? x0 : x1;
        maiorX = x2 > maiorX ? x2 : maiorX;

        // inicializo Arrays
        dpsX = new DataPoint[n];

        for (int i = 0; i < n; i++) {

            h0 = x1 - x0;
            h1 = x2 - x1;

            // calculo fx0, fx1, fx2
            arg_x.setArgumentValue(x0);
            fx0 = _f.calculate();
            arg_x.setArgumentValue(x1);
            fx1 = _f.calculate();
            arg_x.setArgumentValue(x2);
            fx2 = _f.calculate();

            d0 = (fx1 - fx0) / (x1 - x0);
            d1 = (fx2 - fx1) / (x2 - x1);

            a = (d1 - d0) / (h1 + h0);
            b = a*h1 + d1;
            c = fx2;

            disc = Math.sqrt(b*b - 4*a*c);

            x3 = x2 + (-2*c) / (b + Math.signum(b)*disc);

            // Erro matematico, parar a execucao e tratar o erro
            if(Double.isNaN(x3) || Double.isInfinite(x3)){
                throw new ArithmeticException();
            }

            // USADO PARA PLOTAGEM ----------------
            // atualizo maior e menor
            if(x3 > maiorX) maiorX = x3;
            if(x3 < menorX) menorX = x3;

            // adiciono datapoints aos arrays
            dpsX[i] = new DataPoint(x3,0);

            // salvo a qtd de xk achados
            nIteracoes  = i + 1;
            //------------------------------------

            if(!Double.isInfinite(a) && !Double.isInfinite(b)) {
                // checar erro
                erro = erro(x2, x3, true);
                if (erro < tol && x2 != x3)
                    return x3;
            }
            // prepara para prox iteracao
            x0 = x1;
            x1 = x2;
            x2 = x3;
        }

        return x3;
    }


    public static double falsaPosicao(String funcao, double a, double b, double tol, int n) throws ArithmeticException,IllegalArgumentException{
        double  x = b, xm = 0,
                fa, fb, fxm,
                erro;

        Argument arg_x = new Argument("x");
        Expression _f = new Expression(funcao, arg_x);

        // inicializar maior e menor
        menorX = a < b ? a : b;
        maiorX = a > b ? a : b;

        // inicializo Arrays
        dpsX = new DataPoint[n];
        secantes = new DataPoint[n][2];

        for (int i = 0; i < n; i++) {
            arg_x.setArgumentValue(a);
            fa = _f.calculate();

            arg_x.setArgumentValue(b);
            fb = _f.calculate();


            if(i == 0){
                if(fa * fb > 0)
                    throw new IllegalArgumentException("A função deve ter sinais opostos em A e B");
            }

            arg_x.setArgumentValue(xm);
            fxm = _f.calculate();

            xm = (a*fb - b*fa) / (fb - fa);

            erro = erro(x, xm, true);

            x = xm;

            // Erro matematico, parar a execucao e tratar o erro
            if(Double.isNaN(x) || Double.isInfinite(x)){
                throw new ArithmeticException();
            }

            // USADO PARA PLOTAGEM ----------------
            // atualizo maior e menor
            if(xm > maiorX) maiorX = xm;
            if(xm < menorX) menorX = xm;

            // adiciono datapoints aos arrays
            dpsX[i] = new DataPoint(xm,0);
            secantes[i][0] = new DataPoint(a,fa);
            secantes[i][1] = new DataPoint(b,fb);

            // salvo a qtd de xk achados
            nIteracoes  = i + 1;
            //------------------------------------

            if (erro < tol) {
                return xm;
            }

            if(fxm * fa > 0)
                a = xm;
            else if(fxm * fa < 0)
                b = xm;
            else
                return xm;

        }

        return xm;
    }


    public static double secante(String funcao, double x0, double x1, double tol, int n) throws ArithmeticException{
        double xk = 0,
                fa, fb,
                a = x0,
                b = x1,
                erro;

        Argument arg_x = new Argument("x");
        Expression _f = new Expression(funcao, arg_x);

        // inicializar maior e menor
        menorX = x0 < x1 ? x0 : x1;
        maiorX = x0 > x1 ? x0 : x1;

        // inicializo Arrays
        dpsX = new DataPoint[n];
        secantes = new DataPoint[n][2];

        for (int i = 0; i < n; i++) {

            arg_x.setArgumentValue(a);
            fa = _f.calculate();

            arg_x.setArgumentValue(b);
            fb = _f.calculate();

            xk = (a*fb - b*fa) / (fb - fa);

            erro = erro(b, xk, true);

            // Erro matematico, parar a execucao e tratar o erro
            if(Double.isNaN(xk) || Double.isInfinite(xk)){
                throw new ArithmeticException();
            }

            // USADO PARA PLOTAGEM ----------------
            // atualizo maior e menor
            if(xk > maiorX) maiorX = xk;
            if(xk < menorX) menorX = xk;

            // adiciono datapoints aos arrays
            dpsX[i] = new DataPoint(xk,0);
            secantes[i][0] = new DataPoint(a,fa);
            secantes[i][1] = new DataPoint(b,fb);

            // salvo a qtd de xk achados
            nIteracoes  = i + 1;
            //------------------------------------

            if (erro < tol) {
                return xk;
            }

            a = b;
            b = xk;
        }
        return xk;
    }
    public static double[] Spline(double[] x, double[] y){
        // tabela x e y
        //float[] x = new float[5];
        //float[] y = new float[5];

        //float[] x = {0f, 0.5f, 1.0f, 1.5f, 2.0f};
        //float[] y = {3f, 1.8616f, -0.5571f, -4.1987f, -9.0536f};

        //float[] x = {0.9f, 1.3f, 1.9f, 2.1f, 2.6f, 3.0f, 3.9f, 4.4f, 4.7f, 5.0f, 6.0f};
        //float[] y = {1.3f, 1.5f, 1.85f, 2.1f, 2.6f, 2.7f, 2.4f, 2.15f, 2.05f, 2.1f, 2.25f};

        int n = x.length - 1;
        double[] h = new double[n+1];
        double[] alfa = new double[n+1]; //Vetor para armazenar os valores das 2ª derivadas.
        double[] resultado = new double[(n-1)*4];

        //Calculando os Delta x's.
        for (int i = 0; i <= n-1; i++) {
            h[i] = x[i+1] - x[i]; //Variaçao do x seguinte com o atual.
        }

        //Calculo da 2ª derivada
        for (int i = 1; i <= n-1; i++) {
            alfa[i] = ((3/h[i]) * (y[i+1] - y[i]) - (3/h[i-1]) * (y[i] - y[i-1]));
        }

            /*Os vetores l, u e z, sao para a resoluçao
            *de um sistema tridiagonal linear com base no
            algoritmo da fatoraçao de Crout*/
        double[] l = new double[n+1];
        double[] u = new double[n+1];
        double[] z = new double[n+1];

        l[0] = 1;
        u[0] = 0;
        z[0] = 0;

        for (int i = 1; i <= n-1; i++) {
            l[i] = ((2 * (x[i+1] - x[i-1])) - (h[i-1] * u[i-1]));
            u[i] = h[i] / l[i];
            z[i] = ((alfa[i] - (h[i-1] * z[i-1])) / l[i]);
        }

        l[n] = 1;
        z[n] = 0;

        //Vetores para os coeficientes a, b e c.
        double[] b = new double[n+1];
        double[] c = new double[n+1];
        double[] a = new double[n+1];
        double[] a1 = new double[n+1];
        b[n] = 0;

        //Calculo dos coeficientes a, b e c.
        for (int i = n-1; i >= 0; i--) {
            b[i] = z[i] - (u[i]*b[i+1]);
            c[i] = (((y[i+1] - y[i]) / h[i]) - ((h[i] * (b[i+1] + 2 * b[i])) / 3));
            a[i] = ((b[i+1] - b[i]) / (3 * h[i]));
        }

        for (int i = 0; i <= n-1; i++) {
            a1[i+1] = a[i];
        }

        for (int i = 0; i < 3; i++) {
            resultado[i] = a1[i];
            resultado[i+3] = b[i];
            resultado[i+6] = c[i];
            resultado[i+9] = y[i];
        }

        //for (int i = 1; i <= n; i++) {
            //System.out.printf("s[%d] = %.5f(x-%.2f)^3 + %.5f(x-%.2f)^2 + %.5f(x-%.2f) + %.5f \n", i, a1[i], x[i], b[i], x[i], c[i], x[i], y[i]);
        //}

        return resultado;
    }
    /**
     *
     * @param x valor anterior
     * @param xk valor encontrado
     * @param relativo true para usar erro relativo, falso para erro absoluto
     * @return O erro
     */
    private static double erro(double x, double xk, boolean relativo) {
        if (!relativo) {
            return Math.abs(x - xk);
        } else {
            return Math.abs(x - xk) / Math.abs(xk + EPSILON);
        }

    }

    public static double getMenorX(){ return menorX; }
    public static double getMaiorX(){ return maiorX; }
    public static DataPoint[] getDataPointsX() { return dpsX; }
    public static DataPoint[][] getSecantes() { return secantes; }
    public static int getNumeroIteracoes(){ return nIteracoes; }
}
