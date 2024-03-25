package com.muen.tankwar.constant;

import java.util.Random;

import android.graphics.drawable.Drawable;


public class GameConstant {
	public static  int BeShotTimes = 5;
	public static final Random RNG = new Random();
	public static final int  ButtonSize = 80;
	public static int mTileSize = 24;
	//自己坦克地图数组格式3*3
	public static int[][] tankpic ={
			{0,1,0},
			{1,1,1},
			{1,0,1}
			};
	//敌方坦克地图3*3
	public static int[][] smallenemypic ={
		{0,1,0},
		{1,1,1},
		{1,1,1}
		};
	//主敌方坦克地图7*7
	public static int[][] bigenemypic ={
		{1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1},
		{1,1,0,1,0,1,1},
		{1,1,0,1,0,1,1}
		};
	public static Drawable RED ;
	public static Drawable GREEN ;
	public static Drawable BLUE ;
	public static Drawable BULLET;
	public static Drawable TRANPRENT;
	public static int mXOffset;
	public static int mYOffset;
	public static int mXTileCount;
	public static int mYTileCount;
	public static final int NORTH =1;
	public static final int  WEST =2;
	public static final int  EAST =3;
	public static final int SOURTH =4;
}
