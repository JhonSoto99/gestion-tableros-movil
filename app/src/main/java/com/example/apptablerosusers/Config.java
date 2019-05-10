package com.example.apptablerosusers;

public class Config {

    /*
    *Se definen urls para consimir los serivicios del backend rest, el backen se debe correr con el comando python manage.py runserver 0.0.0.0:8000
    * La url de compone con la IP v4 que tiene asignada el computador en la red, esta se obtiene por el CMD con el comando ipconfig para Windows o iconfig para MAC
    * ejemplo si la IP del computador fuera 111.111.1.11 la url sería:
    * "http://111.111.1.11:8000/api/v1/users/user/"
     */
    public static final String URL_INSERT = "http://192.168.3.10:8000/api/v1/users/user/"; // Url para insertar un usuario
    public static final String GET_USER = "http://192.168.3.10:8000/api/v1/users/login/"; // Url para obtener un usuario, de acuerdo a los credenviales ingresados
    public static final String GET_TABLEROS = "http://192.168.3.10:8000/api/v1/tableros/tablero/"; // Url para obtener todos los tableros
    public static final String GET_IDEAS = "http://192.168.3.10:8000/api/v1/tableros/idea/"; // Url para obtener todos las ideas

    //Atributos para el objeto Usuarios
    public static final String KEY_EMAIL_USER = "email_user";
    public static final String KEY_NOMBRES = "nombres";
    public static final String KEY_APELLIDOS = "apellidos";
    public static final String KEY_NUM_IDENTIFICACION = "num_documento";
    public static final String KEY_PASSWORD = "password";

    // Atributos para el objeto Tablero
    public static final String KEY_TABLERO_IDEA = "tablero";
    public static final String KEY_DESCRIPCION_IDEA = "descripcion";
    public static final String KEY_CREADO_POR_IDEA = "creado_por";
    public static final String KEY_APROBADA_IDEA = "aprobada";
}
