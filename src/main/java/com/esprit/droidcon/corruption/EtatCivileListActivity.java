/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esprit.droidcon.corruption;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.droidcon.corruption.adapters.GoogleCardsTravelAdapter;
import com.esprit.droidcon.corruption.adapters.SwipeImageAdapter;
import com.esprit.droidcon.corruption.models.Corruption;
import com.esprit.droidcon.corruption.ui.AboutActivity;
import com.esprit.droidcon.corruption.ui.BaseActivity;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EtatCivileListActivity extends BaseActivity
		{

	private static final int INITIAL_DELAY_MILLIS = 300;
	private FragmentManager fragmentManager;
	public static String PREFERENCE_FILENAME = "reporting_app";
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	public ImageView backImg;
	public RelativeLayout maincont;
	Button retry;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	public ViewPager viewpager;
	RelativeLayout connexionlay;

	private ImageView myimage;
	private TextView UserMail,UserDesc,UserName;
	Fragment fragment;
	Button notifbtn;

	AsyncTask<Void, Void, List> a = null;


	protected ImageLoader imageLoader;
	List<Corruption> mang;
			@Bind(R.id.image_f)
			ImageView mImageView;


	private GoogleCardsTravelAdapter mGoogleCardsAdapter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maillist_layout);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		retry = (Button) findViewById(R.id.retry);
		ButterKnife.bind(this);
		mBackground = mImageView;
		moveBackground();
		initToolbar();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		connexionlay = (RelativeLayout) findViewById(R.id.connexionlay);



		mang = new ArrayList<>();

		final ListView listView = (ListView) findViewById(R.id.list_view);

		final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {

			connexionlay.setVisibility(View.GONE);
			// notify user you are online
			final ParseQuery<ParseObject> query = ParseQuery.getQuery("Corruption");
			query.whereEqualTo("Type", "Etat Civil");
			query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> conThus, ParseException e) {
					if (e == null) {
						//Log.d("score", "Retrieved " + scoreList.size() + " scores");
						for (int i = conThus.size()-1; i > 0; i--) {
							ParseObject dong = conThus.get(i);
							//System.out.println(dong.getParseFile("ImageFile").toString());
							if (dong.getParseFile("AudioFile") != null) {
								mang.add(new Corruption(
										dong.getString("Title"),
										dong.getString("Description"),
										dong.getString("Date"),
										dong.getString("Adress"),
										dong.getParseFile("Photo1").getUrl(),
										dong.getParseFile("Photo2").getUrl(),
										dong.getParseFile("Photo3").getUrl(),
										dong.getParseFile("AudioFile").getUrl(),
										dong.getDouble("Long"),
										dong.getDouble("Lat"),
										dong.getString("Heure"),
										dong.getString("Ville"),
										dong.getString("UserNom"),
										dong.getString("UserAdress"),
										dong.getString("UserTel"),
										dong.getString("UserMail"),
										dong.getString("Type"),
										dong.getString("VideoCode"),
										dong.getString("Etat")


								));



								mGoogleCardsAdapter = new GoogleCardsTravelAdapter(getApplicationContext(), mang
								);
								SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
										mGoogleCardsAdapter);
								swingBottomInAnimationAdapter.setAbsListView(listView);

								assert swingBottomInAnimationAdapter.getViewAnimator() != null;
								swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
										INITIAL_DELAY_MILLIS);

								listView.setClipToPadding(false);
								listView.setDivider(null);
								Resources r = getResources();
								int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
										8, r.getDisplayMetrics());
								listView.setDividerHeight(px);
								listView.setFadingEdgeLength(0);
								listView.setFitsSystemWindows(true);
								px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
										r.getDisplayMetrics());
								listView.setPadding(px, px, px, px);
								listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
								listView.setAdapter(swingBottomInAnimationAdapter);

				/*	SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
							new SwipeDismissAdapter(mGoogleCardsAdapter, this));
					swingBottomInAnimationAdapter.setAbsListView(listView);*/
							}else

							{mang.add(new Corruption(
									dong.getString("Title"),
									dong.getString("Description"),
									dong.getString("Date"),
									dong.getString("Adress"),
									dong.getParseFile("Photo1").getUrl(),
									dong.getParseFile("Photo2").getUrl(),
									dong.getParseFile("Photo3").getUrl(),
									dong.getDouble("Long"),
									dong.getDouble("Lat"),
									dong.getString("Heure"),
									dong.getString("Ville"),
									dong.getString("UserNom"),
									dong.getString("UserAdress"),
									dong.getString("UserTel"),
									dong.getString("UserMail"),
									dong.getString("Type"),
									dong.getString("VideoCode"),
									dong.getString("Etat")


							));



								mGoogleCardsAdapter = new GoogleCardsTravelAdapter(getApplicationContext(), mang
								);
								SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
										mGoogleCardsAdapter);
								swingBottomInAnimationAdapter.setAbsListView(listView);

								assert swingBottomInAnimationAdapter.getViewAnimator() != null;
								swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
										INITIAL_DELAY_MILLIS);

								listView.setClipToPadding(false);
								listView.setDivider(null);
								Resources r = getResources();
								int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
										8, r.getDisplayMetrics());
								listView.setDividerHeight(px);
								listView.setFadingEdgeLength(0);
								listView.setFitsSystemWindows(true);
								px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
										r.getDisplayMetrics());
								listView.setPadding(px, px, px, px);
								listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
								listView.setAdapter(swingBottomInAnimationAdapter);





							}}

					} else {
						Log.d("score", "Error: " + e.getMessage());
						Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			});

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {


					Corruption corp = (Corruption) parent.getItemAtPosition(position);
					SwipeImageAdapter.img1 = corp.getImage1();
					SwipeImageAdapter.img2 = corp.getImage2();
					SwipeImageAdapter.img3 = corp.getImage3();
					SwipeImageAdapter.title = corp.getTitle();
					SwipeImageAdapter.desc = corp.getDesc();
					//prodimg = prod.getProductImage();
					Intent i = new Intent(EtatCivileListActivity.this, SingleCorruptionActivity.class);
					i.putExtra("audiourl", corp.getAudiofile());
					i.putExtra("longi", corp.getLongi());
					i.putExtra("lat", corp.getLat());
					i.putExtra("title", corp.getTitle());
					i.putExtra("desc", corp.getDesc());
					i.putExtra("date", corp.getDate());
					i.putExtra("heure", corp.getHeure());
					i.putExtra("ville", corp.getVille());
					i.putExtra("usernom", corp.getUserNom());
					i.putExtra("useradress", corp.getUserAdress());
					i.putExtra("usertel", corp.getUserTel());
					i.putExtra("usermail", corp.getUserMail());
					i.putExtra("type", corp.getType());
					i.putExtra("videocode", corp.getVideo());
					i.putExtra("etat", corp.getEtat());



					startActivity(i);
					finish();


				}
			});

		} else {
			// notify user you are not online
			connexionlay.setVisibility(View.VISIBLE);
			retry.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// fabProgressCircle.show();
					//  startYourAsynchronousJob();
					Intent refresh = new Intent(EtatCivileListActivity.this, EtatCivileListActivity.class);
					startActivity(refresh);
					finish();


				}
			});


		}




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

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {



		}



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
				Intent i = new Intent(EtatCivileListActivity.this, AboutActivity.class);
				startActivity(i);
				finish();
			}
			break;

			case R.id.HomeBtn:
			{
				Intent i = new Intent(EtatCivileListActivity.this, Main.class);
				startActivity(i);
				finish();
			}
			break;


		}
		return super.onOptionsItemSelected(item);
	}

			@Override
			public void onBackPressed() {
				super.onBackPressed();
				finish();
			}
}