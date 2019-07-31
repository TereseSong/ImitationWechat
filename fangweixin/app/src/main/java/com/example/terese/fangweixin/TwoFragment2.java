package com.example.terese.fangweixin;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoFragment2 extends Fragment  {
    //listview列表
    private ListView listView;
    private TextView textView;
    //数据适配器
    private SimpleAdapter simp_adapter;
    //被展示数据源
    private List<Map<String, Object>> dataList;
    private TwoFragment2.MyHander myhander = new TwoFragment2.MyHander();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        //获得listView控件
        listView = (ListView) view.findViewById(R.id.listview1);
        textView =view.findViewById(R.id.textview);

//        Intent intent =getActivity().getIntent();
//        Bundle bundle = intent.getBundleExtra("number");
//        String number =bundle.getString("username");

        final String number= Constant.username;
        textView.setText(number);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //完成http服务器访问的方法
                System.out.println("here!!!");
                System.out.println(textView.getText().toString());
                httpUrlConnPost(number);

            }
        }).start();
       return view;
    }

    private List<Map<String, Object>> getData() {
        //得到数据
        List<AddressList> list = ListData.getList();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", list.get(i).getAdname());
            map.put("phone", list.get(i).getPhone());
            map.put("email", list.get(i).getEmail());
            dataList.add(map);
        }
        return dataList;
    }

    public void httpUrlConnPost(String number) {
        //创建一个HttpURLConnection
        HttpURLConnection urlConnection = null;
        URL url;
        try {
            // 请求的URL地地址
            url = new URL(
                    "http://192.168.31.135:8080/AndroidService/AddressListServlet");
            urlConnection = (HttpURLConnection) url.openConnection();// 打开http连接
            urlConnection.setConnectTimeout(6000);// 连接的超时时间
            urlConnection.setUseCaches(false);// 不使用缓存
            // urlConnection.setFollowRedirects(false);是static函数，作用于所有的URLConnection对象。
            urlConnection.setInstanceFollowRedirects(true);// 是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
            urlConnection.setReadTimeout(3000);// 响应的超时时间
            urlConnection.setDoInput(true);// 设置这个连接是否可以写入数据
            urlConnection.setDoOutput(true);// 设置这个连接是否可以输出数据
            urlConnection.setRequestMethod("POST");// 设置请求的方式
            urlConnection.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");// 设置消息的类型
            urlConnection.connect();// 连接，从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接
            JSONObject json = new JSONObject();// 创建json对象
            json.put("number", URLEncoder.encode(number, "UTF-8"));// 把数据put进json对象中
            String jsonstr = json.toString();// 把JSON对象按JSON的编码格式转换为字符串
            OutputStream out = urlConnection.getOutputStream();// 输出流，用来发送请求，http请求实际上直到这个函数里面才正式发送出去
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));// 创建字符流对象并用高效缓冲流包装它，便获得最高的效率,发送的是字符串推荐用字符流，其它数据就用字节流
            bw.write(jsonstr);// 把json字符串写入缓冲区中
            bw.flush();// 刷新缓冲区，把数据发送出去，这步很重要
            out.close();
            bw.close();// 使用完关闭
            Log.i("aa", urlConnection.getResponseCode() + "");

            //以下判斷是否訪問成功，如果返回的状态码是200则说明访问成功
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {// 得到服务端的返回码是否连接成功
                InputStream in = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(in));
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = br.readLine()) != null) {// BufferedReader特有功能，一次读取一行数据
                    buffer.append(str);
                }
                in.close();
                br.close();
                String jsonData = buffer.toString();
                if(jsonData==null) {
                    System.out.println("null");
                }
                Log.i("bb", "返回的数据是：=" + jsonData);// rjson={"json":true}
                if (!jsonData.equals("")) {
                    Log.i("得到json数据了---->", "11111111111111111111");

                    try {

                        //解析数据，并将数据保存到集合中
                        List<AddressList> list = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(jsonData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String email = jsonObject.getString("email");
                            String adname = jsonObject.getString("adname");
                            String phone = jsonObject.getString("phone");
                            String id = jsonObject.getString("id");

                            //将得到的数据封装到对象中：
                            AddressList ads = new AddressList();
                            ads.setId(id);
                            ads.setEmail(email);
                            ads.setAdname(adname);
                            ads.setPhone(phone);
                            //将对象封装到集合中
                            list.add(ads);
                        }
                        //将得到的集合保存起来，供所有Activity共享
                        ListData.setList(list);
                        //定义hander消息机制
                        myhander.sendEmptyMessage(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    //如果返回的状态码不是200
                    myhander.sendEmptyMessage(2);
                }
            }
        } catch (Exception e) {
            //如果返回的状态码不是200
//            myhander.sendEmptyMessage(3);
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();// 使用完关闭TCP连接，释放资源
        }


    }

    // 在Android中不可以在线程中直接修改UI，只能借助Handler机制来完成对UI的操作
    class MyHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           //判断hander的内容是什么，如果是1则说明注册成功，如果是2说明注册失败
            switch (msg.what) {
                case 1:
                    //适配器加载数据源
                    dataList = new ArrayList<>();
                    simp_adapter = new SimpleAdapter(getActivity(), getData(), R.layout.list_item, new String[]{
                            "name", "phone", "email"}, new int[]{R.id.name1, R.id.phone1, R.id.email1});
                    //视图加载适配器
                    listView.setAdapter(simp_adapter);
                    break;
                case 2:
                    Log.i("aa", msg.what + "");

                    //這是一個提示框Toast.makeText(getActivity()
                    Toast.makeText(getActivity(), "无通讯录信息", Toast.LENGTH_LONG).show();
                    break;

                case 3:
                    Log.i("aa", msg.what + "");

                    //這是一個提示框
                    Toast.makeText(getActivity(), "通讯录获取失败", Toast.LENGTH_LONG).show();
                    break;
            }

        }
    }

}
















