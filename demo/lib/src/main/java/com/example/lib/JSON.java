package com.example.lib;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class JSON {

    public static String str = "{\"ua\":{\"a\":\"111\",\"b\":\"222\",\"c\":1},\"as\":[{\"a\":\"a0\",\"b\":\"b0\",\"c\":0},{\"a\":\"a1\",\"b\":\"b1\",\"c\":1},{\"a\":\"a2\",\"b\":\"b2\",\"c\":2},{\"a\":\"a3\",\"b\":\"b3\",\"c\":3},{\"a\":\"a4\",\"b\":\"b4\",\"c\":4},{\"a\":\"a5\",\"b\":\"b5\",\"c\":5},{\"a\":\"a6\",\"b\":\"b6\",\"c\":6},{\"a\":\"a7\",\"b\":\"b7\",\"c\":7},{\"a\":\"a8\",\"b\":\"b8\",\"c\":8},{\"a\":\"a9\",\"b\":\"b9\",\"c\":9}],\"name\":\"name\"}";

    public static void main(String args[]) {

        Gson gson = new Gson();
        gson.fromJson(str, User.class);
//        User user = new User();
//        user.ua = new A();
//        user.ua.a = "111";
//        user.ua.b = "222";
//        user.ua.c = 1;
//
//        user.name = "name";
//        user.as = new ArrayList<>();
//        for(int i=0;i<10;i++) {
//            A a = new A();
//            a.a = "a"+i;
//            a.b = "b"+i;
//            a.c = i;
//            user.as.add(a);
//        }
//
//        String str = gson.toJson(user);
//        System.out.println(str);
    }

    static class User {
        public A ua;
        public List<A> as;
        public String name;
    }
    static class A {
        public String a;
        public String b;
        public int c;
    }


}
