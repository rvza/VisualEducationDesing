package com.tatlicilar.visualeducation;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Asus on 15.08.2017.
 */

public class NavigationDrawerItem {
    public static int[] getImages() {
        return  new int[]{R.drawable.pencil_images,R.drawable.pencil_images,R.drawable.pencil_images,R.drawable.pencil_images,R.drawable.pencil_images};
    }

    public static String[] getTitleIds() {
        return new String[]{"Fizik","Kimya","Biyoloji","Matematik","Coğrafya"};
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return ımageId;
    }

    public void setImageId(int ımageId) {
        this.ımageId = ımageId;
    }

    String title;
    int ımageId;
    public static ArrayList<NavigationDrawerItem> getData()
    {
        ArrayList<NavigationDrawerItem> dataList=new ArrayList<NavigationDrawerItem>();
        int [] imageIds=getImages();
        String[] titleIds=getTitleIds();
        for (int i=0;i<titleIds.length;i++)
        {
            NavigationDrawerItem navItem=new NavigationDrawerItem();
            navItem.setTitle(titleIds[i]);
            navItem.setImageId(imageIds[i]);
            dataList.add(navItem);
        }


    return  dataList;
    }
}
