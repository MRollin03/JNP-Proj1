package MainFolder;
import itumulator.executable.*;
import itumulator.world.*;
import java.awt.*;
import java.util.*;

import Animals.*;
import EnviormentObjects.*;

public class Utils {
    
    public static Program p;
    public static World world;

    private static DisplayInformation di = new DisplayInformation(Color.getHSBColor(255,0,255));
    
    public static void  newProgram(int size, int display_size, int delay) {
        p = new Program(size, display_size, delay); // opret et nyt program
        world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilf�je ting!
    }
    
    /**
     * SpawnsIn entites based on type and location
     * @param entType   String name, of desired entity type.
     * @param l         location of the desired spawn location.
     */
    public static void spawnIn(String entType, Location l){
        switch (entType) {
            case "Rabbit":
                Animal currentRabbit = new Rabbit(world);
                world.setTile(l, currentRabbit);
                di = new DisplayInformation(Color.blue,"rabbit-small"); // Color Settings
                p.setDisplayInformation(Rabbit.class, di);
                break;
        
            case "Grass":
                EnvObject currentObject = new Grass(world);
                world.setTile(l, currentObject);
                di = new DisplayInformation(Color.yellow,"grass"); // Color Settings
                p.setDisplayInformation(Grass.class, di);
                break;
            case "RabbitHole":
                EnvObject currentRabbitHole = new RabbitHole(world);
                world.setTile(l, currentRabbitHole);
                di = new DisplayInformation(Color.GRAY, "hole-small"); // Color Settings
                p.setDisplayInformation(RabbitHole.class, di);
                break;

            case "Person":
                Person currentPerson = new Person();
                world.setTile(l, currentPerson);
                di = new DisplayInformation(Color.red); // Color Settings
                p.setDisplayInformation(Person.class, di);
                break;

            case "Wolf":
                Wolf currentWolf = new Wolf(world,1);
                world.setTile(l, currentWolf);
                di = new DisplayInformation(Color.red,"wolf"); // Color Settings
                p.setDisplayInformation(Wolf.class, di);
                break;

            case "Wolfden":
                Wolfden currentden = new Wolfden(world);
                world.setTile(l, currentden);
                di = new DisplayInformation(Color.red,"hole"); // Color Settings
                p.setDisplayInformation(Wolfden.class, di);
                break;
        
            case "Bear":
                Bear currentBear = new Bear(getWorldRandomLocation(5), world);
                world.setTile(l, currentBear);
                di = new DisplayInformation(Color.red, "bear-small"); // Color Settings
                p.setDisplayInformation(Bear.class, di);
                break;
        }
    }

    /**
     * Stepfunction gives each step that it takes from two diffrent locations
     * 
     * @param difference    the diffrens between to integes, example: x-cords or y-cords
     * @param currentCoord  the cordinates of current position.
     * @return  returns a number from -1 to 1.
     */
    private  static int stepFunction(int difference, int currentCoord) {
        if (difference > 0) {
            return currentCoord + 1;
        } else if (difference < 0) {
            return currentCoord - 1;
        } else {
            return currentCoord;
        }
    }
    

    //------------ Location Functions --------------//
    
    /**
     * Checks if theres a nonblocking object near.
     * @param l         location to check around
     * @param objClass  the Class of the object you are trying to find.
     * @return          Location of the object
     * @throws Exception    /IF NO LOCATION FOUND IT THROWS EXCEPTION.
     */
    public static Location isNonBlocktNear(Location l, Class objClass, int radius) throws Exception {
        Set<Location> neighbours = world.getSurroundingTiles(l, radius);
        Set<Object> envObject = new HashSet<>();

        for (Location currentLocation : neighbours) {
            try {
                envObject.add(world.getNonBlocking(currentLocation));
            } catch (IllegalArgumentException ignored) {
            }
        }

        for (Object object : envObject) {
            if (object.getClass() == objClass) {
                return world.getLocation(object);
            }
        }
        throw new IllegalArgumentException("No "+ objClass.getClass() + "nearby");
    }
    
    /**
     * Function that checks if there is a given 'object' in a given radius, from a location.
     * @param l the center location for the search
     * @param objClass  The type of class to search after
     * @param radius    the radius in which to search
     * @return          returns the location of the object found
     * @throws Exception    exception if no object of that class is found.
     */
    public static Location isBlockNear(Location l, Class objClass, int radius) throws Exception {
        Set<Location> neighbours = world.getSurroundingTiles(l, radius);
        Set<Object> blockingObjects = new HashSet<>();

        for (Location currentLocation : neighbours) {
            try {
                blockingObjects.add(world.getTile(currentLocation));
            } catch (IllegalArgumentException ignored) {
            }
        }

        for (Object object : blockingObjects) {
            if (object.getClass() == objClass) {
                return world.getLocation(object);
            }
        }
        
        throw new IllegalArgumentException("No "+ objClass.getClass() + "nearby");
    }
    
    /**
     * moves obj to a random location around the current location.
     * @param currentLocation Location to find a random location around.
     * @param obj             the Object you are trying to move.
     */
    public static Location randomMove(Location currentLocation, Object obj){
        Set<Location> emptyTiles = world.getEmptySurroundingTiles(currentLocation);
    if (!emptyTiles.isEmpty()) {
        Random rand = new Random();
        Location newLocation = new ArrayList<>(emptyTiles).get(rand.nextInt(emptyTiles.size()));
        return newLocation;
    }
    return null;
}

    /**
     * This Function returns a random Location based on the size of the world.
     * 
     * @param size size  is the size of the world f.ex if 5,  5 X 5 there should be 25 possible locations
     * @return  returns a Location.
     */
    public static Location getWorldRandomLocation(int size){        //gets a random location
        Random r = new Random();
        int x = r.nextInt(size);
        int y = r.nextInt(size);
        Location l = new Location(x,y);
        if (world.containsNonBlocking(l)){
            l = getWorldRandomLocation(size);
        } 
        if ((!world.isTileEmpty(l))){
            l = getWorldRandomLocation(size);
        } 
        
        return l;
    }

    /**
     * from Location 2 to Location 1
     * @param Location1     Where we want to go      
     * @param Location2     Where we start from
     * @return
     */
    public static Location diff(Location Location1, Location Location2) {
        int newX = stepFunction(Location1.getX() - Location2.getX(), Location2.getX());
        int newY = stepFunction(Location1.getY() - Location2.getY(), Location2.getY());
        return new Location(newX, newY);
    }



    //------------- Boolean Functions ------------//

    /**
     * 
     * @param l
     * @param objClass
     * @return
     */
    public static boolean checkNonBlockingType (Location l, Class objClass){
        try{
        if (world.getNonBlocking(l).getClass() == objClass){
            return true;
        }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    /**
     * Checks if the location has no nonblocking objects.
     * @param l   Location to check on.
     * @return    Returns true or false
     */
    public static boolean placeable(Location l) {
        return !world.containsNonBlocking(l);
    }

    /**
     * Checks if there exist a NonBlockingObject Of type objClass.
     * @param location  Location of checking.
     * @param objClass  objClass the class to check for.
     * @return returns either false or true,
     */
    public static boolean checkNonBlocking(Location location, Class objClass) {
        try {
            Object obj = world.getNonBlocking(location);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}