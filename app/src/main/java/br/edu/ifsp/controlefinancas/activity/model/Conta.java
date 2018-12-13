package br.edu.ifsp.controlefinancas.activity.model;

import java.io.Serializable;

public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String descricao;
    private double saldo;

    public Conta() {
    }

    public Conta(long id, String descricao, double saldo) {
        this.id = id;
        this.descricao = descricao;
        this.saldo = saldo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
