import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RabbitHole extends EnvObject {
    protected ArrayList<Rabbit> rabbitsInHole = new ArrayList<>();

    public RabbitHole(World world) {
        super(ObjectType.hole, world);
    }

    public void activate() {
        if (world.isDay()) {
            removeFromHole();
        }
    }

    // Adds rabbits to the hole
    public void addToHole(Rabbit rabbit) {
        System.out.println("1 : Rabbits in hole size " + rabbitsInHole.size() );
        rabbitsInHole.add(rabbit);
        world.remove(rabbit);
    }

    public void removeFromHole() {
        if (rabbitsInHole.isEmpty()) {
            return;
        }
        
        Location l = world.getLocation(this);
    
        Set<Location> neighbours = world.getEmptySurroundingTiles(l);
        ArrayList<Location> list = new ArrayList<>(neighbours);
    
        Collections.shuffle(list);
    
        int rabbitsReleased = 0;
    
        for (int i = 0; i < list.size(); i++) {
            try {
                if (i >= rabbitsInHole.size()) {
                    break;
                }
                world.setTile(list.get(i), rabbitsInHole.get(i));
                rabbitsReleased++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    
        for (int index = 0; index < rabbitsReleased; index++) {
            rabbitsInHole.remove(0);
        }
    }
    

    public boolean placeable(Location l, World world) {
        return !world.containsNonBlocking(l);
    }

    @Override
    public ObjectType getObjectType() {
        return super.getObjectType();
    }
}
