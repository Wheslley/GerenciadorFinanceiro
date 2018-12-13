package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.adapter.ContaDetalhesAdapter;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;

public class ContaDetalhesActivity extends AppCompatActivity {

    private TransacaoDAO transacaoDAO;
    private RecyclerView recyclerView;
    private List<TransacaoInfo> transacaoInfos;

    public ContaDetalhesActivity(){
        this.transacaoInfos = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_detalhes);

        this.transacaoDAO = new TransacaoDAO(this);
        this.recyclerView = findViewById(R.id.rv_lista_transacoes);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);

        this.recyclerView.setLayoutManager(layout);
        ContaDetalhesAdapter contaDetalhesAdapter = new ContaDetalhesAdapter(this, this.transacaoInfos);
        this.recyclerView.setAdapter(contaDetalhesAdapter);
        TextView textViewSaldo = findViewById(R.id.tv_saldo_conta_transacao);

        if (getIntent().hasExtra(MainActivity.TAG_CONTA)){
            Conta conta = (Conta) getIntent().getSerializableExtra(MainActivity.TAG_CONTA);
            getSupportActionBar().setTitle(getString(R.string.texto_conta)+": "+ conta.getDescricao());
            textViewSaldo.setText(getText(R.string.texto_saldo)+": " + conta.getSaldo() + " $");
            updateUI(conta.getId());
        }
    }

    private void updateUI(long idConta){
        this.transacaoInfos.clear();
        this.transacaoInfos.addAll(this.transacaoDAO.buscaTodasTransacoesPorConta(idConta));
        this.recyclerView.getAdapter().notifyDataSetChanged();
    }
}
