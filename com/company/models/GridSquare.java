package com.company.models;

import java.util.ArrayList;

public class GridSquare {

    private Coordinate position;
    private int weight;
    private int index;
    private int distanceVal;
    private GridSquare prevGS;
    private ArrayList<GridSquare> firstShortestGridPath = new ArrayList<>();
    private ArrayList<GridSquare> secondShortestGridPath = new ArrayList<>();

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

    public int getDistanceVal() {
        return distanceVal;
    }

    public void setDestinationGS(boolean bool) {
        isDestinationGS = bool;
    }

    public boolean isDestinationGS() {
        return isDestinationGS;
    }

//    public GridSquare getPrevGS() {
//        return prevGS;
//    }
//
//    public void setPrevGS(GridSquare prevGS) {
//        this.prevGS = prevGS;
//    }


    public void addGSToFirstShortestPath(ArrayList<GridSquare> gridSquare) {
        firstShortestGridPath = gridSquare;
    }

    public ArrayList<GridSquare> getFirstShortestPath() {
        return firstShortestGridPath;
    }

}
