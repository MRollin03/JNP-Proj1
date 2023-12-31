package Animals;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;
import MainFolder.*;
import java.util.*;
import java.awt.*;

public class Rabbit extends Animal implements Actor, DynamicDisplayInformationProvider {
    private Homes currentRabbitHole = null;
    private int mate_CD = 15;

    public Rabbit() {
        super();
        mate_CD = 15;
        currentRabbitHole = null;
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

        // jumps into hole if rabbit is ontop of a tile with a hole.
        Location currentLocation = world.getCurrentLocation();
        if (Utils.checkNonBlockingType(currentLocation, RabbitHole.class)) {
            Homes hole = (Homes) world.getNonBlocking(currentLocation);
            currentRabbitHole = hole;
            hole.addToHole(this, hole);
            return;
        }

        // Trying to find if there is any RabbitHoles nearby the location.
        try {
            Location holeLocation = Utils.isNonBlocktNear(currentLocation, RabbitHole.class, 5);

            // calls function to get the new location for its next step.
            Location newLocation = Utils.diff(holeLocation, currentLocation);

            // if the nextsteps tiles is empty it is able to move
            if (world.isTileEmpty(newLocation)) {
                world.move(this, newLocation);
            }

        } catch (Exception e) { // If no locations of holes found nearby then create new hole
            if (world.containsNonBlocking(currentLocation)) {
                world.delete(world.getNonBlocking(currentLocation));
                Utils.spawnIn("RabbitHole", world.getLocation(this));
                System.out.println(e.getMessage());
            } else {
                Utils.spawnIn("RabbitHole", world.getLocation(this));
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleDayBehavior(World world) {

        Location currentLocation = world.getCurrentLocation();

        // if Rabbit currently has no location by day call its current holes
        // removefromhole() function.
        if (currentLocation == null) {
            if (currentRabbitHole != null) {
                currentRabbitHole.removeFromHole();
            }
            return;
        }

        // Gets a random move location and checks if theres grass on the tiles.
        Set<Location> emptyTiles = world.getEmptySurroundingTiles(currentLocation);

        if (mate_CD > 0) {
            mate_CD--;
        }
        Set<Location> surroundingTiles = world.getSurroundingTiles(1);
        Location newLocation = currentLocation;

        try {
            world.move(this, Utils.randomMove(currentLocation));

            // checks if there is grass on the next steps if the eat. give 5 engey points.
            if (Utils.checkNonBlockingType(newLocation, Grass.class)) {
                EnvObject.deleteObj(world, world.getNonBlocking(newLocation));
                energy += 5;
            }
            if (Utils.checkNonBlockingType(newLocation, BerryBush.class)) {
                BerryBush bush = (BerryBush) world.getNonBlocking(newLocation);
                if (bush.hasBerries()) {
                    bush.berriesToggle();
                    energy += 5;
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for (Location l : surroundingTiles) {
            if (!world.isTileEmpty(l)) {
                if (world.getTile(l).getClass() == Bear.class || world.getTile(l).getClass() == Wolf.class) {
                    newLocation = Utils.diff(new Location(-l.getX(), -l.getY()), currentLocation);
                }
            }

            if (world.getTile(l) instanceof Rabbit && mate_CD == 0) {

                if (getothermate_CD(l) == 0) {
                    newLocation = Utils.randomMove(currentLocation); // brug en anden funktion her?
                    if (newLocation == null) {
                        return;
                    } // hvis random lokation omkring kaninen ikke findes, fejles fødtslen.
                    Utils.spawnIn("Rabbit", newLocation);
                    mate_CD = 15; // resets Mate cooldown for 1 rabbit
                    resetmateCD(l); // resets Mate cooldown for the other rabbit
                }
            }

        }

        newLocation = world.getLocation(this);

        try {
            if (Utils.checkNonBlockingType(newLocation, Grass.class)) {
                EnvObject.deleteObj(world, world.getNonBlocking(newLocation));
                energy += 5;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Used to get the other Rabbits mating_CD value.
     * 
     * @param l l is the location of the target rabbit.
     * @return Returns the Mating_CD value as Integer.
     */
    private int getothermate_CD(Location l) {
        Rabbit mate = (Rabbit) world.getTile(l); // returns mate cooldown for rabbit at location l
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
     * used to transfere the Mating cooldown between Rabbits
     * 
     * @return returns the mate_CD value.
     */
    public int getmate_CD() {
        return this.mate_CD;
    }

    /**
     * Resets the mateing CoolDown
     * 
     * @param l location of the rabbit
     */
    public void resetmateCD(Location l) {
        Rabbit temp = (Rabbit) world.getTile(l);
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
            return new DisplayInformation(Color.BLUE, "rabbit-large");
        } else {
            return new DisplayInformation(Color.BLUE, "rabbit-small");
        }
    }

}
