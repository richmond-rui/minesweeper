package com.lanlengran.minesweeper;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<LandMineBean> landMineBeans=new ArrayList<>();
    public static int width=10;
    public static int height=10;
    public static int landMineSize=1;
    private MainRecyclerviewAdapter mainRecyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDate();
        initView();
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerview_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this,10));
        mainRecyclerviewAdapter=new MainRecyclerviewAdapter(this,landMineBeans,height,width,landMineSize);
        recyclerView.setAdapter(mainRecyclerviewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mainRecyclerviewAdapter.setOnWinLister(new MainRecyclerviewAdapter.OnWinLister() {
            @Override
            public void winLister(boolean isWin) {
                ShowDialog(isWin);
            }
        });
    }

    private void ShowDialog(boolean isWin){
        String msg="";
        if (isWin){
            msg="恭喜您，赢得了比赛，是否重来一局";
        }else {
            msg="很抱歉，你壮烈牺牲了，是否重来一局";
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initDate();
                mainRecyclerviewAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }

    private void initDate() {
        landMineBeans.clear();

        for (int i=0;i<width;i++){
            for (int j=0;j<height;j++){
                LandMineBean landMineBean=new LandMineBean();
                landMineBean.setOtherLandMineSize(2);
                landMineBeans.add(landMineBean);
            }
        }
        //生成地雷
        HashSet<Integer> hashSet=new HashSet<>();
        Utils.randomSet(0,width*height,landMineSize,hashSet);

        for (Integer index:hashSet){
            landMineBeans.get(index).setLandMine(true);
        }
    }
}
