package MainFolder;
import itumulator.executable.*;
import itumulator.world.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import Animals.*;
import EnviormentObjects.*;
import MainFolder.*;

public class Utils {
    public static Scan Scanner = new Scan("data/t2-5b.txt");
    public static Program p;
    public static World world;
    public static ArrayList<Wolfpack> Wolfpacks = new ArrayList<>();
    

    private static DisplayInformation di = new DisplayInformation(Color.getHSBColor(255,0,255));
    
    /**
     * Instansiates a new program and world based on the parameter given in the arguments
     * @param size  the size of the desired world
     * @param display_size the display window size
     * @param delay  the delay in between every frame
     */
    public static void  newProgram(int size, int display_size, int delay) {
        p = new Program(size, display_size, delay); // opret et nyt program
        world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilf�je ting!
    }
    
    /**
     * SpawnsIn entites based on type and location
     * @param entType   String name, of desired entity type.
     * @param l         location of the desired spawn location.
     */
    public static void spawnIn(String entType, Location l) throws IllegalArgumentException{
        if (l.getX() >= world.getSize() || l.getY() >= world.getSize()){
            throw new IllegalArgumentException();
        }

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

            case "Wolf":    //unused, consider deleting
                //Wolf currentWolf = new Wolf(world,1,l, wolfpack);
                //world.setTile(l, currentWolf);
                //WolvesInPacks.add(currentWolf);
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
                world.setTile(currentBear.getCentrum(), currentBear);
                di = new DisplayInformation(Color.red, "bear-small"); // Color Settings
                p.setDisplayInformation(Bear.class, di);
                break;

            case "berry-bush":
                BerryBush currentBush = new BerryBush(world);
                world.setTile(l, currentBush);
                di = new DisplayInformation(Color.green, "bush"); // Color Settings
                p.setDisplayInformation(BerryBush.class, di);
                break;

            case "fungi":
                Fungi currentFungi = new Fungi(true);
                world.setTile(l, currentFungi);
                di = new DisplayInformation(Color.green, "fungi"); // Color Settings
                p.setDisplayInformation(Fungi.class, di);
                break;

            case "fungi-small":
                Fungi currentFungiSmall = new Fungi(false);
                world.setTile(l, currentFungiSmall);
                di = new DisplayInformation(Color.green, "fungi-small"); // Color Settings
                p.setDisplayInformation(Fungi.class, di);
                break;

            case "carcass-small":
                Carcass carcass1 = new Carcass(world, false);
                if(checkNonBlocking(l) == true){
                    world.delete(world.getNonBlocking(l));
                }
                world.setTile(l, carcass1);
                di = new DisplayInformation(Color.red, "carcass"); // Color Settings
                p.setDisplayInformation(Carcass.class, di);
                break;

            case "carcass":
                Carcass carcass2 = new Carcass(world, true);
                if(checkNonBlocking(l) == true){
                    world.delete(world.getNonBlocking(l));
                }
                world.setTile(l, carcass2);
                di = new DisplayInformation(Color.red, "carcass-small"); // Color Settings
                p.setDisplayInformation(Carcass.class, di);
                break;

        }
        for (Bear bear : Scanner.getBears()) {
            
            Bear currentBear = new Bear(bear.getCentrum(), world);
            world.setTile(bear.getCentrum(), currentBear);
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
    private static int stepFunction(int difference, int currentCoord) {
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
     * @param radius    the radius in which to search
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
     * @return          returns the location of the object found or null if no objects within radius
     */
    public static Location isBlockNear(Location l, Class objClass, int radius) {
        Set<Location> neighbours = world.getSurroundingTiles(l, radius);
        Set<Object> blockingObjects = new HashSet<>();

        for (Location currentLocation : neighbours) {
            try {
                if (!(world.getTile(currentLocation) == null)){
                    blockingObjects.add(world.getTile(currentLocation));
                }
            }
            catch (IllegalArgumentException ignored) {
            }
        }

        for (Object object : blockingObjects) {
            if (object.getClass() == objClass) {
                return world.getLocation(object);
            }
        }
        
        return null;
    }
    
    /**
     * moves obj to a random location around the current location.
     * @param currentLocation Location to find a random location around.
     * @param obj             the Object you are trying to move.
     */
    public static Location randomMove(Location currentLocation) {
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
    public static boolean checkNonBlocking(Location location) {
        try {
            Object obj = world.getNonBlocking(location);
            return true;
        } catch (IllegalArgumentException e) {
            //System.out.println(e.getMessage());
            return false;
        }
    }



}