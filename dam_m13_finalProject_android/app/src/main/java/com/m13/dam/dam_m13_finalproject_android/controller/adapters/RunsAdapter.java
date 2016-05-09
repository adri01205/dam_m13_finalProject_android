package com.m13.dam.dam_m13_finalproject_android.controller.adapters;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;


public class RunsAdapter extends BaseAdapter {
    private Activity context;
    private Cursor dades;

    /**
     * Constructor
     * @param context el context de l'aplicaci�
     * @param dades cursor amb les dades
     */
    public RunsAdapter(Activity context, Cursor dades) {
        super();
        this.context = context;
        this.dades = dades;
    }

    /**
     * Sobreescriptura del m�tode getView per indicar com s'han de mostrar
     * les dades d'una fila del ListView
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;

       // Run r = getItem(position);

        if(element == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            element = inflater.inflate(R.layout.tasks_adapter, null);
        }

        //((TextView) element.findViewById(R.id.tvDate)).setText(r.getRun_date());
        //((TextView) element.findViewById(R.id.tvTime)).setText(r.getRun_time());

        return element;
    }

    /**
     * Sobreescriptura del m�tode getCount que indica quantes dades gestiona
     * l'adaptador.
     */
    public int getCount() {

        return dades.getCount();
    }
    /**
     * Sobreescriptura del m�tode getItem que retorna l'objecte que ocupa la
     * posici� indicada amb el par�metre.
     */
    public Task getItem(int pos) {
        Task r = new Task();
        if(dades.moveToPosition(pos)) {

//            r.setId(dades.getInt(0));
//            r.setRun_time(dades.getString(1));
//            r.setRun_date(dades.getString(2));
//            r.setId_user(dades.getInt(3));
        }
        return r;
    }
    /**
     * Sobreescriptura del m�tode getItemId que retorna l'id de l'objecte
     * que ocupa la posici� indicad amb el par�metre.
     */
    public long getItemId(int position) {
        return 0;
        //return getItem(position).getId();
    }
}
