package jp.co.cyberz.pikunpikun;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryImageAdapter extends BaseAdapter {
	private Context mContext;
	private List<Bitmap> bmp;
	public GalleryImageAdapter(Context c) {
		mContext = c;
		loadBmp();
	}

	public int getCount() {
		if (bmp == null) {
			return 0;
		}
		return bmp.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// Adapterから参照される新しいImageViewを作成
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {  // インスタンスが生成されていない場合、作成
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		
		if (bmp == null) return imageView;
		
		imageView.setImageBitmap(bmp.get(position));
		return imageView;
	}

	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	    };
	
	/**
	 * 
	 * @return
	 */
	private List<String> loadImageKeyList() {
		//ArrayList<String> keyList = new ArrayList<String>();
		// TODO: プリファレンスのキーを取得する処理
		ArrayList<String> keyList = MainActivity.keyList;
		return keyList;
	}

	/**
	 * 端末に保存されている写真のビットマップ読み込み
	 */
	private void loadBmp() {
		SharedPreferences pref = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
		List<String> keyList = loadImageKeyList();
		if (keyList.isEmpty() || keyList.size() == 0) { 
			return;
		}
		for (int i = 0; i < keyList.size(); i++) {
			byte[] bytes = Base64.decode(pref.getString(keyList.get(i),"").getBytes(),Base64.DEFAULT);
			bmp.add(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
		}
	}
}