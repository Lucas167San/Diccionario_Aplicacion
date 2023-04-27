package mx.itlalaguna.c19130906.diccionario_aplicacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EntryAdapter extends ArrayAdapter<Entry> {
    private ArrayList<Entry> entries;

    public EntryAdapter(Context context, ArrayList<Entry> entries) {
        super(context, 0, entries);
        this.entries = entries;
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
}