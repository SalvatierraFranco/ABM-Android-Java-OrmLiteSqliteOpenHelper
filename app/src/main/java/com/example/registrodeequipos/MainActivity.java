package com.example.registrodeequipos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Region;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.registrodeequipos.Entities.Persona;
import com.example.registrodeequipos.Entities.Usuario;
import com.example.registrodeequipos.EntitiesMaganers.UsuarioManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et_username;
    EditText et_password;
    List<Usuario> auxUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        if(validarCamposLogin()){
            Usuario auxUsuario = obtenerUsuario();
            auxUsuarios = obtenerUsuariosDB();
            boolean checkUser = false;
            boolean checkPass = false;

            for(int i=0; i<auxUsuarios.size(); i++){
                if(auxUsuario.getUsername().equals(auxUsuarios.get(i).getUsername())){
                    checkUser = true;
                    if(auxUsuario.getPassword().equals(auxUsuarios.get(i).getPassword())){
                        auxUsuario.setId(auxUsuarios.get(i).getId());
                        checkPass = true;
                        break;
                    }
                }
            }
            
            if(checkUser && checkPass){
                goToHomeActivity(auxUsuario);
                finish();
            } else if(checkUser == true && checkPass == false){
                Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "Usuario inexistente.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "Todos los campos deben estar completos."
                    , Toast.LENGTH_SHORT).show();
        }
    }

    private void goToHomeActivity(Usuario unUsuario) {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        homeIntent.putExtra("idUsuario", unUsuario.getId());
        homeIntent.putExtra("username", unUsuario.getUsername());
        startActivity(homeIntent);
    }

    private void registrarUsuario() {
        if(validarCamposLogin()){
            Usuario auxUsuario = obtenerUsuario();
            auxUsuarios = obtenerUsuariosDB();
            boolean check = true;

            for(int i=0; i<auxUsuarios.size(); i++){
                if(auxUsuario.getUsername().equals(auxUsuarios.get(i).getUsername())){
                    check = false;
                    break;
                }
            }

            if(check){
                try {
                    UsuarioManager.getInstance(this).agregarUsuario(auxUsuario);
                    Toast.makeText(this, "Registrado!", Toast.LENGTH_SHORT).show();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(this, "Ya existe este usuario.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Falta usuario y/o contraseña.", Toast.LENGTH_SHORT).show();
        }
    }

    private Usuario obtenerUsuario(){
        Usuario auxUsuario = new Usuario();
        auxUsuario.setUsername(et_username.getText().toString());
        auxUsuario.setPassword(et_password.getText().toString());

        return auxUsuario;
    }

    private List<Usuario> obtenerUsuariosDB() {
        try {
            return UsuarioManager.getInstance(this).obtenerUsuarios();
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private boolean validarCamposLogin() {
        boolean check = true;

        if(et_username.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()){
            check = false;
        }

        return check;
    }

    private void setViews() {
        et_username = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_pass);
    }
}
