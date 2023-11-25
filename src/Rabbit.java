
import itumulator.simulator.Actor;
import itumulator.world.*;

import java.util.*;

public class Rabbit extends Animal implements Actor {
    private RabbitHole currentRabbitHole = null;
    private int foodPoint = 0;

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
            System.err.println("Not on the board");
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
                Location newLocation = calculateNextStep(holeLocation, currentLocation);
                world.move(this, newLocation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleDayBehavior(World world) {
    System.err.println("Day behavior");

    if (world.getCurrentLocation() == null) {
        System.err.println("Not on the board");
        if (currentRabbitHole != null) {
            currentRabbitHole.removeFromHole();
        }
        return;
    }

    // Proceed only if world.getCurrentLocation() is not null
    Location currentLocation = world.getCurrentLocation();

    Set<Location> emptyTiles = world.getEmptySurroundingTiles(currentLocation);
    if (!emptyTiles.isEmpty()) {
        Random rand = new Random();
        Location newLocation = new ArrayList<>(emptyTiles).get(rand.nextInt(emptyTiles.size()));
        try {
            world.move(this, newLocation);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        RabbitHole hole = new RabbitHole(world);
        System.err.println("Hole created");

        if (Grass.isTileGrass(world, newLocation)) {
            EnvObject.deleteObj(world, world.getNonBlocking(newLocation));
            foodPoint += 5;
            System.err.println("Grass eaten");
        }
    }

    System.err.println("3");
}


    private Location calculateNextStep(Location holeLocation, Location currentLocation) {
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
