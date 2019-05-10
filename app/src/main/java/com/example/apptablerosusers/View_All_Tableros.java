package com.example.apptablerosusers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class View_All_Tableros extends AppCompatActivity {
    RecyclerViewAdapter adapter;
    List<Tablero> tablerosIdeasList;
    RecyclerView recyclerView;
    String JSON_STRING;
    String JSON_STRING_IDEAS;
    String id_user;

    @Override
    protected  void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mis_tableros);

        Intent intent = getIntent();
        id_user = intent.getStringExtra("id_user"); // Se obtiene el paramentro id_user enviado desde la anterior vista


        // Se ontiene el recicler view encargado de comunicarse con xml para mostrar los datos
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getJsonIdeas();
        getJsonTableros();


        final Button boton_volver_menu = (Button) findViewById(R.id.back);
        boton_volver_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoform = new Intent(View_All_Tableros.this,MainActivity.class);
                startActivity(nuevoform);
            }
        });
    }

    public void getJsonTableros(){
        class GetJsonTableros extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void  onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(View_All_Tableros.this, "Recuperando datos...","Porfavor espere...", false,false);

            }

            @Override
            protected String doInBackground(Void... params){
                RequestHandler rh = new RequestHandler();
                String s = rh.sendgetRequest(Config.GET_TABLEROS); // se obtienen los tableros
                return s;
            }

            @Override
            protected void  onPostExecute(String s){
                super.onPostExecute(s);
                progressDialog.dismiss();
                JSON_STRING = s;
                mostrarTablerosIdeas();
            }

        }
        GetJsonTableros jsonTableros = new GetJsonTableros();
        jsonTableros.execute();
    }

    public void mostrarTablerosIdeas(){
        JSONArray jsonArray_tableros;
        JSONArray jsonArray_ideas;
        try{
            jsonArray_tableros = new JSONArray(JSON_STRING); // Se crea un array con los tableros obtenidos
            jsonArray_ideas = new JSONArray(JSON_STRING_IDEAS); // Se crea un array con las ideas obtenidas
            tablerosIdeasList = new ArrayList<>();
            System.out.println(jsonArray_tableros.length());
            if (jsonArray_tableros.length() > 0){
                System.out.println("if");
                for (int i = 0; i<jsonArray_tableros.length();i++){ // Se recorre el array de tableros
                    JSONObject tableros = jsonArray_tableros.optJSONObject(i);
                    Tablero tablero = new Tablero();
                    String ideas_tablero = "";
                    int count = 0;

                    if(tableros.optString("creado_por").equals(id_user)){ // se valida que el tablero pertenezca al usuario logueado
                        System.out.println("tablero encontrado");
                        tablero.setId(tableros.optString("id"));
                        tablero.setTitulo(tableros.optString("titulo"));
                        tablero.setDescripcion(tableros.optString("descripcion"));

                        for (int i_ideas = 0; i_ideas<jsonArray_ideas.length();i_ideas++){ // se recorren las ideas
                            JSONObject idea = jsonArray_ideas.optJSONObject(i_ideas);
                            if(idea.optString("tablero").equals(tableros.optString("id"))){ // se valida que la idea perteneza al tablero
                                count++;
                                ideas_tablero += (count+") "+idea.optString("descripcion")+"\n");
                            }
                        }


                        tablero.setIdeas(ideas_tablero);
                        tablero.setIdUser(id_user);
                        tablerosIdeasList.add(tablero); // se guarda el registro en la lista
                    }
                    System.out.println(tablerosIdeasList.size());
                    System.out.println("FSDFDfdfdsf");
                    if (tablerosIdeasList.size()<= 0){ // Si el usuario no tiene tableros se muetra notificacion
                        Toast.makeText(getApplicationContext(),"No hay tableros registrados para este usuario",Toast.LENGTH_LONG).show();

                    }

                }
            }else {
                Toast.makeText(getApplicationContext(),"No hay tableros registrados para este usuario",Toast.LENGTH_LONG).show();

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new RecyclerViewAdapter(this, tablerosIdeasList); // se envia la lista al adaptador para ser mostrados en el xml
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void getJsonIdeas(){
        class GetJsonIdeas extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void  onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(View_All_Tableros.this, "Recuperando datos...","Porfavor espere...", false,false);

            }

            @Override
            protected String doInBackground(Void... params){
                RequestHandler rh = new RequestHandler();
                String s = rh.sendgetRequest(Config.GET_IDEAS); // se obtienen las ideas
                return s;
            }

            @Override
            protected void  onPostExecute(String s){
                super.onPostExecute(s);
                progressDialog.dismiss();
                JSON_STRING_IDEAS = s;
            }

        }
        GetJsonIdeas gj = new GetJsonIdeas();
        gj.execute();
    }
}
