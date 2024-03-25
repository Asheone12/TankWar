package com.muen.tankwar.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.muen.tankwar.constant.GameConstant;
import com.muen.tankwar.gameview.GameView;
import com.muen.tankwar.model.BaseModel;
import com.muen.tankwar.tool.Coordinate;

public class Bullet extends BaseModel {
	private int x;
	private int detaCountX = 0;
	public static  final int gapCount = GameConstant.mTileSize / 4;
	private int y;
	private int detaCountY = 0;
	private boolean isAlive;
	private int timeCount ;
	private int direct;
	private boolean isEnemy;
	public Bullet(int x,int y,int direct,boolean isEnemy){
		this.x = x;
		this.y = y;
		this.isAlive =true;
		this.direct = direct;
		timeCount = 0;
		this.isEnemy = isEnemy;
		
	}
	public void  drawself(Canvas canvas,Paint paint) {
		
			
		crash();
		if(isAlive )
		{
				switch (direct) {
				case GameConstant.EAST:
					if(x<=GameConstant.mXTileCount-2) {
						detaCountX++;
						if (Math.abs(detaCountX) == gapCount) {
							x++;
							detaCountX = 0;
						}
					}
					if(x>=GameConstant.mXTileCount-2)
						setAlive(false);
					break;
				case GameConstant.NORTH:
					if(y!=1) {
						detaCountY--;
						if (Math.abs(detaCountY) == gapCount) {
							y--;
							detaCountY = 0;
						}
					}

					if(y< 2)
					{
						setAlive(false);
					}
					break;
				case GameConstant.WEST:
					if(x!=1) {
						detaCountX--;
						if (Math.abs(detaCountX) == gapCount) {
							x--;
							detaCountX = 0;
						}
					}

					if(x<2)
					{
						setAlive(false);
					}
					break;
				case GameConstant.SOURTH:
					if(y!=GameConstant.mYTileCount-2) {
						detaCountY++;
						if (Math.abs(detaCountY) == gapCount) {
							y++;
							detaCountY = 0;
						}
					}
					if(y >= GameConstant.mYTileCount-2)
					{
						setAlive(false);
					}
					break;
				default:
					break;
				}
				int realX = getDrawX(x);
				int realY = getDrawY(y);
				if (!isEnemy)
					Log.i("lsm1"," realX = " + realX + " detaCountX = "+ detaCountX + " realY " + realY + " detaCountY " +detaCountY + " gapCount = " + gapCount);
			canvas.drawBitmap(loadTile(GameConstant.BULLET), realX,realY, paint);

			
		}
		 
	}
	public int getDrawX(int x)
	{
		if (detaCountX !=  0) {
			return GameConstant.mXOffset + this.x  * GameConstant.mTileSize + detaCountX * (GameConstant.mTileSize / gapCount);
		}

		return GameConstant.mXOffset + x * GameConstant.mTileSize;
	}
	public int getDrawY(int y)
	{
		if (detaCountY != 0) {
			return GameConstant.mYOffset + this.y  * GameConstant.mTileSize + detaCountY * (GameConstant.mTileSize / gapCount);
		}
		return GameConstant.mYOffset + y * GameConstant.mTileSize;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public boolean isEnemy() {
		return isEnemy;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public void crash()
	{
		Coordinate tmCoordinate = null;
		BaseModel baseModel = null;
		for(int i = 0; i < GameView.getInstance().getCoordinate(0, 0).size(); i++)
		{
			tmCoordinate = GameView.getInstance().getCoordinate(0, 0).get(i);
			baseModel =GameView.getInstance().getBaseModel(tmCoordinate.getX(), tmCoordinate.getY());
			//Log.i("lsm", "direct == " + direct);
			switch (direct) {
			case GameConstant.EAST:
				if(!GameView.isBigShow&&(getX()>=tmCoordinate.getX()&&getX()<=tmCoordinate.getX()+2)
						&&getY()-tmCoordinate.getY()<3&&getY() -tmCoordinate.getY()>=0)
				{
					if(isEnemy()!=baseModel.isEnemy() )
						baseModel.setAlive(false);
					setAlive(false);
				}
				if(GameView.isBigShow)
				{
					setAlive(false);
				}
				break;
			case GameConstant.WEST://子弹射击中弹判断
				if(!GameView.isBigShow&&(getX()<=tmCoordinate.getX()+2&&getX()>=tmCoordinate.getX() )
						&&getY()-tmCoordinate.getY()<3&&getY() -tmCoordinate.getY()>=0)
				{
					if(isEnemy()!=baseModel.isEnemy() )
						baseModel.setAlive(false);
					setAlive(false);
				}
				if(GameView.isBigShow)
				{
					setAlive(false);
				}
				break;
			case GameConstant.SOURTH:
				if((getY()>=tmCoordinate.getY()&&getY()<=tmCoordinate.getY()+2)
						&&getX()-tmCoordinate.getX()<3 &&getX()-tmCoordinate.getX()>=0 )
				{
					if(isEnemy()!=baseModel.isEnemy() )
						baseModel.setAlive(false);
					setAlive(false);
				}
				if(GameView.isBigShow&&baseModel instanceof BigEnemy)
				{
					//表示有小敌人子弹遗留在大坦克身体内
					if(getX()>=baseModel.getX()&&getX()<=baseModel.getX()+6&&getY()<baseModel.getY()+7)
						setAlive(false);
				}
				break;
			case GameConstant.NORTH:
				if(!GameView.isBigShow&&(getY()<=tmCoordinate.getY()+2&&getY()>=tmCoordinate.getY())
						&&getX()-tmCoordinate.getX()<3 &&getX()-tmCoordinate.getX()>=0 )
				{
					if(isEnemy()!=baseModel.isEnemy() )
						baseModel.setAlive(false);
					setAlive(false);
				}
				if(GameView.isBigShow && baseModel instanceof BigEnemy
						&&getX()>=baseModel.getX()&&getX()<=baseModel.getX()+6&&getY()==baseModel.getY()+7)
				{
					if(getY()-1==tmCoordinate.getY()+6
							&& getX() == tmCoordinate.getX()+3)
					{
						if(isEnemy()!=baseModel.isEnemy())
						{
							GameView.shotTimes++;
							if(GameView.shotTimes == GameConstant.BeShotTimes)
							{
								baseModel.setAlive(false);
							}
							
						}
						
						
					}
					setAlive(false);
					
				}
				break;
			default:
				break;
			}
			
		}
	}
	
	

}
