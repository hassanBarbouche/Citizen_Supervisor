package com.esprit.droidcon.corruption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.esprit.droidcon.corruption.R;
import com.esprit.droidcon.corruption.Utils.ImageUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Hassan on 06/02/16.
 */
public class SavePhotoFragment extends Fragment {
    public static ListView maListViewPerso;
    public static ImageView backopacity;
    Button next;
    public static final int RESULT_OK = -1;
    private int PICK_IMAGE_REQUEST = 1;
    ImageView image1,image2,image3;
    private int position ;
    Bitmap icon;
    private Button SavePhoto;
    public static Bitmap bitmap1,bitmap2,bitmap3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_photo, container, false);
        SavePhoto= (Button) view.findViewById(R.id.savephoto);

        image1 = (ImageView) view.findViewById(R.id.photo1);
        image2 = (ImageView) view.findViewById(R.id.photo2);
        image3 = (ImageView) view.findViewById(R.id.photo3);
        position= 0;
        image1.setTag(R.drawable.noimage);
        image2.setTag(R.drawable.noimage);
        image3.setTag(R.drawable.noimage);

         icon = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.noimage);

        UploadFragment.bit1= icon;
        UploadFragment.bit2= icon;
        UploadFragment.bit3= icon;

        int d1= image1.getId();
        int d2 = image2.getId();
        int d3 = image3.getId();

        System.out.println(getDrawableId(image1)+ "this is id 1");
        System.out.println(getDrawableId(image2)+ "this is id 2");
        System.out.println(getDrawableId(image3)+ "this is id 3");



        SavePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showFileChooser();


            }
        });




        return  view;
    }



    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choisir votre photo"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();



            if(position==0 )
            {
            try {

                //Getting the Bitmap from Gallery
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                //Getting the Bitmap from Gallery

                bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                System.out.println(filePath+ " this is filepath");


                UploadFragment.bit1= bitmap1;
                //Setting the Bitmap to ImageView
                ImageUtil.displayImage(image1, filePath.toString(), null);
              //  image1.setImageBitmap(bitmap1);
                position=1;
            } catch (IOException e) {
                e.printStackTrace();
            }}

            else if(position==1)
            {
                try {
                    //Getting the Bitmap from Gallery

                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    //Getting the Bitmap from Gallery

                    bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    //Setting the Bitmap to ImageView



                    UploadFragment.bit2= bitmap2;
                //    image2.setImageBitmap(bitmap2);
                    ImageUtil.displayImage(image2, filePath.toString(), null);
                    position=2;
                } catch (IOException e) {
                    e.printStackTrace();
                }}

           else if(position==2 )
            {
                try {
                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    //Getting the Bitmap from Gallery


                    bitmap3 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    //Setting the Bitmap to ImageView



                    UploadFragment.bit3= bitmap3;
                    ImageUtil.displayImage(image3, filePath.toString(), null);
                //    image3.setImageBitmap(bitmap3);
                    position=0;
                } catch (IOException e) {
                    e.printStackTrace();
                }}
        }
    }

    private int getDrawableId(ImageView iv) {
        return (Integer) iv.getTag();
    }



}
