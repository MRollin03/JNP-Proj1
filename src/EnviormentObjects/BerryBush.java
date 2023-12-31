package EnviormentObjects;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.util.*;
import java.awt.*;

public class BerryBush extends EnvObject implements Actor, DynamicDisplayInformationProvider {

    private boolean berries = false;

    public BerryBush(World world) {
        super(ObjectType.berryBush, world);
    }

    public void act(World world) {
        super.act(world);
        grow();
    }

    /**
     * A function that gets called and has the probability to grow berries
     */
    public void grow() {
        Random rand = new Random();
        int num = rand.nextInt(11);

        if (num == 10) {
            berries = true;
        }
    }
    /**
     * Checks if bush have berries
     * @return returns true if it has berries and false if not
     */
    public boolean hasBerries() {
        return berries;
    }
    
    public void berriesToggle() {
        berries = !berries;
    }

    public DisplayInformation getInformation() {
        if (berries) {
            return new DisplayInformation(Color.BLUE, "bush-berries");
        } else {
            return new DisplayInformation(Color.BLUE, "bush");
        }
    }
}