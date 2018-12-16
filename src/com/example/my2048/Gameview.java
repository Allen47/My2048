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

//������ʼ���������
public class Gameview  extends GridLayout{
	
	public Card[][] cardsMap=new Card[4][4];
	private List<Point> emptyPoints = new ArrayList<Point>();
	
	//���췽�����ܻ���ö���������ؼ���
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
   
   public void initGameview() {  //��ʼ����Ϸ����ķ���
	   setColumnCount(4);
	   setBackgroundColor(0xffF0FFF0);
	 
	   //����ȥ�ı����
	    addCards(264,264);
//	    startGame();
	    
	   setOnTouchListener(new OnTouchListener() {
		
		   //��¼��ʼ����ͽ�������
		   private float startX,startY,offX,offY;
		   
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
						startX=event.getX();
						startY=event.getY();
						break;
			
			case MotionEvent.ACTION_UP:
						offX=event.getX()-startX;  //�洢����ˮƽ����ı仯ֵ
						offY=event.getY()-startY;  //�洢������ֱ����ı仯ֵ
					
					if(Math.abs(offX)>Math.abs(offY)){  //���˶����ж�Ϊˮƽ����
						if(offX<-5){
//							System.out.println("����");
							swipeLeft();
							 MainActivity.pool.play( MainActivity.soundID, 0.2f,0.5f,1,0,1);
						}else if(offX>5){
//							System.out.println("���Ҷ�");
							swipeRight();
							 MainActivity.pool.play( MainActivity.soundID, 0.2f,0.5f,1,0,1);
						}
						}else {   //���˶����ж�Ϊ��ֱ����
							if(offY<-5){
//								System.out.println("���϶�");
								swipeUp();
							   MainActivity.pool.play( MainActivity.soundID, 0.2f	,0.5f,1,0,1);
							}else if(offY>5){
//								System.out.println("���¶�");
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
   
   private void addCards(int cardWidth,int cardHeight){  //��ӿ�Ƭ
	   Card c;
	   for(int y=0;y<4;y++){
		   for(int x=0;x<4;x++){
			   c=new Card(getContext());
			   c.setNum(0);
			   addView(c, cardWidth, cardHeight);
			   
			   cardsMap[x][y]=c; //��16�ſ�Ƭȫ����ӵ���ά������
		   }
	   }
   }
   
   private void startGame() {  //��ʼ��Ϸ
	   MainActivity.getMainActivity().clearScore(); //������0
	   
       for (int y = 0; y < 4; y++) {
           for (int x = 0; x < 4; x++) {
               cardsMap[x][y].setNum(0);
           }
       }    
       //������������
       addRandomNum();
       addRandomNum();
   }
   
   @Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) { //�ı��С
	super.onSizeChanged(w, h, oldw, oldh);
//	int cardWidth=(Math.min(w, h)-15)/4;
//    Toast.makeText(getContext(), "�����"+cardWidth, 30).show();
//	addCards(cardWidth,cardWidth);
    startGame();
 }
   

private void addRandomNum() { //����������������
       emptyPoints.clear();// ���
       for (int y = 0; y < 4; y++) {
           for (int x = 0; x < 4; x++) {
               if (cardsMap[x][y].getNum() <= 0) {
                   emptyPoints.add(new Point(x, y));
               }

           }
       }
       Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
       cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);   //2��4���ֵĸ���Ϊ9:1
   }
   
   private void swipeLeft(){ //���󻬶�
	   boolean merge=false;
	   
	   for(int y=0;y<4;y++){ //���ұ߱���
		   for(int x=0;x<4;x++){
			   for(int x1=x+1;x1<4;x1++){
				   if(cardsMap[x1][y].getNum()>0) {
					   if(cardsMap[x][y].getNum()<=0){
						   cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                           cardsMap[x1][y].setNum(0);
                           
                           x--;
                           merge=true;
					   }else if(cardsMap[x][y].equals(cardsMap[x1][y])){  //ֵ��ͬ�ͽ��кϲ�
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
	   if(merge){  //�ж��Ƿ�ϲ������ϲ��ˣ������һ�������
		   addRandomNum();
		   checkComplete();
	   }
 }
   
   
   private void swipeRight() { //���һ�
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
   
   private void swipeUp() { //���ϻ�
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

   private void swipeDown() { //���»�
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
   
 private void checkComplete(){ //�����Ϸ����
 boolean complete = true;
 ALL:    //ALL��ǩ
	for (int y = 0; y < 4; y++) {
     for (int x = 0; x < 4; x++) {
         if (cardsMap[x][y].getNum() == 0
                 || (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
                 || (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
                 || (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))
                 || (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {

             complete = false;
             break ALL;  //���������ѭ��
         }
     }
 }
 if (complete) {
		MainActivity.player.stop();
		MainActivity.player.release();
	    MainActivity.poolover.play( MainActivity.soundoverID, 1,1,1,0,1);  //���Ž�������
	    
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
                             System.exit(0);  //ֱ���˳���Ϸ
                         }
                     }).show();


 }

}
 
 
   
 }
