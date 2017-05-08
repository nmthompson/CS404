package com.company;

import com.company.models.Grid;
import com.company.models.GridSquare;

import java.util.ArrayList;
import java.util.PriorityQueue;

class DijkstrasShortestPath {

    private Grid mGrid;

    private ArrayList<GridSquare> mUnVisitedList;

    private ArrayList<GridSquare> mAdjGridSquareList = new ArrayList<>();

    private ArrayList<ArrayList<GridSquare>> pathList = new ArrayList<>();

    private PriorityQueue<GridSquare> minHeapGS = new PriorityQueue<>();

    DijkstrasShortestPath() {
    }

    /**
     *
     * 1. Add all grid squares to unvistited list
     * 2. Set distance value of pathCost to 0;
     * 3. While unvisited list is not empty
     *    a. currentGS <- gridsquare in unvisitedList with minimum weight
     *    b. Remove currentGS from unvisitedList
     *    c. For each adjacentGS of currentGS
     *       I. altRoute <- distance value of currentGS + weight of adjGS
     *       II. if altRoute is less than distance value of adjGS (or -1)
     *           distanceValue[] of adjGS <- altRoute
     *           PrevGS[] of adjGS <- currentGS
     */
    void findShortestPath(Grid grid) {
        mGrid = grid;
        mUnVisitedList = new ArrayList<>(grid.getGridSquareList());

        minHeapGS.addAll(mUnVisitedList);

        runDijkstrasAlgorithm();

        findSecondShortestPath();
    }

    private void runDijkstrasAlgorithm() {
        GridSquare currentSquare = null;
        mGrid.getGridSquare(0,0).setDistanceVal(1);

        while (!minHeapGS.isEmpty()) {
            currentSquare = minHeapGS.peek();
            mUnVisitedList.remove(currentSquare);

            minHeapGS.clear();
            minHeapGS.addAll(mUnVisitedList);

            addAdjSquares(currentSquare);

            for (GridSquare adjSquare : mAdjGridSquareList) {
                int altRoute = currentSquare.getDistanceVal() + adjSquare.getWeight();
                if (adjSquare.getDistanceVal() == -1 || altRoute < adjSquare.getDistanceVal()) {
                    adjSquare.setDistanceVal(altRoute);

                    ArrayList<GridSquare> currentList = new ArrayList<>(currentSquare.getFirstShortestPath());
                    currentList.add(currentSquare);
                    adjSquare.addGSToFirstShortestPath(currentList);

                } else if (adjSquare.getDistanceVal() == -1 || altRoute > adjSquare.getDistanceVal() && (adjSquare.getSecondDistanceVal() == 0 || altRoute < adjSquare.getSecondDistanceVal())) {
                    adjSquare.setSecondDistanceVal(altRoute);

                    ArrayList<GridSquare> secondList = new ArrayList<>(currentSquare.getSecondShortestPath());
                    secondList.add(currentSquare);
                    adjSquare.addGSToSecondShortestPath(secondList);
                }
            }
        }
        System.out.println("Grid: " + mGrid.getDestinationGridSquare().getPosition().getX() + "," + mGrid.getDestinationGridSquare().getPosition().getY() + "/n DV= " + mGrid.getDestinationGridSquare().getDistanceVal());
    }

    private void addAdjSquares(GridSquare mCurrentSquare) {
        mAdjGridSquareList.clear();
        //get corresponding index for the nested array
        int xVal = mCurrentSquare.getPosition().getX() - 1;
        int yVal = mCurrentSquare.getPosition().getY() - 1;

        GridSquare gridSquareTop = null;
        GridSquare gridSquareRight = null;
        GridSquare gridSquareBottom = null;
        GridSquare gridSquareLeft = null;

        if (xVal < mGrid.getGridXParam() - 1) {
            gridSquareTop = mGrid.getGridSquare(xVal + 1, yVal);
        }
        if (yVal < mGrid.getGridYParam() - 1) {
            gridSquareRight = mGrid.getGridSquare(xVal, yVal + 1);
        }
        if (xVal != 0) {
            gridSquareBottom = mGrid.getGridSquare(xVal - 1, yVal);
        }
        if (yVal != 0) {
            gridSquareLeft = mGrid.getGridSquare(xVal, yVal - 1);
        }

        if (gridSquareTop != null && minHeapGS.contains(gridSquareTop)) {
            mAdjGridSquareList.add(gridSquareTop);
        }
        if (gridSquareRight != null && minHeapGS.contains(gridSquareRight)) {
            mAdjGridSquareList.add(gridSquareRight);
        }
        if (gridSquareBottom != null && minHeapGS.contains(gridSquareBottom)) {
            mAdjGridSquareList.add(gridSquareBottom);
        }
        if (gridSquareLeft != null && minHeapGS.contains(gridSquareLeft)) {
            mAdjGridSquareList.add(gridSquareLeft);
        }

    }

    public void findSecondShortestPath() {
        ArrayList<GridSquare> shortestPath =  new ArrayList<>(mGrid.getDestinationGridSquare().getFirstShortestPath());
        int pathCost = 0;

        for (GridSquare gridSquare : shortestPath) {
            gridSquare.setDistanceVal(-1);
            mUnVisitedList = new ArrayList<>(mGrid.getGridSquareList());
            minHeapGS.addAll(mUnVisitedList);

            runDijkstrasAlgorithm();
            System.out.println(mGrid.getDestinationGridSquare().getDistanceVal());

        }
    }
}
