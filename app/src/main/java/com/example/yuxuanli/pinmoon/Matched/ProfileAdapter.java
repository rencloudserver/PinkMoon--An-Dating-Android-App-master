package com.example.yuxuanli.pinmoon.Matched;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuxuanli.pinmoon.R;
import com.example.yuxuanli.pinmoon.Utils.User;

import java.util.List;

/**
 * Created by yuxuanli on 5/20/18.
 */
public class ProfileAdapter extends ArrayAdapter<User>{
    private int resourceId;
    private Context mContext;

    public ProfileAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        //improve the efficiency
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.personPic = (ImageView) view.findViewById(R.id.person_image);
            viewHolder.personName = (TextView) view.findViewById(R.id.person_name);
            viewHolder.imageButton = (ImageButton) view.findViewById(R.id.videoCalBtn);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        String profileImageUrl = user.getProfileImageUrl();
        switch (profileImageUrl) {
            case "defaultFemale":
                Glide.with(mContext).load(R.drawable.default_woman).into(viewHolder.personPic);
                break;
            case "defaultMale":
                Glide.with(mContext).load(R.drawable.default_man).into(viewHolder.personPic);
                break;
            default:
                Glide.with(mContext).load(profileImageUrl).into(viewHolder.personPic);
                break;
        }
        viewHolder.personName.setText(user.getUsername());
        viewHolder.imageButton.setFocusable(false);

        return view;
    }


    class ViewHolder{
        ImageView personPic;
        TextView personName;
        ImageButton imageButton;
    }
}
