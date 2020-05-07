package com.example.andy.frutaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    } //alt+enter para insertar metodos automaticamente. Alt+enter y crear constructor automaticamente
    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("create table  puntaje(nombre text, score int)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}