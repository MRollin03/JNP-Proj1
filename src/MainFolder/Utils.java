package MainFolder;
import itumulator.executable.*;
import itumulator.world.*;
import java.awt.*;
import java.util.*;

import Animals.*;
import EnviormentObjects.*;
import itumulator.executable.*;

public class Utils {
    
    static Program p;
    static World world;
    
    public static void  newProgram(int size, int display_size, int delay) {
        p = new Program(size, display_size, delay); // opret et nyt program
        world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilf�je ting!
    }

    /**
     * This Function returns a random Location based on the size of the world.
     * 
     * @param size size  is the size of the world f.ex if 5,  5 X 5 there should be 25 possible locations
     * @return  returns a Location.
     */
    public static Location getRandomLocation(int size){        //gets a random location
        Random r = new Random();
        int x = r.nextInt(size);
        int y = r.nextInt(size);
        Location l = new Location(x,y);
        if (world.containsNonBlocking(l)){
            l = getRandomLocation(size);
        } 
        if ((!world.isTileEmpty(l))){
            l = getRandomLocation(size);
        } 
        
        return l;
    }
    
    /**
     * SpawnsIn entites based on type and location
     * @param entType   String name, of desired entity type.
     * @param l         location of the desired spawn location.
     */
    public static void spawnIn(String entType, Location l){

        DisplayInformation di = new DisplayInformation(Color.getHSBColor(255,0,255));
        
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
        
        }
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

    /**
     * Checks if the location has no nonblocking objects.
     * @param l   Location to check on.
     * @return    Returns true or false
     */
    public static boolean placeable(Location l) {
        return !world.containsNonBlocking(l);
    }

    /**
     * Function that Gives us the diffrence between two 
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

    /**
     * Stepfunction gives each step that it takes from two diffrent locations
     * 
     * @param difference    the diffrens between to integes, example: x-cords or y-cords
     * @param currentCoord  the cordinates of current position.
     * @return  returns a number from -1 to 1.
     */
    public  static int stepFunction(int difference, int currentCoord) {
        if (difference > 0) {
            return currentCoord + 1;
        } else if (difference < 0) {
            return currentCoord - 1;
        } else {
            return currentCoord;
        }
    }

    /**
     * Checks if theres a nonblocking object near.
     * @param l         location to check around
     * @param objClass  the Class of the object you are trying to find.
     * @return          Location of the object
     * @throws Exception    /IF NO LOCATION FOUND IT THROWS EXCEPTION.
     */
    public static Location isNonBlocktNear(Location l, Class objClass) throws Exception {
        Set<Location> neighbours = world.getSurroundingTiles(l, 5);
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
     * moves obj to a random location around the current location.
     * @param currentLocation Location to find a random location around.
     * @param obj              the Object you trying to move.
     */
    public static void randomMove(Location currentLocation, Object obj){
        Set<Location> emptyTiles = world.getEmptySurroundingTiles(currentLocation);
    if (!emptyTiles.isEmpty()) {
        Random rand = new Random();
        Location newLocation = new ArrayList<>(emptyTiles).get(rand.nextInt(emptyTiles.size()));
        try {
            world.move(obj, newLocation);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}


}