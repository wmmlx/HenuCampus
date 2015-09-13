package com.henucampus.main;

import java.util.List;
import com.henucampus.object.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private Button login;
	private Button register;
	private EditText name;
	private EditText password;
	private String namecontent = null;
	private String passwordcontent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Bmob.initialize(this, "cf86c7dc3bee0bba045e6d7c8b49cc1c");
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.password);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login: {
			namecontent = name.getText().toString();
			passwordcontent = password.getText().toString();
			if (namecontent.trim().equals("")||passwordcontent.trim().equals("")) {
				Toast.makeText(LoginActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
				return;
			}
			BmobQuery<User> query=new BmobQuery<User>();
			query.addWhereEqualTo("name", namecontent);
			query.addWhereEqualTo("password", passwordcontent);
			query.findObjects(LoginActivity.this, new FindListener<User>() {
				
				@Override
				public void onSuccess(List<User> users) {
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
					return;			
				}
			});
			
		}
			break;
		case R.id.register:
		{
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
		}
			break;
		}
	}
}
