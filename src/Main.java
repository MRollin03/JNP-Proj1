import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class Main {

    public static void main(String[] args) {
        scan Scan = new scan(); // This will prompt for input
        int size = Scan.getSize();
        int delay = 1000; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // sk�rm opl�sningen (i px)
        Program p = new Program(size, display_size, delay); // opret et nyt program
        World world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilf�je ting!

        //Making a person
        Random r = new Random();
        HashMap<String, Integer> entSpawnMap = new HashMap<String, Integer>();
        
        //spawn Values for each 
        entSpawnMap.put("Rabbit", Scan.getRabbit());
        entSpawnMap.put("Grass", Scan.getGrass());
        entSpawnMap.put("RabbitHole", Scan.getBurrow());
        entSpawnMap.put("Person", 0);

        DisplayInformation di = new DisplayInformation(Color.getHSBColor(255,0,255));

        // spawner ind hver en type of entreaty.
        for (String entType : entSpawnMap.keySet()) {
            for(int i = 0; i <= entSpawnMap.get(entType); i++){
                Location l = getRandomLocation(size,world);     //Find a random location
                
                switch (entType) {
                    case "Rabbit":
                        Animal currentRabbit = new Rabbit();
                        world.setTile(l, currentRabbit);
                        di = new DisplayInformation(Color.blue,"rabbit-small"); // Color Settings
                        p.setDisplayInformation(Rabbit.class, di);
                        break;
                
                    case "Grass":
                        EnvObject currentObject = new Grass();
                        System.out.println(l + " and " + world.containsNonBlocking(l));
                        world.setTile(l, currentObject);
                        di = new DisplayInformation(Color.yellow,"grass"); // Color Settings
                        p.setDisplayInformation(Grass.class, di);
                        break;
                    /* 
                    case "RabbitHole":
                        EnvObject currentRabbitHole = new Grass();
                        world.setTile(l, currentRabbitHole);
                        di = new DisplayInformation(Color.purple); // Color Settings
                        p.setDisplayInformation(RabbitHole.class, di);
                        break;*/

                    case "Person":
                        Person currentPerson = new Person();
                        world.setTile(l, currentPerson);
                        di = new DisplayInformation(Color.red); // Color Settings
                        p.setDisplayInformation(Person.class, di);
                        break;
                
                }
                
                
            }
        }
        System.out.println("NO more entities");

        DisplayInformation di2 = new DisplayInformation(Color.red,"grass");
        p.setDisplayInformation(Grass.class, di2);

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
    
}