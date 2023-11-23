import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;
public class Main {

    public static void main(String[] args) {
        int size = 15; // st�rrelsen af vores 'map' (dette er altid kvadratisk)
        int delay = 1000; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // sk�rm opl�sningen (i px)
        Program p = new Program(size, display_size, delay); // opret et nyt program
        World world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilf�je ting!

        //Making a person
        int amount = 10;
        Random r = new Random();
        ArrayList<Grass> Grasss = new ArrayList<Grass>(); 

        for(int i = 0; i < amount; i++){
            int x = r.nextInt(size);
            int y = r.nextInt(size);
            Location l = new Location(x,y);
            // S� l�nge pladsen ikke er tom, fors�ger vi med en ny tilf�ldig plads:
            while(!world.isTileEmpty(l)) {
                x = r.nextInt(size);
                y = r.nextInt(size);
                l = new Location(x,y);
            }
            // og herefter kan vi så anvende den:
            Grass currentgrass = new Grass();
            Grasss.add(currentgrass);
            world.setTile(l, currentgrass);
        }

        DisplayInformation di = new DisplayInformation(Color.red);
        p.setDisplayInformation(Person.class, di);

        DisplayInformation di2 = new DisplayInformation(Color.red,"grass");
        p.setDisplayInformation(Grass.class, di2);

        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();
            System.out.println(world.getCurrentTime());

            //for (Grass grass:Grasss){
                //grass.spread(world);
            //}
                
            
        } // k�rer 200 runder, alts� kaldes 'act' 200 gange for alle placerede akt�rer

    }
}