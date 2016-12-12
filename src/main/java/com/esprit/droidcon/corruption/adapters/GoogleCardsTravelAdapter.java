package com.esprit.droidcon.corruption.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.droidcon.corruption.R;
import com.esprit.droidcon.corruption.models.Corruption;
import com.esprit.droidcon.corruption.Utils.ImageUtil;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.List;

public class GoogleCardsTravelAdapter extends BaseAdapter
		implements OnClickListener {

	private LayoutInflater mInflater;
	List<Corruption> corupts;

	public GoogleCardsTravelAdapter(Context context, List<Corruption> items) {
		//super(context, 0, items);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.corupts = items;
	}

	public void update(List<Corruption> corupts) {
		this.corupts = corupts;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return corupts.size();
	}

	@Override
	public Object getItem(int arg0) {
		return corupts.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_google_cards_travel, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_travel_image);
			holder.desc = (TextView) convertView
					.findViewById(R.id.newsdesc);

			holder.title = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_title);
			holder.villename = (TextView) convertView
					.findViewById(R.id.ville);


			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		Corruption item = (Corruption) getItem(position);



		ImageUtil.displayImage(holder.image, item.getImage1(), null);
		holder.title.setText(item.getTitle());
		holder.desc.setText(item.getDesc());
		holder.villename.setText("Situé à: "+item.getVille());


		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView etat;
		public TextView title;
        public TextView villename;
		public TextView text;
		public TextView explore;
		public TextView share;
		public TextView desc;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int possition = (Integer) v.getTag();
		switch (v.getId()) {
		
		}
	}
}
