package com.maxime.monappmeteo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyAsynchTask extends AsyncTask<Object,Void,String> {

    private URL url;
    private  String result;
    private AdapterMeteo adapter;
    private List<Meteo> listMet;
    private TextView villeMain;
    private ProgressDialog dialog;
    private int position;


    public MyAsynchTask(MainActivity activity) {
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Patientez s'il vous plait ...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

            Log.d("METEOTAG", "postexec");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(!result.equals("")){
                try {
                    JSONObject js = new JSONObject(result);
                    JSONObject jsCity = js.getJSONObject("city");
                    String cityStr = jsCity.getString("name");
                    JSONArray array = js.getJSONArray("list");
                    JSONObject js2 = array.getJSONObject(0);
                    String date = js2.getString("dt_txt");
                    Log.d("METEOTAG date :", date);
                    villeMain.setText(cityStr);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                villeMain.setText("Ville inconnue");
            }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected String doInBackground(Object... params) {
        url = (URL) params[0];
        adapter =  (AdapterMeteo) params[1];
        listMet = (List<Meteo>) params[2];
        villeMain = (TextView) params[3];
        position = (int) params[4];

        listMet.clear();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                result = in.readLine();
                in.close(); // et on ferme le flux
                JSONObject js = new JSONObject(result);

                if (position == -1) {
                    for (int i = 0; i < 40; i++) {

                        if (i % 8 == 0) {
                            JSONArray array = js.getJSONArray("list");
                            JSONObject js2 = array.getJSONObject(i);
                            String date = js2.getString("dt_txt");

                            JSONObject jsMain = js2.getJSONObject("main");
                            double tempMaxDouble = Math.round((jsMain.getDouble("temp_max") - 273.15) * 10) / 10.0;
                            double tempMinDouble = Math.round((jsMain.getDouble("temp_min") - 273.15) * 10) / 10.0;
                            String tempMin = String.valueOf(tempMinDouble) + " °C";
                            String tempMax = String.valueOf(tempMaxDouble) + " °C";
                            String temp = String.valueOf(((tempMinDouble * 100) + (tempMaxDouble * 100)) / 200) + " °C";

                            JSONObject image = array.getJSONObject(1);
                            JSONArray arrayImage = image.getJSONArray("weather");
                            String imgStr = arrayImage.getJSONObject(0).getString("icon");

                            Log.d("METEOTAG min", tempMin);
                            Log.d("METEOTAG max", tempMax);
                            Log.d("METEOTAG temp", temp);
                            Log.d("METEOTAG icon", imgStr);

                            listMet.add(new Meteo(imgStr, tempMin, tempMax, temp, date, ""));
                        }
                    }
                } else {
                    int deb = position + (position * 6);
                    JSONArray array = js.getJSONArray("list");
                    JSONObject js2 = array.getJSONObject(deb);
                    String date = js2.getString("dt_txt");
                    for (int i = 0; i < 40; i++) {
                        array = js.getJSONArray("list");
                        js2 = array.getJSONObject(i);
                        if (calculeDate(date).equals(calculeDate(js2.getString("dt_txt")))) {
                            String heure = js2.getString("dt_txt");
                            JSONObject jsMain = js2.getJSONObject("main");
                            // Transformation température en °C depuis Kelvin
                            double tempMaxDouble = Math.round((jsMain.getDouble("temp_max") - 273.15) * 10) / 10.0;
                            double tempMinDouble = Math.round((jsMain.getDouble("temp_min") - 273.15) * 10) / 10.0;
                            String tempMin = String.valueOf(tempMinDouble) + " °C";
                            String tempMax = String.valueOf(tempMaxDouble) + " °C";
                            String temp = String.valueOf(((tempMinDouble * 100) + (tempMaxDouble * 100)) / 200) + " °C";

                            JSONObject image = array.getJSONObject(1);
                            JSONArray arrayImage = image.getJSONArray("weather");
                            String imgStr = arrayImage.getJSONObject(0).getString("icon");

                            Log.d("METEOTAG min", tempMin);
                            Log.d("METEOTAG max", tempMax);
                            Log.d("METEOTAG temp", temp);
                            Log.d("METEOTAG icon", imgStr);

                            listMet.add(new Meteo(imgStr, tempMin, tempMax, temp, date, heure));
                        }
                    }
                }
            }else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND){
                result="";

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            Log.d("METEOTAG", "catch");
            e.printStackTrace();
        }

        return result;
    }

    /**
     *
     * @param date date sélectionnée à reformater pour la comparer avec celle de la liste du JSON
     * @return String de la date reformatée jj/mm/aaaa
     */
    public String calculeDate(String date){
        String mois = date.substring(5,7) + " ";
        String jour = date.substring(8,10) + " ";
        String annee = "20" + date.substring(2,4);
        String dd = "Le " + jour + mois + annee;
        return dd;
    }

}
