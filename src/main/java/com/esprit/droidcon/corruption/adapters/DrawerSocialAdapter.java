package com.esprit.droidcon.corruption.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.droidcon.corruption.Utils.DummyContent;
import com.esprit.droidcon.corruption.models.DummyModel;
import com.esprit.droidcon.corruption.R;

import java.util.List;

public class DrawerSocialAdapter extends BaseAdapter {

	private List<DummyModel> mDrawerItems;
	//private List<DummyModel> mDrawerItemsImage;
	private LayoutInflater mInflater;

	public DrawerSocialAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDrawerItems = DummyContent.getSocialDummyList();
	//	mDrawerItemsImage = DummyContent.getSocialDummyListImage();
	}

	@Override
	public int getCount() {
		return mDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mDrawerItems.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_view_item_navigation_drawer_social, parent,
					false);
			holder = new ViewHolder();
			holder.icon = (TextView) convertView
					.findViewById(R.id.icon_social_navigation_item);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		//	holder.back = (ImageView) convertView.findViewById(R.id.background_social_navigation_item);
		//	convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DummyModel item = mDrawerItems.get(position);
		//DummyModel itemimage = mDrawerItemsImage.get(position);
       // holder.back.setImageResource(itemimage.getDd());
		holder.icon.setText(item.getIconRes());
		holder.title.setText(item.getText());


		return convertView;
	}

	private static class ViewHolder {
		public TextView icon;
		public/* Roboto */TextView title;
		public ImageView back;
		public View mv;
	}
}
