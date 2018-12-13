package br.edu.ifsp.controlefinancas.activity.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.activity.model.Categoria;

public class CategoriaDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public CategoriaDAO(Context context) {
        this.dbHelper= new SQLiteHelper (context);
    }

    public List<Categoria> buscaTodasCategorias(){

        database=dbHelper.getReadableDatabase();
        List<Categoria> categorias = new ArrayList<>();

        Cursor cursor;

        String sql = "SELECT * FROM "
                        +SQLiteHelper.DB_TABLE_CATEGORIAS
                        +" ORDER BY "+SQLiteHelper.KEY_CATEGORIA_DESCRICAO
                        +";";
        cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

        do {
            Categoria categoria = new Categoria();

            categoria.setId(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_CATEGORIA_ID)));
            categoria.setDescricao(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CATEGORIA_DESCRICAO)));

            categorias.add(categoria);

        }while (cursor.moveToNext());

        cursor.close();
        database.close();

        return categorias;
    }

}

