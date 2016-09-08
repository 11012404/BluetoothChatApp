package com.fahim.lokman.bluetoothchatapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fahim.lokman.bluetoothchatapp.R;
import com.fahim.lokman.bluetoothchatapp.contents.Constant;
import com.fahim.lokman.bluetoothchatapp.contents.MessageContents;
import com.fahim.lokman.bluetoothchatapp.robot.BotChatActivity;

import java.util.ArrayList;


public class BotMessageAdapter extends BaseAdapter {


    Context context;
    ArrayList<MessageContents> messageContentses;
    LayoutInflater inflater;
    LinearLayout LL;
    public String messageValue;
    public ViewHolder holder;
    private int playingPosition;
    public ArrayList<ImageView> imageViews = new ArrayList<>();

    public BotMessageAdapter(Context context, ArrayList<MessageContents> messageContentses){
        this.context = context;
        this.messageContentses = messageContentses;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messageContentses.size();
    }

    @Override
    public Object getItem(int position) {
        return messageContentses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

       final MessageContents messageContents = messageContentses.get(position);
        holder = new ViewHolder();
        if(convertView == null){
            convertView = inflater.inflate(R.layout.right_row,null);
            holder.messageView = (TextView) convertView.findViewById(R.id.textView);
            holder.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            holder.voiceView = (ImageView) convertView.findViewById(R.id.voice);
            holder.LL = (LinearLayout) convertView.findViewById(R.id.linearL);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        if(messageContents.type.equals(Constant.TEXTS)){
            holder.messageView.setVisibility(View.VISIBLE);
            holder.messageView.setText(messageContents.message);
            holder.imageView.setVisibility(View.GONE);
            holder.voiceView.setVisibility(View.GONE);
        }

        if(messageContents.sender.equals(BotChatActivity.ME)) {
            holder.LL.setGravity(Gravity.RIGHT);
            holder.messageView.setBackgroundColor(Color.parseColor("#efefef"));
            holder.imageView.setBackgroundColor(Color.parseColor("#efefef"));
            holder.messageView.setTextColor(Color.BLUE);
        }
        else {
            holder.LL.setGravity(Gravity.LEFT);
            holder.messageView.setBackgroundColor(Color.BLUE);
            holder.imageView.setBackgroundColor(Color.BLUE);
            holder.messageView.setTextColor(Color.WHITE);

        }





        return convertView;
    }

    private class ViewHolder{
        public TextView messageView;
        public ImageView imageView,voiceView;
        public LinearLayout LL;
    }



}
