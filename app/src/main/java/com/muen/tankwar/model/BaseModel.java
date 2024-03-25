package com.muen.tankwar.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.muen.tankwar.constant.GameConstant;
import com.muen.tankwar.gameview.GameView;
import com.muen.tankwar.tool.Coordinate;

public class BaseModel {
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long startTime ;
	private int x;
	private int y;
	private boolean isAlive;
	private boolean isEnemy;
	public boolean isEnemy() {
		return isEnemy;
	}
	public void  drawself(Canvas canvas,Paint paint) {
		
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
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	Bitmap mBitmap = null;
	public Bitmap loadTile(Drawable tile) {
		if (mBitmap == null) {
			Bitmap bitmap = Bitmap.createBitmap(GameConstant.mTileSize, GameConstant.mTileSize, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			tile.setBounds(0, 0, GameConstant.mTileSize, GameConstant.mTileSize);
			tile.draw(canvas);
			mBitmap = bitmap;
		}

        
        return mBitmap;
    }
	public int getDrawX(int x)
	{
		return GameConstant.mXOffset + x * GameConstant.mTileSize;
	}
	public int getDrawY(int y)
	{
		return GameConstant.mYOffset + y * GameConstant.mTileSize;
	}
	public boolean isCrash(int direct)
	{
		Coordinate tmpDirect =null;
		for(int i = 0; i < GameView.getInstance().getCoordinate(getX(),getY()).size(); i++)
		{ 
			tmpDirect =  GameView.getInstance().getCoordinate(getX(),getY()).get(i);
			//Log.i("lsm", "tmpDirect x ===" + tmpDirect.getX() + "  getX=== " +getX());
			//Log.i("lsm", "Coordinate size ===" +GameView.getInstance().getCoordinate(getX(),getY()).size() );
			switch (direct) {
			case GameConstant.EAST:
				if(getX() +3 ==tmpDirect.getX() &&Math.abs(getY()-tmpDirect.getY())<3 )
				{
				//	Log.i("lsm", "direct === GameConstant.EAST");
					return true;
				}
				break;
			case GameConstant.WEST:
				if(getX() == tmpDirect.getX() + 3 && Math.abs(getY()-tmpDirect.getY())<3 )
				{
				//	Log.i("lsm", "direct === GameConstant.WEST");
					return true;
				}
				break;
			case GameConstant.NORTH:
				if(getY() == tmpDirect.getY() +3&& Math.abs(getX()-tmpDirect.getX())<3  )
				{
				//	Log.i("lsm", "direct === GameConstant.NORTH");
					return true;
				}
				break;
			case GameConstant.SOURTH:
				if(getY() +3== tmpDirect.getY()&& Math.abs(getX()-tmpDirect.getX())<3 )
				{
				//	Log.i("lsm", "direct === GameConstant.SOURTH");
					return true;
				}
				break;

			default:
				break;
			}
		}
		return false;
	}
}
