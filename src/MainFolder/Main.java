package MainFolder;
import itumulator.executable.*;
import itumulator.world.*;
import java.util.*;



import Animals.Wolf;

public class Main {

    public static void main(String[] args) {
        scan Scanner = new scan("data/t2-2a.txt");
        int size = Scanner.getSize();
        int delay = 300; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // sk�rm opl�sningen (i px)
        Utils.newProgram(size, display_size, delay);

        //Making hashmap for entities and amount
        HashMap<String, Integer> entSpawnMap = new HashMap<String, Integer>();
        
        //spawn Values for each 
        entSpawnMap.put("Rabbit", Scanner.getRabbit());
        entSpawnMap.put("Grass", Scanner.getGrass());
        entSpawnMap.put("RabbitHole", Scanner.getBurrow());
        entSpawnMap.put("Wolf", Scanner.getFirstWolfPack());
       
        //entSpawnMap.put("Wolf", Scanner.getWolf());
        //entSpawnMap.put("Bear" , 1);

        

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