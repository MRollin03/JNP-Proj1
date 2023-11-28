package MainFolder;
import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;


import Animals.*;
import EnviormentObjects.*;
import EnviormentObjects.Grass;

public class Main {
    static Program p;
    static World world;

    public static void main(String[] args) {
        scan Scan = new scan(); // This will prompt for input
        int size = Scan.getSize();
        int delay = 500; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // sk�rm opl�sningen (i px)
        p = new Program(size, display_size, delay); // opret et nyt program
        world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilf�je ting!

        //Making a person
        HashMap<String, Integer> entSpawnMap = new HashMap<String, Integer>();
        
        //spawn Values for each 
        entSpawnMap.put("Rabbit", Scan.getRabbit());
        entSpawnMap.put("Grass", Scan.getGrass());
        entSpawnMap.put("RabbitHole", Scan.getBurrow());
        entSpawnMap.put("Person", 0);

        

        // spawner ind hver en type of entreaty.
        for (String entType : entSpawnMap.keySet()) {
            for(int i = 0; i < entSpawnMap.get(entType); i++){
                Location l = getRandomLocation(size,world);     //Find a random location
                spawnIn(entType, world, l);
            }
        }
        System.out.println("NO more entities");

        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();
        } // k�rer 200 runder, alts� kaldes 'act' 200 gange for alle placerede akt�rer

    }

    public static Location getRandomLocation(int size, World world){        //gets a random location
        Random r = new Random();
        int x = r.nextInt(size);
        int y = r.nextInt(size);
        Location l = new Location(x,y);
        if (world.containsNonBlocking(l)){
            l = getRandomLocation(size, world);
        } 
        if ((!world.isTileEmpty(l))){
            l = getRandomLocation(size, world);
        } 
        
        return l;
    }

    static public void spawnIn(String entType, World world, Location l){

        DisplayInformation di = new DisplayInformation(Color.getHSBColor(255,0,255));
        
        switch (entType) {
                    case "Rabbit":
                        Animal currentRabbit = new Rabbit(world);
                        world.setTile(l, currentRabbit);
                        di = new DisplayInformation(Color.blue,"rabbit-small"); // Color Settings
                        p.setDisplayInformation(Rabbit.class, di);
                        break;
                
                    case "Grass":
                        EnvObject currentObject = new Grass(world);
                        world.setTile(l, currentObject);
                        di = new DisplayInformation(Color.yellow,"grass"); // Color Settings
                        p.setDisplayInformation(Grass.class, di);
                        break;
                    case "RabbitHole":
                        EnvObject currentRabbitHole = new RabbitHole(world);
                        world.setTile(l, currentRabbitHole);
                        di = new DisplayInformation(Color.GRAY, "hole-small"); // Color Settings
                        p.setDisplayInformation(RabbitHole.class, di);
                        break;

                    case "Person":
                        Person currentPerson = new Person();
                        world.setTile(l, currentPerson);
                        di = new DisplayInformation(Color.red); // Color Settings
                        p.setDisplayInformation(Person.class, di);
                        break;
                
                }

    }


    
}