package Animals;
import java.util.ArrayList;

import itumulator.simulator.Actor;
import itumulator.world.*;

public class Wolf extends Animal implements Actor{
    private int packnr = 0;
    protected ArrayList<Wolf> WolvesInPacks = new ArrayList<>();

    private Location packCenter = (0,0);
    public Wolf(World world, int packnr){
        super(world);
        this.packnr = packnr;
    }

    @Override
    public void act(World world){
        if (world.isNight()) {
            handleNightBehavior(world);
        } else{
            handleDayBehavior(world);
        }

        super.act(world);
    }

    private void handleNightBehavior(World world) {
        
    }


    private void handleDayBehavior(World world) {

    }


    private void updatePackCenter(World world, int packnr){
        
    }
}
