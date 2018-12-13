package br.edu.ifsp.controlefinancas.activity.model;

public class Transacao{

    private long id;
    private long id_conta;
    private int id_categoria;
    private String descricao;
    private int natureza;
    private int data;
    private double valor;

    public Transacao() {

    }

    public Transacao(long id, long id_conta, int id_categoria, String descricao, int natureza, int data, float valor) {
        this.id = id;
        this.id_conta = id_conta;
        this.id_categoria = id_categoria;
        this.descricao = descricao;
        this.natureza = natureza;
        this.data = data;
        this.valor = valor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_conta() {
        return id_conta;
    }

    public void setId_conta(long id_conta) {
        this.id_conta = id_conta;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
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
