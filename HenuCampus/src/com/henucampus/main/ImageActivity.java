package com.henucampus.main;

import com.henucampus.adapter.ImageAdapter;
import com.henucampus.object.Image;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageActivity extends Activity {

	private Gallery gallery;
	private ImageAdapter adapter;
	private Image image=null;
	private int[]images={
			R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,
			R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,
			R.drawable.pic7,R.drawable.pic8,R.drawable.pic9		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		image=new Image();
		gallery=(Gallery) findViewById(R.id.gallery);
		adapter=new ImageAdapter(ImageActivity.this,images);
		gallery.setAdapter(adapter);
		gallery.setSelection(images.length/2);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent=getIntent();
				image.setImage(images[position]);
				intent.putExtra("image", image);
				setResult(2000, intent);	
				finish();
			}
		});
	}
}
