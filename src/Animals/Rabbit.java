package Animals;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;
import MainFolder.*;
import java.util.*;

public class Rabbit extends Animal implements Actor {
    private RabbitHole currentRabbitHole = null;
    private int mate_CD = 15;

    public Rabbit(World world){
        super(world);
    }

    @Override
    public void act(World world) {
        
        if (world.isNight()) {
            handleNightBehavior(world);
        } else{
            handleDayBehavior(world);
        }

        super.act(world);
    }

    private void handleNightBehavior(World world) { 
        if (world.getCurrentLocation() == null) {
            return;
        }

        //jumps into hole if rabbit is ontop of a tile with a hole.
        Location currentLocation = world.getCurrentLocation();
        if (world.containsNonBlocking(currentLocation) && Utils.checkNonBlocking(currentLocation, RabbitHole.class)) {
            RabbitHole hole = (RabbitHole) world.getNonBlocking(currentLocation);
            currentRabbitHole = hole;
            hole.addToHole(this);
            return;
        }

        //Trying to find if there is any RabbitHoles nearby the location.
        try {
            Location holeLocation = Utils.isNonBlocktNear(currentLocation, RabbitHole.class, 5);

            // calls function to get the new location for its next step.
            Location newLocation = Utils.diff(holeLocation, currentLocation);

                //if the nextsteps tiles is empty it is able to move
                if(world.isTileEmpty(newLocation)){
                    world.move(this, newLocation);
                }
                
            
        } catch (Exception e) { // If no locations of holes found nearby then create new hole
            if(world.containsNonBlocking(currentLocation)){
                world.delete(world.getNonBlocking(currentLocation));
            }
            
            Utils.spawnIn("RabbitHole", world.getLocation(this));
            System.out.println(e.getMessage());
        }
    }

    private void handleDayBehavior(World world) {

        // if Rabbit currently has no location by day call its current holes removefromhole() function.
        if (world.getCurrentLocation() == null) {
            if (currentRabbitHole != null) {
                currentRabbitHole.removeFromHole();
            }
            return;
        }

        Location currentLocation = world.getCurrentLocation();
        
        // Gets a random move location and checks if theres grass on the tiles.
        try {
            Utils.randomMove(currentLocation, this);
            Location newLocation = world.getLocation(this);

            if (Utils.checkNonBlockingType(newLocation, Grass.class)) {
                EnvObject.deleteObj(world, world.getNonBlocking(newLocation));
                energy += 5;
                System.err.println("Grass eaten");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // rabbits dies if called.
    public void die(World world) {
        super.die(world);
    }
}
