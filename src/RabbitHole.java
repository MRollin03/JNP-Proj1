import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;

public class RabbitHole extends EnvObject{
    protected ArrayList<Rabbit> rabbitsInHole= new ArrayList<Rabbit>();

    RabbitHole(World world){
        super(ObjectType.hole, world);
    }

    public void activate(){
        if(world.isDay()){
            removeFromHole();
        }
    }


    // adds rabbits to hole
    public void addToHole(Rabbit rabbit){
        System.err.println("Rabbit added");
        rabbitsInHole.add(rabbit);
        world.remove(rabbit);

    }

    public void removeFromHole(){
        // getting surrounding tiles, and current location
        System.err.println("3");
        Location l = world.getLocation(this);
        Set<Location> neighbours = world.getEmptySurroundingTiles(l);
        ArrayList<Location> list = new ArrayList<>(neighbours);
        System.err.println("2");
        Collections.shuffle(list);
        

        System.err.println("1");

        for (Rabbit currentRabbit : rabbitsInHole) {
            world.add(currentRabbit);
            world.setTile(list.get(1), currentRabbit);
            
            list.remove(1);
            
            rabbitsInHole.remove(currentRabbit);
            
            
        }

        //Spawns rabbits on surround empty tiles
    }

    public Boolean placeable(Location l , World world){
        if(!world.containsNonBlocking(l)){
            return true;
        }
        return false;
    }
    
    public ObjectType getObjectType(){
        return super.getObjectType();
    }
}   