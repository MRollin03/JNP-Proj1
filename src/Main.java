import itumulator.world.*;
import java.util.*;

import Animals.Wolfpack;
import MainFolder.Scan;
import MainFolder.Utils;

public class Main {

    public static int size;

    public static void main(String[] args) {
        String filepath = "data/t2-4a.txt";
        Scan Scanner = Utils.scanFile(filepath);
        size = Scanner.getSize();
        int delay = 1000;
        int display_size = 600; // sk�rm opl�sningen (i px)
        Utils.newProgram(size, display_size, delay);

        // Making hashmap for entities and amount
        HashMap<String, Integer> entSpawnMap = new HashMap<String, Integer>();

        // Wolfpack Hash maker
        HashMap<Integer, Integer> Wolves = new HashMap<>(Scanner.getHash());
        int PackNumbers = Wolves.size();
        for (int x = 1; x <= PackNumbers; x++) {
            int wolves = Wolves.get(x);
            // System.out.println(Wolves.get(x));
            Location l = new Location((size / (1 + PackNumbers)) * x, size / (1 + PackNumbers) * x);

            Wolfpack wolfpack = new Wolfpack(Utils.world, x, l);
            wolfpack.spawnWolf(wolves);
        }

        //Inserting spawn values of the entities, into hashmap
        entSpawnMap.put("Grass", Scanner.getGrass());
        entSpawnMap.put("Rabbit", Scanner.getRabbit());
        entSpawnMap.put("burrow", Scanner.getBurrow());
        entSpawnMap.put("carcass", Scanner.getCarcass1());
        entSpawnMap.put("Crow", Scanner.getCrow());
        Utils.spawnBears();

        // Spawns every entitie on map.
        for (String entType : entSpawnMap.keySet()) {
            for (int i = 0; i < entSpawnMap.get(entType); i++) {
                Location l = Utils.getWorldRandomLocation(size); // Find a random location
                Utils.spawnIn(entType, l);
            }
        }

        Utils.p.show(); // Shows the simulation

        //Simulate 200 steps
        for (int i = 0; i < 200; i++) { 
            Utils.p.simulate();
        } 
    }

}