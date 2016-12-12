package com.esprit.droidcon.corruption;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esprit.droidcon.corruption.R;
import com.esprit.droidcon.corruption.adapters.DrawerSocialAdapter;

import com.esprit.droidcon.corruption.provider.FragmentTags;
import com.esprit.droidcon.corruption.ui.AboutActivity;
import com.esprit.droidcon.corruption.ui.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.viewpagerindicator.CirclePageIndicator;


public class Main extends BaseActivity {

	private FragmentManager fragmentManager;
	public static String PREFERENCE_FILENAME = "reporting_app";
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	public ImageView backImg;
	public RelativeLayout maincont;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	public ViewPager viewpager;

	private ImageView myimage;
	private TextView UserMail,UserDesc,UserName;
	Fragment fragment;
	Button notifbtn;
	protected ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));







		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainsave);

		maincont = (RelativeLayout) findViewById(R.id.MainContainer);
		fragmentManager = getSupportFragmentManager();

		//addFragment(new MainFragment(), true, R.id.container);
		initToolbar();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//setSupportActionBar(toolbar);
		//getSupportActionBar().setTitle("TLS");
		//getSupportActionBar().setIcon(R.drawable.tlslogo);
		//getSupportActionBar().setCustomView(R.layout.menu_tab);
		ViewGroup cont = null;



		viewpager = (ViewPager) findViewById(R.id.MainViewPager);


		viewpager.setAdapter(new MainVadapter(fragmentManager));
		//	WelcomeListNourriture.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList2));
		viewpager.setOffscreenPageLimit(6);


		viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			int positionCurrent;
			int thispos;
			boolean dontLoadList;

			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				thispos = position;


			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == 0) { // the viewpager is idle as swipping ended
					new Handler().postDelayed(new Runnable() {
						public void run() {
							if (!dontLoadList) {

								//async thread code to execute loading the list...
							}
						}
					}, 200);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				positionCurrent = position;
				if (positionOffset == 0 && positionOffsetPixels == 0) // the offset is zero when the swiping ends
				{
					dontLoadList = false;
				} else
					dontLoadList = true; // To avoid loading content for list after swiping the pager.
			}
		});


		CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.titles);
		titleIndicator.setViewPager(viewpager);


		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mTitle = mDrawerTitle = getTitle();
		mDrawerList = (ListView) findViewById(R.id.list_view);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		View headerView = getLayoutInflater().inflate(
				R.layout.header_navigation_drawer_social, mDrawerList, false);


		//	Glide.with(Main.this)
		//			.load("http://www.online-image-editor.com//styles/2014/images/example_image.png")
		//			.into(myimage);
		//ImageView iv = (ImageView) headerView.findViewById(R.id.imageoo);
		//ImageUtil.displayRoundImage(iv,
		//	"http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg", null);

		mDrawerList.addHeaderView(headerView);// Add header before adapter (for
		// pre-KitKat)
		mDrawerList.setAdapter(new DrawerSocialAdapter(this));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		int color = getResources().getColor(R.color.material_grey_100);
		color = Color.argb(0xCD, Color.red(color), Color.green(color),
				Color.blue(color));
		mDrawerList.setBackgroundColor(color);
		mDrawerList.getLayoutParams().width = (int) getResources()
				.getDimension(R.dimen.drawer_width_social);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
				R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}



	private Fragment findFragmentByTag(FragmentTags tag) {
		return getSupportFragmentManager().findFragmentByTag(tag.toString());
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {

			if(position==1)
			{
				Intent i = new Intent(Main.this, MusicNewsActivity.class);
				startActivity(i);
				finish();
			}

			if(position==2)
			{
				Intent i = new Intent(Main.this, EtatCivileListActivity.class);
				startActivity(i);
				finish();
			}

			if(position==3)
			{
				Intent i = new Intent(Main.this, RelationListActivity.class);
				startActivity(i);
				finish();
			}
			if(position==4)
			{
				Intent i = new Intent(Main.this, SiteWebListActivity.class);
				startActivity(i);
				finish();
			}

			if(position==5)
			{
				Intent i = new Intent(Main.this, AutreListActivity.class);
				startActivity(i);
				finish();
			}


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
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {


			case R.id.about:
			{
				Intent i = new Intent(Main.this, AboutActivity.class);
				startActivity(i);
				finish();
			}
			break;

			case R.id.HomeBtn:
			{

			}
			break;


		}
		return super.onOptionsItemSelected(item);
	}
	/*protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
		invalidateOptionsMenu();
		String backStackName = fragment.getClass().getName();
		boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
		if (!fragmentPopped) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.add(containerId, fragment, backStackName)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			if (addToBackStack)
				transaction.addToBackStack(backStackName);
			transaction.commit();
		}
	}*/


	@Override
	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {

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

	class MainVadapter extends FragmentStatePagerAdapter {


		public MainVadapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			 fragment = null;
			if (position == 0) {
				fragment = new SavePhotoFragment();


			}
			if (position == 1) {
				fragment = new SaveAudioFragment();


			}

			if (position == 2) {
				fragment = new SaveVideoFragment();


			}
			if (position == 3) {
				fragment = new UploadFragment();


			}


			return fragment;
		}

		@Override
		public int getCount() {
			return 4;
		}
	}
}