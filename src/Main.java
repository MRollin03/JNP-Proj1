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
        int size = 15; // st�rrelsen af vores 'map' (dette er altid kvadratisk)
        int delay = 1000; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // sk�rm opl�sningen (i px)
        Program p = new Program(size, display_size, delay); // opret et nyt program
        World world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilf�je ting!

        //Making a person
        Random r = new Random();
<<<<<<< Updated upstream
        ArrayList<Person> persons = new ArrayList<Person>(); 
        ArrayList<Animal> animals = new ArrayList<Animal>(); 
=======
        HashMap<String, Integer> entSpawnMap = new HashMap<String, Integer>();
        
        //spawn Values for each 
        entSpawnMap.put("Rabbit", 10);
        entSpawnMap.put("Grass", 10);
        entSpawnMap.put("RabbitHole", 10);
        entSpawnMap.put("Person", 10);
>>>>>>> Stashed changes

        DisplayInformation di = new DisplayInformation(Color.getHSBColor(255,0,255));





        for (String entType : entSpawnMap.keySet()) {
            for(int i = 0; i < entSpawnMap.get(entType); i++){
                int x = r.nextInt(size);
                int y = r.nextInt(size);
                Location l = new Location(x,y);
                // S� l�nge pladsen ikke er tom, fors�ger vi med en ny tilf�ldig plads:
                while(!world.isTileEmpty(l)) {
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
<<<<<<< Updated upstream
            // og herefter kan vi så anvende den:
            Person currentPerson = new Person();
            persons.add(currentPerson);
            switch (Animal.type) {
                case Rabbit:
                    
                    break;
            
                default:
                    break;
            }
            Animal currentPerson = new Animal();
            animals.add(currentPerson);
            world.setTile(l, currentPerson);
=======
            
>>>>>>> Stashed changes
        }
        System.out.println("NO more entities");

<<<<<<< Updated upstream
        DisplayInformation di = new DisplayInformation(Color.blue);
        p.setDisplayInformation(Person.class, di);

        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
<<<<<<< Updated upstream
            

            p.simulate();
            

            if(world.isNight()){
                if(!persons.isEmpty())
                    for(int j = 0; j < persons.size(); j++){
                        world.delete(persons.get(0));
                        persons.remove(0);
                        
                }
                
            }
=======
            p.simulate();
>>>>>>> Stashed changes
=======

        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();
>>>>>>> Stashed changes
        } // k�rer 200 runder, alts� kaldes 'act' 200 gange for alle placerede akt�rer

    }
}