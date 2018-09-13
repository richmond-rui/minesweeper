package com.lanlengran.minesweeper;

/**
 * @des:
 * @author: 芮勤
 * @date: 2018/9/13 11:14
 * @see {@link }
 */
public class LandMineBean {
    private int otherLandMineSize=0;
    private boolean isLandMine=false;
    private boolean isOpen=false;

    public int getOtherLandMineSize() {
        return otherLandMineSize;
    }

    public void setOtherLandMineSize(int otherLandMineSize) {
        this.otherLandMineSize = otherLandMineSize;
    }

    public boolean isLandMine() {
        return isLandMine;
    }

    public void setLandMine(boolean landMine) {
        isLandMine = landMine;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
