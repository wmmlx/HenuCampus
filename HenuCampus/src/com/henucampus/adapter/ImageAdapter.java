package com.henucampus.adapter;


import com.henucampus.main.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private int [] images=null;
	private Context context;
	ViewHolder holder=null;
	public ImageAdapter(Context context,int []images) {
		this.context = context;
		this.images=images;
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return images[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			//将布局文件转化为view对象
			convertView=LayoutInflater.from(context).inflate(R.layout.item_image,null);
			holder=new ViewHolder();
			//找到view对象的内部控件
			holder.imageview=(ImageView) convertView.findViewById(R.id.image_item);
			//把holder作为标记添加给convertView
			convertView.setTag(holder);
		}		
		//convertView已经获取，那么进行复用
		else{
			//通过getTag（）来获取来获取holder
			holder=(ViewHolder) convertView.getTag();					
		}	
		//将对应资源放到控件中显示		
		holder.imageview.setImageResource(images[position]);
		return convertView;
	}
	class ViewHolder{
		ImageView imageview;
	} 
}
