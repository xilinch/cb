package com.shishic.cb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.shishic.cb.bean.Account;
import com.shishic.cb.fragment.FreePlanFragment2;
import com.shishic.cb.fragment.Fragment3;
import com.shishic.cb.fragment.Fragment4;
import com.shishic.cb.fragment.MainFragment4;
import com.shishic.cb.fragment.MyFragment;
import com.shishic.cb.fragment.SpecialFragment2;
import com.shishic.cb.fragment.ToolFragment2;
import com.shishic.cb.util.ToastUtils;

public class MainActivity extends BaseActivity {

    private RadioGroup rg;
    //首页
    private MainFragment4 mainFragment;
    //免费计划
    private FreePlanFragment2 freePlanFragment2;
    //专家计划
    private SpecialFragment2 fragment3;
    //工具
    private ToolFragment2 fragment4;
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
                    case R.id.rb4:
                        checkedIndex = 3;
                        switchTab(3);
                        break;
                    case R.id.rb5:
                        checkedIndex = 4;
                        switchTab(4);
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
        if (freePlanFragment2 != null) {
            fragmentTransaction.hide(freePlanFragment2);
        }
        if (fragment3 != null) {
            fragmentTransaction.hide(fragment3);
        }
        if (fragment4 != null) {
            fragmentTransaction.hide(fragment4);
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
                if (freePlanFragment2 != null) {
                    fragmentTransaction.show(freePlanFragment2);
                } else if (freePlanFragment2 == null) {
                    freePlanFragment2 = new FreePlanFragment2();
                    fragmentTransaction.add(R.id.ll_content, freePlanFragment2);
                }
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                ToastUtils.toastShow(MainActivity.this, "请登录后使用.");
            }

        } else if (index == 2) {
            if (fragment3 != null) {
                fragmentTransaction.show(fragment3);
            } else if (fragment3 == null) {
                fragment3 = new SpecialFragment2();
                fragmentTransaction.add(R.id.ll_content, fragment3);
            }

        } else if (index == 3) {
            if (fragment4 != null) {
                fragmentTransaction.show(fragment4);
            } else if (fragment4 == null) {
                fragment4 = new ToolFragment2();
                fragmentTransaction.add(R.id.ll_content, fragment4);
            }

        } else if (index == 4) {
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
