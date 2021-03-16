package com.menu.task3.ui.dashboard.Adapter;


import com.menu.task3.R;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private String description;
    private int image;
    private long members;
    public static List<Group> data = new ArrayList<>();

    public Group(String id, String name, String description, int image, long members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public long getMembers() {
        return members;
    }

    public void setMembers(long members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", members=" + members +
                '}';
    }

    public static void installData() {
        data.add(new Group("12323235", "احباب الرحمن", "مجموعة متخصصة في تعليم القرآن الكريم والسنة النبوية الشريفة", R.drawable.img1, 5));
        data.add(new Group("12323235", "احباب الرحمن", "مجموعة متخصصة في تعليم القرآن الكريم والسنة النبوية الشريفة", R.drawable.img2, 10));
        data.add(new Group("12323235", "احباب الرحمن", "مجموعة متخصصة في تعليم القرآن الكريم والسنة النبوية الشريفة", R.drawable.img3, 12));
        data.add(new Group("12323235", "احباب الرحمن", "مجموعة متخصصة في تعليم القرآن الكريم والسنة النبوية الشريفة", R.drawable.img4, 17));
        data.add(new Group("12323235", "احباب الرحمن", "مجموعة متخصصة في تعليم القرآن الكريم والسنة النبوية الشريفة", R.drawable.img5, 18));

    }

    public static void addtoList(Group group) {
        data.add(group);
    }

}
