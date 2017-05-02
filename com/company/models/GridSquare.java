package com.company.models;

public class GridSquare {

    private Coordinate position;
    private int weight;
    private int index;
    private int distanceVal;
    private boolean isDestinationGS = false;

    public GridSquare(Coordinate position, int weight, int index) {
        this.position = position;
        this.weight = weight;
        this.index = index;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setDistanceVal(int val) {
        distanceVal = val;
    }

    public void setDestinationGS(boolean bool) {
        isDestinationGS = bool;
    }

    public boolean isDestinationGS() {
        return isDestinationGS;
    }

}
