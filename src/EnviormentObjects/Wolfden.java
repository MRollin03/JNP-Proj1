package EnviormentObjects;
import itumulator.world.*;
import java.util.*;
import Animals.Wolf;


public class Wolfden extends Homes{
    protected ArrayList<Wolf> WolvesInHole = new ArrayList<>();

    public Wolfden(World world) {
        //super(ObjectType.hole, world);
        //this.packnr = packnr;
    }
    
    
    public boolean exists(Wolf wolf){
        for (Wolf wolf_chk : WolvesInHole){
            if (wolf == wolf_chk){
                return true;
            }
        }
        return false;
    }

    @Override
    public ObjectType getObjectType() {
        return super.getObjectType();
    }

}
