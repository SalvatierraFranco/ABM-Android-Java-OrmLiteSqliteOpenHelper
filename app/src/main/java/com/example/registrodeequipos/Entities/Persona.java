package com.example.registrodeequipos.Entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Personas")
public class Persona {
    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    String nombre;
    @DatabaseField
    String edad;
    @DatabaseField
    int idUsuario;

    public Persona() {
    }

    public Persona(String nombre, String edad, int idUsuario) {
        this.nombre = nombre;
        this.edad = edad;
        this.idUsuario = idUsuario;
    }

    public Persona(int id, String nombre, String edad, int idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
