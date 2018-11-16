package com.shishic.cb;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.shishic.cb.fragment.MainFragment;
import com.shishic.cb.fragment.MainFragment1;
import com.shishic.cb.fragment.MyFragment;

public class MainActivity extends BaseActivity {

    private RadioGroup rg;

    private MainFragment1 mainFragment;

    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        switchTab(0);
    }

    private void initView(){
        rg = findViewById(R.id.rg_tab);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        switchTab(0);
                        break;
                    case R.id.rb2:
                        switchTab(1);
                        break;
                }
            }
        });
    }

    private void switchTab(int index){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(mainFragment != null){
            fragmentTransaction.hide(mainFragment);
        }
        if(myFragment != null){
            fragmentTransaction.hide(myFragment);
        }
        if(index == 0){
            if(mainFragment != null){
                fragmentTransaction.show(mainFragment);
            } else if(mainFragment == null){
                mainFragment = new MainFragment1();
                fragmentTransaction.add(R.id.ll_content,mainFragment);
            }

        } else if(index == 1){
            if(myFragment != null){
                fragmentTransaction.show(myFragment);
            } else if(myFragment == null){
                myFragment = new MyFragment();
                fragmentTransaction.add(R.id.ll_content,myFragment);
            }

        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
