package com.lanlengran.minesweeper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;


/**
 * @des:
 * @author: 芮勤
 * @date: 2018/9/13 11:13
 * @see {@link }
 */
public class MainRecyclerviewAdapter extends RecyclerView.Adapter<MainRecyclerviewAdapter.MyViewHolder> {
    private Context context;
    private List<LandMineBean> landMineBeans;
    private int height=0;
    private int width=0;
    private int landMineSize;
    private int lastPos=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (msg.what==0){
//                openOtherPos(msg.arg1,msg.arg2);
//            }
        }
    };

    public MainRecyclerviewAdapter(Context context, List<LandMineBean> landMineBeans,int height,int width,int landMineSize) {
        this.context=context;
        this.landMineBeans=landMineBeans;
        this.height=height;
        this.width=width;
        this.landMineSize=landMineSize;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_landmine_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.itemView.setTag(i);
        myViewHolder.button.setEnabled(false);

        if (landMineBeans.get(i).isOpen()){
            int landMineSize=getOtherLandMineSize(i);
            if (landMineSize==0){
                myViewHolder.button.setText("");
                myViewHolder.button.setEnabled(false);
            }else {
                myViewHolder.button.setText(""+landMineSize);
                myViewHolder.button.setEnabled(true);
            }

            myViewHolder.button.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            if (landMineBeans.get(i).isLandMine()){
                myViewHolder.button.setVisibility(View.GONE);
            }else {
                myViewHolder.button.setVisibility(View.VISIBLE);
            }
        }else {
            myViewHolder.button.setText("蓝");
            myViewHolder.button.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
            myViewHolder.button.setEnabled(true);
            myViewHolder.button.setVisibility(View.VISIBLE);
        }


    }

    /**
     * 计算这个点，周围8个点的雷的数量
     * @param index
     * @return
     */
    private int getOtherLandMineSize(int index){
        int landMineSize=0;
        int myX=index%width;
        int myY=index/width;

        for (int x=myX-1;x<=myX+1;x++){
            if (x<0||x>=width){
                continue;
            }
            for (int y=myY-1;y<=myY+1;y++){
                if (y<0||y>=height){
                    continue;
                }
                int pos=y*width+x;
                if (pos!=index&&landMineBeans.get(y*width+x).isLandMine()){
                    landMineSize++;
                }
            }
        }
        return landMineSize;
    }

    private void openOtherPos(final int x, final int y){

        if (x<0||x>=width){
            return;
        }
        if (y<0||y>=height){
            return;
        }
        int index=y*width+x;
        if (landMineBeans.get(index).isOpen()){
            return;
        }
        Log.d("qin","index="+index);
        landMineBeans.get(index).setOpen(true);
        if (getOtherLandMineSize(index)!=0){
            return;
        }


        handler.post(new Runnable() {
            @Override
            public void run() {
                lastPos++;
                openOtherPos(x,y-1);
                openOtherPos(x-1,y);
                openOtherPos(x+1,y);
                openOtherPos(x,y+1);
                lastPos--;
                if (lastPos==0){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (lastPos==0){
                                MainRecyclerviewAdapter.this.notifyDataSetChanged();
                            }
                        }
                    },10);
                }
            }
        });
//        senMsg(x,y-1);
//        senMsg(x-1,y);
//        senMsg(x+1,y);
//        senMsg(x,y+1);
    }

    private void senMsg(int x, int y) {
        Message message=new Message();
        message.arg1=x;
        message.arg2=y;
        message.what=0;
        handler.sendMessage(message);
    }

    public boolean checkIsWin(){
        int notOpenSize=0;
        for (LandMineBean landMineBean:landMineBeans){
            if (!landMineBean.isOpen()){
                notOpenSize++;
            }
        }
        if (notOpenSize==landMineSize){
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return landMineBeans==null?0:landMineBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.btn_mine);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos= (int) itemView.getTag();
            if (!landMineBeans.get(pos).isOpen()){
                if (landMineBeans.get(pos).isLandMine()){
                    onWinLister.winLister(false);
                    return;
                }
                openOtherPos(pos%width,pos/width);
                MainRecyclerviewAdapter.this.notifyDataSetChanged();
                if (checkIsWin()){
                    onWinLister.winLister(true);
                }
            }

        }
    }

    private OnWinLister onWinLister;
    public interface OnWinLister{
        void winLister(boolean isWin);
    }

    public void setOnWinLister(OnWinLister onWinLister) {
        this.onWinLister = onWinLister;
    }
}
