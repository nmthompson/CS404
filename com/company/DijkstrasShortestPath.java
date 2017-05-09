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

    private GridSquare mDestinationGridSquare;

    private boolean bool = false;

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
    void findDijkShortestPath(Grid grid) {
        mGrid = grid;
        mDestinationGridSquare = mGrid.getDestinationGridSquare();

        mUnVisitedList = new ArrayList<>(grid.getGridSquareList());

        minHeapGS.addAll(mUnVisitedList);

        runDijkstrasAlgorithm(null, null);

        findSecondShortestPath();
    }

    private void runDijkstrasAlgorithm(GridSquare n, GridSquare m) {
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
                if (adjSquare.equals(mDestinationGridSquare)) {
                    System.out.println();
                }
                if (currentSquare.equals(mGrid.getGridSquare(3,2))) {
                    System.out.println();
                }
                if (!bool && (adjSquare.getDistanceVal() == -1 || altRoute < adjSquare.getDistanceVal())) {
                    adjSquare.setDistanceVal(altRoute);

                    ArrayList<GridSquare> currentList = new ArrayList<>(currentSquare.getFirstShortestPath());
                    currentList.add(currentSquare);
                    adjSquare.addGSToFirstShortestPath(currentList);

                } else if (bool && altRoute > adjSquare.getDistanceVal()
                        && (altRoute < adjSquare.getSecondDistanceVal() || adjSquare.getSecondDistanceVal() == 0)) {
                    adjSquare.setSecondDistanceVal(altRoute);

                    ArrayList<GridSquare> currentList = new ArrayList<>(currentSquare.getSecondShortestPath());
                    currentList.add(currentSquare);
                    adjSquare.addGSToSecondShortestPath(currentList);
                }
            }
            System.out.println("Grid: " + mDestinationGridSquare.getPosition().getX() + "," + mDestinationGridSquare.getPosition().getY() + "/n DV= " + mDestinationGridSquare.getDistanceVal());

        }
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

        if (gridSquareTop != null && (minHeapGS.contains(gridSquareTop) || gridSquareTop.equals(mDestinationGridSquare))) {
            mAdjGridSquareList.add(gridSquareTop);
        }
        if (gridSquareRight != null && (minHeapGS.contains(gridSquareRight) || gridSquareRight.equals(mDestinationGridSquare))) {
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
        ArrayList<GridSquare> shortestPath =  new ArrayList<>(mDestinationGridSquare.getFirstShortestPath());
        int pathCost = 0;
        bool = true;
        for (int i = 0; i < shortestPath.size(); i++) {
            shortestPath.get(i).setDistanceVal(-1);

            mUnVisitedList = new ArrayList<>(mGrid.getGridSquareList());
            minHeapGS.addAll(mUnVisitedList);

            runDijkstrasAlgorithm(null, null);

            System.out.println(mDestinationGridSquare.getSecondDistanceVal());

        }
        System.out.println();
    }

}
