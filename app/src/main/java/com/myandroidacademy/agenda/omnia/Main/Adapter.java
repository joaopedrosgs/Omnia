package com.myandroidacademy.agenda.omnia.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myandroidacademy.agenda.omnia.CircleTransform;
import com.myandroidacademy.agenda.omnia.Entities.Contato;
import com.myandroidacademy.agenda.omnia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private RecyclerInterface recyclerInterface;
    private List<Contato> contactList;
    private Context context;

    Adapter(List<Contato> contactList, Context context){
        this.contactList = contactList;
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        return new ViewHolder(v);
    }
    @Override public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        Contato contato = contactList.get(position);

        if (contato.getCaminhoFoto() != null){
            Picasso.with(context)
                    .load("file://" + contato.getCaminhoFoto())
                    .centerCrop()
                    .transform(new CircleTransform())
                    .fit()
                    .into(holder.foto);
        }

        holder.nome.setText(contato.getNome());
    }
    @Override public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.Nome) TextView nome;
        @BindView(R.id.Foto) ImageView foto;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.container) void onItemClick(View view){
            if(recyclerInterface != null){
                recyclerInterface.onClick(view, getAdapterPosition());
            }
        }
    }
    public void setRecyclerInterface(RecyclerInterface recyclerInterface){
        this.recyclerInterface = recyclerInterface;
    }
}
