package com.henucampus.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Search  extends  Activity  {
	private Button  city,surrond;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		OnClickListener onclicklistener= new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()){
				case R.id.city:
					 Intent intent1=new Intent(Search.this,poisearch.class);
					 startActivity(intent1);
					break;

				case  R.id.surround:
					Intent intent2=new Intent(Search.this,surroundSearch.class);
					 startActivity(intent2);
					break;
				}
			}
		};
		city=(Button) findViewById(R.id.city);
		surrond=(Button) findViewById(R.id.surround);
		city.setOnClickListener(onclicklistener);
		surrond.setOnClickListener(onclicklistener);
	

	}


}
