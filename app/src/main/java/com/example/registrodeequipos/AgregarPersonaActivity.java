package com.example.registrodeequipos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.registrodeequipos.Database.DBHelper;
import com.example.registrodeequipos.Entities.Persona;
import com.example.registrodeequipos.EntitiesMaganers.PersonaManager;

import java.sql.SQLException;

public class AgregarPersonaActivity extends AppCompatActivity {
    EditText et_nombre;
    EditText et_edad;
    Button btn_limpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_persona);

        setToolbar();
        setViews();
        limpiar();

        final Bundle modificarBundle = getIntent().getExtras();

        if(modificarBundle.get("id") != null){
            setViewsModificar(modificarBundle);
            findViewById(R.id.btn_agregar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modificarPersona(modificarBundle.getInt("id"));
                }
            });
        }else{
            findViewById(R.id.btn_agregar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agregarPersona();
                }
            });
        }
    }

    private void limpiar() {
        btn_limpiar = findViewById(R.id.btn_limpiar);

        btn_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_nombre.setText("");
                et_edad.setText("");
            }
        });
    }

    private void modificarPersona(int id) {
        Persona unaPersona = new Persona();
        if(validarCampos()) {
            unaPersona.setId(id);
            unaPersona.setNombre(et_nombre.getText().toString());
            unaPersona.setEdad(et_edad.getText().toString());


            try {
                PersonaManager.getInstance(getApplicationContext()).modificarpersona(unaPersona);
                Toast.makeText(this, "Modificado!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show();
        }
    }

    private void setViewsModificar(Bundle modificarBundle) {
        et_nombre.setText(modificarBundle.getString("nombre"));
        et_edad.setText(modificarBundle.getString("edad"));
    }



    private void agregarPersona() {
        Bundle agregarPersonaBundle = getIntent().getExtras();
        if(validarCampos()){
            Persona auxPersona = new Persona();
            auxPersona.setNombre(et_nombre.getText().toString());
            auxPersona.setEdad(et_edad.getText().toString());
            auxPersona.setIdUsuario(agregarPersonaBundle.getInt("idUsuario"));

            try {
                PersonaManager.getInstance(this).agregarPersona(auxPersona);
                Toast.makeText(this, "Agregado!", Toast.LENGTH_SHORT).show();
                finish();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }else{
            Toast.makeText(this, "Ambos campos deben ser completados", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCampos() {
        boolean check = true;
        if(et_nombre.getText().toString().isEmpty() || et_edad.getText().toString().isEmpty()){
            check = false;
        }
        return check;
    }

    private void setViews() {
        et_nombre = findViewById(R.id.et_nombre);
        et_edad = findViewById(R.id.et_edad);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarAgregar);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Agregar persona");
        }
    }
}
