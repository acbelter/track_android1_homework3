package com.android1.homework3.msg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android1.homework3.R;
import com.android1.homework3.msg.response.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {
    private LayoutInflater mInflater;

    public UserListAdapter(Context context, List<User> users) {
        super(context, R.layout.item_user, users);
        mInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        ImageView image;
        TextView nickname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_user, null);

            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.nickname = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        holder.nickname.setText(user.nick);

        return convertView;
    }
}
