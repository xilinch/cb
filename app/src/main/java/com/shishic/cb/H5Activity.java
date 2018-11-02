package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shishic.cb.fragment.H5Fragment;

public class H5Activity extends BaseActivity {


    private H5Fragment h5Fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        h5Fragment = new H5Fragment();
        Bundle bundle = getIntent().getExtras();
        h5Fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.ll_content,h5Fragment).commit();
    }
}
