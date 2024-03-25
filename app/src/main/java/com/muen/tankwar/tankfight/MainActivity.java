package com.muen.tankwar.tankfight;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muen.tankwar.R;
import com.muen.tankwar.constant.GameConstant;
import com.muen.tankwar.gameview.GameView;

public class MainActivity extends Activity implements OnTouchListener {
	

	private boolean isUp = false;
	private GameView gameView = null;
	private Button left = null;
	private Button right = null;
	private Button up = null;
	private Button down = null;
	private Button shot = null;
	private TextView result = null;
	private FrameLayout tankContent = null;
	private RelativeLayout shot_dir = null;
	private boolean isTimeout = true;
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		GameView.gameRunFlag = false;
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initConstantData();
		gameView = new GameView(MainActivity.this);
		// setContentView(gameView);
		setContentView(R.layout.activity_main);
		initButton();
		tankContent = (FrameLayout) findViewById(R.id.tankview);
		shot_dir = (RelativeLayout) findViewById(R.id.shot_dir);
		tankContent.addView(gameView);

	}

	@Override
	protected void onResume() {
		if (gameView == null) {
			gameView = new GameView(MainActivity.this);
			gameView.setHeight(shot_dir.getHeight());
			tankContent.addView(gameView);
		}
		GameView.gameRunFlag = true;
		result.setVisibility(View.GONE);
		shot_dir.setVisibility(View.VISIBLE);
		super.onResume();
	}

	@Override
	protected void onPause() {
		GameView.gameRunFlag = false;
		tankContent.removeView(gameView);
		gameView = null;
		super.onPause();
	}

	public void initButton() {
		
		
		left = (Button) findViewById(R.id.left);
		left.setOnTouchListener(this);
		right = (Button) findViewById(R.id.right);
		right.setOnTouchListener(this);
		up = (Button) findViewById(R.id.up);
		up.setOnTouchListener(this);
		down = (Button) findViewById(R.id.down);
		down.setOnTouchListener(this);
		shot = (Button) findViewById(R.id.shot);
		shot.setOnTouchListener(this);
		result = (TextView) findViewById(R.id.result);

		result.setClickable(true);
		result.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (gameView == null) {
					gameView = new GameView(MainActivity.this);
					tankContent.addView(gameView);
					result.setVisibility(View.GONE);
					shot_dir.setVisibility(View.VISIBLE);
				}
				GameView.gameRunFlag = true;
			}
		});
	}

	public TextView getResultView() {
		return result;
	}

	void initConstantData() {
		Resources r = this.getResources();
		GameConstant.RED = r.getDrawable(R.drawable.redstar);
		GameConstant.BLUE = r.getDrawable(R.drawable.blue);
		GameConstant.GREEN = r.getDrawable(R.drawable.greenstar);
		GameConstant.BULLET = r.getDrawable(R.drawable.bullet);
		//GameConstant.TRANPRENT = r.getDrawable(R.drawable.transparent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
			GameView.gameRunFlag = false;
		return super.onKeyDown(keyCode, event);
	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 2)// 游戏成功
			{
				Log.d("123","你已经赢了");
				tankContent.removeView(gameView);
				gameView = null;
				result.setVisibility(View.VISIBLE);
				shot_dir.setVisibility(View.GONE);
				result.setText(R.string.ends);
			}
			if (msg.what == 1) {
				Log.d("123","你已经输了");
				tankContent.removeView(gameView);
				gameView = null;
				result.setVisibility(View.VISIBLE);
				result.setText(R.string.endf);
				shot_dir.setVisibility(View.GONE);
			}
		}
	};

	public Handler getHandler() {
		return handler;
	}

	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		Log.i("lsm","onTouch v = " + v + " event = " + event);
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			if (v.getId()!= R.id.shot) {
				isUp = false;
			}

			if(isTimeout == true)
			{
				new Thread(new Runnable() {

					@Override
					public void run() {
						while (!isUp) {
							if (gameView != null)
							{
								switch (v.getId()) {
								case R.id.up:
								
									gameView.upMove();
									break;
								case R.id.down:
								
									gameView.downMove();
									break;
								case R.id.left:
									
									gameView.leftMove();
									break;
								case R.id.right:
								
									gameView.rightMove();
									break;

								}
							}
								isTimeout = false;
								
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							isTimeout =true;
							
						}
					}
				}).start();
			}
			if (v.getId() == R.id.shot) {
				Log.i("tank", "========down loopShot gameView.shot()");
				shotDown = true;
				loopShot();
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			Log.i("tank", "========ACTION_UP");
			if (v.getId()!= R.id.shot) {
				isUp = true;
			} else {
				shotDown = false;
			}
			break;
		default:
			break;
		}
		return true;
	}
	boolean shotDown = false;
	void loopShot() {
		if (shotDown && gameView!= null) {
			gameView.shot();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Log.i("tank", "========loopShot gameView.shot()");
					loopShot();
				}
			},510);
		}
	}
}
