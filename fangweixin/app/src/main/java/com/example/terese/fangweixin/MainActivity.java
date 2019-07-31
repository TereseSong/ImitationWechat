package com.example.terese.fangweixin;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    //UI Object:主布局中的控件
    private ImageView ib_weixin;
    private ImageView ib_contact_list;
    private ImageView ib_find;
    private ImageView ib_profile;
    private FrameLayout ly_content;
    private Button add;

    //Fragment Object
    private OneFragment fg1;
    private TwoFragment2 fg2;
    private ThreeFragment fg3;
    private FourFragment fg4;
    private AddActivity fg5;

    private FragmentManager fManager;
    private FragmentTransaction fTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib_weixin = (ImageView) findViewById(R.id.ib_weixin);
        ib_contact_list = (ImageView) findViewById(R.id.ib_contact_list);
        ib_find = (ImageView) findViewById(R.id.ib_find);
        ib_profile = (ImageView) findViewById(R.id.ib_profile);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);
        add =findViewById(R.id.add);

        ib_weixin.setOnClickListener(this);
        ib_contact_list.setOnClickListener(this);
        ib_find.setOnClickListener(this);
        ib_profile.setOnClickListener(this);
        add.setOnClickListener(this);
        fManager = getFragmentManager();
        ib_weixin.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //重置所有文本的选中状态,将所有的四个选项设置为不选中
    private void setSelected() {
        ib_weixin.setSelected(false);
        ib_contact_list.setSelected(false);
        ib_find.setSelected(false);
        ib_profile.setSelected(false);
    }

    //隐藏所有Fragment，将所有的！=null的Fragment隐藏
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }
        if (fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
    }

    @Override
    public void onClick(View v) {
        fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()) {
            case R.id.ib_weixin:
                //不选中
                setSelected();
                //将自己选中
                ib_weixin.setSelected(true);
                //如果是第一次创建Fragment
                if (fg1 == null) {
                    fg1 = new OneFragment();
                    fTransaction.add(R.id.ly_content, fg1);
                } else {
                    //显示指定的fragment
                    fTransaction.show(fg1);
                }
                break;
            case R.id.ib_contact_list:
                setSelected();
                ib_contact_list.setSelected(true);
                if (fg2 == null) {
                    fg2 = new TwoFragment2();
                    fTransaction.add(R.id.ly_content, fg2);
                } else {
                    fTransaction.show(fg2);
                }
                break;
            case R.id.ib_find:
                setSelected();
                ib_find.setSelected(true);
                if (fg3 == null) {
                    fg3 = new ThreeFragment();
                    fTransaction.add(R.id.ly_content, fg3);
                } else {
                    fTransaction.show(fg3);
                }
                break;
            case R.id.ib_profile:
                setSelected();
                ib_profile.setSelected(true);
                if (fg4 == null) {
                    fg4 = new FourFragment();
                    fTransaction.add(R.id.ly_content, fg4);
                } else {
                    fTransaction.show(fg4);
                }
                break;
            case R.id.add:
//                Intent intent = getIntent();;
//                Bundle bundle = intent.getExtras();
//                String number = bundle.getString("number");
//                System.out.println(number);
//                System.out.println("主函数   找不到包装的数据");
                Intent intent1=new Intent(MainActivity.this, AddActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle.putString("username",number);//数据库的number作username昵称
//                intent.putExtra("number",bundle1);
                startActivity(intent1);


                break;

        }
        //提交事务
        fTransaction.commit();
    }
}
