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

            // Edit: Her skal der kodes Nat behavoir -------

                /**If statement for mulig død.
                 * Foodpoints 0 
                */
                if(true){
                    // Kode ker ---- 
                }

                



            if(list != null){
                
                    
                }
                if(){
                    RabbitHole hole = new RabbitHole();
                    hole.place(l, world);
                    System.out.println("NO HOLLES");
                    super.die(world);
                    
                }

            }

        }
        else{

            // Edit: Her skal der kodes dags behavoir -------

            Set<Location> neighbours = world.getEmptySurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);

            Random rand = new Random();

            if(list != null){
                Location l = list.get(rand.nextInt( list.size()-1)); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
                world.move(this,l);
                RabbitHole hole = new RabbitHole();
            }
        }

        // hvis klokken er 11 og mad point er 0
        if(world.getCurrentTime() == 11 && this.getFoodPoints() < 1 ){
            die(world);
        }

    }

    // Checks if grass are near
    public boolean isGrassNear(){
        boolean res = false;

        // ---- Implement code that check if grass is  on neighboring tiles --- 

        return true;
    }
    
    public Location holeIsNear(World world){

        Set<Location> neighbours = world.getEmptySurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);
        Random rand = new Random();
        Location l = list.get(rand.nextInt( list.size()-1)); 

                Set<Location> envObject = world.getSurroundingTiles(l, 2);
                System.out.print(envObject);

                
                    try{
                        for(Location currentLocation: envObject){
                        if(world.getNonBlocking(currentLocation).getClass() == EnvObject.class || world.getNonBlocking(currentLocation).getClass() == RabbitHole.class){
                        EnvObject obj = (EnvObject) world.getNonBlocking(currentLocation);
                        System.out.println(obj.getObjectType());

                        if (world.getNonBlocking(currentLocation) instanceof RabbitHole){
                            System.out.println("Hole Found");
                            return currentLocation;
                        }
                    

                        }}}
                    catch(IllegalArgumentException e){
                        //System.out.print(e.getMessage());
                    }
                    finally{
                        return null;
                    }

        return null;
    }


    // Die funktion kalder remove via 'Animal' superclass
    public void die(World world){
        super.die(world);
    }
    
    /*if(envObjectsNear.isEmpty()){
                    RabbitHole hole = new RabbitHole();
                    hole.place(l, world);
                    System.out.println("NO HOLLES");
                    super.die(world);
                    
                }*/


}
