package com.company;

import com.company.models.Grid;
import com.company.models.GridSquare;

import java.util.ArrayList;

public class DijkstrasShortestPath {

    private Grid mGrid;

    private ArrayList<GridSquare> mVisitedList = new ArrayList<>();
    private ArrayList<GridSquare> mUnVisitedList = new ArrayList<>();

    private ArrayList<GridSquare> mAdjGridSquareList;

    public DijkstrasShortestPath() {
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
    public void findShortestPath(Grid grid) {
        mGrid = grid;
        mUnVisitedList = grid.getGridSquareList();

//        //TODO check if gridsquares exist. And clean this shit up

        GridSquare mCurrentSquare = grid.getGridSquare(0, 0);
        mCurrentSquare.setDistanceVal(mCurrentSquare.getWeight());
        mUnVisitedList.remove(mCurrentSquare);


        //Basically doing the same thing as our while/for algorithm below, but we know the source grid square and it's exact location
        //so I just resolve it now for simplicities sake and then go forward with the rest of the algorithm. Will condense later
        GridSquare adjGS1 = grid.getGridSquare(0, 1);
        GridSquare adjGS2 = grid.getGridSquare(1, 0);
        GridSquare lowestAdjGS = null;
        mAdjGridSquareList = new ArrayList<>();
        mAdjGridSquareList.add(adjGS1);
        mAdjGridSquareList.add(adjGS2);

        if (adjGS1.getWeight() <= adjGS2.getWeight()) {
            lowestAdjGS = adjGS1;
            mAdjGridSquareList.add(adjGS1);
            mAdjGridSquareList.add(adjGS2);
        } else {
            lowestAdjGS = adjGS2;
            mAdjGridSquareList.add(adjGS2);
            mAdjGridSquareList.add(adjGS1);
        }

        for (GridSquare adjSquare : mAdjGridSquareList) {
            int altRoute = mCurrentSquare.getWeight() + adjSquare.getWeight();
            if (adjSquare.getDistanceVal() == -1 || altRoute < adjSquare.getDistanceVal()) {
                adjSquare.setDistanceVal(altRoute);
//                adjSquare.setPrevGS(mCurrentSquare);
//                adjSquare.addGSToFirstShortestPath(mCurrentSquare);
            }
        }

        mVisitedList.add(mCurrentSquare);

        //Actual Algorithm mostly
        while (!mUnVisitedList.isEmpty()) {

            mCurrentSquare = getLowestGSFromCurrent();
            mUnVisitedList.remove(mCurrentSquare);
//            System.out.println("CurrentSquare = " + mCurrentSquare.getPosition().getX() + "," + mCurrentSquare.getPosition().getY());

            addAdjSquares(mCurrentSquare);


            for (GridSquare adjSquare : mAdjGridSquareList) {
                int altRoute = mCurrentSquare.getDistanceVal() + adjSquare.getWeight();
                if (adjSquare.getDistanceVal() == -1 || altRoute < adjSquare.getDistanceVal()) {
                    adjSquare.setDistanceVal(altRoute);
                    System.out.println("AdjSquare = " + adjSquare.getPosition().getX() + "," + adjSquare.getPosition().getY() + " distance value = " + adjSquare.getDistanceVal());


                    ArrayList<GridSquare> currentList = new ArrayList<>(mCurrentSquare.getFirstShortestPath());
                    currentList.add(mCurrentSquare);
                    adjSquare.addGSToFirstShortestPath(currentList);

                }
            }

            //TODO Heap sort for lowest weight access


         }
        for (GridSquare gs : mGrid.getDestinationGridSquare().getFirstShortestPath()) {
            System.out.println(gs.getPosition().getX() + "," + gs.getPosition().getY());
        }

    }

    private GridSquare getLowestGSFromCurrent() {
        GridSquare lowestSquare = null;
        for (GridSquare gridSquare : mUnVisitedList) {
            if (lowestSquare == null) {
                lowestSquare = gridSquare;
            }
            if (gridSquare.getDistanceVal() != -1 && gridSquare.getDistanceVal() < lowestSquare.getDistanceVal()) {
                lowestSquare = gridSquare;
            }
        }
        return lowestSquare;
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

        if (gridSquareTop != null && mUnVisitedList.contains(gridSquareTop)) {
            mAdjGridSquareList.add(gridSquareTop);
        }
        if (gridSquareRight != null && mUnVisitedList.contains(gridSquareRight)) {
            mAdjGridSquareList.add(gridSquareRight);
        }
        if (gridSquareBottom != null && mUnVisitedList.contains(gridSquareBottom)) {
            mAdjGridSquareList.add(gridSquareBottom);
        }
        if (gridSquareLeft != null && mUnVisitedList.contains(gridSquareLeft)) {
            mAdjGridSquareList.add(gridSquareLeft);
        }


    }



}
