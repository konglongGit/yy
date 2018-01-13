package com.example.mfr.multifunctionrecord;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    /*
    *fragmentactivity 继承自activity，用来解决android3.0
    * 之前没有fragment的api，所以在使用的时候需要导入support包，
    * 同时继承fragmentActivity，这样在activity中就能嵌入fragment来实现你想要的布局效果。
    *当然3.0之后你就可以直接继承自Activity，并且在其中嵌入使用fragment了。
    *获得Manager的方式也不同
    *3.0以下：getSupportFragmentManager()
    *3.0以上：getFragmentManager()
    * Android是在Android 3.0 (API level 11)开始引入Fragment的。
    * 可以把Fragment想成Activity中的模块，这个模块有自己的布局，有自己的生命周期，
    * 单独处理自己的输入，在Activity运行的时候可以加载或者移除Fragment模块。
    * 可以把Fragment设计成可以在多个Activity中复用的模块。
    * 当开发的应用程序同时适用于平板电脑和手机时，可以利用Fragment实现灵活的布局，改善用户体验。
    */
    private ResideMenu resideMenu;
    private MainActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private ResideMenuItem hengxian;
    /**
     * 第一次被创建时调用活动.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
    }

    private void setUpMenu() {

        // 附加到当前活动;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //有效比例因子在0.0f和1.0f之间。leftmenu 宽度 150下降.
        resideMenu.setScaleValue(0.6f);

        // 创建菜单项;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "主页");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "描述");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "日历");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "设置");

        // 设置监听菜单项;
        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

        // 您可以通过设置禁用一个方向 ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });

    }
    //MotionEvent 手势事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }else if (view == itemProfile){
            changeFragment(new ProfileFragment());
        }else if (view == itemCalendar){
            changeFragment(new CityFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
        }

        resideMenu.closeMenu();
    }
    //自定义的 监听
    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "菜单开启!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "菜单关闭!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // 访问resideMenu有什么好方法？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }



}
