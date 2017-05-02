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

    private Map<GridSquare, Integer> mWeightedGridSquares = new HashMap<>();

    private ArrayList<GridSquare> mVisitedList = new ArrayList<>();
    private ArrayList<GridSquare> mUnVisitedList = new ArrayList<>();

    private ArrayList<Integer> shortestPathCost = new ArrayList<>();
    private ArrayList<Integer> secondShortestPathCost;

    public DijkstrasShortestPath() {
    }

    /**
     *
     * 1. Add starting grid square to visited list and set distance value to it's weight (1).
     *    All other grid square distance values are already set to -1.
     *
     * 2. Get it's adjacent grid squares and add them to unvisited list. Initially, we know that GS::1,1 is at index 0,0.
     *    So grid squares 0,1 and 1,0 are the only directly adjacent grid squares. We can later extend this so that it can
     *    work with any source grid square, but we know that our source in this case will always be 1,1.
     *
     * 3.
     */
    public void findShortestPath(Grid grid) {
        mGrid = grid;
        GridSquare currentSquare = grid.getGridSquare(0,0);
        currentSquare.setDistanceVal(currentSquare.getWeight());

        //TODO check if gridsquares exist. And clean this shit up
        GridSquare adjGS1 = grid.getGridSquare(0, 1);
        mUnVisitedList.add(adjGS1);

        GridSquare adjGS2 = grid.getGridSquare(1, 0);
        mUnVisitedList.add(adjGS2);

        GridSquare lowestAdjGS = null;
        if (adjGS1.getWeight() <= adjGS2.getWeight()) {
            lowestAdjGS = adjGS1;
        } else {
            lowestAdjGS = adjGS2;
        }


        while (!mUnVisitedList.isEmpty()) {
            //TODO Heap sort for lowest weight access
            mVisitedList.add(currentSquare);

            currentSquare = lowestAdjGS;
            //I'm stuck after this
            addAdjSquares(currentSquare);

         }

    }

    private void addAdjSquares(GridSquare currentSquare) {
        //get corresponding index for the nested array
        int xVal = currentSquare.getPosition().getX() - 1;
        int yVal = currentSquare.getPosition().getY() - 1;

        GridSquare gridSquareTop = mGrid.getGridSquare(xVal + 1, yVal);
        GridSquare gridSquareRight = mGrid.getGridSquare(xVal, yVal + 1);
        GridSquare gridSquareBottom = null;
        GridSquare gridSquareLeft = null;

        if (xVal != 0) {
            gridSquareBottom = mGrid.getGridSquare(xVal - 1, yVal);
        }
        if (yVal != 0) {
            gridSquareLeft = mGrid.getGridSquare(xVal, yVal - 1);
        }

        if (gridSquareTop != null && !mUnVisitedList.contains(gridSquareTop)) {
            mUnVisitedList.add(gridSquareTop);
        }
        if (gridSquareRight != null && !mUnVisitedList.contains(gridSquareRight)) {
            mUnVisitedList.add(gridSquareRight);
        }
        if (gridSquareBottom != null && !mUnVisitedList.contains(gridSquareBottom)) {
            mUnVisitedList.add(gridSquareBottom);
        }
        if (gridSquareLeft != null && !mUnVisitedList.contains(gridSquareLeft)) {
            mUnVisitedList.add(gridSquareLeft);
        }


    }



}
