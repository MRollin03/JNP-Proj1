import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;

public class EnvObject implements NonBlocking {
    Set<Location> neighbours;
    ArrayList<Location> list;
    ObjectType objType;

    EnvObject(ObjectType objType){
        this.objType = objType;
    }

    enum ObjectType{
        grass,
        hole
    }

    public void act(World world){
        neighbours = world.getEmptySurroundingTiles();
        list = new ArrayList<>(neighbours);
    }
    





    //---------------------- set methods----------------------//
    public void setObjecType(ObjectType type){
        this.objType = type;
    }



    //---------------------- get methods----------------------//
    public ArrayList getLocation(){
        return list;
    }

    public ObjectType getObjectType(){
        return objType;
    }


}
