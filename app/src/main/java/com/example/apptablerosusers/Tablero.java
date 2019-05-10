package com.example.apptablerosusers;

public class Tablero {
    public String id;
    public String id_user;
    public String titulo;
    public String descripcion;
    public String ideas;


    public String getId(){return id;}
    public String getIdUser(){return id_user;}
    public String getTitulo(){return titulo;}
    public String getDescripcion(){return descripcion;}
    public String getIdeas(){return ideas;}

    public void setId(String id){this.id = id;}
    public void setIdUser(String id_user){this.id_user = id_user;}
    public void setTitulo(String titulo){this.titulo = titulo;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}
    public void setIdeas(String ideas){this.ideas = ideas;}

}
