package com.example.vitor.listacompras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vitor on 27/06/2017.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> itens) {
        super(context, 0, itens);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row, parent, false);
        }
        // Lookup view for data population
        TextView textNome = (TextView) convertView.findViewById(R.id.textNome);
        CheckBox checkCompra = (CheckBox) convertView.findViewById(R.id.checkConcluido);
        // Populate the data into the template view using the data object
        textNome.setText(item.getNome());
        checkCompra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ItemController itemController = new ItemController(getContext());
                item.setConcluido(isChecked);
                itemController.alterarRegistro(item);
            }
        });
        checkCompra.setChecked(item.isConcluido());

        // Return the completed view to render on screen
        return convertView;
    }
}
