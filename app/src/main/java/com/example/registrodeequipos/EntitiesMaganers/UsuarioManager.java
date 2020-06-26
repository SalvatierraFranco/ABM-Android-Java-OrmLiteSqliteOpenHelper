package com.example.registrodeequipos.EntitiesMaganers;

import android.content.Context;

import com.example.registrodeequipos.Database.DBHelper;
import com.example.registrodeequipos.Entities.Usuario;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class UsuarioManager {
    private static UsuarioManager instance;
    Dao<Usuario, Integer> dao;

    public static UsuarioManager getInstance(Context context){
        if(instance == null){
            instance = new UsuarioManager(context);
        }

        return instance;
    }

    private UsuarioManager(Context context){
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
        try{
            dao = helper.getDao(Usuario.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Usuario> obtenerUsuarios() throws SQLException{
        return dao.queryForAll();
    }

    public void agregarUsuario(Usuario unUsuario) throws SQLException{
        dao.create(unUsuario);
    }
}
