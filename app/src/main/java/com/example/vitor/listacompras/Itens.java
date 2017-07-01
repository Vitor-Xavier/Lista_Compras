package com.example.vitor.listacompras;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class Itens extends AppCompatActivity {
    private Compra compra;
    private ListView listaItem;
    private EditText editNome;
    private EditText editTitulo;
    private EditText editData;
    private CheckBox checkConcluido;
    private ItemAdapter adapter;
    private ItemController controller;
    private CompraController compraController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008b8b")));
        setTitle("Itens da Lista");

        controller = new ItemController(this);
        compraController = new CompraController(this);

        Intent intent = getIntent();
        compra = (Compra) intent.getSerializableExtra("compra");

        editNome = (EditText) findViewById(R.id.editNome);
        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editData = (EditText) findViewById(R.id.editData);
        checkConcluido = (CheckBox) findViewById(R.id.checkConcluido);
        listaItem = (ListView) findViewById(R.id.listaItem);
        listaItem.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(Menu.NONE, 1, Menu.NONE, "deletar");
            }

        });

        editTitulo.setText(compra.getTitulo());
        editData.setText(compra.getDataPrevista());
        checkConcluido.setChecked(compra.isConcluido());


        atualizar();
    }

    @Override
    protected void onResume() {
        atualizar();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.saveItem:
                salvarCompra();
                return true;
            case R.id.deleteCart:
                compraController.deletaRegistro(compra.getId());
                finish();
                return true;
            case R.id.updateItems:
                atualizar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        Item it = ((Item) listaItem.getItemAtPosition(position));
        switch (item.getItemId()) {
            case 1:
                controller.deletaRegistro(it.getId());
                atualizar();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void atualizar() {
        if(controller.carregaDados(compra.getId()) != null) {
            adapter = new ItemAdapter(this, controller.carregaDados(compra.getId()));
            listaItem.setAdapter(adapter);
        }
    }

    public void hanleButtonSave(View view) {
        salvarCompra();
    }

    public void handleButtonNovo(View view) {
        adicionar();
    }

    private void salvarCompra() {
        compra.setTitulo(editTitulo.getText().toString());
        //SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        compra.setDataPrevista(editData.getText().toString());
        compra.setConcluido(checkConcluido.isChecked());
        compraController.alterarRegistro(compra);
    }

    private void adicionar() {
        Item item = new Item();
        item.setNome(editNome.getText().toString());
        item.setConcluido(false);
        item.setId_compra(compra.getId());

        Toast.makeText(this, controller.insereDados(item), Toast.LENGTH_SHORT).show();
        atualizar();
    }

    public void handleDeletar(View view) {

    }
}
