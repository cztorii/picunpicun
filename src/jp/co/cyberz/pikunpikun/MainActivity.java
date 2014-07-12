package jp.co.cyberz.pikunpikun;

//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;
import android.provider.MediaStore;
import jp.appAdForce.android.AdManager;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			startActivityForResult(intent, 0);
			break;
		}
	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == IMAGE_CAPTURE) {
//			Log.d(TAG, "onActivityResult");
//			if (resultCode == RESULT_OK) {
//				Bundle bundle = data.getExtras();
//				Bitmap bitmap = (Bitmap) bundle.getParcelable("data");
//				ImageView imageView = (ImageView) findViewById(R.id.photo_image);
//				imageView.setImageBitmap(bitmap);
//			}
//		}
//	}
}