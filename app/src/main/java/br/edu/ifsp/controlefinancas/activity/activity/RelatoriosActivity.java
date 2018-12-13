package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;

public class RelatoriosActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton buttonBusca;
    private RadioGroup radioGroup;
    private List<DataEntry> data;
    private List<TransacaoInfo> transacoes;
    private List<Conta> contas = new ArrayList<>();
    private TransacaoDAO transacaoDAO;
    private ContaDAO contaDAO;
    private Pie pie;
    private AnyChartView anyChartView;

    public RelatoriosActivity(){
        data = new ArrayList<>();
        transacoes = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);
        getSupportActionBar().setTitle(R.string.texto_rel);
        transacaoDAO = new TransacaoDAO(this);
        contaDAO = new ContaDAO(this);
        buttonBusca = findViewById(R.id.btnBuscar_relatorios);
        radioGroup = findViewById(R.id.rd_group_tiposCategorias);
        anyChartView = findViewById(R.id.piechart_relatorios);
        buttonBusca.setOnClickListener(this);
        pie = new AnyChart().pie();
        pie.fill("aquastyle");
        anyChartView.setChart(pie);
        pie.animation().enabled(true);
        pie.animation().duration(1500);

    }

    public void buscar(){


        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.rb_categoria:
                setGraficoCategoria(0);
                break;
            case R.id.rb_conta:
                setGraficoConta(0);
                break;

        }

    }

    private void refreshChart(){

        anyChartView.invalidate();
        transacoes.clear();
        contas.clear();

        data.clear();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnBuscar_relatorios:
                buscar();
                break;
        }

    }

    private void setGraficoCategoria(long idCategoria) {

        refreshChart();
        pie.title(getString(R.string.texto_rel) + ": " + getString(R.string.texto_cat));
        transacoes = transacaoDAO.buscaTransacaoPorCategoria(idCategoria);
        for (TransacaoInfo t : transacoes) data.add(new ValueDataEntry(t.getDescricao(), t.getValor()));
        pie.data(data);
        pie.draw(true);

    }

    private void setGraficoConta(long idConta){

        refreshChart();
        pie.title(getString(R.string.texto_rel) + ": " + getString(R.string.texto_conta));
        contas = contaDAO.buscaTodasContas(0);
        for (Conta c : contas) data.add(new ValueDataEntry(c.getDescricao(), c.getSaldo()));
        pie.data(data);
        pie.draw(true);

    }


}
