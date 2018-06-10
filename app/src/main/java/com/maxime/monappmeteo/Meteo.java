package com.maxime.monappmeteo;

import java.io.Serializable;

public class Meteo implements Serializable {

    private int img;
    private String tempMin;
    private String tempMax;
    private String date;
    private String temp;
    private String heure;


    //constructeur
    public Meteo(String img, String tempMin, String tempMax, String temp, String date, String heure) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.temp = temp;
        this.date = date;
        this.heure = heure;
        if(img.equals("01d")) {
            this.img = R.mipmap.sun;
        }
        else if(img.equals("02d")) {
            this.img = R.mipmap.semi_couvert_jour; // semi couvert
        }
        else if(img.equals("02n")) {
            this.img = R.mipmap.semi_couvert_nuit; // semi couvert nuit
        }
        else if(img.equals("03d") || img.equals("03n")) {
            this.img = R.mipmap.nuages;
        }
        else if(img.equals("04d") || img.equals("04n")) {
            this.img = R.mipmap.nuages_noirs; // nuages noirs
        }
        else if(img.equals("09d") || img.equals("09n")) {
            this.img = R.mipmap.grosse_pluie; // grosse pluie
        }
        else if(img.equals("10d")) {
            this.img = R.mipmap.pluie_jour; // pluie de jour
        }
        else if(img.equals("10n")) {
            this.img = R.mipmap.pluie_nuit; // pluie de nuit
        }
        else if(img.equals("11d") || img.equals("11n")) {
            this.img = R.mipmap.orage;
        }
        else if(img.equals("13d") || img.equals("13n")) {
            this.img = R.mipmap.neige;
        }
        else if(img.equals("50d") || img.equals("50n")) {
            this.img = R.mipmap.brouillard; // brouillard
        }
        else {
            this.img = R.mipmap.autre;
        }
    }

    public String getTemp() {
        return temp;
    }

    public int getImg() {
        return img;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getHeure() {
        if(heure.equals("")){
            return "";
        }else {
            return heure.substring(10,16);
        }
    }

    public String getDate() {
        String mois = date.substring(5,7) + " ";
        String jour = date.substring(8,10) + " ";
        String annee = "20" + date.substring(2,4);
        String dd = "Le " + jour + mois + annee;
        return dd;
    }

}
