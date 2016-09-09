package com.example.transaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DexterActivity extends BaseActivity implements WhiteRatFragment.OnFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = DexterActivity.class.getSimpleName();
    private static final String EXTRA_TYPE_STATE_LOSS = "EXTRA_TYPE_STATE_LOSS";

    // commit vs commitAllowingStateLoss
    private boolean allowStateLoss;
    private boolean changeOnStop;
    StringBuilder mBuilder = new StringBuilder();

    Button btnDisallowStateLoss;
    Button btnAllowStateLoss;
    Button btnNoStateLoss;
    Button btnCommit;
    TextView lblStackCount;

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                mBuilder.append(" After handler post delayed: ");
                mBuilder.append(getSupportFragmentManager().getBackStackEntryCount());
                mBuilder.append("\n");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, BlackRatFragment.newInstance(null, null)).commitNow();
                mBuilder.append(" After commit now with black rat: ");
                mBuilder.append(getSupportFragmentManager().getBackStackEntryCount());
                mBuilder.append("\n");
                Log.i(TAG, "handleMessage    : " + getSupportFragmentManager().getBackStackEntryCount());
                getSupportFragmentManager().popBackStackImmediate();
                mBuilder.append(" After popBackStackImmediate: ");
                mBuilder.append(getSupportFragmentManager().getBackStackEntryCount());
                mBuilder.append("\n");
                Log.i(TAG, "handleMessage after pop-back-stack: Immediate" + getSupportFragmentManager().getBackStackEntryCount());
                getSupportFragmentManager().popBackStack();
                mBuilder.append(" After simple popBackStack: ");
                mBuilder.append(getSupportFragmentManager().getBackStackEntryCount());
                mBuilder.append("\n");
                Log.i(TAG, "handleMessage pop-back: " + getSupportFragmentManager().getBackStackEntryCount());
                getSupportFragmentManager().executePendingTransactions();
                mBuilder.append(" After executePendingTransactions: ");
                mBuilder.append(getSupportFragmentManager().getBackStackEntryCount());
                mBuilder.append("\n");
                lblStackCount.setText(mBuilder.toString());
            }
        }
    };
    private boolean isSateLoss;

    public static Intent getStartIntent(Context context, boolean isSateLoss) {
        Intent intent = new Intent(context, DexterActivity.class);
        intent.putExtra(EXTRA_TYPE_STATE_LOSS, isSateLoss);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dexter);

        if(getIntent()!= null) {
            isSateLoss = getIntent().getBooleanExtra(EXTRA_TYPE_STATE_LOSS, false);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, WhiteRatFragment.newInstance(null, null))
                    .commit();
        }

        // commit vs commitAllowingStateLoss
        btnDisallowStateLoss = (Button) findViewById(R.id.disallow_state_loss);
        btnAllowStateLoss = (Button) findViewById(R.id.allow_state_loss);
        btnNoStateLoss = (Button) findViewById(R.id.no_state_loss);
        btnCommit = (Button) findViewById(R.id.commit_now_and_other);
        lblStackCount = (TextView) findViewById(R.id.lbl_stack_count);

        btnDisallowStateLoss.setOnClickListener(this);
        btnAllowStateLoss.setOnClickListener(this);
        btnNoStateLoss.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        if(isSateLoss){
            btnDisallowStateLoss.setVisibility(View.VISIBLE);
            btnAllowStateLoss.setVisibility(View.VISIBLE);
            btnNoStateLoss.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.GONE);
            lblStackCount.setVisibility(View.GONE);
        }else{
            btnCommit.setVisibility(View.VISIBLE);
            lblStackCount.setVisibility(View.VISIBLE);
            btnDisallowStateLoss.setVisibility(View.GONE);
            btnAllowStateLoss.setVisibility(View.GONE);
            btnNoStateLoss.setVisibility(View.GONE);
        }
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
            case R.id.commit_now_and_other:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 50; i++) {
                            fragmentTransaction(R.id.container, REPLACE_FRAGMENT, WhiteRatFragment.newInstance(null, null), true, String.valueOf(i));
                        }
                        mBuilder.append("After 50 replace add to back stack transaction: ");
                        mBuilder.append(getSupportFragmentManager().getBackStackEntryCount());
                        mBuilder.append("\n");
                        Log.i(TAG, "run: " + getSupportFragmentManager().getBackStackEntryCount());
                        handler.sendEmptyMessage(100);
                        lblStackCount.setText(mBuilder.toString());
                    }
                }, 3 * 1000);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(isSateLoss) {
            super.onBackPressed();
        }else{
            finish();
        }
    }
}
