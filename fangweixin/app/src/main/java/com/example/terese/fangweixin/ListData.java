package com.example.terese.fangweixin;

import java.util.List;

public class ListData {
    public static List<AddressList> list = null;

    public static List<AddressList> getList() {
        return list;
    }

    public static void setList(List<AddressList> list) {
        ListData.list = list;
    }
}
