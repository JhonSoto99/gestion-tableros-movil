package com.example.apptablerosusers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Insert_Idea extends AppCompatActivity implements View.OnClickListener  {

    private Button btn_registrar;
    private Button btn_volver;
    private EditText et_descripcion_idea;
    String descripcion_idea;
    String id_user;
    String id_tablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_idea);

        // Se capturan los datos enviados
        Intent intent = getIntent();
        id_user = intent.getStringExtra("id_user");
        id_tablero = intent.getStringExtra("id_tablero");

        cast();
        registrar_btn_registro();
        registrar_btn_volver();
    }

    private void cast() {
        btn_registrar = (Button) findViewById(R.id.btn_registrar);
        btn_volver = (Button) findViewById(R.id.btn_volver);

        et_descripcion_idea = (EditText) findViewById(R.id.et_descripcion_idea);

    }

    private void registrar_btn_registro(){
        btn_registrar.setOnClickListener(this);
    }
    private void registrar_btn_volver(){
        btn_volver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { // Metodo que se ejecuta al clickear un boton
        switch (v.getId()){
            case R.id.btn_registrar:
                if (et_descripcion_idea.getText().toString().length() == 0){ // Se valida que los campos estén diligenciados
                    et_descripcion_idea.setError("Campo requerido");
                } else {
                    insertarIdea(); // se inserta la idea
                }
                break;

            case R.id.btn_volver:
                Intent intent = new Intent(Insert_Idea.this,View_All_Tableros.class);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
                break;

        }
    }

    private void insertarIdea() {
        descripcion_idea = et_descripcion_idea.getText().toString();
        class InsertarIdea extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Insert_Idea.this, "Insertando...", "Por favor espere...", false, false);
            }

            @Override
            protected String doInBackground(Void... params) {
                // Se construye data para insertar idea
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_TABLERO_IDEA, id_tablero);
                hashMap.put(Config.KEY_DESCRIPCION_IDEA, descripcion_idea);
                hashMap.put(Config.KEY_CREADO_POR_IDEA, id_user);
                hashMap.put(Config.KEY_APROBADA_IDEA, "SI");

                RequestHandler requestHandler = new  RequestHandler();
                String rh = requestHandler.sendPostRequest(Config.GET_IDEAS, hashMap);
                return rh;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(Insert_Idea.this,"Idea creada exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Insert_Idea.this,View_All_Tableros.class);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }

        }
        InsertarIdea in = new InsertarIdea();
        in.execute();
    }
}
