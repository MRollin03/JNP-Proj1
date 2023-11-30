package MainFolder;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import itumulator.world.Location;

public class scan {
    private Map<String, Integer> dataMap = new HashMap<>();
    private HashMap<Integer, Integer> wolfPacks = new HashMap<>();
    private List<BearEntry> bears = new ArrayList<>();
    private int size;
    private int rabbit;
    private int burrow;
    private int grass;
    private int packCounter = 1;

    public static class BearEntry {
        private Location location;

        public BearEntry(Location location) {
            this.location = location;
        }

        public String getLocationString() {
            if (location != null) {
                return location.getX() + "," + location.getY();
            } else {
                return "No location";
            }
        }
    }
    

    public scan(String filePath) {
        scanner(filePath);
    }

    private void scanner(String filePath) {
        File inputFile = new File(filePath);
        try {
            Scanner scanner = new Scanner(inputFile);
            Random random = new Random();
            boolean isFirstInteger = true;
            String lastString = "";

            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int value = scanner.nextInt();
                    if (isFirstInteger) {
                        size = value;
                        isFirstInteger = false;
                    } else {
                        if (lastString.equals("wolf")) {
                            wolfPacks.put(packCounter++, value);
                        } else if (lastString.equals("bear")) {
                            handleBearEntry(scanner, value);
                        } else {
                            dataMap.put(lastString, value);
                        }
                    }
                } else {
                    String next = scanner.next();
                    if (next.contains("-")) {
                        String[] parts = next.split("-");
                        int lowerBound = Integer.parseInt(parts[0]);
                        int upperBound = Integer.parseInt(parts[1]);
                        int randomValue = lowerBound + random.nextInt(upperBound - lowerBound + 1);
                        dataMap.put(lastString, randomValue);
                    } else {
                        lastString = next;
                    }
                }
            }

            rabbit = dataMap.getOrDefault("rabbit", 0);
            burrow = dataMap.getOrDefault("burrow", 0);
            grass = dataMap.getOrDefault("grass", 0);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    private void handleBearEntry(Scanner scanner, int bearCount) {
        Location location = null;
        if (scanner.hasNext("\\(\\d+,\\d+\\)")) {
            String locationStr = scanner.findInLine("\\(\\d+,\\d+\\)");
            location = parseLocation(locationStr);
        }
        for (int i = 0; i < bearCount; i++) {
            bears.add(new BearEntry(location));
        }
    }

    private Location parseLocation(String locationStr) {
        locationStr = locationStr.replaceAll("[()]", "");
        String[] parts = locationStr.split(",");
        return new Location(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public int getSize() {
        return size;
    }

    public int getRabbit() {
        return rabbit;
    }

    public int getBurrow() {
        return burrow;
    }

    public int getGrass() {
        return grass;
    }

    public HashMap<Integer, Integer> getHash() {
        return wolfPacks;
    }

    public List<BearEntry> getBears() {
        return bears;
    }
    
    public void getbears(){
        for (BearEntry bear : getBears()) {
            System.out.println(bear.getLocationString());
    }
}
}
