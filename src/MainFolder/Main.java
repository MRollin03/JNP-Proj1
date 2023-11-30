package MainFolder;
import itumulator.executable.*;
import itumulator.world.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        scan Scanner = new scan("data/t1-2cde.txt");
        int size = Scanner.getSize();
        int delay = 500; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // sk�rm opl�sningen (i px)
        Utils.newProgram(size, display_size, delay);

        //Making hashmap for entities and amount
        HashMap<String, Integer> entSpawnMap = new HashMap<String, Integer>();
        
        //spawn Values for each 
        entSpawnMap.put("Rabbit", Scanner.getRabbit());
        entSpawnMap.put("Grass", Scanner.getGrass());
        entSpawnMap.put("RabbitHole", Scanner.getBurrow());
        entSpawnMap.put("Wolf", Scanner.getBurrow());

        

        // Spawns every entitie on map.
        for (String entType : entSpawnMap.keySet()) {
            for(int i = 0; i < entSpawnMap.get(entType); i++){
                Location l = Utils.getRandomLocation(size);     //Find a random location
                Utils.spawnIn(entType, l);
            }
        }
        System.out.println("NO more entities");

        Utils.p.show(); // Shows the simulation
        for (int i = 0; i < 200; i++) {
            Utils.p.simulate();
        } // k�rer 200 runder, alts� kaldes 'act' 200 gange for alle placerede akt�rer

    }
    
}