package com.menu.task4;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import static com.menu.task4.Constant.packages;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Public {
    @SuppressLint("StaticFieldLeak")
    public static List<String> paks = new ArrayList<>();

    public static boolean search(String pak) {
        if (paks == null) {
            return true;
        }
        for (String s : paks) {
            if (s.equals(pak)) {
                return true;
            }
        }

        return false;
    }

    public static boolean searchPackages(String pak) {
        for (String s : packages) {
            if (s.equals(pak)) {
                return true;
            }
        }
        return false;
    }
}

