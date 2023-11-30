package EnviormentObjects;
import itumulator.world.*;
import java.util.ArrayList;
import Animals.Wolf;

public class Wolfden extends EnvObject{
    protected ArrayList<Wolf> WolvesInHole = new ArrayList<>();
    //protected int packnr; 

    public Wolfden(World world) {
        super(ObjectType.hole, world);
        //this.packnr = packnr;
    }

    public static boolean getexists(){
        return true;
    }

    @Override
    public ObjectType getObjectType() {
        return super.getObjectType();
    }

}
