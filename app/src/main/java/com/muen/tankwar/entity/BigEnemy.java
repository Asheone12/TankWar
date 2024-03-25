package com.muen.tankwar.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.muen.tankwar.constant.GameConstant;
import com.muen.tankwar.gameview.GameView;
import com.muen.tankwar.model.BaseModel;

public class BigEnemy extends BaseModel {

    private int x;
    private int y;
    private boolean isAlive;
    private boolean isEnemy = true;
    private long timeCount = 0;

    public boolean isEnemy() {
        return isEnemy;
    }

    public BigEnemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
    }

    public void drawself(Canvas canvas, Paint paint) {

        if (isAlive) {
            for (int i = 0; i < GameConstant.bigenemypic.length; i++)
                for (int j = 0; j < GameConstant.bigenemypic[i].length; j++) {
                    if (GameConstant.bigenemypic[i][j] == 1) {
                        canvas.drawBitmap(loadTile(GameConstant.BLUE), getDrawX(x + j), getDrawY(y + i), paint);
                    }
                }
            if (timeCount % 40 == 0) {
                if (GameConstant.RNG.nextInt(2) == 1) {
                    leftMove();
                } else
                    rightMove();


            }
            if (timeCount % 55 == 0) {
                GameView.getInstance().shot(this);
            }
            timeCount++;
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void leftMove() {
        if (getX() > 1)
            setX(getX() - 1);
    }

    public void rightMove() {
        if (getX() + 6 < GameConstant.mXTileCount - 2) {
            setX(getX() + 1);
        }


    }
}
