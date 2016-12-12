package com.esprit.droidcon.corruption.models;

import android.widget.Button;

/**
 * Created by Hassan on 27/02/16.
 */
public class Corruption {

    public String Title;
    public  String Desc;
    public String Date;
    public String Adress;
    public String Image1;
    public String Image2;
    public String Image3;
    public String Audiofile;
    public String Video;

    public double longi;
    public double lat;
    public String Heure;
    public String Ville;
    public String UserNom;
    public String userAdress;
    public String UserTel;
    public String UserMail;
    public String Type;
    public String Etat;

    public Corruption(String title, String desc, String date, String adress, String image1, String image2, String image3, String audiofile, String video, double longi, double lat, String heure, String ville, String userNom, String userAdress, String userTel, String userMail, String type, String etat) {
        Title = title;
        Desc = desc;
        Date = date;
        Adress = adress;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Audiofile = audiofile;
        Video = video;
        this.longi = longi;
        this.lat = lat;
        Heure = heure;
        Ville = ville;
        UserNom = userNom;
        this.userAdress = userAdress;
        UserTel = userTel;
        UserMail = userMail;
        Type = type;
        Etat = etat;
    }

    public Corruption(String title, String desc, String date, String adress, String image1, String image2, String image3, String audiofile, double longi, double lat, String heure, String ville, String userNom, String userAdress, String userTel, String userMail, String type, String video, String etat) {
        Title = title;
        Desc = desc;
        Date = date;
        Adress = adress;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Audiofile = audiofile;
        Video = video;
        this.longi = longi;
        this.lat = lat;
        Heure = heure;
        Ville = ville;
        UserNom = userNom;
        this.userAdress = userAdress;
        UserTel = userTel;
        UserMail = userMail;
        Type = type;
        Etat = etat;
    }



    public Corruption(String title, String desc, String date, String adress, String image1, String image2, String image3,  double longi, double lat, String heure, String ville, String userNom, String userAdress, String userTel, String userMail, String type,String video, String etat) {
        Title = title;
        Desc = desc;
        Date = date;
        Adress = adress;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Video = video;
        this.longi = longi;
        this.lat = lat;
        Heure = heure;
        Ville = ville;
        UserNom = userNom;
        this.userAdress = userAdress;
        UserTel = userTel;
        UserMail = userMail;
        Type = type;
        Etat= etat;
    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getAudiofile() {
        return Audiofile;
    }

    public void setAudiofile(String audiofile) {
        Audiofile = audiofile;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public String getHeure() {
        return Heure;
    }

    public void setHeure(String heure) {
        Heure = heure;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String ville) {
        Ville = ville;
    }

    public String getUserNom() {
        return UserNom;
    }

    public void setUserNom(String userNom) {
        UserNom = userNom;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }

    public String getUserTel() {
        return UserTel;
    }

    public void setUserTel(String userTel) {
        UserTel = userTel;
    }

    public String getUserMail() {
        return UserMail;
    }

    public void setUserMail(String userMail) {
        UserMail = userMail;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }
}
