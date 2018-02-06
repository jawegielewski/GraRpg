package pl.jawegiel.grarpg;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kuba on 2018-02-04.
 */

public class DrawerMenuAdapter extends ArrayAdapter<DrawerMenu>{

    Context context;
    int layoutResourceId;
    DrawerMenu data[] = null;

    public DrawerMenuAdapter(Context context, int layoutResourceId, DrawerMenu[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MenuHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MenuHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            if(row.findViewById(R.id.count)!=null) holder.count = (TextView)row.findViewById(R.id.count);

            row.setTag(holder);
        }
        else
        {
            holder = (MenuHolder)row.getTag();
        }

        DrawerMenu weather = data[position];
        holder.txtTitle.setText(weather.title);
        holder.imgIcon.setImageResource(weather.icon);
        if(data[position].getCount()!=0) holder.count.setText(String.valueOf(weather.count));
        else row.findViewById(R.id.count).setVisibility(View.GONE);

        return row;
    }

    static class MenuHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView count;
    }
}