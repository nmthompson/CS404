package com.company;

import com.company.models.Grid;
import com.company.models.GridSquare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//In order to apply dijkstra's algorithm in a grid there is no need for any modifications, since a grid is a graph in
// which a node (cell) has 4/8 children (depending on your connectivity) which are the neighbors. Therefore, all you
// have to do is: choose your root node (where to start), assign it value 0 and then evaluate the 4/8 neighbors, using
// as cost just 1 (or sqrt(2) if for the 4 diagonal neighbors if you are using 8-connectivity). This root node has to be
// labeled as visited and the neighbors evaluated are labeled as open. Then, you pick the node(cell) in evaluated that has
// a minimum value (in this case, all of them will have value 1) so you choose any of them. When adding the neighbors to
// the open list, it will happen that some of these neigbors are already visited, so you just ignore them. If the are
// already in the open list, you recompute their value and if they are unvisited, you compute their value and add them
// to open, and mark the current node as closed.
//
//        In fact, you will see that there is no difference at all with the generic Dijkstra's algorithm you have been reading for graphs.
//
//        NOTE: in order to be efficient when getting the minimum of the open list, it is recommended to use a heap
// (usually a binary heap) instead of running the min function along all the open nodes at every iteration.

public class DijkstrasShortestPath {

    private Grid mGrid;

    private GridSquare mCurrentSquare;

    private Map<GridSquare, Integer> mWeightedGridSquares = new HashMap<>();

    private ArrayList<GridSquare> mVisitedList = new ArrayList<>();
    private ArrayList<GridSquare> mUnVisitedList = new ArrayList<>();

    private ArrayList<Integer> shortestPathCost = new ArrayList<>();
    private ArrayList<Integer> secondShortestPathCost;
    private ArrayList<GridSquare> mAdjGridSquareList;

    public DijkstrasShortestPath() {
    }

    /**
     *
     * 1. Add all grid squares to unvistited list
     * 2. Set distance value of pathCost to 0;
     * 3. While unvisited list is not empty
     *    a. currentGS <- gridsquare in unvisitedList with minimum weight from currentGS
     *    b. Remove currentGS from unvisitedList
     *    c. For each adjacentGS of currentGS
     *       I. altRoute <- distance value of currentGS + weight of adjGS
     *       II. if altRoute is less than distance value of adjGS (or -1)
     *           distanceValue[] of adjGS <- altRoute
     *           PrevGS[] of adjGS <- currentGS
     */
    public void findShortestPath(Grid grid) {
        mGrid = grid;
//      1. Add all grid squares to unvistited list
        mUnVisitedList = grid.getGridSquareList();

//      2. Set distance value of pathCost to 0;
        shortestPathCost.clear();

//        //TODO check if gridsquares exist. And clean this shit up

        mCurrentSquare =  grid.getGridSquare(0,0);
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
                adjSquare.setPrevGS(mCurrentSquare);
            }
        }

//      3. While unvisited list is not empty
        while (!mUnVisitedList.isEmpty()) {
//          a. currentGS <- gridsquare in unvisitedList with minimum weight from currentGS
//          b. Remove currentGS from unvisitedList
            mCurrentSquare = getLowestGSFromCurrent(mCurrentSquare);
            if (mCurrentSquare == null) {
                System.out.println("It's null");
                //Ughhh why is it null at like when mUnvisitedList is down to 20
            }
            mUnVisitedList.remove(mCurrentSquare);
            System.out.println("CurrentSquare = " + mCurrentSquare.getPosition().getX() + "," + mCurrentSquare.getPosition().getY());

            addAdjSquares(mCurrentSquare);
//            c. For each adjacentGS of currentGS
//               I. altRoute <- distance value of currentGS + weight of adjGS
//              II. if altRoute is less than distance value of adjGS (or -1)
//                     distanceValue[] of adjGS <- altRoute
//                     PrevGS[] of adjGS <- currentGS
            for (GridSquare adjSquare : mAdjGridSquareList) {
                int altRoute = mCurrentSquare.getDistanceVal() + adjSquare.getWeight();
                if (adjSquare.getDistanceVal() == -1 || altRoute < adjSquare.getDistanceVal()) {
                    adjSquare.setDistanceVal(altRoute);
                    System.out.println("AdjSquare = " + adjSquare.getPosition().getX() + "," + adjSquare.getPosition().getY() + " distance value = " + adjSquare.getDistanceVal());
                    adjSquare.setPrevGS(mCurrentSquare);
                }
            }


            //TODO Heap sort for lowest weight access


         }

    }

    private GridSquare getLowestGSFromCurrent(GridSquare mCurrentSquare) {
        GridSquare lowestSquare = null;
        addAdjSquares(mCurrentSquare);
        for (GridSquare gridSquare : mAdjGridSquareList) {
            if (lowestSquare == null) {
                lowestSquare = gridSquare;
            }
            if (gridSquare.getWeight() < lowestSquare.getWeight()) {
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
