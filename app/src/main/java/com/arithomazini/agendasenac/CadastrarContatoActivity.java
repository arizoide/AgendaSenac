package com.arithomazini.agendasenac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arithomazini.agendasenac.dao.ContatoDAO;
import com.arithomazini.agendasenac.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class CadastrarContatoActivity extends AppCompatActivity {

    public static List<Contato> contatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_contato);

        ContatoDAO dao = new ContatoDAO(CadastrarContatoActivity.this, "agenda", null, 1);

        EditText editNome = findViewById(R.id.editTextNome);
        EditText editEmail = findViewById(R.id.editTextEmail);
        EditText editTelefone = findViewById(R.id.editTextTelefone);

        Button cadastrar = findViewById(R.id.buttonCadastrar);

        cadastrar.setText("Cadastrar");

        String nome = getIntent().getStringExtra("NOME");

        Contato contato = null;

        if(nome != null && !nome.isEmpty()) {
            contato = dao.listarByNome(nome);
            editNome.setText(contato.getNome());
            editEmail.setText(contato.getEmail());
            editTelefone.setText(contato.getTelefone());
            cadastrar.setText("Atualizar");
        }



        Contato finalContato = contato;
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contato c = new Contato();
                c.setNome(editNome.getText().toString());
                c.setEmail(editEmail.getText().toString());
                c.setTelefone(editTelefone.getText().toString());

                Contato contatoExistente = dao.listarByNome(editNome.getText().toString());

                if (contatoExistente != null && contatoExistente.getId() != null) {
                    Toast.makeText(CadastrarContatoActivity.this, "Nome já existe", Toast.LENGTH_SHORT).show();
                } else {
                    if (nome != null && !nome.isEmpty()) {
                        c.setId(finalContato.getId());
                        dao.atualizar(c);

                        getIntent().putExtra("NOME", "");
                    } else {
                        dao.salvar(c);
                    }

                    Intent intent = new Intent(CadastrarContatoActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}