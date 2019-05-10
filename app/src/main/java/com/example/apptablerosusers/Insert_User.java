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

public class Insert_User extends AppCompatActivity implements View.OnClickListener  {

    private Button btn_registrarme;
        private Button btn_volver;
        private EditText et_email_user, et_nombres, et_apellidos, et_num_documento, et_password;
        String email_user, nombres, apellidos, num_documento, password;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.insert_user);

            cast();
            registrar_boton_registro();
            registrar_boton_volver();
        }

        private void cast() {
            btn_registrarme = (Button) findViewById(R.id.btn_registrarme);
            btn_volver = (Button) findViewById(R.id.btn_volver);

            et_email_user = (EditText) findViewById(R.id.et_email_user);
            et_nombres = (EditText) findViewById(R.id.et_nombres);
            et_apellidos = (EditText) findViewById(R.id.et_apellidos);
            et_num_documento = (EditText) findViewById(R.id.et_num_documento);
            et_password = (EditText) findViewById(R.id.et_password);
        }

        private void registrar_boton_registro(){
            btn_registrarme.setOnClickListener(this);
        }
        private void registrar_boton_volver(){
            btn_volver.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) { // Metodo que se ejecuta al clickear un boton
            switch (v.getId()){
                case R.id.btn_registrarme: // Si se presiona el boton registrarme, se valida que se hayan dilenciado los campos
                    if (et_email_user.getText().toString().length() == 0){
                        et_email_user.setError("Campo requerido");
                    } else if (et_nombres.getText().toString().length() == 0){
                        et_nombres.setError("Campo requerido");
                    } else if (et_apellidos.getText().toString().length() == 0){
                        et_apellidos.setError("Campo requerido");
                    }else if (et_num_documento.getText().toString().length() == 0){
                        et_num_documento.setError("Campo requerido");
                    }else if (et_password.getText().toString().length() == 0){
                        et_password.setError("Campo requerido");
                    } else {
                        insertarUsuario(); // Se procede a inceetar el usuario, con los datos ingresados
                    }
                    break;

                case R.id.btn_volver: // si se presiona el boton volver, se pasa a la vista MainActivity
                    Intent intent = new Intent(Insert_User.this,MainActivity.class);
                    startActivity(intent);
                    break;

            }
        }


        private void insertarUsuario() {
            email_user = et_email_user.getText().toString().trim();
            nombres = et_nombres.getText().toString().trim().toUpperCase();
            apellidos = et_apellidos.getText().toString().toUpperCase().trim();
            num_documento = et_num_documento.getText().toString().trim();
            password = et_password.getText().toString().trim();
            class InsertarUsuario extends AsyncTask<Void, Void, String> {
                ProgressDialog progressDialog;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressDialog = ProgressDialog.show(Insert_User.this, "Insertando...", "Por favor espere...", false, false);
                }

                @Override
                protected String doInBackground(Void... params) {
                    // Se construye data para enviar al servicio de creracion de usuario
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.KEY_EMAIL_USER, email_user);
                    hashMap.put(Config.KEY_NOMBRES, nombres);
                    hashMap.put(Config.KEY_APELLIDOS, apellidos);
                    hashMap.put(Config.KEY_NUM_IDENTIFICACION, num_documento);
                    hashMap.put(Config.KEY_PASSWORD, password);

                    RequestHandler requestHandler = new  RequestHandler();
                    String rh = requestHandler.sendPostRequest(Config.URL_INSERT, hashMap); // Se consume el serivico de creracion de usuarios
                    return rh;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    progressDialog.dismiss();
                    Toast.makeText(Insert_User.this,"Usuario creado exitosamente", Toast.LENGTH_LONG).show(); // Se muestra notificacion de registro exitoso
                    Intent intent = new Intent(Insert_User.this,MainActivity.class); // se envia a la vista MainActivity
                    startActivity(intent);
                }

            }
            InsertarUsuario insertarUsuario = new InsertarUsuario();
            insertarUsuario.execute();
    }

}
