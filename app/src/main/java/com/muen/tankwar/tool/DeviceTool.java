package com.muen.tankwar.tool;


import com.muen.tankwar.constant.GameConstant;

public class DeviceTool {
	/**
	 *   0,1,0         0,1,1     1,1,0      1 0 1
	 *   1,1,1    ---> 1,1,0 --->0,1,1   -->1 1 1
	 *   1,0,1         0,1,1     1,1,0      0 1 0
	 *  north        west          east 
	 * @param array
	 * @param direct
	 */
	public static int[][]  changeArray(int[][] array ,int direct)
	{
		int [][] tmp = new int[array.length][array.length];
		if(direct == GameConstant.WEST)
		{
			for(int i =0;i<array.length;i++)
				for(int j =0;j<array.length;j++)
				{
					tmp[j][i] = array[i][j];
					
				}
		}
			if(direct ==GameConstant.EAST)
		{
			for(int i =0;i<array.length;i++)
				for(int j =0;j<array.length;j++)
				{
					int index =array.length - i-1;
					tmp[j][index] = array[i][j];
					
				}
		}
		if(direct ==GameConstant.SOURTH)
		{
			for(int i =0;i<array.length;i++)
				for(int j =0;j<array.length;j++)
				{
					int index =array.length - i-1;
					tmp[index][j] = array[i][j];
					
				}
		}
		if(direct == GameConstant.NORTH)
			return array;
		return tmp;
	}
}
 