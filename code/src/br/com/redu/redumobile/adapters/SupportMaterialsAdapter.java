package br.com.redu.redumobile.adapters;

import java.util.List;

import br.com.developer.redu.models.File;
import br.com.developer.redu.models.Folder;
import br.com.developer.redu.models.Status;
import br.com.redu.redumobile.R;
import br.com.redu.redumobile.activities.HomeSpaceActivity;
import br.com.redu.redumobile.widgets.LazyLoadingImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SupportMaterialsAdapter extends BaseAdapter {

	final private LayoutInflater mInflater;
	final private Context mContext;
	
	private List<Folder> mFolders;
	private List<File> mFiles;
	
	public SupportMaterialsAdapter(Context context, List<Folder> folders, List<File> files) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mFolders = folders; 
		mFiles = files;
	}
	
	@Override
	public int getCount() {
		if(mFolders == null) {
			return 0;
		}
		if(mFiles == null) {
			return 0;
		}
				
		return mFolders.size()+mFiles.size();
	}

	@Override
	public Object getItem(int position) {
		if(mFolders.size() > position){
			return mFolders.get(position);
		} else {
			return mFiles.get(position-(mFolders.size()));
		}
		
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
//	public void add(TextView status) {
//		listMaterials.add(status);
//	}
//
//	public void add(List<TextView> statuses) {
//		listMaterials.addAll(statuses);
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v;
		if(mFolders.size() > position){
			v = (TextView) mInflater.inflate(R.layout.support_material_folders, null);
			v.setText(mFolders.get(position).name);
		} else {
			v= (TextView) mInflater.inflate(R.layout.support_material_files, null);
			v.setText(mFiles.get(position-(mFolders.size())).name);
		}
		
		return v;
	}
}
