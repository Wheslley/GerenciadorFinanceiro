package br.edu.ifsp.controlefinancas.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;

public class ContaAdapter extends RecyclerView.Adapter<ContaAdapter.ContaViewHolder> {

    private static List<Conta> contas;
    private Context context;

    private static ItemClickListener clickListener;

    public ContaAdapter(Context context, List<Conta> contas) {
        this.context = context;
        this.contas = contas;
    }

    @Override
    public ContaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.conta_celula, viewGroup, false);
        return new ContaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContaViewHolder holder, int position) {

        holder.descricao.setText(contas.get(position).getDescricao());
        holder.saldo.setText(String.valueOf(contas.get(position).getSaldo()));
        holder.idConta = contas.get(position).getId();

    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }


    public class ContaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView descricao, saldo;
        long idConta;

        public ContaViewHolder(View view) {
            super(view);
            descricao = view.findViewById(R.id.tv_descricao_conta);
            saldo = view.findViewById(R.id.tv_saldo_conta);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }

    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

}