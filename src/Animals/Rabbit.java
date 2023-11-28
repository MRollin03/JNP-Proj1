package Animals;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;
import MainFolder.*;
import java.util.*;

public class Rabbit extends Animal implements Actor {
    private RabbitHole currentRabbitHole = null;

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

        Location currentLocation = world.getCurrentLocation();
        if (world.containsNonBlocking(currentLocation) && Utils.checkNonBlocking(currentLocation, RabbitHole.class)) {
            RabbitHole hole = (RabbitHole) world.getNonBlocking(currentLocation);
            currentRabbitHole = hole;
            hole.addToHole(this);
            return;
        }

        try {
            Location holeLocation = Utils.isNonBlocktNear(currentLocation, RabbitHole.class);
            List<Location> emptyTiles = new ArrayList<>(world.getEmptySurroundingTiles());

            if (!emptyTiles.isEmpty()) {

                Location newLocation = Utils.diff(holeLocation, currentLocation);

                if(world.isTileEmpty(newLocation)){
                    world.move(this, newLocation);
                }
                
            }
        } catch (Exception e) {
            Utils.spawnIn("RabbitHole", world.getLocation(this));
            System.out.println(e.getMessage());
        }
    }

    private void handleDayBehavior(World world) {

    if (world.getCurrentLocation() == null) {
        if (currentRabbitHole != null) {
            currentRabbitHole.removeFromHole();
        }
        return;
    }

    
    Location currentLocation = world.getCurrentLocation();

    try {
        Utils.randomMove(currentLocation, this);
        Location newLocation = world.getLocation(this);

        if (Grass.isTileGrass(world, newLocation)) {
            EnvObject.deleteObj(world, world.getNonBlocking(newLocation));
            foodPoint += 5;
            System.err.println("Grass eaten");
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

    public void die(World world) {
        super.die(world);
    }
}
