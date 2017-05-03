package com.company.models;

import java.util.ArrayList;
import java.util.Comparator;

public class Grid {

    private final int gridSizeX;
    private final int gridSizeY;
    private GridSquare[][] gridSquares;

    private GridSquare mStartGrid, mDestinationGridSquare;
    private ArrayList<GridSquare> mGridList = new ArrayList<>();

    public Grid (ArrayList<String> inputFileList) {
        //TODO put logic to find end gridsquare into Main
        String destination[] = inputFileList.get(0).split(",");
        Coordinate endCoordinate = new Coordinate(Integer.parseInt(destination[0].trim()), Integer.parseInt(destination[1].trim()));
        inputFileList.remove(0);

        //TODO write sort
//        inputFileList.sort(Comparator.comparingInt(String::hashCode));

        String finalGridValues[] = inputFileList.get(inputFileList.size() - 1).split(",");
        gridSizeX = Integer.parseInt(finalGridValues[0].trim());
        gridSizeY = Integer.parseInt(finalGridValues[1].trim());

        gridSquares = new GridSquare[gridSizeX][gridSizeY];

        int i = 0;
        for(String line : inputFileList) {
            String values[] = line.split(",");
            int xVal = Integer.parseInt(values[0].trim());
            int yVal = Integer.parseInt(values[1].trim());

            Coordinate coordinate = new Coordinate(xVal, yVal);
            int weight = Integer.parseInt(values[2].trim());
            GridSquare currentGrid = new GridSquare(coordinate, weight, i);
            currentGrid.setDistanceVal(-1);
            if (coordinate == endCoordinate) {
                mDestinationGridSquare = currentGrid;
            }
            mGridList.add(currentGrid);
            gridSquares[xVal - 1][yVal - 1] = currentGrid;
            i++;
        }

    }

    public int getGridXParam() {
        return gridSizeX;
    }
    public int getGridYParam() {
        return gridSizeY;
    }

    public ArrayList<GridSquare> getGridSquareList() {
        return mGridList;
    }

    public GridSquare getDestinationGridSquare() {
        return mDestinationGridSquare;
    }

    public GridSquare getGridSquare(int x, int y) {
        return gridSquares[x][y];
    }
}
