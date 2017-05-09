package com.federico.explorador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;


public class MainActivity extends ActionBarActivity {
    private GridView SDS; // Grid
    private ArrayList<String> SD_CARDS; // Rutas de almacenamiento

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asignamos el layout principal
        SDS = (GridView) findViewById(R.id.sdcards); // Definimos el grid
        SD_CARDS = getAllsds();  // Definimos las rutas de almacenamiento
        //final GridAdapter gadapter = new GridAdapter(this,
        //       R.layout.sdcards, SD_CARDS);
        Adapter gadapter = new Adapter(this, R.layout.sdcards, SD_CARDS); // Creamos un adaptador
        SDS.setAdapter(gadapter); // Le asignamos el adaptador al grid
        SDS.setOnItemClickListener(new OnItemClickListener() {
            // Manejamos los clic sobre los elementos del grid
            @Override
            public void onItemClick(AdapterView<?> a, View v,
                                    final int position, long id) {
                // Vamos a iniciar una nueva actividad pasando como extra la ruta de almacenamiento
                Intent in = new Intent(MainActivity.this, Explorador.class);
                in.putExtra("SD", SD_CARDS.get(position));
                startActivity(in); // Inicamos la actividad
            }
        });
    }

    private ArrayList<String> getAllsds() {
        ArrayList<String> tmp = new ArrayList<String>(); // Array de todas las rutas

        String rutas[] = { "/mnt/", "/storage/" }; // Rutas posibles

        for (int i = 0; i < rutas.length; i++) {
            File file = new File(rutas[i]);// Ruta actual ( listamos de uno a uno )
            if (!file.exists()) // Si no existe pasamos a la siguiente
                continue;
            System.out.println("Ruta : " + rutas[i]); // Imprimimos para "depurar"
            String f[] = file.list(); // Contenido de ruta actual
            for (int o = 0; o < f.length; o++) { // Recorremos el contenido de la ruta actual
                if (f[o].indexOf("sd") != -1) { // Vemos si contiene sd en el nombre si es asi es almacenamiento
                    String ruta = rutas[i] + f[o]; // Creamos una Ruta,esta ruta es la original
                    File fd = new File(ruta); // Creamos un nuevo File para evitar rutas repetidas basadas en symlinks
                    String toAdd;
                    try {
                        System.out.println("Canocial :"
                                + fd.getCanonicalPath().toString()); // Esta seria la ruta original
                        toAdd = fd.getCanonicalPath().toString();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        toAdd = null;
                        continue;
                    }
                    if (!tmp.contains(toAdd)) { // Si se repitiera no lo añadimos
                        System.out.println("A añadir :" + toAdd);
                        tmp.add(toAdd);
                    }
                }
            }
        }

        return tmp; // Regresamos el Array de almacenamientos
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
