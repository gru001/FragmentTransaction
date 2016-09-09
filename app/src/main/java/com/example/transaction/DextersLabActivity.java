package com.example.transaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DextersLabActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dexters_lab);
        Button btnCommitVariants = (Button) findViewById(R.id.btnOtherCommitVariant);
        Button btnStateLoss = (Button) findViewById(R.id.btnStateLoss);
        btnCommitVariants.setOnClickListener(this);
        btnStateLoss.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnOtherCommitVariant) {
            startActivity(DexterActivity.getStartIntent(this,false));

        } else if (i == R.id.btnStateLoss) {
            startActivity(DexterActivity.getStartIntent(this,true));
        }
    }
}
