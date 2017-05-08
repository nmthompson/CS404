package com.company.models;

import java.util.ArrayList;
import java.util.Comparator;

public class GridSquare implements Comparable<GridSquare> {

    private Coordinate position;
    private int weight;
    private int index;
    private int distanceVal;
    private int secondDistanceVal;
    private GridSquare prevGS;
    private ArrayList<GridSquare> firstShortestGridPath = new ArrayList<>();
    private ArrayList<GridSquare> secondShortestGridPath = new ArrayList<>();

    private boolean isDestinationGS = false;

    GridSquare(Coordinate position, int weight, int index) {
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

    public void setSecondDistanceVal(int val) {
        secondDistanceVal = val;
    }

    public int getSecondDistanceVal() {
        return secondDistanceVal;
    }

    public void setDistanceVal(int val) {
        distanceVal = val;
    }

    public int getDistanceVal() {
        return distanceVal;
    }

    void setDestinationGS() {
        isDestinationGS = true;
    }

    public boolean isDestinationGS() {
        return isDestinationGS;
    }

    public void addGSToFirstShortestPath(ArrayList<GridSquare> gridSquare) {
        firstShortestGridPath = gridSquare;
    }

    public ArrayList<GridSquare> getFirstShortestPath() {
        return firstShortestGridPath;
    }

    public void addGSToSecondShortestPath(ArrayList<GridSquare> gridSquare) {
        secondShortestGridPath = gridSquare;
    }

    public ArrayList<GridSquare> getSecondShortestPath() {
        return secondShortestGridPath;
    }

    @Override
    public int compareTo(GridSquare o) {
//        if (this.getDistanceVal() < o.getDistanceVal() && this.getDistanceVal() != -1) {
//            return -1;
//        } else if (this.getDistanceVal() > o.getDistanceVal() && o.getDistanceVal() != -1) {
//            return 1;
//        }
//        return 0;
        if ((this.getDistanceVal() == -1 && o.getDistanceVal() != -1) || (this.getDistanceVal() > o.getDistanceVal())) {
            return 1;
        } else if ((this.getDistanceVal() != -1 && o.getDistanceVal() == -1) || (this.getDistanceVal() < o.getDistanceVal())) {
            return -1;
        }
        return 0;
    }
}
