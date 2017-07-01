package com.example.vitor.listacompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CursorAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vitor on 26/06/2017.
 */

public class CompraController {
    public static final String TABELA_COMPRA = "tb_compra";
    public static final String CAMPOS[] = {"_id", "titulo", "dataPrevista", "concluido"};
    public static final String ID = "_id";
    public static final String TITULO = "titulo";
    public static final String DATA_PREVISTA = "dataPrevista";
    public static final String CONCLUIDO = "concluido";

    private SQLiteDatabase db;
    private CriaBanco banco;
    private CursorAdapter cursor;

    public CompraController(Context context) {
        banco = new CriaBanco(context);
    }

    public String insereDados(Compra compra) {
        ContentValues valores = new ContentValues();
        long resultado;

        db = banco.getWritableDatabase();

        valores.put(TITULO, compra.getTitulo());
        //SimpleDateFormat dt1 = new SimpleDateFormat("dd/mm/yyyy");
        valores.put(DATA_PREVISTA, compra.getDataPrevista());
        valores.put(CONCLUIDO, compra.isConcluido());

        resultado = db.insert(TABELA_COMPRA, null, valores);
        db.close();

        return resultado == -1 ? "Erro ao inserir compra" : "Compra adicionada";
    }

    public ArrayList<Compra> carregaDados() {
        db = banco.getWritableDatabase();
        Cursor dados = db.query(TABELA_COMPRA, CAMPOS, null, null, null, null, null);

        ArrayList<Compra> compras = null;
        if(dados != null) {
            if(!dados.moveToFirst()) return null;
            compras = new ArrayList<Compra>();
            do {
                Compra c = new Compra();
                c.setId(dados.getInt(0));
                c.setTitulo(dados.getString(1));
                c.setDataPrevista(dados.getString(2));
                c.setConcluido(dados.getInt(3) == 0 ? false : true);
                compras.add(c);
            } while (dados.moveToNext());
        }

        db.close();
        return compras;
    }

    public void alterarRegistro(Compra compra) {
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        String where = ID + " = " + compra.getId();

        valores.put(TITULO, compra.getTitulo());
        valores.put(DATA_PREVISTA, compra.getDataPrevista());
        valores.put(CONCLUIDO, compra.isConcluido());

        db.update(TABELA_COMPRA, valores, where, null);
        db.close();
    }

    public void deletaRegistro(int id) {
        String where = ID + " = " + id;
        db = banco.getReadableDatabase();
        db.delete(TABELA_COMPRA, where, null);
        db.close();
    }
}
