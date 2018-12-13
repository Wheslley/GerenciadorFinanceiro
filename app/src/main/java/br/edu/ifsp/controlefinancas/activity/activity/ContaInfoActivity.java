package br.edu.ifsp.controlefinancas.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import br.com.concrete.canarinho.watcher.ValorMonetarioWatcher;
import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.util.Util;

public class ContaInfoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String CONSTANTE_TAG = ContaInfoActivity.class.getSimpleName();

    private ContaDAO contaDAO;
    private View view;
    private EditText editTextDescricao;
    private EditText editTextSaldo;
    private MaterialButton buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_info);
        getSupportActionBar().setTitle(R.string.texto_nova_conta);

        this.view = findViewById(R.id.coordinator_conta_info);
        this.editTextDescricao = findViewById(R.id.textInput_ed_descricao_conta_info);
        this.editTextSaldo = findViewById(R.id.textInput_ed_saldo_conta_info);
        setCampoMonetario(editTextSaldo);

        this.buttonSalvar = findViewById(R.id.button_salvar_conta_info);
        this.buttonSalvar.setOnClickListener(this);
        this.contaDAO = new ContaDAO(this);

    }

    public void setCampoMonetario(EditText editText) {
        editText.addTextChangedListener(new ValorMonetarioWatcher());
        editText.getText().clear();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_salvar_conta_info:
                if (validaCampos()) {
                    salvarConta();
                } else {
                    Util.showSnackBarAlert(view, getString(R.string.texto_campos_vazios));
                }
                break;

        }
    }

    private void salvarConta() {

        Conta conta = new Conta();

        String desc = this.editTextDescricao.getText().toString();
        double saldo = Util.getCampoValorMonetario(this.editTextSaldo);

        conta.setDescricao(desc);
        conta.setSaldo(saldo);
        this.contaDAO.salvaConta(conta);

        Intent result = new Intent();
        setResult(RESULT_OK, result);
        Log.d(CONSTANTE_TAG, String.valueOf(R.string.texto_conta_ok));
        finish();
    }

    public boolean validaCampos() {

        boolean ok = true;

        if (this.editTextDescricao.getText().toString().trim().isEmpty()) {
            ok = false;
        }

        if (this.editTextSaldo.getText().toString().trim().isEmpty()) {
            ok = false;
        }

        return ok;
    }

}
