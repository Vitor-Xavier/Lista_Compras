package com.example.vitor.listacompras;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vitor on 26/06/2017.
 */

public class CriaBanco extends SQLiteOpenHelper {
    private static final String BANCO = "test2.db";
    private static final int VERSAO = 2;

    public CriaBanco(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCompra = "CREATE TABLE " + CompraController.TABELA_COMPRA + " ("
                + CompraController.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CompraController.TITULO + " TEXT, "
                + CompraController.DATA_PREVISTA + " TEXT, "
                + CompraController.CONCLUIDO + " INTEGER )";
        db.execSQL(sqlCompra);

        String sqlItem = "CREATE TABLE " + ItemController.TABELA_ITEM + " ("
                + ItemController.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemController.ID_COMPRA + " INTEGER, "
                + ItemController.NOME + " TEXT, "
                + ItemController.CONCLUIDO + " INTEGER, "
                + "FOREIGN KEY(" + ItemController.ID_COMPRA + ") REFERENCES "
                + CompraController.TABELA_COMPRA + "(" + CompraController.ID + ") )";
        db.execSQL(sqlItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemController.TABELA_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + CompraController.TABELA_COMPRA);
        onCreate(db);
    }
}
