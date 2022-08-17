package com.arithomazini.agendasenac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ContatoDAO dao = new ContatoDAO(CadastrarContatoActivity.this, "agenda", null, 1);

        EditText editNome = findViewById(R.id.editTextNome);
        EditText editEmail = findViewById(R.id.editTextEmail);
        EditText editTelefone = findViewById(R.id.editTextTelefone);

        Button cadastrar = findViewById(R.id.buttonCadastrar);

        Button atualizar = findViewById(R.id.buttonAtualizar);

        cadastrar.setText("Cadastrar");

        String nome = getIntent().getStringExtra("NOME");

        Contato contato = null;

        cadastrar.setVisibility(View.VISIBLE);
        atualizar.setVisibility(View.INVISIBLE);
        myToolbar.setVisibility(View.INVISIBLE);

        if (nome != null && !nome.isEmpty()) {
            contato = dao.listarByNome(nome);
            editNome.setText(contato.getNome());
            editEmail.setText(contato.getEmail());
            editTelefone.setText(contato.getTelefone());
            cadastrar.setVisibility(View.INVISIBLE);
            atualizar.setVisibility(View.VISIBLE);
            myToolbar.setVisibility(View.VISIBLE);
        }

        ImageView image = findViewById(R.id.imagemTrash);

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

                    dao.salvar(c);
                    Intent intent = new Intent(CadastrarContatoActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contato contatoExistente = dao.listarByNome(editNome.getText().toString());

                if (contatoExistente.getId()!= null && contatoExistente.getId() != finalContato.getId()){
                    Toast.makeText(CadastrarContatoActivity.this, "Nome já existe", Toast.LENGTH_SHORT).show();
                } else {
                    Contato c = new Contato();
                    c.setNome(editNome.getText().toString());
                    c.setEmail(editEmail.getText().toString());
                    c.setTelefone(editTelefone.getText().toString());
                    c.setId(finalContato.getId());
                    dao.atualizar(c);
                    getIntent().putExtra("NOME", "");
                    Intent intent = new Intent(CadastrarContatoActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contato contatoExistente = dao.listarByNome(editNome.getText().toString());
                dao.remover(contatoExistente.getId());
                Intent intent = new Intent(CadastrarContatoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}