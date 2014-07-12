package jp.co.cyberz.pikunpikun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Board extends View {
	
	public Board(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		Paint p = new Paint();
		Bitmap img;
		
		// TODO: preferenceからその日に撮影された画像を取得する
		Resources res = this.getContext().getResources();
//		img0 = BitmapFactory.decodeResource(res, R.drawable.back);
		img = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
		
		// TODO: いい感じに表示
		c.drawBitmap(img,0,0,p);
//		c.drawBitmap(img1,0,0,p);
	}
}
