package com.example.apptablerosusers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public List<Tablero> tablerosIdeasList = null;
    public Context context = null;


    public RecyclerViewAdapter(Context context, List<Tablero> tablerosIdeasList){
        this.context = context;
        this.tablerosIdeasList =  tablerosIdeasList;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView id,id_user,titulo,descripcion,ideas;
        CardView cardView;

        public ViewHolder(final  View itemView){
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            id_user = (TextView) itemView.findViewById(R.id.tv_id_user);
            titulo = (TextView) itemView.findViewById(R.id.tv_titulo);
            descripcion = (TextView) itemView.findViewById(R.id.tv_descripcion);
            ideas = (TextView) itemView.findViewById(R.id.tv_ideas);
            cardView.setOnClickListener(new  View.OnClickListener(){
                @Override
                public void onClick(View v){ // metodo que se ejecuta al presionar un card view
                    Intent intent = new Intent(v.getContext(), Insert_Idea.class); // si presiono el carview me envia a inserta una idea sobre el tablero seleccionado
                    intent.putExtra("id_tablero", id.getText().toString());
                    intent.putExtra("id_user", id_user.getText().toString());
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { // Se obtienen los datos enviados en la lista
        String titulo = tablerosIdeasList.get(position).getTitulo();
        String id = tablerosIdeasList.get(position).getId();
        String id_user = tablerosIdeasList.get(position).getIdUser();
        String descripcion = tablerosIdeasList.get(position).getDescripcion();
        String ideas = tablerosIdeasList.get(position).getIdeas();
        holder.id.setText(id);
        holder.id_user.setText(id_user);
        holder.titulo.setText(titulo);
        holder.descripcion.setText(descripcion);
        holder.ideas.setText(ideas);
    }

    @Override
    public int getItemCount(){
        return tablerosIdeasList.size();
    } // se retorna elk tamaño de la lista para crear las cantidad de cardviews necesarios
}

