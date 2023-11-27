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
        System.err.println("Rabbit added");
        rabbitsInHole.add(rabbit);
        world.remove(rabbit);
    }

    public void removeFromHole() {
        Location l = world.getLocation(this);
        Set<Location> neighbours = world.getEmptySurroundingTiles(l);
        ArrayList<Location> list = new ArrayList<>(neighbours);
        System.out.println("Available locations " + list.toString());

        Collections.shuffle(list);
        DisplayInformation di = new DisplayInformation(Color.getHSBColor(255,0,255));

        Iterator<Rabbit> iterator = rabbitsInHole.iterator();
        for (Rabbit currentRabbit : rabbitsInHole) {
            world.setTile(list.get(0), currentRabbit);
        }
        list.clear();
        rabbitsInHole.clear();
    }

    public boolean placeable(Location l, World world) {
        return !world.containsNonBlocking(l);
    }

    @Override
    public ObjectType getObjectType() {
        return super.getObjectType();
    }
}
