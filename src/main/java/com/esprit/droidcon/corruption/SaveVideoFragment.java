package com.esprit.droidcon.corruption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.droidcon.corruption.R;

/**
 * Created by Hassan on 06/02/16.
 */
public class SaveVideoFragment extends Fragment implements View.OnTouchListener, Handler.Callback {
    public static ListView maListViewPerso;
    public static ImageView backopacity;
    private Button SaveVideo;
    RelativeLayout webLay;
    private WebView myWebView;
    TextView urlvideo;
    private final Handler handler = new Handler(this);
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    Bundle link=new Bundle();
    String idyoutube="";
    private WebViewClient client;
    String webUrl="";
    String url="http://m.youtube.com/";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_video, container, false);
        SaveVideo= (Button) view.findViewById(R.id.savephoto);
        webLay = (RelativeLayout) view.findViewById(R.id.weblay);
        webLay.setVisibility(View.GONE);
        myWebView = (WebView) view.findViewById(R.id.activity_main_webview);
        urlvideo=(TextView) view.findViewById(R.id.urlvideo);
        UploadFragment.videocode="OuylEZyQnw4";
        myWebView.setOnTouchListener(this);
        client = new WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                handler.sendEmptyMessage(CLICK_ON_URL);

                return false;
            }



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub

                webUrl = myWebView.getUrl();
                System.out.println("###nasjkxbsa99999999"+webUrl);



                super.onPageStarted(view, url, favicon);


            }
        };

        myWebView.setWebViewClient(client);
        myWebView.setVerticalScrollBarEnabled(false);
        myWebView.loadUrl(url);

        myWebView.getSettings().setJavaScriptEnabled(true);
        SaveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webLay.setVisibility(View.VISIBLE);



            }
        });
        return  view;
    }






    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_ON_URL){
            handler.removeMessages(CLICK_ON_WEBVIEW);
            System.out.println(webUrl);


            return true;
        }
        if (msg.what == CLICK_ON_WEBVIEW){
            handler.removeMessages(CLICK_ON_WEBVIEW);


            //Toast.makeText(getActivity().getBaseContext(), "WebView clicked", Toast.LENGTH_SHORT).show();

            webUrl = myWebView.getUrl();
            if(webUrl.contains("/watch?list")) {
                Toast.makeText(getActivity().getBaseContext(), "ceci est une liste veuillez cliquez sur une video", Toast.LENGTH_SHORT).show();
            }
            if(webUrl.contains("/watch?v=")){
             String code =  webUrl.substring(webUrl.lastIndexOf("=") + 1);
               // Toast.makeText(getActivity().getBaseContext(),"this is "+ code, Toast.LENGTH_SHORT).show();
                urlvideo.setText(webUrl);
                webLay.setVisibility(View.GONE);
                myWebView.destroy();
                UploadFragment.videocode=code;




            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        return false;
    }
}
