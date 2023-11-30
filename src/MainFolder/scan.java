package MainFolder;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
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

    private static class BearEntry {
        private Location location;

        public BearEntry(Location location) {
            this.location = location;
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
                            handleBearEntry(scanner);
                        } else {
                            handleEntityEntry(lastString, value, scanner, random);
                        }
                    }
                } else {
                    lastString = scanner.next();
                }
            }

            rabbit = dataMap.getOrDefault("rabbit", 0);
            burrow = dataMap.getOrDefault("burrow", 0);
            grass = dataMap.getOrDefault("grass", 0);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    private void handleBearEntry(Scanner scanner) {
        int bearCount = scanner.nextInt();
        Location location = null;
        if (scanner.hasNext("\\(\\d+,\\d+\\)")) {
            String locationStr = scanner.findInLine("\\(\\d+,\\d+\\)");
            location = parseLocation(locationStr);
        }
        for (int i = 0; i < bearCount; i++) {
            bears.add(new BearEntry(location));
        }
    }

    private void handleEntityEntry(String entity, int value, Scanner scanner, Random random) {
        if (scanner.hasNext("-")) {
            scanner.next(); // Consume the '-' symbol
            int upperBound = scanner.nextInt();
            int randomValue = random.nextInt(upperBound - value + 1) + value;
            dataMap.put(entity, randomValue);
        } else {
            dataMap.put(entity, value);
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

}
