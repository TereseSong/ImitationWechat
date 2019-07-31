package com.example.terese.fangweixin;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class OneFragment extends Fragment {
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_one,container, false);
//        return view;
//    }
//}

public class OneFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

// 定义一个listview
    private ListView listView;
    private TextView textView;
    //定义一个适配器
    private SimpleAdapter simp_adapter;
    //定义一个集合，来存放数据
    private List<Map<String, Object>> datalist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,container, false);
        //获得listView控件
        listView = (ListView) view.findViewById(R.id.listView);
        textView =view.findViewById(R.id.textview);

//        Intent intent =getActivity().getIntent();
//        Bundle bundle = intent.getBundleExtra("number");
//        String number =bundle.getString("username");
        String number = Constant.username;
        textView.setText(number);

        //实例化集合
        datalist = new ArrayList<>();

        //实例化适配器
        simp_adapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.list_item1,
                new String[]{"pic", "name", "profile"},
                new int[]{R.id.pic, R.id.name, R.id.profile});

        listView.setAdapter(simp_adapter);


        //itme的点击事件
        listView.setOnItemClickListener(this);
        //item的长按事件
        listView.setOnItemLongClickListener(this);

        return view;
    }


    //编写一个方法，可以初始化数据

    private List<Map<String, Object>> getData() {

        //用数组来存放数据
        //1.图片
        int[] arr_pic = {R.drawable.fbb, R.drawable.ym, R.drawable.zly, R.drawable.yz, R.drawable.lyf};

        //2.名字
        String[] arr_name = {"范冰冰", "杨幂", "赵丽颖", "杨紫", "刘亦菲"};
        //3.个人简介
        String[] arr_profile = {
                "出生于1981年2月3号，毕业于上海师范学院，当今，中国影视演员，国家二级演员，最火的明星",
                "出生于1981年2月3号，毕业于上海师范学院，当今，中国影视演员，国家二级演员，最火的明星",
                "出生于1981年2月3号，毕业于上海师范学院，当今，中国影视演员，国家二级演员，最火的明星",
                "出生于1981年2月3号，毕业于上海师范学院，当今，中国影视演员，国家二级演员，最火的明星",
                "出生于1981年2月3号，毕业于上海师范学院，当今，中国影视演员，国家二级演员，最火的明星",
        };

        //将数据放到集合中
        for (int i = 0; i < arr_pic.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("pic", arr_pic[i]);
            map.put("name", arr_name[i]);
            map.put("profile", arr_profile[i]);
            //将map集合放到list集合中
            datalist.add(map);
        }
        return datalist;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //得到item中指定的控件
        TextView textView = (TextView) view.findViewById(R.id.name);

        //得到TextView中的值
        String name = textView.getText().toString();

        Toast.makeText(getActivity(), name + ",item=" + id, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(MainActivity.this,"mmmmmmm",Toast.LENGTH_SHORT).show();
        //调用一个封装好的dag的方法
        showNormalDialog(position);
        return true;
    }
    //封装一个对话框
    private void showNormalDialog(final int position) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.drawable.fbb);
        normalDialog.setTitle("删除选项");
        normalDialog.setMessage("确定要删除此选项吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //思路：1.删除集合中的数据数据 2.刷新ListView

                        if(datalist.remove(position)!=null){

                            //刷新页面
                            simp_adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}
