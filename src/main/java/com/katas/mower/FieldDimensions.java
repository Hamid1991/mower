package com.katas.mower;

public class FieldDimensions {
    
    private int xDimension;
    
    private int yDimension;

    public int getXDimension() {
        return xDimension;
    }

    public void setXDimension(int xDimension) {
        this.xDimension = xDimension;
    }

    public int getYDimension() {
        return yDimension;
    }

    public void setYDimension(int yDimension) {
        this.yDimension = yDimension;
    }

    public FieldDimensions(int xDimension, int yDimension) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
    }
}
