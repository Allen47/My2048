package com.example.my2048;

import java.util.ArrayList;
import java.util.List;

//import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
//import android.widget.Toast;

//用来初始化界面的类
public class Gameview  extends GridLayout{
	
	public Card[][] cardsMap=new Card[4][4];
	private List<Point> emptyPoints = new ArrayList<Point>();
	
	//构造方法可能会调用多个，多重载几个
   public Gameview(Context context,AttributeSet attrs,int defStyle){
	   super(context,attrs,defStyle);
	   initGameview();
   }
   
    public Gameview(Context context,AttributeSet attrs){
    	super(context,attrs);
    	initGameview();
    }

    public Gameview(Context context){
    	super(context);
    	initGameview();
    }
   
   public void initGameview() {  //初始化游戏界面的方法
	   setColumnCount(4);
	   setBackgroundColor(0xffF0FFF0);
	 
	   //主动去改变截面
	    addCards(264,264);
//	    startGame();
	    
	   setOnTouchListener(new OnTouchListener() {
		
		   //记录起始坐标和结束坐标
		   private float startX,startY,offX,offY;
		   
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
						startX=event.getX();
						startY=event.getY();
						break;
			
			case MotionEvent.ACTION_UP:
						offX=event.getX()-startX;  //存储的是水平方向的变化值
						offY=event.getY()-startY;  //存储的是竖直方向的变化值
					
					if(Math.abs(offX)>Math.abs(offY)){  //将此动作判定为水平方向
						if(offX<-5){
//							System.out.println("向左动");
							swipeLeft();
							 MainActivity.pool.play( MainActivity.soundID, 0.2f,0.5f,1,0,1);
						}else if(offX>5){
//							System.out.println("向右动");
							swipeRight();
							 MainActivity.pool.play( MainActivity.soundID, 0.2f,0.5f,1,0,1);
						}
						}else {   //将此动作判定为竖直方向
							if(offY<-5){
//								System.out.println("向上动");
								swipeUp();
							   MainActivity.pool.play( MainActivity.soundID, 0.2f	,0.5f,1,0,1);
							}else if(offY>5){
//								System.out.println("向下动");
								swipeDown();
								 MainActivity.pool.play( MainActivity.soundID, 0.2f,0.5f,1,0,1);

							}
						}
						break;
			default: break;
			}
			return true;
		}   } );



	   
   }
   
   private void addCards(int cardWidth,int cardHeight){  //添加卡片
	   Card c;
	   for(int y=0;y<4;y++){
		   for(int x=0;x<4;x++){
			   c=new Card(getContext());
			   c.setNum(0);
			   addView(c, cardWidth, cardHeight);
			   
			   cardsMap[x][y]=c; //把16张卡片全部添加到二维数组中
		   }
	   }
   }
   
   private void startGame() {  //开始游戏
	   MainActivity.getMainActivity().clearScore(); //分数清0
	   
       for (int y = 0; y < 4; y++) {
           for (int x = 0; x < 4; x++) {
               cardsMap[x][y].setNum(0);
           }
       }    
       //添加两个随机数
       addRandomNum();
       addRandomNum();
   }
   
   @Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) { //改变大小
	super.onSizeChanged(w, h, oldw, oldh);
//	int cardWidth=(Math.min(w, h)-15)/4;
//    Toast.makeText(getContext(), "宽度是"+cardWidth, 30).show();
//	addCards(cardWidth,cardWidth);
    startGame();
 }
   

