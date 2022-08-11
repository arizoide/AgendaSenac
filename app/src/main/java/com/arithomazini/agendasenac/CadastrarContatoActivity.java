package com.arithomazini.agendasenac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.arithomazini.agendasenac.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class CadastrarContatoActivity extends AppCompatActivity {

    public static List<Contato> contatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_contato);

        Button cadastrar = findViewById(R.id.buttonCadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editNome = findViewById(R.id.editTextNome);
                EditText editEmail = findViewById(R.id.editTextEmail);
                EditText editTelefone = findViewById(R.id.editTextTelefone);

                Contato contato = new Contato();
                contato.setNome(editNome.getText().toString());
                contato.setEmail(editEmail.getText().toString());
                contato.setTelefone(editTelefone.getText().toString());

                contatos.add(contato);

                Intent intent = new Intent(CadastrarContatoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}