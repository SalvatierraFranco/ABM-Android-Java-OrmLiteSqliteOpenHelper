package com.example.registrodeequipos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.registrodeequipos.Adapter.PersonaAdapter;
import com.example.registrodeequipos.Entities.Persona;
import com.example.registrodeequipos.EntitiesMaganers.PersonaManager;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    List<Persona> auxPersonas;
    ListView lv_personas;
    PersonaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setToolbar();
        setAdapter();
        contador();

        lv_personas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ShowAlert("Eliminar", "¿Desea eliminar?", position, null);
                return true;
            }
        });

        lv_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Persona> auxPersonas = obtenerPersonasPorIdUsuario();
                ShowAlert("Modificar", "¿Desea modificar?", position, auxPersonas.get(position));
            }
        });
    }

    private void eliminarPersona(int position) {
        List<Persona> auxPersonas = obtenerPersonasPorIdUsuario();
        try {
            PersonaManager.getInstance(this).eliminarPersona(auxPersonas.get(position).getId());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        lv_personas = findViewById(R.id.lv_personas);
        adapter = new PersonaAdapter(obtenerPersonasPorIdUsuario());
        lv_personas.setAdapter(adapter);
    }

    private List<Persona> obtenerPersonasPorIdUsuario() {
        Bundle homeBundle = getIntent().getExtras();
        try {
            return PersonaManager.getInstance(getApplicationContext())
                    .obtenerPersonasByIdUsuario(homeBundle.getInt("idUsuario"));
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Mi equipo");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.add_persona:
                goToAgregarPersona();
                break;

            case R.id.logoff:
                cerrarSesion();
        }

        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        Intent logOffIntent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(logOffIntent);
        finish();
    }

    private void goToAgregarPersona() {
        Bundle homeBundle = getIntent().getExtras();

        Intent AgregarPersonaIntent = new Intent(HomeActivity.this, AgregarPersonaActivity.class);
        AgregarPersonaIntent.putExtra("idUsuario", homeBundle.getInt("idUsuario"));
        startActivity(AgregarPersonaIntent);
    }

    @Override
    protected void onRestart() {
        contador();
        setAdapter();
        super.onRestart();
    }

    private void contador() {
        Bundle homeBundle = getIntent().getExtras();
        try {
            auxPersonas = PersonaManager.getInstance(this).obtenerPersonasByIdUsuario(homeBundle.getInt("idUsuario"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        Toast.makeText(this, "Cantidad: " + auxPersonas.size(), Toast.LENGTH_SHORT).show();
    }

    public void ShowAlert(final String title, String text, final int position, final Persona unaPersona){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title)
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (title){
                            case "Modificar":
                                goToModificarDb(unaPersona.getId(), unaPersona.getNombre(), unaPersona.getEdad());
                                break;

                            case "Eliminar":
                                eliminarPersona(position);
                                onRestart();
                                break;
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(R.drawable.ic_delete_black_24dp)
                .show();
    }

    private void goToModificarDb(int id, String nombre, String edad) {
        Intent modificarIntent = new Intent(HomeActivity.this, AgregarPersonaActivity.class);
        modificarIntent.putExtra("id", id);
        modificarIntent.putExtra("nombre", nombre);
        modificarIntent.putExtra("edad", edad);
        startActivity(modificarIntent);
    }
}
