package com.example.transaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DexterActivity extends BaseActivity implements WhiteRatFragment.OnFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = DexterActivity.class.getSimpleName();

    // commit vs commitAllowingStateLoss
    private boolean allowStateLoss;
    private boolean changeOnStop;

    Button btnDisallowStateLoss;
    Button btnAllowStateLoss;
    Button btnNoStateLoss;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DexterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dexter);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, WhiteRatFragment.newInstance(null, null))
                    .commit();
        }

        // commit vs commitAllowingStateLoss
        btnDisallowStateLoss = (Button) findViewById(R.id.disallow_state_loss);
        btnAllowStateLoss = (Button) findViewById(R.id.allow_state_loss);
        btnNoStateLoss = (Button) findViewById(R.id.no_state_loss);
        btnDisallowStateLoss.setOnClickListener(this);
        btnAllowStateLoss.setOnClickListener(this);
        btnNoStateLoss.setOnClickListener(this);

        /*final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 100) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, WhiteRatFragment.newInstance(null, null)).commitNow();
                    Log.i(TAG, "handleMessage    : " + getSupportFragmentManager().getBackStackEntryCount());
                    getSupportFragmentManager().popBackStackImmediate();
                    Log.i(TAG, "handleMessage after pop-back-stack: Immediate" + getSupportFragmentManager().getBackStackEntryCount());
                    getSupportFragmentManager().popBackStack();
                    Log.i(TAG, "handleMessage pop-back: " + getSupportFragmentManager().getBackStackEntryCount());
                }
            }
        };*/
        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    fragmentTransaction(R.id.container, REPLACE_FRAGMENT, WhiteRatFragment.newInstance(null, null), true, String.valueOf(i));
                }
                Log.i(TAG, "run: " + getSupportFragmentManager().getBackStackEntryCount());
                handler.sendEmptyMessage(100);
            }
        }, 3 * 1000);*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (changeOnStop) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BlackRatFragment.newInstance(null, null));

            if (allowStateLoss) {
                transaction.commitAllowingStateLoss();
            } else {
                transaction.commit();
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.disallow_state_loss:
                allowStateLoss = false;
                changeOnStop = true;
                startActivity(new Intent(DexterActivity.this, SecondActivity.class));
                break;
            case R.id.allow_state_loss:
                allowStateLoss = true;
                changeOnStop = true;
                startActivity(new Intent(DexterActivity.this, SecondActivity.class));
                break;
            case R.id.no_state_loss:
                changeOnStop = false;

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, BlackRatFragment.newInstance(null, null));

                if (allowStateLoss) {
                    transaction.commitAllowingStateLoss();
                } else {
                    transaction.commit();
                }

                startActivity(new Intent(DexterActivity.this, SecondActivity.class));
                break;
        }
    }
}
