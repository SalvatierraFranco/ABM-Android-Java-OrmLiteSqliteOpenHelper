package com.example.registrodeequipos.EntitiesMaganers;

import android.content.Context;
import android.database.Cursor;

import com.example.registrodeequipos.Database.DBHelper;
import com.example.registrodeequipos.Entities.Persona;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

public class PersonaManager {
    private static PersonaManager instance;
    Dao<Persona, Integer> dao;

    public static PersonaManager getInstance(Context context){
        if(instance == null){
            instance = new PersonaManager(context);
        }

        return instance;
    }

    public PersonaManager(Context context){
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
        try{
            dao = helper.getDao(Persona.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Persona> obtenerPersonas() throws SQLException{
        return dao.queryForAll();
    }

    public void agregarPersona(Persona unaPersona) throws SQLException{
        dao.create(unaPersona);
    }

    public List<Persona> obtenerPersonasByIdUsuario(int idUsuario) throws SQLException{
        return dao.queryForEq("idUsuario", idUsuario);
    }

    public void eliminarPersona(int id) throws SQLException{
        dao.deleteById(id);
    }

    public void modificarpersona(Persona auxPersona) throws SQLException {
        UpdateBuilder<Persona, Integer> ub = dao.updateBuilder();
        ub.where().eq("id", auxPersona.getId());
        ub.updateColumnValue("nombre", auxPersona.getNombre());
        ub.updateColumnValue("edad", auxPersona.getEdad());
        ub.update();
    }
}
