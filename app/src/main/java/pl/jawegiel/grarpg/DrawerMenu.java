package pl.jawegiel.grarpg;

import android.graphics.drawable.Icon;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;

/**
 * Created by Kuba on 2018-02-04.
 */

public class DrawerMenu {



    String title;
    int icon;
    int count;

    public String getTitle() { return title; }

    public int getIcon() { return icon; }

    public int getCount() { return count; }


    public DrawerMenu() {
        super();
    }

    public DrawerMenu(String title, int icon)
    {
        this.title = title;
        this.icon = icon;
    }

    public DrawerMenu(String title, int icon, int count)
    {
        this.title = title;
        this.icon = icon;
        this.count = count;
    }
}
