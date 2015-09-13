package com.henucampus.main;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.henucampus.object.Find;
import com.henucampus.object.Lost;

/*
 * 失物招领的Activity，extends SherlockActivity，要导入actionbarsherlock包
 */
public class LostFindActivity extends SherlockActivity {
	private ToggleButton lostfindtbn;//失物招领和寻物启事间的转换
	private ListView LostList;
	private SimpleAdapter LostAdapter;
	private ListView FindList;
	private SimpleAdapter FindAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostfind);
        Bmob.initialize(this, "cf86c7dc3bee0bba045e6d7c8b49cc1c");//Bmob链接ID，还需导包，设置权限
        getSupportActionBar().setDisplayShowTitleEnabled(true);//设置标题文字显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置标题栏返回按钮, 那么如何响应呢,
         														//也是重写onOptionsItemSelected() ，这个返回键的id是android.R.id.home。
        searchLost();//初始寻物启事 
        lostfindtbn=(ToggleButton) findViewById(R.id.lostfindtbn);//失物招领和寻物启事间的转换按钮
        lostfindtbn.setOnCheckedChangeListener(new OnCheckedChangeListener() {//点击事件
			@Override
        	public void onCheckedChanged(CompoundButton buttonView,  
                    boolean isChecked) {  
                if (isChecked) {  
                	searchFind();//寻物启事  
                } else {  
                	searchLost();//失物招领  
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
                addLostFind();     
                return true;
              
            default:
                return super.onOptionsItemSelected(item);
        }
    }
     
    
    //阻止点击按钮退出dialog
	public void poaseBack(DialogInterface arg0){
		try {
			Field field = arg0.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(arg0, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//允许点击按钮退出dialog
	public void allowBack(DialogInterface arg0){
		try {
			Field field = arg0.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(arg0, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    //添加Lost或Find
    private void addLostFind() {
    	final TableLayout addlostfind;
    	
    	addlostfind= (TableLayout) getLayoutInflater().inflate(R.layout.dialog_addlostfind, null);
    	AlertDialog.Builder builder = new Builder(LostFindActivity.this);
    	builder.setTitle("填写信息")
		.setView(addlostfind)
		.setPositiveButton("确定", new OnClickListener() {
			EditText addtitle = (EditText) addlostfind.findViewById(R.id.addtitle);
			EditText adddate = (EditText) addlostfind.findViewById(R.id.adddate);
			EditText adddescribe = (EditText) addlostfind.findViewById(R.id.adddescribe);
			EditText addphone = (EditText) addlostfind.findViewById(R.id.addphone);
			RadioButton addlost=(RadioButton) addlostfind.findViewById(R.id.addlost);
			private String titletext,datetext,describetext,phonetext;
			// 确定提交失物招领信息
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				titletext=addtitle.getText().toString();
				datetext=adddate.getText().toString();
				describetext=adddescribe.getText().toString();
				phonetext=addphone.getText().toString();
				
				
				if (titletext.equals("")) {//物品名称为空时，提示输入，阻止关闭
					Toast.makeText(LostFindActivity.this, "请输入物品名称",Toast.LENGTH_SHORT).show();
					poaseBack(arg0);
				} else if (datetext.equals("")) {//丢失日期为空时，提示输入，阻止关闭
					Toast.makeText(LostFindActivity.this, "请输入丢失/拾得日期",Toast.LENGTH_SHORT).show();
					poaseBack(arg0);
				} else if (describetext.equals("")) {//物品描述为空时，提示输入，阻止关闭
					Toast.makeText(LostFindActivity.this, "请输入物品描述",Toast.LENGTH_SHORT).show();
					poaseBack(arg0);
				} else if (phonetext.equals("")) {//联系方式为空时，提示输入，阻止关闭
					Toast.makeText(LostFindActivity.this, "请输入联系方式",Toast.LENGTH_SHORT).show();
					poaseBack(arg0);
				} else {//验证通过，允许关闭，存储数据
					if(addlost.isChecked()){
						Lost l = new Lost();
						l.setTitle(titletext);
						l.setDate(datetext);
						l.setDescribe(describetext);
						l.setPhone(phonetext);	
						l.save(LostFindActivity.this, new SaveListener() {
							@Override
							public void onSuccess() {
								Toast.makeText(LostFindActivity.this, "信息存储成功",
										Toast.LENGTH_SHORT).show();

							}
							@Override
							public void onFailure(int arg0, String arg1) {
								Toast.makeText(LostFindActivity.this, "信息存储失败",
										Toast.LENGTH_SHORT).show();
							}
						});
					}else{
						Find f = new Find();
						f.setTitle(titletext);
						f.setDate(datetext);
						f.setDescribe(describetext);
						f.setPhone(phonetext);	
						f.save(LostFindActivity.this, new SaveListener() {
							@Override
							public void onSuccess() {
								Toast.makeText(LostFindActivity.this, "信息存储成功",
										Toast.LENGTH_SHORT).show();

							}
							@Override
							public void onFailure(int arg0, String arg1) {
								Toast.makeText(LostFindActivity.this, "信息存储失败",
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				}
			}
		}).setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// 取消提交信息，允许关闭
				allowBack(arg0);
			}
		}).create().show();
		
	}

	// 查询Lost表，寻物启事
    public void searchLost() {
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		query.order("-createdAt");
		query.findObjects(this, new FindListener<Lost>() {
			@Override
			public void onSuccess(List<Lost> losts) {
				List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < losts.size(); i++) {
					Map<String, Object> dataitem = new HashMap<String, Object>();
					dataitem.put("photo", losts.get(i).getPhoto());
					dataitem.put("title", "物品：" + losts.get(i).getTitle());
					dataitem.put("date", "捡到于：" + losts.get(i).getDate());
					dataitem.put("describe", "描述："
							+ losts.get(i).getDescribe());
					dataitem.put("phone", "tel:" + losts.get(i).getPhone());
					dataitem.put("createAt", "发布于："
							+ losts.get(i).getCreatedAt());
					datalist.add(dataitem);
				}
				LostAdapter = new SimpleAdapter(LostFindActivity.this, datalist,
						R.layout.item_lost, new String[] { "photo", "title",
								"date", "describe", "phone", "createAt" },
						new int[] { R.id.lostphoto, R.id.losttitle, R.id.lostdate,
								R.id.lostdescribe, R.id.lostphone, R.id.lostcreateAt });
				LostList = (ListView) findViewById(R.id.LostOrFindList);
				LostList.setAdapter(LostAdapter);
			}

			@Override
			public void onError(int code, String arg0) {
				Toast.makeText(LostFindActivity.this, "寻物启事列表加载失败~",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
    
    // 查询Find表,失物招领
	public void searchFind() {
		BmobQuery<Find> query = new BmobQuery<Find>();
		query.order("-createdAt");
		query.findObjects(this, new FindListener<Find>() {
			@Override
			public void onSuccess(List<Find> finds) {
				List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < finds.size(); i++) {
					Map<String, Object> dataitem = new HashMap<String, Object>();
					dataitem.put("photo", finds.get(i).getPhoto());
					dataitem.put("title", "物品：" + finds.get(i).getTitle());
					dataitem.put("date", "丢失于：" + finds.get(i).getDate());
					dataitem.put("describe", "描述："
							+ finds.get(i).getDescribe());
					dataitem.put("phone", "tel:" + finds.get(i).getPhone());
					dataitem.put("createAt", "发布于："
							+ finds.get(i).getCreatedAt());
					datalist.add(dataitem);
				}
				FindAdapter = new SimpleAdapter(LostFindActivity.this, datalist,
						R.layout.item_find, new String[] { "photo", "title",
								"date", "describe", "phone", "createAt" },
						new int[] { R.id.findphoto, R.id.findtitle, R.id.finddate,
								R.id.finddescribe, R.id.findphone, R.id.findcreateAt });
				FindList = (ListView) findViewById(R.id.LostOrFindList);
				FindList.setAdapter(FindAdapter);
			}

			@Override
			public void onError(int code, String arg0) {
				Toast.makeText(LostFindActivity.this, "失物招领列表加载失败~",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	 
    
}