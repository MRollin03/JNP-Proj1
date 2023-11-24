import itumulator.simulator.Actor;
import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;


public class Rabbit extends Animal implements Actor{

    Rabbit(){
        super();
    }

    @Override
    // kode til den specefikke Behavior
    public void act(World world) {

        
        // Nat og dags Behavior 
        if(world.isNight()){

            Set<Location> neighbours = world.getEmptySurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);

            Random rand = new Random();

            Location l = world.getCurrentLocation();

            try {
                Location holeLocation= holeIsNear(world, l);
                if(list != null){
                    try {
                        l = new Location(
                            stepFunction((holeLocation.getX() - l.getX()), l.getX()), // finds next X-step
                            stepFunction((holeLocation.getY() - l.getY()), l.getY()) // finds nexr y_step
                            );
                        System.err.println("new loaction " + l);

                        System.err.println(l); 
                        world.move(this,l);
                        
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
             catch (Exception e) {
                System.out.println(e.getMessage() +" This is the ");
            }


            
        }

        else{

            // Edit: Her skal der kodes dags behavoir -------

            Set<Location> neighbours = world.getEmptySurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);

            Random rand = new Random();
            Location l = world.getCurrentLocation();

            if(list != null){
                try {
                    l = list.get(rand.nextInt( list.size()-1)); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
                    world.move(this,l);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                RabbitHole hole = new RabbitHole();
            }
            
            if (Grass.isTileGrass(world, l)){
                EnvObject.deleteObj(world, world.getNonBlocking(l));
                foodPoint = foodPoint + 5;
            }

        }

        super.act(world);
    }



    private int stepFunction(int differance, int currentCord){
        if(differance > 0){
            return  currentCord + 1;
        }
        if(differance < 0){
            return currentCord - 1;
        }
        else{
            return currentCord;
        }

    }

    //Checks if grass are near
    public boolean isGrassNear(){
        boolean res = false;

        // ---- Implement code that check if grass is  on neighboring tiles --- 

        return true;
    }
    
    //checks if the is a hole in the given radius
    public Location holeIsNear(World world, Location l) throws Exception {
        Set<Location> neighbours =  world.getSurroundingTiles(l, 5);
        ArrayList<Location> list = new ArrayList<>(neighbours);
    
        Set<Object> envObject = new HashSet<>();


    
        for (Location currentLocation : neighbours) {
            try{
                envObject.add(world.getNonBlocking(currentLocation));
            }
            catch(IllegalArgumentException e){

            }
        }

        for (Object object : envObject) {
             if (object.getClass() == RabbitHole.class) {
                return world.getLocation(object);
             }
        }   

        throw new IllegalArgumentException("no holes nearby");
    }

    // Die funktion kalder remove via 'Animal' superclass
    public void die(World world){
        super.die(world);
    }


}
