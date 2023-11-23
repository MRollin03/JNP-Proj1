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
        entSpawnMap.put("Rabbit", 10);
        entSpawnMap.put("Grass", 10);
        entSpawnMap.put("RabbitHole", 10);
        entSpawnMap.put("Person", 10);

        DisplayInformation di = new DisplayInformation(Color.getHSBColor(255,0,255));

        // spawner ind hver en type of entreaty.
        for (String entType : entSpawnMap.keySet()) {
            for(int i = 0; i < entSpawnMap.get(entType); i++){
                int x = r.nextInt(size);
                int y = r.nextInt(size);
                Location l = new Location(x,y);
                // S� l�nge pladsen ikke er tom, fors�ger vi med en ny tilf�ldig plads:
                while(!world.isTileEmpty(l) && !world.containsNonBlocking(l)) {
                    x = r.nextInt(size);
                    y = r.nextInt(size);
                    l = new Location(x,y);
                }
                // og herefter kan vi så anvende den:'
                switch (entType) {
                    case "Rabbit":
                        Animal currentRabbit = new Rabbit();
                        world.setTile(l, currentRabbit);
                        di = new DisplayInformation(Color.blue); // Color Settings
                        p.setDisplayInformation(Rabbit.class, di);
                        break;
                /*
                    case "Grass":
                        EnvObject currentObject = new Grass();
                        world.setTile(l, currentObject);
                        di = new DisplayInformation(Color.Pink); // Color Settings
                        p.setDisplayInformation(Grass.class, di);
                        break;

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

        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();
        } // k�rer 200 runder, alts� kaldes 'act' 200 gange for alle placerede akt�rer

    }

    
}