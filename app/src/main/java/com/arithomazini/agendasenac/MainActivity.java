package com.arithomazini.agendasenac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.arithomazini.agendasenac.dao.ContatoDAO;
import com.arithomazini.agendasenac.model.Contato;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContatoDAO dao = new ContatoDAO(this, "agenda", null, 1);
        ListView listView = findViewById(R.id.ListViewContatos);

        listView.setAdapter(new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, dao.listar()));

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CadastrarContatoActivity.class);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intencao = new Intent(MainActivity.this, CadastrarContatoActivity.class);
                String nome = listView.getItemAtPosition(position).toString();
                intencao.putExtra("NOME", nome);
                startActivity(intencao);
                return false;
            }
        });

    }
}