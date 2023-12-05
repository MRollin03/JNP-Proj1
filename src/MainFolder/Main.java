package MainFolder;
import itumulator.executable.*;
import itumulator.world.*;
import java.util.*;

import Animals.Wolfpack;

public class Main {
    public static ArrayList<Wolfpack> Wolfpacks = new ArrayList<>();


    public static void main(String[] args) {
        scan Scanner = new scan("data/tf2-1.txt");
        int size = Scanner.getSize();
        int delay = 800; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 600; // sk�rm opl�sningen (i px)
        Utils.newProgram(size, display_size, delay);

        //Making hashmap for entities and amount
        HashMap<String, Integer> entSpawnMap = new HashMap<String, Integer>();
        
        //Print statemant for spawn Values of each 
        System.out.println("Grass: " + Scanner.getGrass());
        System.out.println("Rabbit: " + Scanner.getRabbit());
        System.out.println("Burrow: " + Scanner.getBurrow());
        System.out.println("Bear locations:");
        System.out.println("Wolf Packs: " + Scanner.getHash());
        System.out.println("NO more entities");

        //WolfSpawner
        HashMap<Integer, Integer> Wolves = new HashMap<>(Scanner.getHash());
        int PackNumbers = Wolves.size();
        for (int x = 1; x <= PackNumbers; x++) {
            int wolves = Wolves.get(x);
            //System.out.println(Wolves.get(x));
            Location l = new Location((size/(1+PackNumbers))*x, size/(1+PackNumbers)*x);
            
            Wolfpack wolfpack = new Wolfpack(Utils.world, x, l);
            wolfpack.spawnWolf(wolves);
        }

        entSpawnMap.put("Grass", Scanner.getGrass());
        entSpawnMap.put("Rabbit", Scanner.getRabbit());
        entSpawnMap.put("burrow", Scanner.getBurrow());

        // Spawns every entitie on map.
        for (String entType : entSpawnMap.keySet()) {
            for(int i = 0; i < entSpawnMap.get(entType); i++){
                Location l = Utils.getWorldRandomLocation(size);     //Find a random location
                Utils.spawnIn(entType, l);
            }
        }

        Utils.p.show(); // Shows the simulation
        for (int i = 0; i < 200; i++) {
            Utils.p.simulate();
        } // k�rer 200 runder, alts� kaldes 'act' 200 gange for alle placerede akt�rer

    }
    
}