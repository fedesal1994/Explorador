package com.federico.explorador;

/**
 * Created by federicoa on 07/05/2017.
 */

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<Archivo> {
    private Context c; // Contexto
    private int id; // Id del resource (layout)
    private ArrayList<Archivo> Archivos; // Todos los archivos

    public Adapter(Context context, int id, ArrayList<Archivo> archivos) {
        super(context, id, archivos);
        c = context; // Definimos las variables globales
        this.id = id;
        Archivos = archivos;
    }

    public Archivo getArchivo(int i) {
        return Archivos.get(i); // Esta funcion regresa un Archivo basado en el
        // index que le pasemos
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = convertView;
        if (layout == null) {
            LayoutInflater Linflated = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = Linflated.inflate(id, null); // Inflamos la layout
        }

        final Archivo file = Archivos.get(position); // Guardamos el archivo
        // actual en un objeto
        if (file != null) { // Vemos si no hay un error
            TextView Name = (TextView) layout.findViewById(R.id.textViewName); // El
            // nombre
            TextView Path = (TextView) layout.findViewById(R.id.textViewPath); // El
            // path
            // o
            // ruta
   /*
    * Para el nombre y el path vemos si hay un error y si no es asi le
    * ponemos el texto
    */
            if (Name != null)
                Name.setText(file.getName());
            if (Path != null)
                Path.setText(file.getPath());
            // Vemos si es un directorio
            if (file.isDirectory()) {
                ImageView folder = (ImageView) layout
                        .findViewById(R.id.imageViewIcon);
                Drawable image = c.getResources().getDrawable(
                        R.drawable.generic256);
                folder.setImageDrawable(image); // Si es asi le ponemos el icono
                // de directorio
            }
            if (!file.isDirectory()) { // Si no es directorio
                ImageView folder = (ImageView) layout
                        .findViewById(R.id.imageViewIcon);
                Drawable image = c.getResources().getDrawable(
                        R.drawable.fileblanc256);
                folder.setImageDrawable(image); // Le ponemos el icono de
                // archivo
            }
        }
        return layout;
    }
}