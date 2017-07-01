package com.example.vitor.listacompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CursorAdapter;

import java.util.ArrayList;

/**
 * Created by vitor on 26/06/2017.
 */

public class ItemController {
    public static final String TABELA_ITEM = "tb_item";
    public static final String CAMPOS[] = {"_id", "id_compra", "nome", "concluido"};
    public static final String ID = "_id";
    public static final String ID_COMPRA = "id_compra";
    public static final String NOME = "nome";
    public static final String CONCLUIDO = "concluido";

    private SQLiteDatabase db;
    private CriaBanco banco;
    private CursorAdapter cursor;

    public ItemController(Context context) {
        banco = new CriaBanco(context);
    }

    public ArrayList<Item> carregaDados(int id) {
        db = banco.getWritableDatabase();
        String where = ID_COMPRA + " = " + id;
        Cursor dados = db.query(TABELA_ITEM, CAMPOS, where, null, null, null, null);

        ArrayList<Item> itens = new ArrayList<Item>();
        if(dados != null) {
            if(!dados.moveToFirst()) return null;
            do {
                Item c = new Item();
                c.setId(dados.getInt(0));
                c.setNome(dados.getString(2));
                c.setId_compra(dados.getInt(1));
                c.setConcluido(dados.getInt(3) == 0 ? false : true);
                itens.add(c);
            } while (dados.moveToNext());
        }
        db.close();

        return itens.isEmpty() ? null : itens;
    }

    public String insereDados(Item item) {
        ContentValues valores = new ContentValues();
        long resultado;

        db = banco.getWritableDatabase();

        valores.put(NOME, item.getNome());
        valores.put(ID_COMPRA, item.getId_compra());
        valores.put(CONCLUIDO, item.isConcluido());

        resultado = db.insert(TABELA_ITEM, null, valores);
        db.close();

        return resultado == -1 ? "Erro ao inserir item" : "Item adicionado";
    }

    public void alterarRegistro(Item item) {
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        String where = ID + " = " + item.getId();

        valores.put(NOME, item.getNome());
        valores.put(ID_COMPRA, item.getId_compra());
        valores.put(CONCLUIDO, item.isConcluido());

        db.update(TABELA_ITEM, valores, where, null);
        db.close();
    }

    public void deletaRegistro(int id) {
        String where = ID + " = " + id;
        db = banco.getReadableDatabase();
        db.delete(TABELA_ITEM, where, null);
        db.close();
    }
}
