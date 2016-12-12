package com.esprit.droidcon.corruption;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Geocoder;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.esprit.droidcon.corruption.R;
import com.esprit.droidcon.corruption.adapters.SwipeImageAdapter;
import com.esprit.droidcon.corruption.ui.AboutActivity;
import com.esprit.droidcon.corruption.ui.BaseActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.viewpagerindicator.CirclePageIndicator;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hassan on 27/02/16.
 */
public class SingleCorruptionActivity extends BaseActivity implements OnMapReadyCallback,YouTubePlayer.OnInitializedListener {


    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerFragment youTubeView;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;
    private YouTubePlayer player;
    private static final float DEFAULTZOOM=15;
    private GoogleMap mMap;
    private String AudioUrl;
    public ViewPager viewpager;
    public ShareButton facebookbtn;
    public MediaPlayer mediaPlayer;
    public TextView charg;
    public double longi ,lat;
    SharePhoto photo;
    FABProgressCircle fabProgressCircle;
    View Startplay,Stopplay;
    private GoogleApiClient client;
    String date,heure,ville,usernom,useradress,usertel,usermail,type,videocode,title,desc,etat;
    private TextView datetxt,heuretxt,villetxt,usernomtxt,useradresstxt,usermailtxt,userteltxt,typetxt,setat;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.singlecorruption);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LikeView likeView = (LikeView) findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);

        likeView.setObjectIdAndType(
                "https://www.facebook.com/Citizen-supervisor-1534263073534723/",
                LikeView.ObjectType.PAGE);
        facebookbtn = (ShareButton)
                findViewById(R.id.facebookshare);

        shareDialog = new ShareDialog(this);
        // this part is optional

        Bundle extras = getIntent().getExtras();
        AudioUrl= extras.getString("audiourl");
        date= extras.getString("date");
        title= extras.getString("title");
        desc=extras.getString("desc");
        heure= extras.getString("heure");
        ville= extras.getString("ville");
        type = extras.getString("type");
        usernom= extras.getString("usernom");
        useradress= extras.getString("useradress");
        usertel= extras.getString("usertel");
        usermail= extras.getString("usermail");
        longi= extras.getDouble("longi");
        videocode= extras.getString("videocode");
        etat= extras.getString("etat");
        setat=(TextView) findViewById(R.id.etat);

        if(etat=="traite")
        {setat.setText(etat);
            setat.setTextColor(Color.GREEN);}
        else  if(etat=="en cours de traitement")
        {setat.setText(etat);
            setat.setTextColor(Color.WHITE);}
        else{
            setat.setText(etat);
            setat.setTextColor(Color.BLACK);
        }
        lat= extras.getDouble("lat");
        System.out.println(videocode + "this is video code");

        if(videocode == null  || videocode =="")
        {
            videocode ="OuylEZyQnw4";
        }

        System.out.println(videocode + "this is video code");

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });


        facebookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fabProgressCircle.show();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()

                            .setContentTitle(title)
                            .setContentDescription(
                                    desc)

                            .setContentUrl(Uri.parse("https://www.youtube.com/watch?v="+videocode))
                            .build();

                    shareDialog.show(linkContent);
                }
                //  startYourAsynchronousJob();


            }
        });



        youTubeView = (YouTubePlayerFragment)getFragmentManager()
                .findFragmentById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Startplay = (View) findViewById(R.id.fabplay);
        Stopplay = (View) findViewById(R.id.fabstop);
        mediaPlayer = new MediaPlayer();
        datetxt= (TextView) findViewById(R.id.date);
        heuretxt= (TextView) findViewById(R.id.heure);
        villetxt= (TextView) findViewById(R.id.ville);
        usernomtxt= (TextView) findViewById(R.id.UserNom);
        useradresstxt= (TextView) findViewById(R.id.AdressUser);
        usermailtxt= (TextView) findViewById(R.id.EmailUser);
        userteltxt= (TextView) findViewById(R.id.TelephoneUser);
        typetxt= (TextView) findViewById(R.id.service);

        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();

        initToolbar();

        fabProgressCircle = (FABProgressCircle) findViewById(R.id.fabProgressCirclerec);
        charg = (TextView) findViewById(R.id.chargement);
        charg.setVisibility(View.GONE);

        datetxt.setText("Le " + date);
        heuretxt.setText("Vers " + heure);
        villetxt.setText("A " + ville);
        usernomtxt.setText("Nom,Prénom : " + usernom);
        usermailtxt.setText("Email : " + usermail);
        userteltxt.setText("Tel: " + usertel);
        useradresstxt.setText("Adresse: " + useradress);
        typetxt.setText("Service : "+ type);


        viewpager=(ViewPager)findViewById(R.id.ImageProductPager);
        viewpager.setAdapter(new SwipeImageAdapter(this));
        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);
        titleIndicator.setViewPager(viewpager);

        Stopplay.setVisibility(View.GONE);
        Startplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Startplay.setVisibility(View.GONE);
                Stopplay.setVisibility(View.VISIBLE);
                //llsave.setVisibility(View.GONE);
                fabProgressCircle.show();

                new Get_User_Data().execute();


            }
        });

        Stopplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Startplay.setVisibility(View.VISIBLE);
                Stopplay.setVisibility(View.GONE);
                //llsave.setVisibility(View.GONE);
                fabProgressCircle.hide();

                mediaPlayer.stop();






            }});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.about:
            {
                Intent i = new Intent(SingleCorruptionActivity.this, AboutActivity.class);
                startActivity(i);
                finish();
            }
            break;

            case R.id.HomeBtn:
            {
                Intent i = new Intent(SingleCorruptionActivity.this, Main.class);
                startActivity(i);
                finish();
            }
            break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("citizen supervisor");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(pos).title("Ici"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng((pos)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longi), 15.4f));


        //hideSoftKeyboard(v);
        // EditText et =(EditText) findViewById(R.id.editText1);


        Geocoder gc =new Geocoder(this);





    }
    public void gotoLocation(double lat,double lng, float zoom){
        LatLng ll= new LatLng(lat, lng);
        //lmMap.addMarker(new MarkerOptions().position(ll).title("Marker in Sydney"));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if(videocode!=null && videocode !="")
        {   this.player = youTubePlayer;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        if (!b  ) {

            player.cueVideo(videocode); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }}

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {

        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


    public class Get_User_Data extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(
                getBaseContext());

        protected void onPreExecute() {

            charg.setVisibility(View.VISIBLE);




        }

        @Override
        protected Void doInBackground(Void... params) {


            if (AudioUrl != null) {


                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(AudioUrl);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                } catch (IllegalArgumentException e1) {
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                } catch (SecurityException e1) {
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                } catch (IllegalStateException e1) {
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
            if (AudioUrl == null) {

                System.out.println("Pas de source audio");
            }

            return null;

        }

        protected void onPostExecute(Void result) {

            charg.setVisibility(View.GONE);


            // Here if you wish to do future process for ex. move to another activity do here



        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }

    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().

        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().

        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.

        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SingleCorruptionActivity.this, Main.class);
        startActivity(i);
        finish();
        finish();
    }



}
