package com.example.vitor.listacompras;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.edit;

public class MainActivity extends AppCompatActivity {
    private CompraController compraController;
    private CustomAdapter adapter;
    private ListView listaCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008b8b")));
        setTitle("Listas de Compras");

        listaCompras = (ListView) findViewById(R.id.listaCompras);
        compraController = new CompraController(this);

        listaCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Compra compra = (Compra) listaCompras.getItemAtPosition(position);
                editarCompraActivity(compra);
            }
        });
        listaCompras.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(Menu.NONE, 1, Menu.NONE, "deletar");
                menu.add(Menu.NONE, 2, Menu.NONE, "editar");
            }

        });

        atualizar();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        Compra compra = ((Compra) listaCompras.getItemAtPosition(position));
        switch (item.getItemId()) {
            case 1:
                compraController.deletaRegistro(compra.getId());
                atualizar();
                break;
            case 2:
                editarCompraActivity(compra);
                break;
            default:
                Toast.makeText(getBaseContext(), "Teste não foi", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        atualizar();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newCart:
                adicionarCompra();
                return true;
            case R.id.update:
                atualizar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void handleButtonAdd(View view) {
        adicionarCompra();
    }

    private void adicionarCompra() {
        EditText editTitulo = (EditText) findViewById(R.id.editTitulo);
        EditText editData = (EditText) findViewById(R.id.editData);
        CheckBox checkConcluido = (CheckBox) findViewById(R.id.checkConcluido);

        Compra c = new Compra();
        c.setTitulo(editTitulo.getText().toString());
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        c.setDataPrevista(editData.getText().toString());
        c.setConcluido(checkConcluido.isChecked());
        compraController.insereDados(c);

        atualizar();
    }

    private void editarCompraActivity(Compra compra) {
        Intent intent = new Intent(getBaseContext(), Itens.class);
        intent.putExtra("compra", compra);
        startActivity(intent);
    }

    private void atualizar() {
        if(compraController.carregaDados() != null) {
            adapter = new CustomAdapter(this, compraController.carregaDados());
            listaCompras.setAdapter(adapter);
        }
    }

}
