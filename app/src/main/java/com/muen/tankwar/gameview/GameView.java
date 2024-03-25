package com.muen.tankwar.gameview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.muen.tankwar.constant.GameConstant;
import com.muen.tankwar.entity.BigEnemy;
import com.muen.tankwar.entity.Bullet;
import com.muen.tankwar.entity.SmallEnemy;
import com.muen.tankwar.entity.Tank;
import com.muen.tankwar.entity.Wall;
import com.muen.tankwar.model.BaseModel;
import com.muen.tankwar.sound.GameSoundPool;
import com.muen.tankwar.tankfight.MainActivity;
import com.muen.tankwar.tool.Coordinate;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback ,Runnable
{
	public static int shotTimes = 0;
	public static  boolean isBigShow = false;
	public static boolean gameRunFlag =true;
	private static GameView gameView;
	private  int SMALL1 = 10;
	private long startCreatSmallTime = 0;
	private ArrayList<BaseModel> smallList;
	private ArrayList<BaseModel> bulletList;
	private GameSoundPool sounds = null;
	private Thread thread = null;
	private Canvas canvas;
	private Paint paint;
	private SurfaceHolder surfaceHolder;
	public int[][] mTileGrid;
	public Tank tank = null;
	public BigEnemy bigEnemy = null;
	public Wall wall = null;
	public Bullet bullet = null;
	public SmallEnemy smallEnemy = null;
	protected MainActivity mainActivity;
	public void clearTiles(Canvas canvas) {
        for (int x = 0; x < GameConstant.mXTileCount; x++) {
            for (int y = 0; y < GameConstant.mYTileCount; y++) {
            	canvas.drawBitmap(tank.loadTile(GameConstant.TRANPRENT), tank.getDrawX(x),tank.getDrawY(y), paint);
            }
        }
    }
	 public void setTile(int tileindex, int x, int y) {
	        mTileGrid[x][y] = tileindex;
	    }
	 
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if(h <=800)
			GameConstant.mTileSize =24;
		else
			GameConstant.mTileSize =32;
		Log.i("lsm", "size #### w ="+ w +"***** h ="+ h);
		GameConstant.mXTileCount = (int) Math.floor(w / GameConstant.mTileSize);
        GameConstant.mXOffset = ((w - (GameConstant.mTileSize * GameConstant.mXTileCount)) / 2);
        if(h <=800)
        {
        	GameConstant.mYTileCount = (int) Math.floor((h - 240) / GameConstant.mTileSize);
        	GameConstant.mYOffset = (((h - 240) - (GameConstant.mTileSize * GameConstant.mYTileCount)) / 2);
        
        }else
        {
        	GameConstant.mYTileCount = (int) Math.floor((h - 720) / GameConstant.mTileSize);
        	GameConstant.mYOffset = (((h - 720) - (GameConstant.mTileSize * GameConstant.mYTileCount)) / 2);
        }
        	Log.i("lsm", "mXTileCount == " + GameConstant.mXTileCount + "mYTileCount == "+GameConstant.mYTileCount);
        mTileGrid = new int[GameConstant.mXTileCount][GameConstant.mYTileCount];
		super.onSizeChanged(w, h, oldw, oldh);
		
	}
	int mHeight = 0;
	public void setHeight(int height) {
		mHeight = height;
	}
	public GameView(Context context) {
		super(context);
		mainActivity = (MainActivity)context;
		// TODO Auto-generated constructor stub
		paint = new Paint();
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		gameView = this;
		isBigShow =false;
		shotTimes = 0;
		sounds = new GameSoundPool(mainActivity);
		sounds.initGameSound();
	}
	void createSmallEnemy()
	{
		boolean isLeftFull = false;
		boolean isRightFull = false;
		if(System.currentTimeMillis() - startCreatSmallTime > 5000)//5s产生一个敌方小坦克
		{
			Log.i("lsm", "Coordinate size ===" +getCoordinate(0,0).size() );
			for(int i = 0;i < getCoordinate(0, 0).size();i++)
			{
				if(getCoordinate(0, 0).get(i).getX()>(GameConstant.mXTileCount - 7) &&
						getCoordinate(0, 0).get(i).getY()<=4)
				{
					isRightFull =true;
				}
				if(getCoordinate(0, 0).get(i).getX()<4 &&
						getCoordinate(0, 0).get(i).getY()<=4)
				{
					isLeftFull =true;
				}
				
			}
			Log.i("lsm", "#############create   a SmallEnemy");
			if(SMALL1>0)
			{
					if(!isRightFull)
					{
						if(GameConstant.RNG.nextInt(2)==0)
							smallList.add(new SmallEnemy(GameConstant.mXTileCount - 4, 1,GameConstant.SOURTH));
						else
							smallList.add(new SmallEnemy(GameConstant.mXTileCount - 4, 1,GameConstant.WEST));
						SMALL1 --;
					}
					if(SMALL1>0&&!isLeftFull)
					{
						if(GameConstant.RNG.nextInt(2)==0)
							smallList.add(new SmallEnemy(1, 1,GameConstant.EAST));
						else
							smallList.add(new SmallEnemy(1, 1,GameConstant.SOURTH));
						SMALL1 --;
					}
				startCreatSmallTime = System.currentTimeMillis();
				
			}
			else
			{
				startCreatSmallTime = System.currentTimeMillis();
				return;
			}
			
			
		}
	}
	public void createElement()
	{
		bulletList  = new ArrayList<BaseModel>();
		smallList   = new ArrayList<BaseModel>();
		
		wall   = new Wall();
		tank   = new Tank( 4, GameConstant.mYTileCount-2 -GameConstant.tankpic.length);
	}

	public void ondraw(Canvas canvas)
	{	
		createSmallEnemy();//定时5s产生小坦克
		wall.drawself(canvas, paint);
		//多个集合的子弹
		if(bulletList.size()>0)
		{
			for(int i =0;i < bulletList.size();i++)
			{
				bulletList.get(i).drawself(canvas, paint);
			}
		}

		tank.drawself(canvas, paint);

		//多个敌方xiao坦克
		if(smallList.size()>0)
		{
			for(int i =0;i < smallList.size();i++)
			{
				smallList.get(i).drawself(canvas, paint);
			}
		}

		if(bigEnemy!=null)
		{
			if(shotTimes>0)
			{

				paint.setAlpha(200-shotTimes*25);
				bigEnemy.drawself(canvas, paint);
				paint.setAlpha(255);
			}else
			{
				bigEnemy.drawself(canvas, paint);
			}

		}

	}
	void dataDeal()
	{
		
		if(bulletList.size()>0)
		{
			for(int i =0;i < bulletList.size();i++)
			{
				if(bulletList.get(i).isAlive() == false)
				{
					bulletList.remove(bulletList.get(i));
				}
			}
		}
		//////////////////////////////////////////////////
		if(smallList.size()>0)
		{
			for(int i =0;i < smallList.size();i++)
			{
				if(smallList.get(i).isAlive() == false)
				{
					smallList.remove(smallList.get(i));
				}
			}
		}
		if(SMALL1 == 0&&smallList.size()==0&&isBigShow == false)
		{
			bigEnemy  =new BigEnemy(GameConstant.mXTileCount/2 -3, 1);
			isBigShow =true;
			tank.setX(GameConstant.mXTileCount/2-1);
			tank.setY(GameConstant.mYTileCount-4);
			tank.setDirect(GameConstant.NORTH);
		}
		if(tank.isAlive()==true&&bigEnemy!=null&&bigEnemy.isAlive()==false)
		{//游戏成功
			gameRunFlag = false;
			Message message = new Message();   
			message.what = 2;
			mainActivity.getHandler().sendMessage(message);
		}
		else if(false==tank.isAlive())
		{//失败
			gameRunFlag = false;
			Message message = new Message();   
			message.what = 1;
			mainActivity.getHandler().sendMessage(message);
		}
	}
	@SuppressLint("SuspiciousIndentation")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (gameRunFlag) {
			long startTime = System.currentTimeMillis();
			synchronized (surfaceHolder) {
				try {
					
					canvas = surfaceHolder.lockCanvas();
					canvas.drawColor(Color.BLACK);
					dataDeal();
					ondraw(canvas);
					
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					if(canvas !=null)
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			long endTime = System.currentTimeMillis();
			//Log.i("lsm", "end -start  = " + (endTime - startTime));
			try {
				if(endTime -startTime <20)
				Thread.sleep(20 - (endTime -startTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		createElement();
		gameRunFlag = true;
		thread=new Thread(this);
		thread.start();
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Log.i("lsm", "surface Change =======");
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		gameRunFlag =false;
		thread = null;
		
	}
	public static   GameView getInstance() {
		// TODO Auto-generated constructor stub
		return gameView;
	} 
	
	public void  leftMove()
	{
		if(tank!=null)
		if(isBigShow||tank.getDirect() == GameConstant.WEST )
			tank.leftMove();
		else
		 tank.setDirect(GameConstant.WEST);
	}
	public void  rightMove()
	{
		if(tank!=null)
		if(isBigShow||tank.getDirect() == GameConstant.EAST)
			tank.rightMove();
		else
			 tank.setDirect(GameConstant.EAST);
	}
	public void  upMove()
	{
		if(isBigShow == false&&tank!=null)
		{
			if(tank.getDirect() == GameConstant.NORTH)
				tank.upMove();
			else
				tank.setDirect(GameConstant.NORTH);
		}
		
	}
	public void  downMove()
	{
		if(isBigShow == false&&tank!=null)
		{
			if(tank.getDirect() == GameConstant.SOURTH)
				tank.downMove();
			else
				tank.setDirect(GameConstant.SOURTH);
		}
		
	}
	public void shot()
	{
		shot(tank);
		
	}
	int gap = 2;
	public void shot( BaseModel baseModel)
	{
		
		if(baseModel instanceof Tank || (baseModel instanceof SmallEnemy))
		{
			if((System.currentTimeMillis() - baseModel.getStartTime())>500)
			{
				int tmpdir = GameConstant.NORTH;
				if(baseModel instanceof Tank)
				{
					tmpdir = ((Tank) baseModel).getDirect();
				}else
				{
					tmpdir = ((SmallEnemy) baseModel).getDirect();
				}
				//if(baseModel.isCrash(tmpdir)==false)
				//{
				switch (tmpdir)
				{
				case GameConstant.NORTH:
					if(baseModel.getY()!=1)
					{
						if(baseModel instanceof Tank)
						{
						  bulletList.add(new Bullet(baseModel.getX() + 1, baseModel.getY() -gap,GameConstant.NORTH,false));
						  sounds.playSound(1, 0);
						}
						  else
						  bulletList.add(new Bullet(baseModel.getX() + 1, baseModel.getY() -gap,GameConstant.NORTH,true));
						
					}
					break;
				case GameConstant.WEST:
					if(baseModel.getX()!=1)
					{
						if(baseModel instanceof Tank)
							{
								bulletList.add(new Bullet(baseModel.getX() - gap, baseModel.getY() + 1,GameConstant.WEST,false));
								sounds.playSound(1, 0);
							}
							else
							bulletList.add(new Bullet(baseModel.getX() - gap, baseModel.getY() + 1,GameConstant.WEST,true));
					
					}
					
					break;
				case GameConstant.EAST:
					if(baseModel.getX()!=GameConstant.mXTileCount-4)
					{
						
						if(baseModel instanceof Tank)
						{
							bulletList.add(new Bullet(baseModel.getX() + 2 + gap, baseModel.getY() + 1,GameConstant.EAST,false));
							sounds.playSound(1, 0);
						}else
							bulletList.add(new Bullet(baseModel.getX() + 2 + gap, baseModel.getY() + 1,GameConstant.EAST,true));
						
					}
					
					break;
				case GameConstant.SOURTH:
					if(baseModel.getY()!=GameConstant.mYTileCount-4)
					{
						if(baseModel instanceof Tank)
						{
							bulletList.add(new Bullet(baseModel.getX() + 1, baseModel.getY() + 2 + gap,GameConstant.SOURTH,false));
							sounds.playSound(1, 0);
						}else
							bulletList.add(new Bullet(baseModel.getX() + 1, baseModel.getY() + 2 + gap,GameConstant.SOURTH,true));
						
					}
					
					break;
				default:
					break;
				}
				baseModel.setStartTime(System.currentTimeMillis());
				//}
			}
		}
		if(baseModel instanceof BigEnemy)
		{
			bulletList.add(new Bullet(baseModel.getX() + 3, baseModel.getY() + 7,GameConstant.SOURTH,true));
		}
	}
	public ArrayList<Coordinate> getCoordinate(int x,int y)
	{
		ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>();
		
		coordinateList.add(new Coordinate(tank.getX(),tank.getY()));
		if(bigEnemy!=null)
		{
			coordinateList.add(new Coordinate(bigEnemy.getX(),bigEnemy.getY()));
		}
		if(smallList.size() > 0)
		{
			
			for(int i =0 ; i< smallList.size();i++)
			{
				if(x==smallList.get(i).getX()&& y==smallList.get(i).getY())
				{
				
					
				}
				else
				{
					coordinateList.add(new Coordinate(smallList.get(i).getX(), smallList.get(i).getY()));
					
				}
				
			}
		}
		
		
		return coordinateList;
	}
	
	public BaseModel getBaseModel(int x, int y)
	{
		BaseModel tmpbaseModel = null;
		if(tank.getX() == x&& tank.getY() == y)
		{
			return tank;
		}
		else 
		{
			for(int i = 0;i<smallList.size();i++)
			{
				if(smallList.get(i).getX()==x&&smallList.get(i).getY()==y)
				{
					return smallList.get(i);
				}
			}
		}
		if(bigEnemy!=null&&bigEnemy.getX() == x&& bigEnemy.getY() == y)
		{
			return bigEnemy;
		}
		return tmpbaseModel;
	}
}
