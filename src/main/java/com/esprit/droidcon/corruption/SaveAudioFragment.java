package com.esprit.droidcon.corruption;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.droidcon.corruption.R;
import com.esprit.droidcon.corruption.font.RobotoTextView;
import com.github.jorgecastilloprz.FABProgressCircle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;

import butterknife.ButterKnife;

/**
 * Created by Hassan on 06/02/16.
 */
public class SaveAudioFragment extends Fragment {
    public static ListView maListViewPerso;
    public static ImageView backopacity;
    Button newfile, retrieve,  d_saverec, d_cancelrec;
    TextView d_countdown;
    String outputFile = null;
    byte[] data;
    Button save;
    MediaRecorder myRecorder;
    LinearLayout llrec, llsave;
    CountDownTimer countDownTimer;
    Context context = getContext();
    View Startrec,Stoprec;
    private FABProgressCircle fabProgressCircle;

    View Startplay,Stopplay;


    Button btn;
    Bitmap bb;
    byte[] image;
    private Timer timer;
    public static String PREFERENCE_FILENAME = "reporting_app";
    private boolean isOpenActivitiesActivated = true;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    View fab,fabprog;
    // private FABProgressCircle fabProgressCircle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ImageView myimage;
    EditText nameedit,descedit;
    RobotoTextView esm;
    Uri selectedimg;
    Spinner sp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_audio, container, false);
        ButterKnife.bind(getActivity());


        Startplay = (View) view.findViewById(R.id.fabplay);
        Stopplay = (View) view.findViewById(R.id.fabstop);
        save= (Button) view.findViewById(R.id.savebtn);

        Startrec = (View) view.findViewById(R.id.fabrec);
        Stoprec = (View) view.findViewById(R.id.fabstop);
        d_countdown = (TextView) view.findViewById(R.id.sec);
        fabProgressCircle = (FABProgressCircle) view.findViewById(R.id.fabProgressCirclerec);
      final  MediaPlayer mediaPlayer = new MediaPlayer();

        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/rumor.mp3";

        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myRecorder.setOutputFile(outputFile);

        countDownTimer = null;

        Startrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Startrec.setVisibility(View.GONE);
                Stoprec.setVisibility(View.VISIBLE);
                //llsave.setVisibility(View.GONE);
                fabProgressCircle.show();


                //  dialog.setCancelable(true);

                countDownTimer = new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        d_countdown.setText(millisUntilFinished / 1000 + " s");
                    }

                    public void onFinish() {
                        d_countdown.setText("Time is up!");
                        Startrec.setVisibility(View.VISIBLE);
                        Stoprec.setVisibility(View.GONE);

                        fabProgressCircle.hide();

                        try {
                            myRecorder.stop();
                            myRecorder.release();
                            myRecorder = null;

                        } catch (IllegalStateException e) {
                            //  it is called before start()
                            e.printStackTrace();
                        } catch (RuntimeException e) {
                            // no valid audio/video data has been received
                            e.printStackTrace();
                        }
                    }
                }.start();

                try {
                    myRecorder.prepare();
                    myRecorder.start();
                } catch (IllegalStateException e) {
                    // start:it is called before prepare()
                    // prepare: it is called after start() or before setOutputFormat()
                    e.printStackTrace();
                } catch (IOException e) {
                    // prepare() fails
                    e.printStackTrace();
                }
            }
        });

        Stoprec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                // dialog.setCancelable(false);
                Startrec.setVisibility(View.VISIBLE);
                Stoprec.setVisibility(View.GONE);

                fabProgressCircle.hide();
                // llsave.setVisibility(View.VISIBLE);
                try {
                    myRecorder.stop();
                    myRecorder.release();
                    myRecorder = null;

                } catch (IllegalStateException e) {
                    //  it is called before start()
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    // no valid audio/video data has been received
                    e.printStackTrace();
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileInputStream fileInputStream = null;
                File fileObj = new File(outputFile);
                data = new byte[(int) fileObj.length()];
                UploadFragment.dataAudio= new byte[(int) fileObj.length()];

                try {
                    //convert file into array of bytes
                    fileInputStream = new FileInputStream(fileObj);
                    fileInputStream.read(data);
                    fileInputStream.close();
                    Toast.makeText(getContext(), "Fichier enregistré avec succée", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fabProgressCircle = (FABProgressCircle) view.findViewById(R.id.fabProgressCirclerec);


        //ParseQuery query = new ParseQuery("Music");
        //query.orderByDescending("createdAt");


        Startplay = (View) view.findViewById(R.id.fabplay);
        Stopplay = (View) view.findViewById(R.id.fabstop);









        return  view;
    }






}
