package com.example.transaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DexterActivity extends BaseActivity implements RatFragment.OnFragmentInteractionListener{

    private static final String TAG = DexterActivity.class.getSimpleName();

    public static Intent getStartIntent(Context context){
        return new Intent(context, DexterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dexter);
        fragmentTransaction(R.id.container,ADD_FRAGMENT,RatFragment.newInstance(null,null),false,null);

       final Handler handler =  new Handler(){
           @Override
           public void handleMessage(Message msg) {
               super.handleMessage(msg);
               if(msg.what == 100){
                   getSupportFragmentManager().beginTransaction().replace(R.id.container,RatFragment.newInstance(null,null)).commitNow();
                   Log.i(TAG, "handleMessage    : "+getSupportFragmentManager().getBackStackEntryCount());
                   getSupportFragmentManager().popBackStackImmediate();
                   Log.i(TAG, "handleMessage after pop-back-stack: Immediate"+getSupportFragmentManager().getBackStackEntryCount());
                   getSupportFragmentManager().popBackStack();
                   Log.i(TAG, "handleMessage pop-back: "+getSupportFragmentManager().getBackStackEntryCount());
               }
           }
       };
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 50; i++) {
                    fragmentTransaction(R.id.container, REPLACE_FRAGMENT, RatFragment.newInstance(null, null), true, String.valueOf(i));
                }
                Log.i(TAG, "run: "+getSupportFragmentManager().getBackStackEntryCount());
                handler.sendEmptyMessage(100);
            }
        }, 3*1000);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
