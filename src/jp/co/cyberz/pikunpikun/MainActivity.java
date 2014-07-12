package jp.co.cyberz.pikunpikun;

//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import jp.appAdForce.android.AdManager;

public class MainActivity extends Activity implements OnClickListener {
	private static final int IMAGE_CAPTURE = 10001;
    private static final String KEY_IMAGE_URI = "KEY_IMAGE_URI";
    private static final String PHOTO_KEY_LIST = "PHOTO_KEY_LIST";
    private static final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static ArrayList<String> keyList;

    private Uri mImageUri;
    private String fileName;
    private ArrayList<Uri> uriList;

    SharedPreferences pref;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pref = getSharedPreferences("pref",MODE_PRIVATE);

		uriList = new ArrayList<Uri>();
		keyList = new ArrayList<String>();

		//		AdManager ad = new AdManager(this);
		//		ad.sendConversion("");

		//buttonを取得
		Button btn = (Button)findViewById(R.id.board);
		btn.setOnClickListener(this);

		Button cbtn = (Button)findViewById(R.id.camera);
		cbtn.setOnClickListener(this);

		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		 */
	}

	@Override
	protected void onResume() {
		super.onResume();
		//AnalyticsManager.sendStartSession(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View v) {
		Intent intent=new Intent();
		switch(v.getId()){
		case R.id.board:
			intent.setClassName(this,"jp.co.cyberz.pikunpikun.BoardActivity");
			// intent.putExtra("org.jpn.techbooster.demo.intent.testString", "!TEST STRING!");
			startActivity(intent);
			break;
		case R.id.camera:
			mImageUri = getPhotoUri();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            startActivityForResult(intent, IMAGE_CAPTURE);
			break;
		}
	}

    /**
     * カメラから戻ってきた時の処理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                setImageView();
            }
        }
    }

    /**
     * 状態を保持する
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_IMAGE_URI, mImageUri);
    }

    /**
     * 保持した状態を元に戻す
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageUri = (Uri) savedInstanceState.get(KEY_IMAGE_URI);

        // ボード未処理のImageUriをリストに
        uriList.add(mImageUri);

        setImageView();
    }

    /**
     * ImageViewに画像をセットする
     */
    private void setImageView() {

        ImageView imageView = (ImageView) findViewById(R.id.photo_image);

//        for (Uri uri : uriList) {
//        	imageView.setImageURI(uri);
//        }
//        imageView.setImageURI(mImageUri);
//        imageView.setImageURI(mImageUri);


        //SharedPreferenceに保存した文字列からBitmap復元
        byte[] bytes = Base64.decode(pref.getString(fileName,"").getBytes(),Base64.DEFAULT);

        ImageView image = (ImageView)findViewById(R.id.photo_image);
        image.setImageBitmap(
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length)
        );
    }

    /**
     * 画像のディレクトリパスを取得する
     *
     * @return
     */
    private String getDirPath() {
        String dirPath = "";
        File photoDir = null;
        File extStorageDir = Environment.getExternalStorageDirectory();
        if (extStorageDir.canWrite()) {
            photoDir = new File(extStorageDir.getPath() + "/" + getPackageName());
        }
        if (photoDir != null) {
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }
            if (photoDir.canWrite()) {
                dirPath = photoDir.getPath();
            }
        }
        return dirPath;
    }

    /**
     * 画像のUriを取得する
     *
     * @return
     */
    private Uri getPhotoUri() {
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String title = dateFormat.format(today);
        String dirPath = getDirPath();
        fileName = "photo_" + title + ".jpg";
        String path = dirPath + "/" + fileName;
        setFileName(fileName);
        File file = new File(path);

        // ギャラリーに保存
        ContentValues values = new ContentValues();
        values.put(Images.Media.TITLE, title);
        values.put(Images.Media.DISPLAY_NAME, fileName);
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        values.put(Images.Media.DATA, path);
        values.put(Images.Media.DATE_TAKEN, currentTimeMillis);
        if (file.exists()) {
            values.put(Images.Media.SIZE, file.length());
        }
        Uri uri = getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);

        // アプリ内領域に保存
//        pref = getSharedPreferences("pref",MODE_PRIVATE);
        //BitmapをBase64で文字列にしてSharedPreferenceに書き込む
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        String bitmapString = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        pref.edit().putString(fileName, bitmapString).commit();

        keyList.add(fileName);

        return uri;
    }

	private void setFileName(String fileName) {
		// TODO 自動生成されたメソッド・スタブ
		this.fileName = fileName;
	}
}