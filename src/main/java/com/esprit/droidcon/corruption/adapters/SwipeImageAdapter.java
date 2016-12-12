package com.esprit.droidcon.corruption.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esprit.droidcon.corruption.R;
import com.esprit.droidcon.corruption.Utils.ImageUtil;

/**
 * Created by Hassan on 08/02/16.
 */


public class SwipeImageAdapter extends PagerAdapter {

    private Context ctx ;
    private LayoutInflater layoutInflater;
    public static boolean touched=false;
    public static String img1;
    public static String img2;
    public static String img3;
    public static String title;
    public static String title2;
    public static String title3;
    public static String desc;
    public static String desc2;
    public static String desc3;



    public SwipeImageAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater =(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.produc_image_pager,container,false);


        ImageView MyImage = (ImageView) item_view.findViewById(R.id.ProductImage);
        TextView Titre = (TextView) item_view.findViewById(R.id.ImageName);
        TextView Description = (TextView) item_view.findViewById(R.id.ImageDesc);

        Titre.setText(title);
        Description.setText(desc);


        if(position==0)

        {

        ImageUtil.displayImage(MyImage, img1, null);

        container.addView(item_view);


        }

        if(position==1)

        {   ImageUtil.displayImage(MyImage, img2, null);

            container.addView(item_view);


        }

        if(position==2)

        {  ImageUtil.displayImage(MyImage, img3, null);

            container.addView(item_view);


        }

//        container.addView(item_view);
        return item_view;







    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}
