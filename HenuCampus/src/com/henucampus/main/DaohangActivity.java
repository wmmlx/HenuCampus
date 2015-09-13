package com.henucampus.main;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.henucampus.ArcView.ArcMenu;
import com.henucampus.ArcView.ArcMenu.OnMenuItemClickListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DaohangActivity extends SherlockActivity implements LocationSource,AMapLocationListener { 
	//卫星菜单
	private ArcMenu                                 mArcMenu;
	//AMap 是地图的对象。AMap 地图将会通过 MapFragment 或 MapView 容器类来表现。
	private AMap                                      aMap;
	private MapView                                 mapView;
	// 定位相关
	private OnLocationChangedListener      mListener;
	private LocationManagerProxy             mAMapLocationManager;
	private Context                                   context;
	//地图控件
	private  UiSettings                               mUiSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daohang);
		getSupportActionBar().setDisplayShowTitleEnabled(true);//设置标题文字显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置标题栏返回按钮, 那么如何响应呢,
         														//也是重写onOptionsItemSelected() ，这个返回键的id是android.R.id.home。
		
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		this.context = this;	
		init(); 
		initView();
		initEvent();
		mUiSetting.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);// 设置地图logo显示在底部居中
		mUiSetting.setScaleControlsEnabled(true);//设置显示地图的默认比例尺
	}

    //标题栏按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.add:    
                    
                return true;
              
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	/*
	 * 卫星菜单点击事件
	 */
	private void initEvent()
	{
		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public void onClick(View view, int pos)
			{				
				switch (pos)
				{
				case 1 :
					 aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
					Toast.makeText(context, "普通模式",
							Toast.LENGTH_SHORT).show();
					break;
				case 2:
					aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
					Toast.makeText(context, "卫星模式",
							Toast.LENGTH_SHORT).show();
					break;
				case 3:				
					 aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图模式
						Toast.makeText(context, "夜景地图模式",
								Toast.LENGTH_SHORT).show();
					break;
				
				case 4:
					Toast.makeText(context, "导航",
							Toast.LENGTH_SHORT).show();
					break;
				case 5:
					Toast.makeText(context, "公交线路",
							Toast.LENGTH_SHORT).show();
					break;
				case 6:
					 if (aMap.isTrafficEnabled()) {
					        aMap.setTrafficEnabled(true);
					        Toast.makeText(context, "实时交通已打开",
									Toast.LENGTH_SHORT).show();
					    }
					 else{
						 aMap.setTrafficEnabled(false);
					Toast.makeText(context, "实时交通已关闭",
							Toast.LENGTH_SHORT).show();
					break;
					 }
				}
			}
		});
	}

	//actionbarsherlock重写onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	private void initView()
	{
		
		mArcMenu = (ArcMenu) findViewById(R.id.id_menu);
		
		
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
			  mUiSetting=aMap.getUiSettings() ;
		}
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	   // aMap.setMyLocationType()
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 此方法已经废弃
	 */
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null && aLocation != null) {
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
			Toast.makeText(context, aLocation.getAddress(),
					Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(context, "定位失败",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork,60000, 10, this);
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

}


