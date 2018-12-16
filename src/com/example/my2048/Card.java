package com.example.my2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout{

	private TextView lable; //�洢�����ı�
	private int num=0;  //�洢��ʱ������
	
	public Card(Context context) {  
		super(context);
		lable = new TextView(getContext());
        lable.setTextSize(32);
      
        setBackgroundColor(0xffbbada0); // �������屳��
        lable.setBackgroundColor(0x33ffffff);
        lable.setGravity(Gravity.CENTER);   //��Ƭ���м���ʾ
        
        LayoutParams lp=new LayoutParams(-1,-1); //ɧ��������-1��-1����ʾ�����������������
        lp.setMargins(15, 15, 0, 0);  //����ÿ����Ƭ�ļ��
        addView(lable,lp);
//        setNum(0);  //������Ƭʱ��ʼֵΪ0
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
		case 0: lable.setBackgroundColor(0xffe5ffff);  //͸��
		           break;
		case 2: lable.setBackgroundColor(0xffb2ffff);  //͸��
        break;
		case 4: lable.setBackgroundColor(0xffa8ffff);  //͸��
        break;
		case 8: lable.setBackgroundColor(0xff99ffff);  //͸��
        break;
		case 16: lable.setBackgroundColor(0xffd3ffa8);  //͸��
        break;
		case 32: lable.setBackgroundColor(0xffceff9e);  //͸��
        break;
		case 64: lable.setBackgroundColor(0xffffff9e);  //͸��
        break;
		case 128: lable.setBackgroundColor(0xffffff93);  //͸��
        break;
		case 256: lable.setBackgroundColor(0xffffff89);  //͸��
        break;
		case 512: lable.setBackgroundColor(0xffffc489);  //͸��
        break;
		case 1024: lable.setBackgroundColor(0xffffc184);  //͸��
        break;
		case 2048: lable.setBackgroundColor(0xffffbf7f);  //͸��
        break;
		}
	}
	
	public boolean equals(Card o){
		return getNum()==o.getNum();
	}

}
