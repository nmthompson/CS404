package com.company;

import com.company.models.Coordinate;
import com.company.models.Grid;
import com.company.models.GridSquare;

import java.io.*;
import java.util.ArrayList;

public class Main {

    //input/read file
    //parse file
    //create grid
    //populate grid
    //traverse grid using djikstra
    //???
    //profit

    public static void main(String[] args) {
//        parseCoordFile(new File("~/src/coordinates.txt"));

        Grid grid = new Grid(parseFile(new File("src/input/CS404SP16RewardMatrixInput1.txt")));
        DijkstrasShortestPath dijkstra = new DijkstrasShortestPath();
        dijkstra.findShortestPath(grid);

    }

    private static ArrayList<String> parseFile(File file) {
        ArrayList<String> inputValues = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    inputValues.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        //blah blah logic to parse file that i'll do later
        return inputValues;
    }
}
