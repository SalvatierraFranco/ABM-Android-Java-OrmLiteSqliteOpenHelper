package com.example.registrodeequipos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.registrodeequipos.Entities.Persona;
import com.example.registrodeequipos.R;

import java.util.List;

public class PersonaAdapter extends BaseAdapter {
    List<Persona> auxPersonas;

    public PersonaAdapter(){
    }

    public PersonaAdapter(List<Persona> misPersonas){
        auxPersonas = misPersonas;
    }

    @Override
    public int getCount() {
        return auxPersonas.size();
    }

    @Override
    public Object getItem(int position) {
        return auxPersonas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return auxPersonas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_persona, parent, false);

        Persona auxPersona = this.auxPersonas.get(position);

        TextView tv_nombre = v.findViewById(R.id.tv_nombre);
        TextView tv_edad = v.findViewById(R.id.tv_edad);

        tv_nombre.setText(auxPersona.getNombre());
        tv_edad.setText(auxPersona.getEdad());

        return v;
    }
}
