package com.henucampus.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {
	private RadioGroup rg;
	private Fragment[] mfragments;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	RadioButton rb1, rb2, rb3;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mfragments = new Fragment[3];
		fragmentManager = getSupportFragmentManager();
		// fragmentManager = getFragmentManager();
		mfragments[0] = fragmentManager.findFragmentById(R.id.frage1);
		mfragments[1] = fragmentManager.findFragmentById(R.id.frage2);
		mfragments[2] = fragmentManager.findFragmentById(R.id.frage3);
		fragmentTransaction = fragmentManager.beginTransaction()
				.hide(mfragments[0]).hide(mfragments[1]).hide(mfragments[2]);
		fragmentTransaction.show(mfragments[1]).commit();
		setFragmentIndicator();
		
		

	}

	
	
	private void setFragmentIndicator() {
		rg = (RadioGroup) findViewById(R.id.radiogroup);
		rb1 = (RadioButton) findViewById(R.id.first);
		rb2 = (RadioButton) findViewById(R.id.second);
		rb3 = (RadioButton) findViewById(R.id.thrid);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 自动生成的方法存根
				fragmentTransaction = fragmentManager.beginTransaction()
						.hide(mfragments[0]).hide(mfragments[1])
						.hide(mfragments[2]);
				switch (checkedId) {
				case R.id.first:
					Intent it_daohang=new Intent(MainActivity.this,DaohangActivity.class);
					startActivity(it_daohang);
					break;

				case R.id.second:
					fragmentTransaction.show(mfragments[1]).commit();
					break;

				case R.id.thrid:
					fragmentTransaction.show(mfragments[2]).commit();
					break;

				default:
					break;
				}
			}
		});
	}

}
