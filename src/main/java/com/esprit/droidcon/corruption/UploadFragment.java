package com.esprit.droidcon.corruption;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.droidcon.corruption.R;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hassan on 06/02/16.
 */
public class UploadFragment extends Fragment {
    public static ListView maListViewPerso;
    public static ImageView backopacity;
    private FABProgressCircle fabProgressCircle;
    View fab,fabprog;
    double latitude;
    String insertUrl = "http://172.19.247.193:8888/citizen/InsertCase.php";
    double longitude;
    private EditText title,description,date,heure,adress,ville,type,nom,useradress,tel,email;
    private String stitle,sdescription,sdate,sheure,sadress,sville,snom,suseradress,stel,semail,stype;
    String outputFile = null;
    public static Bitmap bit1,bit2,bit3;
    TimePicker tp ;
    GPSTracker gps;
    String setat="Traite";
    public static byte[] dataAudio;
    public static String videocode;
    DatePicker datePicker;
    Spinner sp;
    byte[] image1,image2,image3;
    Uri selectedimg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_page, container, false);

        fabProgressCircle = (FABProgressCircle) view.findViewById(R.id.fabProgressCircle);
        fab = (View) view.findViewById(R.id.fab);

        title = (EditText) view.findViewById(R.id.corruption_name);
        description = (EditText) view.findViewById(R.id.Desc_enter);
      //  date = (EditText) view.findViewById(R.id.corruption_date);
      //  heure = (EditText) view.findViewById(R.id.corruption_heure);
        adress = (EditText) view.findViewById(R.id.adress_name);
        ville = (EditText) view.findViewById(R.id.ville_name);
        nom = (EditText) view.findViewById(R.id.UserNom);
        useradress = (EditText) view.findViewById(R.id.UserAdress);
        tel = (EditText) view.findViewById(R.id.UserTel);
        email = (EditText) view.findViewById(R.id.UserEmail);
        sp = (Spinner) view.findViewById(R.id.spinner4);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);

