package com.codyfjm.secret;

import com.codyfjm.secret.atys.AtyLogin;
import com.codyfjm.secret.atys.AtyTimeline;
import com.codyfjm.secret.ld.MyContacts;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        System.out.println("phoneList:"+MyContacts.getContactsJSONString(this));
        
        String token = Config.getCachedToken(this);
        if (token!=null) {
			Intent intent = new Intent(this,AtyTimeline.class);
			intent.putExtra(Config.KEY_TOKEN, token);
			startActivity(intent);
		}else {
			startActivity(new Intent(this,AtyLogin.class));
		}
        
        
        finish();
    }
}
