package com.denisfernandez.endevina_numero;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    // Variables del juego
    private int numeroSecret; // número que hay que adivinar
    private int intents;      // número de intentos

    // Referencias a las vistas
    private EditText etNumero;
    private Button btnComprovar;
    private TextView tvHistorial, tvIntents;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Enlazar elementos del layout
        etNumero = findViewById(R.id.etNumero);
        btnComprovar = findViewById(R.id.btnComprovar);
        tvHistorial = findViewById(R.id.tvHistorial);
        tvIntents = findViewById(R.id.tvIntents);
        scrollView = findViewById(R.id.scrollView);

    // Empezar la primera partida
        iniciarPartida();

    // Listener del botón
        btnComprovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarNumero();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void iniciarPartida() {
        numeroSecret = new Random().nextInt(100) + 1; // 1 a 100
        intents = 0;
        tvHistorial.setText(""); // limpiar historial
        tvIntents.setText("Intents: 0"); // reset contador
        Log.i("INFO", "Nou número generat: " + numeroSecret);
    }

    private void comprovarNumero() {
        String text = etNumero.getText().toString();

        if (text.isEmpty()) {
            Toast.makeText(this, "Introdueix un número!", Toast.LENGTH_SHORT).show();
            return;
        }

        int numUsuari = Integer.parseInt(text);
        intents++;
        tvIntents.setText("Intents: " + intents);
        Log.i("INFO", "Número introduït: " + numUsuari);

        if (numUsuari == numeroSecret) {
            tvHistorial.append("Has encertat el número " + numeroSecret + " en " + intents + " intents!\n");
            mostrarFiPartida();
        } else if (numUsuari < numeroSecret) {
            tvHistorial.append(numUsuari + " → És més gran\n");
        } else {
            tvHistorial.append(numUsuari + " → És més petit\n");
        }

        etNumero.setText(""); // limpiar campo
        etNumero.requestFocus(); // devolver foco
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN)); // scroll al final
    }

    private void mostrarFiPartida() {
        new AlertDialog.Builder(this)
                .setTitle("Enhorabona!")
                .setMessage("Has encertat el número en " + intents + " intents.\nVols jugar de nou?")
                .setPositiveButton("Sí", (dialog, which) -> iniciarPartida())
                .setNegativeButton("No", null)
                .show();
    }

}