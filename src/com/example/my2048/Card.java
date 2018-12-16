package com.example.my2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout{

	private TextView lable; //存储数字文本
	private int num=0;  //存储此时的数字
	
	public Card(Context context) {  
		super(context);
		lable = new TextView(getContext());
        lable.setTextSize(32);
      
        setBackgroundColor(0xffbbada0); // 设置整体背景
        lable.setBackgroundColor(0x33ffffff);
        lable.setGravity(Gravity.CENTER);   //卡片在中间显示
        
        LayoutParams lp=new LayoutParams(-1,-1); //骚操作，（-1，-1）表示填充满整个父级容器
        lp.setMargins(15, 15, 0, 0);  //设置每个卡片的间隔
        addView(lable,lp);
//        setNum(0);  //创建卡片时初始值为0
	}
	
	public int getNum(){
		return num;
	}
	
	public void setNum(int num){
		this.num=num;
		if(num<=0){
			lable.setText(" ");
		}else{
			lable.setText(num+"");
		}
		
		switch(num){
		case 0: lable.setBackgroundColor(0xffe5ffff);  //透明
		           break;
		case 2: lable.setBackgroundColor(0xffb2ffff);  //透明
        break;
		case 4: lable.setBackgroundColor(0xffa8ffff);  //透明
        break;
		case 8: lable.setBackgroundColor(0xff99ffff);  //透明
        break;
		case 16: lable.setBackgroundColor(0xffd3ffa8);  //透明
        break;
		case 32: lable.setBackgroundColor(0xffceff9e);  //透明
        break;
		case 64: lable.setBackgroundColor(0xffffff9e);  //透明
        break;
		case 128: lable.setBackgroundColor(0xffffff93);  //透明
        break;
		case 256: lable.setBackgroundColor(0xffffff89);  //透明
        break;
		case 512: lable.setBackgroundColor(0xffffc489);  //透明
        break;
		case 1024: lable.setBackgroundColor(0xffffc184);  //透明
        break;
		case 2048: lable.setBackgroundColor(0xffffbf7f);  //透明
        break;
		}
	}
	
	public boolean equals(Card o){
		return getNum()==o.getNum();
	}

}
