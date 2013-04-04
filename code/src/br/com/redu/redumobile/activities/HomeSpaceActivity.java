package br.com.redu.redumobile.activities;

import java.util.ArrayList;

import android.content.ClipData.Item;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import br.com.developer.redu.models.Folder;
import br.com.developer.redu.models.Space;
import br.com.redu.redumobile.R;
import br.com.redu.redumobile.fragments.space.MorphologyFragment;
import br.com.redu.redumobile.fragments.space.SpaceWallFragment;
import br.com.redu.redumobile.fragments.space.SupportMaterialFragment;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class HomeSpaceActivity extends BaseActivity {
	public static final String ITEM_EXTRA_PARAM = "ITEM_CHECKED";

	private static final String[] titles = new String[]{"Aulas", "Mural", "Material de Apoio"};
	
	static final int NUM_ITEMS = 3;

	static final int ITEM_MORPHOLOGY = 0;
	static final int ITEM_WALL = 1;
	static final int ITEM_SUPPORT_MATERIAL = 2;
	
	
    private PageIndicator mIndicator;
    public MainAdapter mAdapter;
    
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_space);
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		final ViewPager vp = (ViewPager) findViewById(R.id.vp2);
		mAdapter = new MainAdapter(getSupportFragmentManager());
		vp.setAdapter(mAdapter);
		
		
		mIndicator = (TitlePageIndicator) findViewById(R.id.titles2);
		mIndicator.setViewPager(vp);
		
		Space space = (Space)getIntent().getExtras().get(Space.class.getName());
		setActionBarTitle("Disciplina "+space.name);
		
		int itemChecked = getIntent().getIntExtra(ITEM_EXTRA_PARAM, ITEM_WALL);
		mIndicator.setCurrentItem(itemChecked);
		
	}
	
	class MainAdapter extends FragmentPagerAdapter {
		private final Fragment[] items;
		private final FragmentManager mFragmentManager;
		private int currentLevel;
		private ArrayList<SupportMaterialFragment> materialFragments;
	    //private Fragment mFragmentAtPos0;
		

		public MainAdapter(FragmentManager fm) {
			super(fm);
			mFragmentManager = fm;
			items = new Fragment[NUM_ITEMS];
			currentLevel = 0;
			materialFragments = new ArrayList<SupportMaterialFragment>();
			items[ITEM_MORPHOLOGY] = new MorphologyFragment();
			items[ITEM_WALL] = new SpaceWallFragment();
			items[ITEM_SUPPORT_MATERIAL] = new SupportMaterialFragment();
			((SupportMaterialFragment) items[ITEM_SUPPORT_MATERIAL]).setListener(new SupportMaterialFragmentListener() {
				
				@Override
				public void onSwitchToNextFragment(Folder folder) {
					materialFragments.add((SupportMaterialFragment)items[ITEM_SUPPORT_MATERIAL]);
					mFragmentManager.beginTransaction().remove(items[ITEM_SUPPORT_MATERIAL]).commit();
					//TODO criar pilha
					//Bundle de folder
					//mFragmentManager.beginTransaction().add(arg0, arg1, arg2)
					items[ITEM_SUPPORT_MATERIAL] = new SupportMaterialFragment();
                    notifyDataSetChanged();
				}
				
				public void onBackToPreviousFragment(Folder folder){
					materialFragments.remove((SupportMaterialFragment)items[ITEM_SUPPORT_MATERIAL]);
					mFragmentManager.beginTransaction().remove(items[ITEM_SUPPORT_MATERIAL]).commit();
					items[ITEM_SUPPORT_MATERIAL] = materialFragments.get(materialFragments.size()-1);
				}
			});
			
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
		
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			/*if (position == 2){
					return items[ITEM_SUPPORT_MATERIAL];
				}
				return items[ITEM_SUPPORT_MATERIAL];
			}
			else*/
				return items[position]; 
		}
		
		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			if (object instanceof SupportMaterialFragment)
	            return POSITION_NONE;
	        return POSITION_UNCHANGED;
		}
	}
	
	public interface SupportMaterialFragmentListener
	{
	    void onSwitchToNextFragment(Folder folder);
	    void onBackToPreviousFragment(Folder folder);
	}
	
}
