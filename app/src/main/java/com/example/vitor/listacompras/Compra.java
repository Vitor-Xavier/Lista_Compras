package com.example.vitor.listacompras;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vitor on 21/06/2017.
 */

public class Compra implements Serializable {
    private int id;
    private String titulo;
    private String dataPrevista;
    private boolean concluido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public String getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(String dataPrevista) {
        this.dataPrevista = dataPrevista;
    }
}
