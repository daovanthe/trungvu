package th.com.arrayadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import th.com.th.R;

/**
 * Created by The on 5/8/2016.
 */
public class FriendListAdapter extends ArrayAdapter  {

    public FriendListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        TH user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_friend, parent, false);
        }

//        TextView tvName = (TextView) convertView.findViewById(R.id.TH_nickName_textView);
//        TextView tvBaiHatCuaBanBeDangChoi = (TextView) convertView.findViewById(R.id.TH_baiHatCuaBanBeDangChoi_textView);
//        tvName.setText(user.getNickName());
//        tvBaiHatCuaBanBeDangChoi.setText(user.getBaiHatDangChoi().getTenBaiHat());
        return convertView;

    }
}