tp = (TimePicker)view.findViewById(R.id.timePicker);

        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            System.out.println("thoss "+ latitude + "  " + longitude);

            // \n is for new line


        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fabProgressCircle.show();
                //  startYourAsynchronousJob();
                final ProgressDialog loading = ProgressDialog.show(getContext(),"Uploading...","Please wait...",false,false);

                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                        System.out.println("ressssp");
                        loading.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), response , Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        System.out.println(error.getMessage().toString());
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();
                        final String dates = ""+day+"/"+month+"/"+year;
                        int hour=tp.getCurrentHour();

                        stitle = title.getText().toString();
                        sdescription = description.getText().toString();
                        sdate = dates;
                        sheure = hour+"h";
                        sadress = adress.getText().toString();
                        sville = ville.getText().toString();
                        snom = nom.getText().toString();
                        suseradress = useradress.getText().toString();
                        stel =tel.getText().toString();
                        semail = email.getText().toString();
                        stype = sp.getSelectedItem().toString();
                        Map<String,String> parameters  = new HashMap<String, String>();

                       // String image = getStringImage(bitmap);


                        parameters.put("title", stitle);
                        parameters.put("description", sdescription);
                        parameters.put("date", sdate);
                        parameters.put("adress", sadress);
                        parameters.put("lat", latitude+"");
                        parameters.put("longg", longitude+"");
                        parameters.put("heure", sheure);
                        parameters.put("ville", sville);
                        parameters.put("useradress", suseradress);
                        parameters.put("usertel", stel);
                        parameters.put("usermail", semail);
                        parameters.put("type", stype);
                        parameters.put("videocode", videocode);
                        parameters.put("etat", "pas encore traite");

                        return parameters;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());



                requestQueue.add(request);
             //   startActivity(new Intent(InscriptionActivity.this, Welcome.class));
             //   Toast.makeText(getActivity().getApplicationContext(), "Enregistré avec succés! Connectez Vous", Toast.LENGTH_LONG).show();
               // finish();
                final ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    // notify user you are online
                    fab.setClickable(false);
                    new Get_User_Data().execute();





                } else {
                    // notify user you are not online
                    Toast.makeText(getActivity(),
                            "Verifiez votre connexion internet",
                            Toast.LENGTH_LONG).show();
                }




            }
        });



        return  view;
    }

    public class Get_User_Data extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(
                getContext());

        protected void onPreExecute() {


            fabProgressCircle.show();


            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            final String dates = ""+day+"/"+month+"/"+year;
            int hour=tp.getCurrentHour();

            stitle = title.getText().toString();
            sdescription = description.getText().toString();
            sdate = dates;
            sheure = hour+"h";
            sadress = adress.getText().toString();
            sville = ville.getText().toString();
            snom = nom.getText().toString();
            suseradress = useradress.getText().toString();
            stel =tel.getText().toString();
            semail = email.getText().toString();
            stype = sp.getSelectedItem().toString();




        }

        @Override
        protected Void doInBackground(Void... params) {


            System.out.println("backgroundsssss");

            if(bit1!=null)
            {   ByteArrayOutputStream stream1 = new ByteArrayOutputStream();

 bit1.compress(Bitmap.CompressFormat.JPEG, 30, stream1);
            image1 = stream1.toByteArray();}
if(bit2!=null)
{   ByteArrayOutputStream stream2 = new ByteArrayOutputStream();

            bit2.compress(Bitmap.CompressFormat.JPEG, 30, stream2);

            image2 = stream2.toByteArray();}

if(bit3!=null)
{ByteArrayOutputStream stream3 = new ByteArrayOutputStream();


            bit3.compress(Bitmap.CompressFormat.JPEG, 30, stream3);
            image3 = stream3.toByteArray();}

            ////////////////////////////////////////







            ////////////////////////////////////////
            outputFile = Environment.getExternalStorageDirectory().
                    getAbsolutePath() + "/rumor.mp3";

            FileInputStream fileInputStream = null;
            File fileObj = new File(outputFile);
            if(videocode==null)
            {videocode="";}


            try {
                //convert file into array of bytes
                fileInputStream = new FileInputStream(fileObj);
                fileInputStream.read(dataAudio);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(dataAudio!= null)
            {ParseFile parseAudioFile = new ParseFile("Music", dataAudio);

                ParseFile file1 = new ParseFile("Photo", image1);
                ParseFile file2 = new ParseFile("Photo", image2);
                ParseFile file3 = new ParseFile("Photo", image3);
                //file.saveInBackground();

                ParseObject Corruption = new ParseObject("Corruption");


                // Create a column named "ImageFile" and insert the image
                Corruption.put("UserMail", semail);
                Corruption.put("UserTel", stel);
                Corruption.put("UserAdress", suseradress);
                Corruption.put("UserNom", snom);
                Corruption.put("Ville", sville);
                Corruption.put("Heure", sheure);
                Corruption.put("Date", sdate);
                Corruption.put("Adress", sadress);
                Corruption.put("Description", sdescription);
                Corruption.put("Title", stitle);
                Corruption.put("Photo1", file1);
                Corruption.put("Photo2", file2);
                Corruption.put("Photo3", file3);
                Corruption.put("AudioFile", parseAudioFile);
                Corruption.put("Lat", latitude);
                Corruption.put("Long", longitude);
                Corruption.put("Type", stype);
                Corruption.put("VideoCode", videocode);
                Corruption.put("Etat", "pas encore traite");


                // Create the class and the columns
                Corruption.saveInBackground();}
            else{

                ParseFile file1 = new ParseFile("Photo", image1);
                ParseFile file2 = new ParseFile("Photo", image2);
                ParseFile file3 = new ParseFile("Photo", image3);
                //file.saveInBackground();

                ParseObject Corruption = new ParseObject("Corruption");


                // Create a column named "ImageFile" and insert the image
                Corruption.put("UserMail", semail);
                Corruption.put("UserTel", stel);
                Corruption.put("UserAdress", suseradress);
                Corruption.put("UserNom", snom);
                Corruption.put("Ville", sville);
                Corruption.put("Heure", sheure);
                Corruption.put("Date", sdate);
                Corruption.put("Adress", sadress);
                Corruption.put("Description", sdescription);
                Corruption.put("Title", stitle);
                Corruption.put("Photo1", file1);
                Corruption.put("Photo2", file2);
                Corruption.put("Photo3", file3);

                Corruption.put("Lat", latitude);
                Corruption.put("Long", longitude);
                Corruption.put("Type", stype);
                Corruption.put("VideoCode", videocode);
                Corruption.put("Etat", "pas encore traite");


                // Create the class and the columns
                Corruption.saveInBackground();
            }


            // mail = reportingPref.getString("mail", "");

            return null;

        }

        protected void onPostExecute(Void result) {
            fabProgressCircle.beginFinalAnimation();

            // Here if you wish to do future process for ex. move to another activity do here

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }

        public String getStringImage(Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
    }



}
