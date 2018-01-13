package com.example.mfr.multifunctionrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * User: special
 * Date: 13-12-10
 * Time: 下午11:05
 * Mail: specialcyci@gmail.com
 */
public class ResideMenuItem extends LinearLayout{

    /** 菜单项的图标  */
    private ImageView iv_icon;
    /** 菜单项名称 */
    private TextView tv_title;

    public ResideMenuItem(Context context) {
        super(context);
        initViews(context);
    }

    public ResideMenuItem(Context context, int icon, int title) {
        super(context);
        initViews(context);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);
    }

    public ResideMenuItem(Context context, int icon, String title) {
        super(context);
        initViews(context);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);
    }

    private void initViews(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.residemenu_item, this);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    /**
     * 设置图标的颜色;
     *
     * @param icon
     */
    public void setIcon(int icon){
        iv_icon.setImageResource(icon);
    }

    /**
     * 用资源设置标题
     * ;
     * @param title
     */
    public void setTitle(int title){
        tv_title.setText(title);
    }

    /**
     * 用字符串设置标题;
     *
     * @param title
     */
    public void setTitle(String title){
        tv_title.setText(title);
    }
}
