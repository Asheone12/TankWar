package com.muen.tankwar.entity;

import static com.muen.tankwar.entity.Bullet.gapCount;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.muen.tankwar.constant.GameConstant;
import com.muen.tankwar.model.BaseModel;
import com.muen.tankwar.tool.DeviceTool;

public class Tank extends BaseModel {
	private int x;
	private int detaCountX = 0;
	private int y;
	private int detaCountY = 0;
	private boolean isAlive;
	private int direct ;
	private boolean isEnemy = false;
	public boolean isEnemy() {
		return isEnemy;
	}
	public Tank(int x,int y){
		this.x = x;
		this.y = y;
		this.isAlive =true;
		this.direct = GameConstant.NORTH;
		startTime = System.currentTimeMillis();
	}
	public void  drawself(Canvas canvas,Paint paint) {
		
		
		if(isAlive)
		{
			if (!isEnemy)
				Log.i("lsm3"," drawself detaCountX = " + detaCountX +  " x = "+ x+ " detaCountY = " + detaCountY + " y = "+y);
			int [][] tmp = DeviceTool.changeArray(GameConstant.tankpic, direct);
			for(int i =0;i < GameConstant.tankpic.length;i++)
				for(int j =0;j< GameConstant.tankpic[i].length;j++)
				{
					if(tmp[i][j] == 1)
					{
						canvas.drawBitmap(loadTile(GameConstant.RED), getDrawX(x+j),getDrawY(y+i), paint);
					}
					
				}
			if (detaCountX < 0) {
				leftMove();
			} else if (detaCountX > 0) {
				rightMove();
			}
			if (detaCountY < 0) {
				upMove();
			} else if (detaCountY > 0) {
				downMove();
			}
		}
		 
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
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public void leftMove()
	{
		if(getX()>1 && !isCrash(getDirect()))
		{
			detaCountX--;
			if (Math.abs(detaCountX) == gapCount + 1) {
				setX(getX() - 1);
				detaCountX = 0;
			}
		}
	}
	public void rightMove()
	{
		if((getX()+3)<=(GameConstant.mXTileCount -2) && !isCrash(getDirect()))
		{
			detaCountX++;
			if (Math.abs(detaCountX) == gapCount + 1) {
				setX(getX() + 1);
				detaCountX = 0;
			}
		}
	}
	public void  upMove()
	{
		if(getY()>1 && !isCrash(getDirect()))
		{
			detaCountY--;
			if (Math.abs(detaCountY) == gapCount + 1) {
				setY(getY() - 1);
				detaCountY = 0;
			}
		}
	}
	public void  downMove()
	{
		if((getY()+3)<=(GameConstant.mYTileCount -2) && !isCrash(getDirect()))
		{
			detaCountY++;
			if (Math.abs(detaCountY) == gapCount + 1) {
				setY(getY() + 1);
				detaCountY = 0;
			}
		}
	}


}
