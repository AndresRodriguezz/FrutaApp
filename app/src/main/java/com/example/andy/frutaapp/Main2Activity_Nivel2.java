package com.example.andy.frutaapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.service.autofill.FieldClassification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class Main2Activity_Nivel2 extends AppCompatActivity {

    private TextView tv_nombre, tv_score;
    private ImageView iv_Auno,iv_Ados,iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp,mp_great,mp_bad;

    int score,numAleatorio_dos, resultado,numAleatorio_uno,vidas;
    String nombre_Jugador,string_score,string_vidas;

    String numero [] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};//2estamal,6estamal,5estamal,4estanbien,8
    //array donde voy a cambiar de imagenes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__nivel2);

        Toast.makeText(this,"nivel 2 - Sumas avanzadas",Toast.LENGTH_SHORT).show();

        tv_nombre = (TextView) findViewById(R.id.textView_nombre);
        tv_score = (TextView) findViewById(R.id.textView2_score);
        iv_Auno = (ImageView)findViewById(R.id.imageView2_NumUno);
        iv_Ados = (ImageView)findViewById(R.id.imageView3_NumDos);
        iv_vidas = (ImageView)findViewById(R.id.imageView_vidas);
        et_respuesta = (EditText)findViewById(R.id.editText_resultado);



        nombre_Jugador = getIntent().getStringExtra("jugador");// Se asigna a la variable nombre_Jugador el valor Jugador del Main Activity anterior
        tv_nombre.setText("Jugador: "+ nombre_Jugador); //Asigna a tv_nombre el valor en nombre_Jugador

        string_vidas = getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(string_vidas);
        switch(vidas){
            case 3: iv_vidas.setImageResource(R.drawable.tresvidas);
                break;
            case 2: iv_vidas.setImageResource(R.drawable.dosvidas);
                break;
            case 1:iv_vidas.setImageResource(R.drawable.unavida);
                break;
        }
        string_score = getIntent().getStringExtra("score");
        score = Integer.parseInt(string_score);
        tv_score.setText("Score: "+ score);


        //Manipulacion de ActionBar para implementar imagen tipo icono
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Icono que se hizo en el SetImage

        mp = MediaPlayer.create(this,R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this,R.raw.wonderful);
        mp_bad = MediaPlayer.create(this,R.raw.bad);

        NumAleatorio();
    }

    public void Comparar(View view){
        String respuesta = et_respuesta.getText().toString();

        if(!respuesta.equals("")){
            int respuesta_jugador= Integer.parseInt(respuesta);
            if(resultado == respuesta_jugador){
                score++;
                mp_great.start();
                tv_score.setText("Score: "+ score);
                et_respuesta.setText("");
                BaseDeDAtos();

            } else {
                mp_bad.start();

                vidas--;
                BaseDeDAtos();

                switch (vidas){
                    case 3: iv_vidas.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2: iv_vidas.setImageResource(R.drawable.dosvidas);
                        Toast.makeText(this,"Te quedan 2 vidas",Toast.LENGTH_LONG).show();
                        break;
                    case 1:iv_vidas.setImageResource(R.drawable.unavida);
                        Toast.makeText(this,"Te queda 1 vida",Toast.LENGTH_LONG).show();
                        break;
                    case 0: Toast.makeText(this,"No te quedan vidas",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        mp.stop();
                        mp.release();
                        break;
                }
                et_respuesta.setText("");
            }
            NumAleatorio(); //se manda a llamar el metodo NumAleatorio
        } else {
            Toast.makeText(this,"Escribe tu respuesta",Toast.LENGTH_LONG).show();

        }
    }

    public void NumAleatorio () { //Metodo para el numero aleatorio
        if (score <= 19) {
            numAleatorio_uno = (int) (Math.random() * 10);//Numero  random asignado a la variable numAleatorio_uno
            //se hace un cast o sea se pone el tipo de dato entero (int) ya que el metodo random los genera el tipo duble
            numAleatorio_dos = (int) (Math.random() * 10);//Numero  random asignado a la variable numAleatorio_uno
            //se hace un cast o sea se pone el tipo de dato entero (int) ya que el metodo random los genera el tipo duble
            resultado = numAleatorio_uno + numAleatorio_dos;

                for (int i = 0; i < numero.length; i++) {
                    int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                    if (numAleatorio_uno == i) {//numeroAleatorio_uno con el valor asiganado en la parte de arriba, se le asigna al indice
                        iv_Auno.setImageResource(id);//poner imagen en el objeto de imagen 1 de mi valor id
                    }if (numAleatorio_dos == i){//numeroAleatorio_uno con el valor asiganado en la parte de arriba, se le asigna al indice
                        iv_Ados.setImageResource(id);//poner imagen en el objeto de imagen 2 de mi valor od
                    }
                }

            }
        else { Intent intent = new Intent(this,Main2Activity_Nivel3.class);

            string_score = String.valueOf(score);// se convierte la variable score de tipo int a tipo string
            string_vidas = String.valueOf(vidas); // se convierte la variable vidas de tipo int a tipo string
            intent.putExtra("jugador",nombre_Jugador);//Se envian la variable nombre_Jugador al siguiente activity
            intent.putExtra("score",string_score);//Se envian la variable string_score al siguiente activity
            intent.putExtra("vidas",string_vidas);//Se envian la variable string_vidas al siguiente activity

            startActivity(intent); // empieza activity_main2_Nivel2
            finish();//se cieera el activity actual
            mp.stop();//se pone pausa al reproductor
            mp.release();//se destruye mp
        }
    }

    public void BaseDeDAtos(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);//objeto de base de datos
        SQLiteDatabase BD = admin.getWritableDatabase(); //Se abre base de datos

        Cursor consulta =BD.rawQuery("select * from puntaje where score = (select max(score) from puntaje)",null );// objeto para hacer consulta
        if(consulta.moveToFirst()){

            String temp_nombre = consulta.getString(0); //consulta el nombre donde esta en la columna cero
            String temp_score = consulta.getString(1);// consulta el tem_score esta en la columna 1

            int bestScore = Integer.parseInt(temp_score); //se declara bestscore de tipo integer se convierte la variable temp_score a Integer

            if (score>bestScore){ //si score es mayor que bestscore
                ContentValues modificacion = new ContentValues(); //objeto para modificar
                modificacion.put("nombre",nombre_Jugador); //modificacion del nombre del jugador
                modificacion.put("score",score);//moficicacion de score en score

                BD.update("puntaje",modificacion,"score= "+ bestScore,null);//actualizar datos en la tabla puntaje
            } // cambiar los datos en el objeto modoficacion en la tabla puntaje poniendo como clausula score=bestScore
            BD.close();//se cierra conexion
        } else{
            ContentValues insertar = new ContentValues(); //insertar
            insertar.put("nombre",nombre_Jugador);//valor nombre se guarda nombre
            insertar.put("score",score);//valor score se guarda en la columna score
            BD.insert("puntaje",null,insertar); //insertar informacion en tabla
            BD.close();//se cierra base datos
        }
    }
}
