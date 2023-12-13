package EnviormentObjects;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.util.*;

import MainFolder.Utils;

import java.awt.*;

public class Fungi extends EnvObject implements Actor, NonBlocking, DynamicDisplayInformationProvider{

    private boolean big;
    private Random rand;
    private int state;

    private int spreadChance = 10-1;

    public Fungi(boolean big){
        super(ObjectType.fungi, Utils.world);
        this.big = big;
        this.rand = new Random();
        this.state = 24;
    }

    public void act(World world) {
        spread();
        state--;
        if(state < 1){
            world.delete(this);
        }
    }

    
    public void spread(){
        Location currentLocation = world.getCurrentLocation();
        Location carcassLocation;

        try {
            carcassLocation = Utils.isNonBlocktNear(currentLocation, Carcass.class, 3);
            super.act(world);
            int value = rand.nextInt(spreadChance);
            if(value == 0){
                Carcass currentCarcass = (Carcass) world.getTile(carcassLocation);
                currentCarcass.setInfested(true);
                System.out.println("infested!");
            }
            
        } catch (Exception e) {
            
        }
    }

    /** used to check the size state of the fungus.
     * @return returns true if fungus is big.
     */
    public boolean isBig(){
        return big;
    }
    public DisplayInformation getInformation() {
        if(big){
            return new DisplayInformation(Color.BLUE, "fungi");
        }
        else{
            return new DisplayInformation(Color.BLUE, "fungi-small");
        }
    }
}