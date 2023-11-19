import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;
public class Main {

    public static void main(String[] args) {

        int size = 15; // størrelsen af vores 'map' (dette er altid kvadratisk)
        int delay = 1000; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // skærm opløsningen (i px)
        Program p = new Program(size, display_size, delay); // opret et nyt program
        World world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilføje ting!

        //Making a person
        int amount = 10;
        Random r = new Random();
        ArrayList<Person> persons = new ArrayList<Person>(); 

        for(int i = 0; i < amount; i++){
            int x = r.nextInt(size);
            int y = r.nextInt(size);
            Location l = new Location(x,y);
            // Så længe pladsen ikke er tom, forsøger vi med en ny tilfældig plads:
            while(!world.isTileEmpty(l)) {
                x = r.nextInt(size);
                y = r.nextInt(size);
                l = new Location(x,y);
            }
            // og herefter kan vi så anvende den:
            world.setTile(l, new Person());
        }

        DisplayInformation di = new DisplayInformation(Color.red);
        p.setDisplayInformation(Person.class, di);

        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();
            if(world.isNight()){
                for(Person currentPerson :persons){
                    world.remove(currentPerson);
                }
                
            }
        } // kører 200 runder, altså kaldes 'act' 200 gange for alle placerede aktører

    }
}
