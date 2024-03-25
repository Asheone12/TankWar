package com.muen.tankwar.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.muen.tankwar.constant.GameConstant;
import com.muen.tankwar.model.BaseModel;

public class Wall extends BaseModel {
	private boolean isAlive ;
	public Wall(){
		
		this.isAlive =true;
	}
	public void  drawself(Canvas canvas,Paint paint) {
		
		if(isAlive)
		{
			  for (int x = 0; x < GameConstant.mXTileCount; x++) {
				  canvas.drawBitmap(loadTile(GameConstant.GREEN), getDrawX(x),getDrawY(0), paint);
				  canvas.drawBitmap(loadTile(GameConstant.GREEN), getDrawX(x),getDrawY(GameConstant.mYTileCount - 1), paint);
				  
		        }
		        for (int y = 1; y < GameConstant.mYTileCount - 1; y++) {
		        	 canvas.drawBitmap(loadTile(GameConstant.GREEN), getDrawX(0),getDrawY(y), paint);
					 canvas.drawBitmap(loadTile(GameConstant.GREEN), getDrawX(GameConstant.mXTileCount -1),getDrawY(y), paint);
				}
		}
		 
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
}
