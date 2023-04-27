package mx.itlalaguna.c19130906.diccionario_aplicacion;

import static java.security.KeyStore.*;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.KeyStore;
import java.util.ArrayList;

import mx.itlalaguna.c19130906.diccionario_aplicacion.EntryAdapter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private ArrayList<Entry> entries;

    private SearchView txtBuscar;

    private EntryAdapter adapter;
    private EditText wordEditText;
    private EditText definitionEditText;
    private int editPosition = -1;

    String nombre[] = {"Flutter", "GUID (Identificador único Global)", "Katalon","NFC(Near - Field Communication)","UUID (Identificador Único Universal)"};
    String definicion[] = { "SDK de código fuente abierto de desarrollo de aplicaciones móviles creado por Google.",
                            "Número pseudoaleatorio empleado en aplicaciones de software.",
                            "Herramienta de automatización de pruebas de código abierto de KMS Technology",
                            "Tecnología de comunicación inalámbrica de corto alcance y alta frecuencia creada para el intercambio de datos dos dispositivos cercanos",
                            "Formato estandarizado para identificar de manera única recursos en un sistema informático."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBuscar = findViewById(R.id.searchView);
        entries = new ArrayList<>();
        for (int i = 0; i < nombre.length; i++){
            Entry entry = new Entry(nombre[i],definicion[i]);
            entries.add(entry);
        }
        adapter = new EntryAdapter(this, entries);
        ListView entryListView = findViewById(R.id.entryListView);
        entryListView.setAdapter(adapter);

        wordEditText = findViewById(R.id.wordEditText);
        definitionEditText = findViewById(R.id.definitionEditText);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordEditText.getText().toString();
                String definition = definitionEditText.getText().toString();
                if (wordEditText.getText().toString().isEmpty() || definitionEditText.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "Rellene los datos faltantes", Toast.LENGTH_SHORT).show();
                    wordEditText.requestFocus();
                } else {
                    if (editPosition == -1) {
                        Entry entry = new Entry(word, definition);
                        entries.add(entry);
                    } else {
                        Entry entry = entries.get(editPosition);
                        entry.setWord(word);
                        entry.setDefinition(definition);
                        editPosition = -1;
                    }
                    adapter.notifyDataSetChanged();
                    wordEditText.setText("");
                    definitionEditText.setText("");
                }
            }
        });

        entryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entry entry = entries.get(position);
                //wordEditText.setText(entry.getWord());
                //definitionEditText.setText(entry.getDefinition());
                editPosition = position;
                wordEditText.requestFocus();
               AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext() );
                dialog.setTitle("Diccionario").setMessage(entry.getWord()+"\n\nDefinición: "
                        +entry.getDefinition()).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).create().show();
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = entryListView.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    entries.remove(position);
                    adapter.notifyDataSetChanged();
                    entryListView.clearChoices();
                    wordEditText.setText("");
                    definitionEditText.setText("");
                    editPosition = -1;
                }
            }
        });

        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordEditText.getText().toString();
                String definition = definitionEditText.getText().toString();
                int position = entryListView.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    Entry entry = new Entry(word, definition);
                    entries.set(position,entry);
                    adapter.notifyDataSetChanged();
                    entryListView.clearChoices();
                    wordEditText.setText("");
                    definitionEditText.setText("");
                    editPosition = -1;
                }
            }
        });

        txtBuscar.setOnQueryTextListener(this);

    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}
