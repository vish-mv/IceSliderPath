
import java.util.List;
import java.util.Scanner;

public class IceSlider {
    public static void main(String[] args) {
        System.out.println("----- Welcome to Ice Slider Path Finder -----");
        System.out.println("\nIf you are using your own map Make sure to Move it to the 'maps' folder\n");
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your map name (eg: maze10_1) Or Press 'Enter' to use default map: ");
            String filename=scanner.nextLine();
            if (filename.isEmpty()){
                filename="maze25_1.txt";
            }
            if (!filename.endsWith(".txt")) {
                filename += ".txt";
            }
            System.out.println("\nShortest Path for : "+filename+"\n");
            String pathname = "maps/"+filename;
            MapParser mapParser = new MapParser(pathname);
            char[][] map = mapParser.getMap();
            int rows = mapParser.getRows();
            int cols = mapParser.getCols();

            int startX = -1, startY = -1, finishX = -1, finishY = -1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (map[i][j] == 'S') {
                        startX = i;
                        startY = j;
                    } else if (map[i][j] == 'F') {
                        finishX = i;
                        finishY = j;
                    }
                }
            }
            long startTime = System.nanoTime();

            ShortestPath shortestPath = new ShortestPath();
            List<int[]> path = shortestPath.findShortestPath(map, startX, startY, finishX, finishY);
            long endTime = System.nanoTime();

            if (!path.isEmpty()) {
                formatAndPrintPath(path);
            } else {
                System.out.println("No path found.");
            }
            double durationMs = (endTime - startTime) / 1e6; // Convert nanoseconds to milliseconds
            System.out.println("\nExecution time: " + durationMs + " ms");
        }catch (Exception e){
            System.out.println("Error Occurred Please Try Again");
        }
    }

    private static void formatAndPrintPath(List<int[]> path) {
        System.out.println("1. Start at (" + (path.get(0)[1] + 1) + "," + (path.get(0)[0] + 1) + ")");
        int stepCounter = 2; // Start counting steps from 2 since the start is step 1

        for (int i = 1; i < path.size(); i++) {
            int[] start = path.get(i - 1);
            int[] end = path.get(i);

            // Adjusting for 1-based index in output
            if (end[1] > start[1]) { // Moving right
                System.out.println(stepCounter + ". Move right to (" + (end[1] + 1) + "," + (end[0] + 1) + ")");
            } else if (end[1] < start[1]) { // Moving left
                System.out.println(stepCounter + ". Move left to (" + (end[1] + 1) + "," + (end[0] + 1) + ")");
            } else if (end[0] > start[0]) { // Moving down
                System.out.println(stepCounter + ". Move down to (" + (end[1] + 1) + "," + (end[0] + 1) + ")");
            } else if (end[0] < start[0]) { // Moving up
                System.out.println(stepCounter + ". Move up to (" + (end[1] + 1) + "," + (end[0] + 1) + ")");
            }
            stepCounter++;
        }
        System.out.println(stepCounter + ". Done");
    }
}
