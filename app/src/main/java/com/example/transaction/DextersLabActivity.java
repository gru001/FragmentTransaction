package com.example.transaction;

import android.os.Bundle;
import android.view.View;

public class DextersLabActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dexters_lab);
        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(DexterActivity.getStartIntent(DextersLabActivity.this));
            }
        });
    }
}
