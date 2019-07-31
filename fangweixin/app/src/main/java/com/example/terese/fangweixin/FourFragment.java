package com.example.terese.fangweixin;

/**
 * Created by terese on 2019/5/30.
 */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourFragment extends Fragment {
    private TextView textView1;
    private TextView textView2;
    private List<Map<String, Object>> dataList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four,container, false);
        textView1 =view.findViewById(R.id.tvname1);
        textView2 =view.findViewById(R.id.tvmsg1);
//        Intent intent =getActivity().getIntent();
//        Bundle bundle = intent.getBundleExtra("number");
//        String username =bundle.getString("username");
        String number = Constant.username;
        textView1.setText(number);

        System.out.println("this no");
        System.out.println(ListData.list);
        
        return view;
   }


}