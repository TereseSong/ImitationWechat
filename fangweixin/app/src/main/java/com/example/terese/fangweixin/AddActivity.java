package com.example.terese.fangweixin;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class AddActivity extends Activity {
    private EditText adname;
    private EditText phone;
    private EditText email;
    private  Button add;
    private TextView addview;

    private MyHander myhander = new MyHander();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fregment);

        add = this.findViewById(R.id.addfriend);
        addview= this.findViewById(R.id.addfriendview);
        adname = (EditText) this.findViewById(R.id.adname);
        phone = (EditText) this.findViewById(R.id.phone);
        email =(EditText) this.findViewById(R.id.email);
//        Intent intent = getIntent();;
//        Bundle bundle = intent.getExtras();
//        String xuehao = bundle.getString("number");
        String xuehao = Constant.username;
        addview.setText(xuehao);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpUrlConnPost(adname.getText() + "",
                                phone.getText() + "",
                                email.getText()+"",
                                Constant.username+"");
                    }
                }).start();
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void httpUrlConnPost(String adname, String phone,String email,String adnumber) {
        HttpURLConnection urlConnection = null;
        URL url;
        try {
            // 请求的URL地地址
            url = new URL(
                    "http://192.168.31.135:8080/AndroidService/InsertAddresListServlet");
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
            json.put("adname", URLEncoder.encode(adname, "UTF-8"));// 使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("phone", URLEncoder.encode(phone, "UTF-8"));// 把数据put进json对象中
            json.put("email", URLEncoder.encode(email, "UTF-8"));
            json.put("adnumber", URLEncoder.encode(adnumber, "UTF-8"));

            String jsonstr = json.toString();// 把JSON对象按JSON的编码格式转换为字符串
            // -------------使用字节流发送数据--------------
            // OutputStream out = urlConnection.getOutputStream();
            // BufferedOutputStream bos = new
            // BufferedOutputStream(out);//缓冲字节流包装字节流
            // byte[] bytes = jsonstr.getBytes("UTF-8");//把字符串转化为字节数组
            // bos.write(bytes);//把这个字节数组的数据写入缓冲区中
            // bos.flush();//刷新缓冲区，发送数据
            // out.close();
            // bos.close();
            // ------------字符流写入数据------------
            OutputStream out = urlConnection.getOutputStream();// 输出流，用来发送请求，http请求实际上直到这个函数里面才正式发送出去
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));// 创建字符流对象并用高效缓冲流包装它，便获得最高的效率,发送的是字符串推荐用字符流，其它数据就用字节流
            bw.write(jsonstr);// 把json字符串写入缓冲区中
            bw.flush();// 刷新缓冲区，把数据发送出去，这步很重要
            out.close();
            bw.close();// 使用完关闭
            Log.i("aa", urlConnection.getResponseCode() + "");

            //以下判斷是否訪問成功，如果返回的状态码是200则说明访问成功
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {// 得到服务端的返回码是否连接成功
                // ------------字节流读取服务端返回的数据------------
                // InputStream in =
                // urlConnection.getInputStream();//用输入流接收服务端返回的回应数据
                // BufferedInputStream bis = new
                // BufferedInputStream(in);//高效缓冲流包装它，这里用的是字节流来读取数据的，当然也可以用字符流
                // byte[] b = new byte[1024];
                // int len = -1;
                // StringBuffer buffer = new
                // StringBuffer();//用来接收数据的StringBuffer对象
                // while((len=bis.read(b))!=-1){
                // buffer.append(new String(b, 0, len));//把读取到的字节数组转化为字符串
                // }
                // in.close();
                // bis.close();
                // Log.d("zxy", buffer.toString());//{"json":true}
                // JSONObject rjson = new
                // JSONObject(buffer.toString());//把返回来的json编码格式的字符串数据转化成json对象
                // ------------字符流读取服务端返回的数据------------
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
                JSONObject rjson = new JSONObject(buffer.toString());

                Log.i("bb", "rjson=" + rjson);// rjson={"json":true}
                boolean result = rjson.getBoolean("json");// 从rjson对象中得到key值为"json"的数据，这里服务端返回的是一个boolean类型的数据
                System.out.println("json:===" + result);
                //如果服务器端返回的是true，则说明注册成功，否则注册失败
                if (result) {// 判断结果是否正确
                    //在Android中http请求，必须放到线程中去作请求，但是在线程中不可以直接修改UI，只能通过hander机制来完成对UI的操作
                    myhander.sendEmptyMessage(1);

                    Log.i("用户：", "添加成功");
                    Intent intent=new Intent(AddActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    myhander.sendEmptyMessage(2);
                    System.out.println("222222222222222");
                    Log.i("用户：", "添加失败");
                    Intent intent=new Intent(AddActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            } else {
                myhander.sendEmptyMessage(2);
            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.i("aa", e.toString());
            System.out.println("11111111111111111");
            Intent intent=new Intent(AddActivity.this,MainActivity.class);
            startActivity(intent);
            myhander.sendEmptyMessage(2);
        } finally {
            urlConnection.disconnect();// 使用完关闭TCP连接，释放资源
        }
    }

    class MyHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//判断hander的内容是什么，如果是1则说明注册成功，如果是2说明注册失败
            switch (msg.what) {
                case 1:
                    Log.i("aa", msg.what + "");
                    Toast.makeText(getApplicationContext(), "添加成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Log.i("aa", msg.what + "");

                    //這是一個提示框
                    Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_LONG).show();
            }

        }
    }


}


