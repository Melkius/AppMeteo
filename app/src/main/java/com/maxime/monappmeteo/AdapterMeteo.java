package com.maxime.monappmeteo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class AdapterMeteo extends ArrayAdapter<Meteo> {

    private List<Meteo> maList;

    public AdapterMeteo(Context context, List<Meteo> meteos) {
        super(context,0,  meteos);
        maList = meteos;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.listmeteo, parent, false);
        }

        MeteoViewHolder viewHolder = (MeteoViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MeteoViewHolder();

            viewHolder.tempMin =  convertView.findViewById(R.id.tvTempMin);
            viewHolder.tempMax =  convertView.findViewById(R.id.tvTempMax);
            viewHolder.temp = convertView.findViewById(R.id.tvTemp);
            viewHolder.img = convertView.findViewById(R.id.imvImg);
            viewHolder.date = convertView.findViewById(R.id.tvDate);
            viewHolder.heure = convertView.findViewById(R.id.tvHeure);
            convertView.setTag(viewHolder);
        }

        Meteo meteo = getItem(position);

        viewHolder.tempMin.setText(meteo.getTempMin());
        viewHolder.tempMax.setText(meteo.getTempMax());
        viewHolder.temp.setText(meteo.getTemp());
        viewHolder.img.setImageResource(meteo.getImg());
        viewHolder.heure.setText(meteo.getHeure());
        viewHolder.date.setText(meteo.getDate());

        // Pour éviter des boucles d'AsynchTask dans la vue Détail
        if(meteo.getHeure().equals("")){
            convertView.setClickable(false);
        } else {
            convertView.setClickable(true);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return maList.size();
    }

    @Override
    public Meteo getItem(int position) {
        return maList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class MeteoViewHolder{

        public TextView tempMin;
        public TextView tempMax;
        public TextView temp;
        public ImageView img;
        public TextView heure;
        public TextView date;
    }
}
