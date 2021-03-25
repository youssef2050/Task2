package com.menu.task4;

import java.util.ArrayList;
import java.util.List;

public class Public {
    public static List<String> paks = new ArrayList<>();

    public static boolean search(String pak) {
        for (String s : paks) {
            if (s.equals(pak)) {
                return true;
            }
        }
        return false;
    }
}

