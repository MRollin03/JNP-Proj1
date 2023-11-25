import itumulator.simulator.Actor;
import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;


public class Rabbit extends Animal implements Actor{
    RabbitHole currentRabbitHole = null;

    Rabbit(World world){
        super(world);
    }

    @Override
    // kode til den specefikke Behavior
    public void act(World world) {

        
        // Nat og dags Behavior 
        if(world.isNight()){
            if(world.getCurrentLocation() == null) {
                System.err.println("not on board");
                return;
            }

            // getting surrounding tiles, and current location
            Set<Location> neighbours = world.getEmptySurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);
            Random rand = new Random();
            Location l = world.getCurrentLocation();

            if(world.containsNonBlocking(l)){
                if(checkNonBlocking(l, world)){
                    RabbitHole hole = (RabbitHole) world.getNonBlocking(l);
                    currentRabbitHole = (RabbitHole) world.getNonBlocking(l);
                    hole.addToHole(this);
                    return;
                }
            }


            /**
             * This try method consist of
             * - finds the differance from the rabbit to the holes
             * - afterwouds calculates the steps to the hole from
             * - the current position of the rabbit itself.
             */
            try {

                // call function that gives us the cordinates of a hole.
                Location holeLocation= holeIsNear(world, l);

                if(list != null){
                    try {

                        //calculating the new location for the rabbits new step
                        l = new Location(
                            stepFunction((holeLocation.getX() - l.getX()), l.getX()), // finds next X-step
                            stepFunction((holeLocation.getY() - l.getY()), l.getY()) // finds nexr y_step
                            );

                        //moves the rabbit to the given location
                        world.move(this,l);
                        
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
             catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        }

        else{

            // Edit: Her skal der kodes dags behavoir -------
            if (currentRabbitHole != null) {
                currentRabbitHole.activate();
              }

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
                RabbitHole hole = new RabbitHole(world);
            }
            
            if (Grass.isTileGrass(world, l)){
                EnvObject.deleteObj(world, world.getNonBlocking(l));
                foodPoint = foodPoint + 5;
            }

        }

        super.act(world);
    }


    //Calculates the nexstep X - Y / -cordinate between to cordinates.
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
    private boolean isGrassNear(){
        boolean res = false;

        // ---- Implement code that check if grass is  on neighboring tiles --- 

        return true;
    }
    
    //checks if the is a hole in the given radius
    private Location holeIsNear(World world, Location l) throws Exception {
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

    
    private boolean checkNonBlocking(Location location, World world) {
        try {
            Object obj = world.getNonBlocking(location);
    
            // Check if the object is not null and its class is RabbitHole
            return obj != null && obj instanceof RabbitHole;
        } catch (IllegalArgumentException e) {
            // Handle the exception if needed
            System.out.println(e.getMessage());
            return false;
        }
    }
    
        
    
}
