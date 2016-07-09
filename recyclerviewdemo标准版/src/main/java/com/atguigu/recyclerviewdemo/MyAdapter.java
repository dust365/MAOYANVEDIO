package com.atguigu.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dust on 2016/6/13.
 * 作用：适配器
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private  Context context;
    private  ArrayList<String > datas;

    public MyAdapter(Context context, ArrayList<String> datas) {
        this.context=context;
        this.datas=datas;
    }

    /**
     * 相当于ListView中适配器的getview方法中的创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =View.inflate(context,R.layout.item_main,null);
        return new MyViewHolder(view);
    }

    /**
     *相当于ListView中适配器的getview方法中的绑定数据部分，在这个方法写
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    String content=datas.get(position);
        holder.tv_text.setText(content);
    }

    /**
     * 得到总条数
     * @return
     */

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 增加数据
     * @param position
     * @param data
     */
    public void addData(int position,String data){
        if(datas != null){
            datas.add(position,data);
            notifyItemInserted(position);
        }
    }
    /**
     * 移除数据
     * @param position
     */
    public void removeData(int position){
        if(datas != null){
            datas.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_icon;
        private TextView tv_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(v,getLayoutPosition(),datas.get(getLayoutPosition()));
                    }
                }
            });
            iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageListener!=null) {
                        imageListener.OnClickImage(v,getLayoutPosition(),datas.get(getLayoutPosition()));
                    }
                }
            });


        }
    }

    /**
     * 监听点击某一条
     *
     */

    public interface  OnItemClickListener{

        void onItemClick(View view,int position,String content);
    }

    /**
     * 设置点击某条的点击事件
     * @param itemClickListener
     */
    public void setItemClickListener(OnItemClickListener itemClickListener ){
        this.itemClickListener=itemClickListener;
    }
    private OnItemClickListener itemClickListener;
////888888888888888888888888888888888888888888888888888
    //设置点击某条图片的监听
    public  interface  OnClickImageListener{
        void OnClickImage(View view ,int position,String content);
    }

    private OnClickImageListener imageListener;

    //点击图片的监听
    public void setImageListener(OnClickImageListener imageListener){
        this.imageListener=imageListener;
    }



}
