package edu.netcracker.small_learning_things.test_small_things_here;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        ArrayList<Integer> ints = new ArrayList<>(Integer.MAX_VALUE / 2);
        System.out.println(ints.size());
        ArrayList<C> l = new ArrayList<>();
//        doSmth(l);
    }

    static class A{
        public String s;
    }

    static class B extends A{
        public String ss;
    }

    static class C extends B{
        public String sss;
    }

    public static List<? super B> doSmth(List<? super B> l){
        l.add(new C());
        ArrayList<C> cs = new ArrayList<>();
        ArrayList<B> bs = new ArrayList<>();
        ArrayList<A> as = new ArrayList<>();
        return as;
    }
}
