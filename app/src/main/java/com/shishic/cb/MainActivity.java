package com.shishic.cb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.shishic.cb.bean.Account;
import com.shishic.cb.fragment.Fragment2;
import com.shishic.cb.fragment.Fragment3;
import com.shishic.cb.fragment.Fragment4;
import com.shishic.cb.fragment.MainFragment3;
import com.shishic.cb.fragment.MainFragment4;
import com.shishic.cb.fragment.MyFragment;
import com.shishic.cb.util.ToastUtils;

public class MainActivity extends BaseActivity {

    private RadioGroup rg;
    //首页
    private MainFragment4 mainFragment;
    //免费计划
    private Fragment2 fragment2;
    //我的
    private MyFragment myFragment;

    private int checkedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        switchTab(0);
    }

    private void initView() {
        rg = findViewById(R.id.rg_tab);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb1:
                        switchTab(0);
                        checkedIndex = 0;
                        break;
                    case R.id.rb2:
                        checkedIndex = 1;
                        switchTab(1);
                        break;
                    case R.id.rb3:
                        checkedIndex = 2;
                        switchTab(2);
                        break;
                }
            }
        });
    }

    private void switchTab(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mainFragment != null) {
            fragmentTransaction.hide(mainFragment);
        }
        if (myFragment != null) {
            fragmentTransaction.hide(myFragment);
        }
        if (fragment2 != null) {
            fragmentTransaction.hide(fragment2);
        }

        if (index == 0) {
            if (mainFragment != null) {
                fragmentTransaction.show(mainFragment);
            } else if (mainFragment == null) {
                mainFragment = new MainFragment4();
                fragmentTransaction.add(R.id.ll_content, mainFragment);
            }

        } else if (index == 1) {
            Account account = Account.getAccount();
            if (account != null) {
                if (fragment2 != null) {
                    fragmentTransaction.show(fragment2);
                } else if (fragment2 == null) {
                    fragment2 = new Fragment2();
                    fragmentTransaction.add(R.id.ll_content, fragment2);
                }
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                ToastUtils.toastShow(MainActivity.this, "请登录后使用.");
            }

        } else if (index == 2) {
            if (myFragment != null) {
                fragmentTransaction.show(myFragment);
            } else if (myFragment == null) {
                myFragment = new MyFragment();
                fragmentTransaction.add(R.id.ll_content, myFragment);
            }

        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private long lastClick;

    @Override
    protected void onResume() {
        super.onResume();
        Account account = Account.getAccount();
        if (account != null && checkedIndex == 1) {
            //
            findViewById(R.id.rb2).performClick();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTIme = System.currentTimeMillis();
            if (currentTIme - lastClick < 300) {
                //
                lastClick = currentTIme;
                return super.onKeyDown(keyCode, event);
            } else {
                lastClick = currentTIme;
                ToastUtils.toastShow(this, "再点击一次退出");
                return true;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
