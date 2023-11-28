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

        Location currentLocation = world.getCurrentLocation();
        if (world.containsNonBlocking(currentLocation) && checkNonBlocking(currentLocation, world)) {
            RabbitHole hole = (RabbitHole) world.getNonBlocking(currentLocation);
            currentRabbitHole = hole;
            hole.addToHole(this);
            return;
        }

        try {
            
            Location holeLocation = holeIsNear(world, currentLocation);
            List<Location> emptyTiles = new ArrayList<>(world.getEmptySurroundingTiles());
            if (!emptyTiles.isEmpty()) {

                Location newLocation = diff(holeLocation, currentLocation);

                if(world.isTileEmpty(newLocation)){
                    world.move(this, newLocation);
                }
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
                try {
                    world.setTile(newLocation,new Rabbit(world));
                    mate_CD = 15;               //resets Mate cooldown for 1 rabbit
                    resetmateCD(l);             //resets Mate cooldown for the other rabbit
                } catch (Exception e ){

                }
            }
        }
    }


    //move
    if (!emptyTiles.isEmpty()) {
        Random rand = new Random();
        Location newLocation = new ArrayList<>(emptyTiles).get(rand.nextInt(emptyTiles.size()));
        try {
            world.move(this, newLocation);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        
    //if grass, eat
        if (Grass.isTileGrass(world, newLocation)) {
            EnvObject.deleteObj(world, world.getNonBlocking(newLocation));
            energy += 10;
            //System.err.println("Grass eaten");
        }
    }


}
    private int getmate_CD(){                                   //returns mate cooldown for rabbit at current location
        return this.mate_CD;
    }

    private int getothermate_CD(Location l){ 
        Rabbit mate = (Rabbit) world.getTile(l);                                  //returns mate cooldown for rabbit at current location
        return mate.getmate_CD();
    }

    /**
     * Resets mate cooldown timer for rabbit at location l
     */
    private void resetmateCD(Location l){
        Rabbit temp = (Rabbit) world.getTile(l);
        temp.mate_CD = 15;
    }

    private Location diff(Location holeLocation, Location currentLocation) {
        int newX = stepFunction(holeLocation.getX() - currentLocation.getX(), currentLocation.getX());
        int newY = stepFunction(holeLocation.getY() - currentLocation.getY(), currentLocation.getY());
        return new Location(newX, newY);
    }

    private int stepFunction(int difference, int currentCoord) {
        if (difference > 0) {
            return currentCoord + 1;
        } else if (difference < 0) {
            return currentCoord - 1;
        } else {
            return currentCoord;
        }
    }

    private boolean isGrassNear() {
        // Implement code that checks if grass is on neighboring tiles
        return true;
    }

    private Location holeIsNear(World world, Location l) throws Exception {
        Set<Location> neighbours = world.getSurroundingTiles(l, 5);
        Set<Object> envObject = new HashSet<>();

        for (Location currentLocation : neighbours) {
            try {
                envObject.add(world.getNonBlocking(currentLocation));
            } catch (IllegalArgumentException ignored) {
            }
        }

        for (Object object : envObject) {
            if (object.getClass() == RabbitHole.class) {
                return world.getLocation(object);
            }
        }
        Main.spawnIn("RabbitHole", world, world.getLocation(this));
        throw new IllegalArgumentException("No holes nearby");
    }

    private boolean checkNonBlocking(Location location, World world) {
        try {
            Object obj = world.getNonBlocking(location);
            return obj != null && obj instanceof RabbitHole;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void die(World world) {
        super.die(world);
    }
}
