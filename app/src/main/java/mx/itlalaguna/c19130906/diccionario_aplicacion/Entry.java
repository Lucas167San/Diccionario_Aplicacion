package mx.itlalaguna.c19130906.diccionario_aplicacion;

import android.graphics.Bitmap;

public class Entry {
    private String word;
    private String definition;
    private Bitmap image;

    public Entry(String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
