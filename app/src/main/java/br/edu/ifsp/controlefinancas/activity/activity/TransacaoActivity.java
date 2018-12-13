package br.edu.ifsp.controlefinancas.activity.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;


import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.CategoriaDAO;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.Categoria;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.Transacao;
import br.edu.ifsp.controlefinancas.activity.util.Util;

public class TransacaoActivity extends AppCompatActivity {

    private String acao = "";
    private View view;
    private TextInputEditText textInputDescricao;
    private TextInputEditText textInputValor;
    private TextInputEditText textInputData;
    private MaterialBetterSpinner spinnerCategoria;
    private MaterialBetterSpinner spinnerConta;
    private ImageButton buttonCalendario;
    private ContaDAO contaDAO;
    private CategoriaDAO categoriaDAO;
    private TransacaoDAO transacaoDAO;
    private List<Conta> contas;
    private List<Categoria> categorias;
    private long idConta = 0;
    private int idCategoria = 0;
    private int idNatureza = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);
        Intent intent = getIntent();

        this.view = findViewById(R.id.view_transacao_activity);
        this.categoriaDAO = new CategoriaDAO(this);
        this.contaDAO = new ContaDAO(this);
        this.transacaoDAO = new TransacaoDAO(this);

        if (intent != null){

            this.acao = intent.getAction();
            if (this.acao.equals(MainActivity.TAG_RECEITA)) {
                getSupportActionBar().setTitle(getString(R.string.texto_nova)+" "+getString(R.string.texto_receita));
                this.idNatureza = 1;
            }
            if (this.acao.equals(MainActivity.TAG_DESPESA)) {
                getSupportActionBar().setTitle(getString(R.string.texto_nova)+" "+getString(R.string.texto_despesa));
                this.idNatureza = 2;
            }

        }

        this.spinnerCategoria = findViewById(R.id.spinner_categoria_transacao);
        setCampoCategorias();
        this.spinnerConta = findViewById(R.id.spinner_conta_transacao);
        setCampoContas();

        this.textInputDescricao = (TextInputEditText) findViewById(R.id.et_descricao_transacao);
        this.textInputValor = (TextInputEditText) findViewById(R.id.et_valor_transacao);
        this.textInputData = (TextInputEditText) findViewById(R.id.ed_date_transacao);
        this.buttonCalendario = (ImageButton) findViewById(R.id.ib_calendario_transacao);

        this.buttonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarDialog();
            }
        });
        Util.setCampoMonetario(this.textInputValor);

    }

    private void setCampoCategorias() {

        this.categorias = this.categoriaDAO.buscaTodasCategorias();

        ArrayAdapter<Categoria> arrayAdapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        this.spinnerCategoria.setAdapter(arrayAdapter);

        if (this.categorias.size()>0 && this.categorias != null){
            this.spinnerCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    idCategoria = ((Categoria)parent.getItemAtPosition(position)).getId();
                }
            });
        }

    }

    public void setCampoContas(){

        this.contas = this.contaDAO.buscaTodasContas(0);

        ArrayAdapter<Conta> arrayAdapter = new ArrayAdapter<Conta>(this, android.R.layout.simple_spinner_dropdown_item, contas);
        this.spinnerConta.setAdapter(arrayAdapter);

        if (this.contas.size()>0 && this.contas != null){

            this.spinnerConta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    idConta = ((Conta)parent.getItemAtPosition(position)).getId();
                }
            });
        }

    }

    private void openCalendarDialog() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        DecimalFormat df = new DecimalFormat("00");

                        textInputData.setText(df.format(year)+"-"+(df.format(month + 1))+"-"+df.format(day));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    private int getDataFormatada(EditText campoData){
        String txt = campoData.getText().toString();
        return Integer.valueOf(txt.replace("-", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_transacao_salvar:
                salvarTransacao();
                return true;
            case R.id.menu_transacao_cancelar:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void salvarTransacao() {

        if (validaCampos()){
            Transacao transacao = getDados();

            boolean res = this.transacaoDAO.salvaTransacao(transacao);

            if (res) {
                this.transacaoDAO.atualizaSaldo(transacao.getId_conta(), transacao.getValor(), transacao.getNatureza());
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                Util.showSnackBarAlert(view, getString(R.string.texto_erro));
            }
        }
        else {
            Util.showSnackBarAlert(view, getString(R.string.texto_campos_vazios));
        }

    }

    private boolean validaCampos(){

        boolean ok = true;

        if (this.textInputDescricao.getText().toString().isEmpty()){
            ok = false;
        }
        if(this.idCategoria == 0){
            ok = false;
        }
        if (this.idConta == 0){
            ok = false;
        }
        if (this.textInputData.getText().toString().isEmpty()){
            ok = false;
        }
        if (this.textInputValor.getText().toString().isEmpty() || this.textInputValor.getText().toString() == "0.00"){
            ok = false;
        }
        if (this.idNatureza == 0){
            ok = false;
        }

        return ok;
    }

    private Transacao getDados(){

        Transacao transacao = new Transacao();

        transacao.setDescricao(this.textInputDescricao.getText().toString());
        transacao.setValor(Util.getCampoValorMonetario(this.textInputValor));
        transacao.setData(getDataFormatada(this.textInputData));
        transacao.setId_categoria(this.idCategoria);
        transacao.setId_conta(this.idConta);
        transacao.setNatureza(this.idNatureza);

        return transacao;
    }




}
