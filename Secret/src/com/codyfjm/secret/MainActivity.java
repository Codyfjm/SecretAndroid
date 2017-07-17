package com.codyfjm.secret;

import com.codyfjm.secret.atys.AtyLogin;
import com.codyfjm.secret.atys.AtyTimeline;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
//        System.out.println("phoneList:"+MyContacts.getContactsJSONString(this));
        
        String token = Config.getCachedToken(this);
        String phone_num = Config.getCachedPhoneNum(this);
        if (token!=null && phone_num!=null) {
			Intent intent = new Intent(this,AtyTimeline.class);
			intent.putExtra(Config.KEY_TOKEN, token);
			intent.putExtra(Config.KEY_PHONE_NUM, phone_num);
			startActivity(intent);
		}else {
			startActivity(new Intent(this,AtyLogin.class));
		}
        
        
        finish();
    }
}
