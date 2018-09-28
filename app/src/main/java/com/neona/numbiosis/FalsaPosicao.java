package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FalsaPosicao extends AppCompatActivity implements  View.OnClickListener {
    double raiz;
    EditText funcao , a,b,tol,n;
    String funcaoS,aS,bS,tolS,nS;//variaveis para captação dos dados introduzidos pelo usuário
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falsa_posicao);//tela que estamos usando

        Button button = (Button) findViewById(R.id.button_falsaPosição);//instanciamos o botão da tela
        button.setOnClickListener((View.OnClickListener ) this); //colocamos ele pra ser "escutado"

        Button btn_help_falsa_posicao = (Button) findViewById(R.id.btn_help_falsa_posicao);
        btn_help_falsa_posicao.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View view) {

        funcao = (EditText) findViewById(R.id.editText_função_falsaPosição); //instanciamos as caixas de texto da tela
        a = (EditText) findViewById(R.id.editText_a_falsaPosição);
        b = (EditText) findViewById(R.id.editText_b_falsaPosição);
        tol = (EditText) findViewById(R.id.editText_tol_falsaPosição);
        n = (EditText) findViewById(R.id.editText_n_falsPosição);

        if(funcao.getText().toString().equals("")){
            funcao.setText("x^2 + x - 6");
        }
        if(a.getText().toString().equals("")){
            a.setText("-8");
        }
        if(b.getText().toString().equals("")){
            b.setText("1.5");
        }
        if(tol.getText().toString().equals("")){
            tol.setText(getText(R.string.txt_hint_ERRO));
        }
        if(n.getText().toString().equals("")) {
            n.setText(R.string.txt_hint_N);
        }

        switch(view.getId()){
            case R.id.button_falsaPosição://caso o click seja no botão calcular
            try {
                funcaoS = funcao.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                aS = a.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                bS = b.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                tolS = tol.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                nS = n.getText().toString();


                double a, b, tol;
                int n;
                n = Integer.parseInt(nS);
                a = Double.parseDouble(aS);
                b = Double.parseDouble(bS);
                tol = Double.parseDouble(tolS);

                if(n >= 0) {
                        try {
                            raiz = Raiz.falsaPosicao(funcaoS, a, b, tol, n);

                            Intent intent = new Intent(this, PlotagemActivity.class);
                            intent.putExtra("funcao", funcaoS);
                            intent.putExtra("raiz", raiz);
                            startActivity(intent);
                        } catch (ArithmeticException ex) {
                            Toast.makeText(getApplicationContext(),"Raiz não encontrada.\nVerifique se os valores estão corretos.",Toast.LENGTH_LONG).show();
                        } catch (IllegalArgumentException ex){
                            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                        }
                }else{
                    Toast.makeText(getApplicationContext(), "Máximo de Iterações deve ser positivo.", Toast.LENGTH_LONG).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(getApplicationContext(),"Erro encontrado.\nConfirme os valores escritos.", Toast.LENGTH_LONG).show();

            }
            break;

            case R.id.btn_help_falsa_posicao:
                Intent intent = new Intent(this, HelpFalsaPosicao.class);
                startActivity(intent);
                break;
        }
    }
}
