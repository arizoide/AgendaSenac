package com.arithomazini.agendasenac.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.arithomazini.agendasenac.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends SQLiteOpenHelper {
    public ContatoDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableContato = "CREATE TABLE CONTATO (" +
                "ID INTEGER PRIMARY KEY," +
                "NOME TEXT, " +
                "EMAIL TEXT," +
                "TELEFONE TEXT );";

        sqLiteDatabase.execSQL(tableContato);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void salvar(Contato contato){
        ContentValues c = new ContentValues();
        c.put("NOME", contato.getNome());
        c.put("EMAIL", contato.getEmail());
        c.put("TELEFONE", contato.getTelefone());

        SQLiteDatabase db = getWritableDatabase();

        db.insert("CONTATO", null, c);

    }

    public List<Contato> listar(){
        SQLiteDatabase db = getReadableDatabase();

        List<Contato> contatos = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM CONTATO", null);

        if(cursor.moveToFirst()){
            do {
                Contato c = new Contato();
                c.setNome(cursor.getString(1));
                c.setEmail(cursor.getString(2));
                c.setTelefone(cursor.getString(3));
                contatos.add(c);
            } while (cursor.moveToNext());
        }

        return contatos;
    }

    public Contato listarByNome(String nome) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CONTATO WHERE NOME = ?", new String[]{nome});
        Contato c = new Contato();
        if(cursor.moveToFirst()){
            do {
                c.setId(cursor.getInt(0));
                c.setNome(cursor.getString(1));
                c.setEmail(cursor.getString(2));
                c.setTelefone(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return c;
    }

    public void atualizar(Contato c) {
        ContentValues cv = new ContentValues();
        cv.put("NOME", c.getNome());
        cv.put("EMAIL", c.getEmail());
        cv.put("TELEFONE", c.getTelefone());

        SQLiteDatabase db = getWritableDatabase();

        db.update("CONTATO", cv, "ID = ?", new String[]{c.getId().toString()});
    }
}
