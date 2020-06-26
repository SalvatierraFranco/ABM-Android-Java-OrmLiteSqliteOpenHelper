package com.example.registrodeequipos.Database;

import android.app.Person;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.registrodeequipos.Entities.Persona;
import com.example.registrodeequipos.Entities.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String NOMBRE_DB = "DB_Equipos";
    private static final int VERSION_DB = 1;

    public DBHelper(Context context){
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Persona.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
