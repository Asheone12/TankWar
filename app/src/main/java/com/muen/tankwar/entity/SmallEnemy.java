package com.muen.tankwar.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.muen.tankwar.constant.GameConstant;
import com.muen.tankwar.gameview.GameView;
import com.muen.tankwar.model.BaseModel;
import com.muen.tankwar.tool.DeviceTool;

public class SmallEnemy extends BaseModel {
	private int x;
	private int y;
	private boolean isAlive;
	private int direct ;
	private long timeCount ;
	private boolean isEnemy = true;
	public boolean isEnemy() {
		return isEnemy;
	}
	public SmallEnemy(int x,int y,int direct){
		this.x = x;
		this.y = y;
		this.isAlive =true;
		this.direct = direct;
		timeCount = 0;
		startTime = System.currentTimeMillis();
	}
	public void  drawself(Canvas canvas,Paint paint) {
		
		if(isAlive)
		{
			int [][] tmp = DeviceTool.changeArray(GameConstant.smallenemypic, direct);
			for(int i =0;i < GameConstant.tankpic.length;i++)
				for(int j = 0; j< GameConstant.tankpic[i].length; j++)
				{
					if(tmp[i][j] == 1)
					{
						canvas.drawBitmap(loadTile(GameConstant.BLUE), getDrawX(x+j),getDrawY(y+i), paint);
					}
					
				}
			
			if(timeCount%20 == 0 && timeCount>0)
			{
				switch (getDirect()) {
				case GameConstant.WEST:
					leftMove();
					break;
				case GameConstant.EAST:
					rightMove();
					break;
				case GameConstant.NORTH:
					upMove();
					break;
				case GameConstant.SOURTH:
					downMove();
					break;
				default:
					break;
				}
			}
			if(timeCount%35 == 0 && timeCount>0)
			{
				GameView.getInstance().shot(this);
			}
			if(timeCount%50 ==0 && x >=2 &&x<=GameConstant.mXTileCount-4
					&&y >=2&&y<=GameConstant.mYTileCount-4)
			{
				if(GameConstant.RNG.nextInt(2) == 1)
				{
					setDirect(GameConstant.RNG.nextInt(4)+1);
				}
			}
			
		
		}
		timeCount ++;
		 
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
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public void leftMove()
	{
		if(getX()>1 && !isCrash(GameConstant.WEST))
		{
			setX(getX() - 1);
		}else
		{
			if(GameConstant.RNG.nextInt(2)==0)
				setDirect(GameConstant.EAST);
			else
			{
				setDirect(GameConstant.SOURTH);
			}
		}
	}
	public void rightMove()
	{
		if((getX()+3)<=(GameConstant.mXTileCount -2) && !isCrash(GameConstant.EAST))
		{
			setX(getX() + 1);
		}else
		{
			if(GameConstant.RNG.nextInt(2)==0)
				setDirect(GameConstant.WEST);
			else
				setDirect(GameConstant.SOURTH);
		}
	}
	public void  upMove()
	{
		if(getY()>1&& !isCrash(GameConstant.NORTH))
		{
			setY(getY() - 1);
		}else
		{
			if(GameConstant.RNG.nextInt(2)==0)
				setDirect(GameConstant.SOURTH);
			else
				setDirect(GameConstant.WEST);
		}
	}
	public void  downMove()
	{
		if((getY()+3)<=(GameConstant.mYTileCount -2) && !isCrash(GameConstant.SOURTH))
		{
			setY(getY() + 1);
		}else
		{
			if(GameConstant.RNG.nextInt(2)==0)
				setDirect(GameConstant.NORTH);
			else
				setDirect(GameConstant.EAST);
			
		}
	}
	
}
