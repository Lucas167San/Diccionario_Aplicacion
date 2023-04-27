package mx.itlalaguna.c19130906.diccionario_aplicacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntryAdapter extends ArrayAdapter<Entry> {
    private ArrayList<Entry> entries;
    private ArrayList<Entry> entriesOriginal;

    public EntryAdapter(Context context, ArrayList<Entry> entries) {
        super(context, 0, entries);
        this.entries = entries;
        entriesOriginal = new ArrayList<>();
        entriesOriginal.addAll(entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtiene la entrada actual
        Entry entry = entries.get(position);

        // Infla el archivo entry_item.xml
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_item, parent, false);
        }

        // Busca las vistas correspondientes
        TextView wordTextView = convertView.findViewById(R.id.word_text_view);
        TextView definitionTextView = convertView.findViewById(R.id.definition_text_view);
        ImageView imageView = convertView.findViewById(R.id.image_view);

        // Establece los valores de las vistas
        wordTextView.setText(entry.getWord());
        definitionTextView.setText(entry.getDefinition());
        imageView.setImageBitmap(entry.getImage());

        // Retorna la vista actualizada
        return convertView;
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            entries.clear();
            entries.addAll(entriesOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Entry> coleccion = entries.stream()
                        .filter(i -> i.getWord().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                entries.clear();
                entries.addAll(coleccion);
            } else {
                for (Entry e: entriesOriginal){
                    if(e.getWord().toLowerCase().contains(txtBuscar.toLowerCase())){
                        entries.add(e);
                    }
                }
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}