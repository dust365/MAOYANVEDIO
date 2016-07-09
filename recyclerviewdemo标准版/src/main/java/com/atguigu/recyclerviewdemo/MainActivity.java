package com.atguigu.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerview;


    private ArrayList<String> datas;
    private MyAdapter myAdapter;


    private Button btn_add;
    private Button btn_remove;
    private Button btn_list;
    private Button btn_grid;
    private LinearLayoutManager recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);


        btn_add = (Button) findViewById(R.id.btn_add);
        btn_remove = (Button) findViewById(R.id.btn_remove);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_grid = (Button) findViewById(R.id.btn_grid);
        initData();
        //设置适配器
        myAdapter=new MyAdapter(this,datas);
        recyclerview.setAdapter(myAdapter);
        //设置布局管理器
        /**
         * 第一个参数：上下文
         * 第二参数：方向：竖直和水平
         * 第三个参数：排序:升序和降序
         */
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        /**
         * 第一个参数：上下文
         * 第二个参数：设置多少列
         * 第三参数：设置方向：竖直和水平
         * 第四个参数：排序:升序和降序
         */
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,true);


        StaggeredGridLayoutManager staggeredGridLayoutManager =  new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
       ///添加分割线
        recyclerview.addItemDecoration(new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST));
        //设置点击某一条item的点击事件
        myAdapter.setItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String content) {
                Toast.makeText(MainActivity.this, "content==" + content + ",position==" + position, Toast.LENGTH_SHORT).show();
            }
        });


        //设置点击某一条item的图片点击事件

        myAdapter.setImageListener(new MyAdapter.OnClickImageListener() {
            @Override
            public void OnClickImage(View view, int position, String content) {
                Toast.makeText(MainActivity.this, "我是图片哦content==" + content + ",position==" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //设置某条的动画 默认的动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        //
        setListener();
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();


    }

    private void setListener() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.addData(0,"new Conttent");
                recyclerview.scrollToPosition(0);
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.removeData(0);
            }
        });
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                recyclerview.setLayoutManager(linearLayoutManager);

            }
        });
        btn_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridLayoutManager gridLayoutManager =  new GridLayoutManager(MainActivity.this,3,GridLayoutManager.VERTICAL,false);
                recyclerview.setLayoutManager(gridLayoutManager);

            }
        });




    }

    //假数据
    private void initData() {
        datas=new ArrayList<>();
        for (int i=0;i<100;i++){
            datas.add("消息"+i+"条");
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_menu:
                Toast.makeText(MainActivity.this, "我是list", Toast.LENGTH_SHORT).show();
                break;
            case R.id.grid_menu:
                Toast.makeText(MainActivity.this, "我是grid", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }



    //初始化操作
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//加载要显示的menu文件
        getMenuInflater().inflate(R.menu.menu_window, menu);
//初始化search菜单项
        MenuItem item = menu.findItem(R.id.list_menu);
        MenuItem item1 = menu.findItem(R.id.grid_menu);



        return true;
    }




}
