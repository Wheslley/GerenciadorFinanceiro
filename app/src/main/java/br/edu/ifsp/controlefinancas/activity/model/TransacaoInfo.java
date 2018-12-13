package br.edu.ifsp.controlefinancas.activity.model;

public class TransacaoInfo {

    private long id_transacao;
    private long id_conta;
    private String categoria;
    private String descricao;
    private int natureza;
    private int data;
    private double valor;

    public long getId_transacao() {
        return id_transacao;
    }

    public void setId_transacao(long id_transacao) {
        this.id_transacao = id_transacao;
    }

    public long getId_conta() {
        return id_conta;
    }

    public void setId_conta(long id_conta) {
        this.id_conta = id_conta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNatureza() {
        return natureza;
    }

    public void setNatureza(int natureza) {
        this.natureza = natureza;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


}
