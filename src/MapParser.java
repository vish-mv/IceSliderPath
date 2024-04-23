/*Author : Visal Vitharana
  UoW ID : w1954048
  IIT ID : 20221302*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapParser {
    private char[][] map;
    private int rows;
    private int cols;

    public MapParser(String filename) {
        loadMap(filename);
    }

    private void loadMap(String filename) {
        List<char[]> tempMap = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                tempMap.add(line.toCharArray());
            }
        } catch (IOException e) {
            System.out.println("Invalid File Name");
        }

        rows = tempMap.size();
        cols = tempMap.get(0).length;
        map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            map[i] = tempMap.get(i);
        }
    }

    public char[][] getMap() {
        return map;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}