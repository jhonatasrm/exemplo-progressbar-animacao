package com.jhonatasrm.exemplo_progressbar_animacao;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView texto;
    private ProgressBar progress;
    private int tempoAnimacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define o tempo de animação
        tempoAnimacao = getResources().getInteger(android.R.integer.config_longAnimTime);

        texto = findViewById(R.id.text_msg);
        progress = findViewById(R.id.progress);

        texto.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                
                // modifica main activity na thread principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mostrarConteudo();
                    }
                });
            }
        }).start();

    }

    private void mostrarConteudo() {
        // torna o texto visível mas deixa invisivel ao definir o alpha como 0.0f
        texto.setVisibility(View.VISIBLE);
        texto.setAlpha(0.0f);

        // animação que modifica o alpha de 0.0f para visível 1.0f no tempo de animação definido
        texto.animate()
                .alpha(1.0f)
                .setDuration(tempoAnimacao)
                .setListener(null);

        // remove a visibilidade no tempo de animação definido para tornar suave a transição
        progress.animate()
                .alpha(0.0f)
                .setDuration(tempoAnimacao)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        progress.setVisibility(View.GONE);
                    }
                });
    }
}
