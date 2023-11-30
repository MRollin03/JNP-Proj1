package Animals;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;
import MainFolder.*;
import java.util.*;

public class Rabbit extends Animal implements Actor {
    private Homes currentRabbitHole = null;
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

        Location currentLocation = world.getCurrentLocation();
        if (Utils.checkNonBlocking(currentLocation, RabbitHole.class)) {
            Homes hole = (Homes) world.getNonBlocking(currentLocation);
            currentRabbitHole = hole;
            hole.addToHole(this,hole);
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
            if(world.containsNonBlocking(currentLocation)){
                world.delete(world.getNonBlocking(currentLocation));
                Utils.spawnIn("RabbitHole", world.getLocation(this));
                System.out.println(e.getMessage());
            }
            else{
                Utils.spawnIn("RabbitHole", world.getLocation(this));
            System.out.println(e.getMessage());
            }
        }
    }

    private void handleDayBehavior(World world) {
        if (world.getCurrentLocation() == null) {       // Proceed only if world.getCurrentLocation() is not null
            if (currentRabbitHole != null) {
                currentRabbitHole.removeFromHole();
            }
            return;
        }
        Location currentLocation = world.getCurrentLocation();

        Set<Location> emptyTiles = world.getEmptySurroundingTiles(currentLocation);
    
        if (mate_CD > 0){
            mate_CD--;
        }
        Set<Location> surroundingTiles = world.getSurroundingTiles(1);
        for (Location l : surroundingTiles) {
            if (world.getTile(l) instanceof Rabbit && mate_CD == 0){
                //System.out.println("main:" + getmate_CD());
                //System.out.println("other:" + getothermate_CD(l));
                if(getothermate_CD(l) == 0){
                    Random rand = new Random();
                    Location newLocation = new ArrayList<>(emptyTiles).get(rand.nextInt(emptyTiles.size()));      //brug en anden funktion her?
                    //try {
                        Utils.spawnIn("Rabbit",newLocation);
                        mate_CD = 15;               //resets Mate cooldown for 1 rabbit
                        resetmateCD(l);             //resets Mate cooldown for the other rabbit
                    //} catch (Exception e ){

                    //}
                }
            }
        }

    try {
        Utils.randomMove(currentLocation, this);
        Location newLocation = world.getLocation(this);

        if (Grass.isTileGrass(world, newLocation)) {
            EnvObject.deleteObj(world, world.getNonBlocking(newLocation));
            energy += 10;
            //System.err.println("Grass eaten");
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

    private int getmate_CD(){                                   //returns mate cooldown for rabbit at current location
        return this.mate_CD;
    }

    private int getothermate_CD(Location l){ 
        Rabbit mate = (Rabbit) world.getTile(l);                 //returns mate cooldown for rabbit at location l
        return mate.getmate_CD();
    }

/**
 * Resets mate cooldown timer for rabbit at location l
 */
    private void resetmateCD(Location l){
        Rabbit temp = (Rabbit) world.getTile(l);
        temp.mate_CD = 15;
    }

    public void die(World world) {
        super.die(world);
    }
}
