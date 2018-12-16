package com.example.my2048;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements android.view.View.OnClickListener{

	private int score=0;
	private TextView tvScore;
	private Button restart,start,quit,help;
	public static MainActivity mainActivity = null;
	public static MediaPlayer player;
	public static SoundPool pool;
	public static int soundID;
    public static SoundPool poolover;
	public static int soundoverID;    
	
    public static MainActivity getMainActivity() {
        return mainActivity;
    }
    public MainActivity() {
        mainActivity = this;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvScore = (TextView)this.findViewById(R.id.tvScore);
		restart  =(Button)this.findViewById(R.id.restart);
		quit=(Button)this.findViewById(R.id.quit);
		start=(Button)this.findViewById(R.id.start);
		help=(Button)this.findViewById(R.id.help);
		restart.setOnClickListener(this);
		quit.setOnClickListener(this);
		start.setOnClickListener(this);
		help.setOnClickListener(this);
		//��ֵ
		player=MediaPlayer.create(this,R.raw.thepiano);  //˵�˺ܶ�Σ������ֲ�Ҫ�ô�д����Ҫ�ÿո�
	 
		try {
			player.prepare(); // Ԥ��
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
		player.setLooping(true); //ѭ������
		player.setVolume(0.5f, 0.5f);  //��������Ҫ�ŵ���ʼ����֮��
		
		pool=new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundID=pool.load(this,R.raw.slide, 0);
		
		poolover=new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundoverID=poolover.load(this, R.raw.over, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//ʵ�ֻ��ֹ���
	public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        tvScore.setText(score + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();
    }
    
	@Override
	public void onClick(View v) {
		try{
			switch(v.getId()){
			case R.id.quit:
				AlertDialog.Builder dialogquit=new AlertDialog.Builder(this);
				dialogquit.setTitle("��ʾ");
				dialogquit.setMessage("���ѣ������Ҫ���𣿣�");
				dialogquit.setPositiveButton("�ǵ�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				});
				dialogquit.setNegativeButton("û���ֻ�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {						
					}
				});
				dialogquit.show();
				break;
				
			case R.id.restart:
				Toast.makeText(this, "Not comlete", Toast.LENGTH_SHORT).show();
				break;
//			case R.id.start:
//				 new Gameview(this).onSizeChanged(w, h, oldw, oldh);  //���÷������ɹ�****************
//				 break;
			case R.id.help:
				//���˰����󵯳��Ի���
				AlertDialog.Builder dialog=new AlertDialog.Builder(this);
				dialog.setTitle("����");
				dialog.setMessage("��ô�򵥵���Ϸ�㻹Ҫ������\nLow������...\nʵ�ڲ�����Լ��ٶȰ�!");
				dialog.setPositiveButton("��������", new DialogInterface.OnClickListener() { 					
					public void onClick(DialogInterface dialog, int which) {}
				});
				dialog.show();
				break;
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
	}
    




}
