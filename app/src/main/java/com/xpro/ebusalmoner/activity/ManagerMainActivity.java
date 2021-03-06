package com.xpro.ebusalmoner.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.fragment.MessageFragment;
import com.xpro.ebusalmoner.fragment.MonitorFragment;
import com.xpro.ebusalmoner.fragment.SettingFragment;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @ClassName: MainActivity
 * @Description: 主界面
 * @author: houyang
 * @date: 2016年9月20日 下午7:16:01
 */

@ContentView(value = R.layout.activity_main)
public class ManagerMainActivity extends BaseActivity {
    @ViewInject(R.id.bottom_menu_group)
    private RadioGroup radioGroup;

    @ViewInject(R.id.bottom_menu_shift)
    private RadioButton radio_shift;

    @ViewInject(R.id.bottom_menu_breakdown)
    private RadioButton bottom_menu_breakdown;

    @ViewInject(R.id.bottom_menu_message)
    private RadioButton radio_message;

    @ViewInject(R.id.bottom_menu_setting)
    private RadioButton radio_setting;

    @ViewInject(R.id.frameLayout_breakdown)
    private FrameLayout frameLayout_breakdown;

    @ViewInject(R.id.frameLayout_task)
    private FrameLayout frameLayout_task;


    private final int MainContent = R.id.main_content;

    private com.xpro.ebusalmoner.fragment.MessageFragment messageFragment;
    private com.xpro.ebusalmoner.fragment.SettingFragment settingFragment;
    private com.xpro.ebusalmoner.fragment.MonitorFragment monitorFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    ;
    // 该变量用于接收推送过来接收的intent的传递值
    int push;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        super.initLayout(savedInstanceState);

//		shiftFragment = new ShiftFragment_S();
//		historyFragment = new HistoryFragment();
        messageFragment = new MessageFragment();
        settingFragment = new SettingFragment();
        monitorFragment = new MonitorFragment();


//		bottom_menu_breakdown.setVisibility(View.VISIBLE);
//		radio_shift.setVisibility(View.GONE);
//		frameLayout_breakdown.setVisibility(View.VISIBLE);
//		frameLayout_task.setVisibility(View.GONE);
        radio_shift.setText("救济");

        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        // 接收推送过来的值
        Intent intent= getIntent();
        push = intent.getIntExtra("push", 0);
        // 若是推送过来的消息跳转的mainactivity
        if (push == Constants.notice) {
            transaction.add(R.id.main_content, messageFragment);
            radio_shift.setChecked(false);
            radio_message.setChecked(true);
        } else {
            // 登录获取启动时跳转的mainactivity
//			transaction.add(R.id.main_content, shiftFragment);
            transaction.add(R.id.main_content, monitorFragment);
        }
        transaction.commit();
        initBottomMenu();

    }



    private void initBottomMenu() {
        WindowManager wm = getWindowManager();
        int w = (int) (wm.getDefaultDisplay().getWidth() / 5 * 0.28);
        int h = (int) (DensityUtil.dip2px(50) * 0.38);

        Drawable drawable_homepage = getResources().getDrawable(R.drawable.selector_bottom_menu_shift);
        drawable_homepage.setBounds(0, 0, w, h);
        radio_shift.setCompoundDrawables(null, drawable_homepage, null, null);

//        Drawable drawable_shoppingcart = getResources().getDrawable(R.drawable.selector_bottom_menu_task);
//        drawable_shoppingcart.setBounds(0, 0, w, h);
//        radio_task.setCompoundDrawables(null, drawable_shoppingcart, null, null);

        Drawable drawable_message = getResources().getDrawable(R.drawable.selector_bottom_menu_message);
        drawable_message.setBounds(0, 0, w, h);
        radio_message.setCompoundDrawables(null, drawable_message, null, null);


        Drawable drawable_mine = getResources().getDrawable(R.drawable.selector_bottom_menu_setting);
        drawable_mine.setBounds(0, 0, w, h);
        radio_setting.setCompoundDrawables(null, drawable_mine, null, null);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.bottom_menu_shift://监控
//                        transaction.replace(MainContent, shiftFragment);
                        transaction.replace(MainContent, monitorFragment);
                        break;

//                    case R.id.bottom_menu_task://历史
//                        transaction.replace(MainContent, historyFragment);
//                        break;

                    case R.id.bottom_menu_message://消息
                        transaction.replace(MainContent, messageFragment);
                        break;

                    case R.id.bottom_menu_setting://我的
                        transaction.replace(MainContent, settingFragment);
                        break;
                }
                transaction.commit();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(resultCode==RESULT_OK){
                    String line=getIntent().getStringExtra("line");
                    Log.e("mm", line+"");
                }
                break;

            default:
                break;
        }
    }

}
