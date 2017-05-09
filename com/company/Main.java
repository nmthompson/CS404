package com.company;

import com.company.models.Grid;

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
        ArrayList<String> inputs = parseFile(new File("src/input/CS404SP16Test.txt"));
        Grid grid = new Grid(inputs);
        DijkstrasShortestPath dijkstra = new DijkstrasShortestPath();
        dijkstra.findDijkShortestPath(grid);
        grid = new Grid(parseFile(new File("src/input/CS404SP16Test.txt")));
        DynamicShortestPath dynamic = new DynamicShortestPath();
        dynamic.findDPShortestPath(grid, 3, 3);

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
        return inputValues;
    }
}
