package com.example.vitor.listacompras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vitor on 22/06/2017.
 */

public class CustomAdapter extends ArrayAdapter<Compra>  {

    public CustomAdapter(Context context, ArrayList<Compra> compras) {
        super(context, 0, compras);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Compra compra = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        // Lookup view for data population
        final TextView textTitulo = (TextView) convertView.findViewById(R.id.textTitulo);
        TextView textData = (TextView) convertView.findViewById(R.id.textData);
        CheckBox checkCompra = (CheckBox) convertView.findViewById(R.id.checkConcluido);
        // Populate the data into the template view using the data object
        textTitulo.setText(compra.getTitulo());
        textData.setText(compra.getDataPrevista().toString());

        checkCompra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CompraController compraController = new CompraController(getContext());
                compra.setConcluido(isChecked);
                compraController.alterarRegistro(compra);
            }
        });
        checkCompra.setChecked(compra.isConcluido());


        // Return the completed view to render on screen
        return convertView;
    }
}
