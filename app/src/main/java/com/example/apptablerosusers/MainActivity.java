package com.example.apptablerosusers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_ingresar, btn_registrarme;
    private EditText et_email_user, et_password;
    String email_user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cast();
        register();

    }

    private void cast() {
        btn_ingresar  = (Button) findViewById(R.id.btn_ingresar);
        btn_registrarme  = (Button) findViewById(R.id.btn_registrarme);

        et_email_user = (EditText) findViewById(R.id.et_email_user);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void register() { // Se registran los botones para escucharlos cuando estos sean cliqueados
        btn_ingresar.setOnClickListener(this);
        btn_registrarme.setOnClickListener(this);
    }

    private void obtenerUsuario(){ // Metodo para consultar el usuario en la base de datos, según los credenciales ingresados
        email_user = et_email_user.getText().toString();
        password = et_password.getText().toString();
        class ObtenerUsuario extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() { // Se muestra progresdialog mientra se realiza la consulta
                super.onPreExecute();
                progressDialog = ProgressDialog.show(MainActivity.this, "Consultando usuario...", "Porfavor espere...", false, false);
            }

            @Override
            protected String doInBackground(Void... params) { // Se consume servicio para obtener el usuario
                RequestHandler rh = new RequestHandler();
                String s = rh.sendgetRequest(Config.GET_USER+"?email_user="+email_user+"&password="+password);
                return s;
            }

            @Override
            protected void onPostExecute(String s){ // Se captura data obtenida a traves del servicio
                super.onPostExecute(s);
                progressDialog.dismiss();
                validarUsuarioConsultado(s); // se valida si el usuario existe, según la data obtenida

            }
        }
        ObtenerUsuario obtenerUsuario = new ObtenerUsuario();
        obtenerUsuario.execute();
    }

    private void  validarUsuarioConsultado(String json){
        /*Metodo para valñidar si el usuario existe
        *  segun la data obtenida a traves del servicio
        * */
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(json);
            Boolean success = jsonObject.getBoolean("success"); // se captura el dato obtenidop del serivicio, para realizar la validacion
            if (success){ // Si success es true, significa que existe un usuario con los credenciales ingresados
                et_email_user.setText("");
                et_password.setText("");
                String id_user = jsonObject.getString("id");
                Intent tableros  = new Intent(getApplicationContext(), View_All_Tableros.class); // Se da acceso a la siguiente vista View_Tableros
                tableros.putExtra("id_user", id_user); // Se para el paramentro id_user a las siguiente vista
                startActivity(tableros);
            } else { // Si seccess es false, significa que noo existe el usuario
                // Se muestra notificacion informando que los credenciales son incorrectos
                Toast.makeText(getApplicationContext(),"Credenciales incorrectos",Toast.LENGTH_LONG).show();
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) { // Metodo que se ejecuta al presionar un boton
        switch (v.getId()){
            case R.id.btn_ingresar: // Si se presiona el boton ingresar, se consulta un usuario en el backend
                obtenerUsuario();
                break;
            case R.id.btn_registrarme: // Si se presiona el boton registrarme se pasa a la vista Insert_User, para registrarse
                Intent view  = new Intent(getApplicationContext(), Insert_User.class);
                startActivity(view);
                break;
        }
    }
}
