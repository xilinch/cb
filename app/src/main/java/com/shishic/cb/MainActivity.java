package com.shishic.cb;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.shishic.cb.fragment.Fragment2;
import com.shishic.cb.fragment.Fragment3;
import com.shishic.cb.fragment.Fragment4;
import com.shishic.cb.fragment.MainFragment;
import com.shishic.cb.fragment.MainFragment1;
import com.shishic.cb.fragment.MainFragment3;
import com.shishic.cb.fragment.MyFragment;

public class MainActivity extends BaseActivity {

    private RadioGroup rg;
    //首页
    private MainFragment3 mainFragment;
    //生成
    private Fragment2 fragment2;
    //全部
    private Fragment3 fragment3;
    //遗漏
    private Fragment4 fragment4;
    //我的
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
                    case R.id.rb3:
                        switchTab(2);
                        break;
                    case R.id.rb4:
                        switchTab(3);
                        break;
                    case R.id.rb5:
                        switchTab(4);
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
        if(fragment2 != null){
            fragmentTransaction.hide(fragment2);
        }
        if(fragment3 != null){
            fragmentTransaction.hide(fragment3);
        }
        if(fragment4 != null){
            fragmentTransaction.hide(fragment4);
        }
        if(index == 0){
            if(mainFragment != null){
                fragmentTransaction.show(mainFragment);
            } else if(mainFragment == null){
                mainFragment = new MainFragment3();
                fragmentTransaction.add(R.id.ll_content,mainFragment);
            }

        } else if(index == 1){
            if(fragment2 != null){
                fragmentTransaction.show(fragment2);
            } else if(fragment2 == null){
                fragment2 = new Fragment2();
                fragmentTransaction.add(R.id.ll_content,fragment2);
            }
        } else if(index == 2){
            if(fragment3 != null){
                fragmentTransaction.show(fragment3);
            } else if(fragment3 == null){
                fragment3 = new Fragment3();
                fragmentTransaction.add(R.id.ll_content,fragment3);
            }

        } else if(index == 3){
            if(fragment4 != null){
                fragmentTransaction.show(fragment4);
            } else if(fragment4 == null){
                fragment4 = new Fragment4();
                fragmentTransaction.add(R.id.ll_content,fragment4);
            }

        } else if(index == 4){
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
