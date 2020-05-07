package com.example.andy.frutaapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Declaracion de objetos
    private EditText et_nombre;
    private ImageView iv_personaje;
    private TextView tv_bestscore;
    private MediaPlayer mp;

    int num_aleatorio = (int) (Math.random() * 10); // Se hace un random dentro los 10 numero al principio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Se hace la conexion entre los obejtos del grafico  a lo logico con el constructor

        et_nombre = (EditText)findViewById(R.id.txt_nombre);
        iv_personaje = (ImageView)findViewById(R.id.Personaje);
        tv_bestscore = (TextView)findViewById(R.id.BestScore);

        //Manipulacion de ActionBar para implementar imagen tipo icono
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Icono que se hizo en el SetImage

        int id; // variable para almacenar el identificador de la imagen
        if (num_aleatorio==0 || num_aleatorio ==10){ //Condicional
            id = getResources().getIdentifier("mango","drawable",getPackageName());// buscar el identificador de la imagen a la variable id
            iv_personaje.setImageResource(id); //asignar la variable id a la imagen
        } else if (num_aleatorio==1 || num_aleatorio ==9) {
            id = getResources().getIdentifier("fresa", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (num_aleatorio==2 || num_aleatorio ==8) {
            id = getResources().getIdentifier("manzana", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }if (num_aleatorio==3 || num_aleatorio ==7) {
            id = getResources().getIdentifier("sandia", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }else if (num_aleatorio==4 || num_aleatorio ==5 || num_aleatorio==6) {
            id = getResources().getIdentifier("uva", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null, 1); // se crea el objeto  de la clase admin
        SQLiteDatabase BD = admin.getWritableDatabase(); //Se crea objeto para abrir la base de datos

        Cursor consulta = BD.rawQuery("select * from puntaje where score =(select max (score) from puntaje)",null);

        if(consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);
            tv_bestscore.setText("Record: "+ temp_score + " de "+ temp_nombre);
            BD.close();
        } else{
            BD.close();
        }

        mp = MediaPlayer.create(this,R.raw.alphabet_song); // se asigna el metodo al objeto
        mp.start(); // play a la cancion
        mp.setLooping(true);// hacer un bucle a la cancion
    }

    public void Jugar (View view) { // metodo Jugar
        String nombre = et_nombre.getText().toString(); // leer dato nombre
        if(!nombre.equals("")){  //comparacion de campo vacio, si esta diferente a nada entonces sigue
            mp.stop(); //termina cancion
            mp.release(); //destruye el espacio de memoria de la cancion

            Intent intent = new Intent(this, Main2Activity_Nivel1.class); //objeto para cambiar de activity
            intent.putExtra("Jugador",nombre); // pasar datos al activity2
            startActivity(intent); //inicia activity2
            finish(); //Termina activity
        } else { // si el campo esta vacio
            Toast.makeText(this, "Debes de escribir tu nombre", Toast.LENGTH_SHORT).show(); //mensaje tipo toast

            et_nombre.requestFocus(); //llevar el puntero al campo edittext
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE); //mandar a llamar el teclado
            imm.showSoftInput(et_nombre,InputMethodManager.SHOW_IMPLICIT);//mostrar el teclado direccionando al edittext
        }
    }

    @Override
    public void onBackPressed(){ //Metodo para desabilitar el boton de back

    }
}