private void addRandomNum() { //往数组中添加随机数
       emptyPoints.clear();// 清空
       for (int y = 0; y < 4; y++) {
           for (int x = 0; x < 4; x++) {
               if (cardsMap[x][y].getNum() <= 0) {
                   emptyPoints.add(new Point(x, y));
               }

           }
       }
       Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
       cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);   //2和4出现的概率为9:1
   }
   
   private void swipeLeft(){ //往左滑动
	   boolean merge=false;
	   
	   for(int y=0;y<4;y++){ //往右边遍历
		   for(int x=0;x<4;x++){
			   for(int x1=x+1;x1<4;x1++){
				   if(cardsMap[x1][y].getNum()>0) {
					   if(cardsMap[x][y].getNum()<=0){
						   cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                           cardsMap[x1][y].setNum(0);
                           
                           x--;
                           merge=true;
					   }else if(cardsMap[x][y].equals(cardsMap[x1][y])){  //值相同就进行合并
                           cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                           cardsMap[x1][y].setNum(0);
                           
                           MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                           merge=true;
				   }
					   break;
			   }

		   }
	   }		   	   
   }
	   if(merge){  //判断是否合并，若合并了，则添加一个随机数
		   addRandomNum();
		   checkComplete();
	   }
 }
   
   
   private void swipeRight() { //向右滑
	   boolean merge=false;

       for (int y = 0; y < 4; y++) {
           for (int x = 3; x >=0; x--) {
               
               for (int x1 = x - 1; x1 >=0; x1--) {
                   if (cardsMap[x1][y].getNum() > 0) {
                       
                       if (cardsMap[x][y].getNum() <= 0) {
                           cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                           cardsMap[x1][y].setNum(0);
                           
                           x++;
                           merge=true;
                       } else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                           cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                           cardsMap[x1][y].setNum(0);
                           
                           MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                           merge=true;
                       }
                       break;
                   }
               }
           }
       }

		       if(merge){
		           addRandomNum();
		           checkComplete();
		       }
   }
   
   private void swipeUp() { //向上滑
	   boolean merge=false;
	   
       for (int x = 0; x < 4; x++) {
           for (int y = 0; y < 4; y++) {
               
               for (int y1 = y + 1; y1 < 4; y1++) {
                   if (cardsMap[x][y1].getNum() > 0) {
                       
                       if (cardsMap[x][y].getNum() <= 0) {
                           cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                           cardsMap[x][y1].setNum(0);
                           
                           y--;
                           merge=true;
                       } else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                           cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                           cardsMap[x][y1].setNum(0);

                           MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                           merge=true;
                       }
                       break;
                   }
               }
           }
       }
       if(merge){
    	   addRandomNum();
    	   checkComplete();
       }

   }

   private void swipeDown() { //向下滑
	   boolean merge=false;

       for (int x = 0; x < 4; x++) {
           for (int y = 3; y >=0; y--) {
               
               for (int y1 = y - 1; y1 >= 0; y1--) {
                   if (cardsMap[x][y1].getNum() > 0) {
                       
                       if (cardsMap[x][y].getNum() <= 0) {
                           cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                           cardsMap[x][y1].setNum(0);
                           
                           y++;
                           merge=true;
                       } else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                           cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                           cardsMap[x][y1].setNum(0);

                           MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                           merge=true;
                       }
                       break;
                   }
               }
           }
       }
       
       if(merge){
           addRandomNum();
           checkComplete();
       }
    }
   
 private void checkComplete(){ //检查游戏结束
 boolean complete = true;
 ALL:    //ALL标签
	for (int y = 0; y < 4; y++) {
     for (int x = 0; x < 4; x++) {
         if (cardsMap[x][y].getNum() == 0
                 || (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
                 || (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
                 || (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))
                 || (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {

             complete = false;
             break ALL;  //跳出这个大循环
         }
     }
 }
 if (complete) {
		MainActivity.player.stop();
		MainActivity.player.release();
	    MainActivity.poolover.play( MainActivity.soundoverID, 1,1,1,0,1);  //播放结束音乐
	    
     new AlertDialog.Builder(getContext())
             .setTitle("Oh")
             .setMessage("Game Over!")
             .setPositiveButton("restart",
                     new DialogInterface.OnClickListener() {

                         @Override
                         public void onClick(DialogInterface dialog,
                                 int which) {
                             startGame();
                          }
                     }).setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             System.exit(0);  //直接退出游戏
                         }
                     }).show();


 }

}
 
 
   
 }
