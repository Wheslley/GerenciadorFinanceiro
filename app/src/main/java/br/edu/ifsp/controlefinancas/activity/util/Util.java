package br.edu.ifsp.controlefinancas.activity.util;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.SimpleDateFormat;

import br.com.concrete.canarinho.watcher.ValorMonetarioWatcher;

public class Util {

    public static void showSnackBarAlert(View view, String msg) {
        esconderTeclado(view);
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static void esconderTeclado(View view){

        try {
            Context context = view.getContext().getApplicationContext();

            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setCampoMonetario(EditText editText){
        editText.addTextChangedListener(new ValorMonetarioWatcher());
        editText.getText().clear();
    }

    public static double getCampoValorMonetario(EditText editText){

        String str = editText.getText().toString();
        double valor = Double.valueOf(str.replace(".", "").replace(",","."));

        return valor;
    }

    public static String intDateForStringDate(int date){

        String ano;
        String mes;
        String dia;
        String dataFormatada = "";

        dataFormatada = String.valueOf(date);

        ano = dataFormatada.substring(0, 4);
        mes = dataFormatada.substring(4, 6);
        dia = dataFormatada.substring(6, 8);

        dataFormatada = dia+"/"+mes+"/"+ano;

        return dataFormatada;
    }

}
