package MainFolder;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Parser data fra en angivet fil.
 * Dataene inkluderer en størrelse og værdier for kanin, bur, græs, og ulvepakker.
 */
public class scan {
    private Map<String, Integer> dataMap = new HashMap<>();
    private List<Integer> wolves = new ArrayList<>();
    private int size;
    private int rabbit;
    private int burrow;
    private int grass;
    private int firstWolfPack = 0;
    private int secondWolfPack = 0;

    /**
     * Konstruktør for DataParser.
     * @param filePath Stien til filen, der indeholder dataene, der skal parses.
     */
    public scan(String filePath) {
        scanner(filePath);
    }

    /**
     * Parser filen og gemmer værdierne for størrelse, kanin, bur, græs, og ulvepakker.
     * @param filePath Stien til filen, der skal parses.
     */
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
                            wolves.add(value);
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
            } assignWolfPacks();

            // Tildel specifikke værdier til variabler
            rabbit = dataMap.getOrDefault("rabbit", 0);
            burrow = dataMap.getOrDefault("burrow", 0);
            grass = dataMap.getOrDefault("grass", 0);
                        
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }


    
    }

    public void assignWolfPacks() {
        if (!wolves.isEmpty()) {
            firstWolfPack = wolves.get(0);
            if (wolves.size() > 1) {
                secondWolfPack = wolves.get(1); 
            }
        }
    }

    /**
     * Henter størrelsesværdien.
     * @return Størrelsesværdien.
     */
    public int getSize() {
        return size;
    }

    /**
     * Henter antal kaniner.
     * @return antal kaniner.
     */
    public int getRabbit() {
        return rabbit;
    }

        /**
     * Henter antal huller.
     * @return antal huller.
     */
    public int getBurrow() {
        return burrow;
    }

    /**
     * Henter antal græs.
     * @return antal græs.
     */
    public int getGrass() {
        return grass;
    }

    /**
     * Henter antal ulve i første flok.
     * @return antal ulve i første flok.
     */
    public int getFirstWolfPack() {
        return firstWolfPack;
    }

    /**
     * Henter antal ulve i anden fol.
     * @return antal ulve i anden flok.
     */
    public int getSecondWolfPack() {
        return secondWolfPack;
    }
}
