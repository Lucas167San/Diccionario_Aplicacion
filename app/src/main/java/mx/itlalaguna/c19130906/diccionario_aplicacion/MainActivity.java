package mx.itlalaguna.c19130906.diccionario_aplicacion;

import static java.security.KeyStore.*;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.KeyStore;
import java.util.ArrayList;

import mx.itlalaguna.c19130906.diccionario_aplicacion.EntryAdapter;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Entry> entries;
    private EntryAdapter adapter;
    private EditText wordEditText;
    private EditText definitionEditText;
    private int editPosition = -1;

    String nombre[] = {"Copiar", "Cortar", "Pegar"};
    String definicion[] = {"Copia el texto o documento", "Corta el texto o documento", "Pega el texto o documento"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        });

        entryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entry entry = entries.get(position);
                wordEditText.setText(entry.getWord());
                definitionEditText.setText(entry.getDefinition());
                editPosition = position;
               AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext() );
                dialog.setTitle("Diccionario").setMessage("Concepto: "+ entry.getWord()+"\n\nDefinición: "
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
    }
}
