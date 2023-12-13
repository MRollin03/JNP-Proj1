package Animals;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;
import MainFolder.*;
import java.util.*;
import java.awt.*;
import java.util.ArrayList.*;

public class Crow extends Animal implements Actor, DynamicDisplayInformationProvider {
    private int mate_CD = 15;

    public Crow() {
        super();
        mate_CD = 15;
    }

    @Override
    public void act(World world) {

        if (world == null) {
            return;
        }

        if (world.isNight()) {
            handleNightBehavior(world);
        } else {
            handleDayBehavior(world);
        }

        super.act(world);
    }

    private void handleNightBehavior(World world) {
        if (world.getCurrentLocation() == null) {
            return;
        }

    }

    // Trying to find if there is any CrowHoles nearby the location.

    private void handleDayBehavior(World world) {

        Location currentLocation = world.getCurrentLocation();

        if (mate_CD > 0) {
            mate_CD--;
        }

        Set<Location> surroundingTiles = world.getSurroundingTiles(2);
        ArrayList<Location> list = new ArrayList<Location>();

        for (Location location : surroundingTiles) {
            if (world.isTileEmpty(location)) {
                list.add(location);
            }
        }

        if (list.isEmpty()) {
            return;
        }

        Collections.shuffle(list);

        while (!world.isTileEmpty(list.get(0))) {
            Collections.shuffle(list);
        }

        Location newLocation = list.get(0);
        world.move(this, newLocation);

        Set<Location> mateSurroundingTiles = world.getEmptySurroundingTiles();
        for (Location l : mateSurroundingTiles) {

            if (world.getTile(l) == Crow.class && mate_CD == 0) {

                if (getothermate_CD(l) == 0) {

                    newLocation = Utils.randomMove(currentLocation); // brug en anden funktion her?
                    if (newLocation == null) {
                        return;
                    } // If no random location existe around the crow the birth failes

                    Utils.spawnIn("Crow", newLocation);
                    mate_CD = 15; // resets Mate cooldown for 1 Crow
                    resetmateCD(l); // resets Mate cooldown for the other Crow
                }
            }

        }

        try

        {
            if (Utils.checkNonBlockingType(newLocation, BerryBush.class)) {
                BerryBush bush = (BerryBush) world.getNonBlocking(newLocation);
                if (bush.hasBerries()) {
                    bush.berriesToggle();
                    energy += 10;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Used to get the other Crows mating_CD value.
     * 
     * @param l l is the location of the target Crow.
     * @return Returns the Mating_CD value as Integer.
     */
    private int getothermate_CD(Location l) {
        Crow mate = (Crow) world.getTile(l); // returns mate cooldown for Crow at location l
        return mate.getmate_CD();
    }

    /**
     * Used to set the mating-cooldown value to a given value
     * 
     * @param value desired mating_CD value.
     */
    public void setMate_CD(int value) {
        mate_CD = value;
    }

    /**
     * used to transfere the Mating cooldown between Crows
     * 
     * @return returns the mate_CD value.
     */
    public int getmate_CD() {
        return this.mate_CD;
    }

    /**
     * Resets the mateing CoolDown
     * 
     * @param l location of the Crow
     */
    public void resetmateCD(Location l) {
        Crow temp = (Crow) world.getTile(l);
        temp.mate_CD = 15;
    }

    public void die() {
        super.die();
        Location currentLocation = world.getCurrentLocation();
        if (currentLocation != null) {
            super.spawnCarcass(1, currentLocation);
        }
    }

    public DisplayInformation getInformation() {
        if (super.getAge() > 1) {
            return new DisplayInformation(Color.BLACK);
        } else {
            return new DisplayInformation(Color.BLACK);
        }
    }

}
