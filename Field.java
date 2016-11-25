import java.util.*;
public class Field
{
    public ArrayList<Integer> coordinates = new ArrayList<Integer>();
    public int player;
    public int row;
    public int dimensions;
    public String note = "";

    public Field(ArrayList<Integer> coordinates,int player)
    {
        this.coordinates = coordinates;
        this.player      = player;
        dimensions       = coordinates.size();
    }

    public Field(ArrayList<Integer> coordinates)
    {
        this.coordinates = coordinates;
        dimensions       = coordinates.size();
    }

    public Field(int row,ArrayList<Integer> coordinates)
    {
        this.coordinates = coordinates;
        this.row         = row;
        dimensions       = coordinates.size();
    }

    public Field()
    {

    }

    public int getCoordinates(int dimension){
        return coordinates.get(dimension);
    }

    public String toString(){
        String s = ""+"Player: "+player;
        if(coordinates.size()>0){
            s += " coordinatesinates: ";
            for(int i=0;i<coordinates.size()-1;i++){
                s += +coordinates.get(i)+"|";
            }
            s += +coordinates.get(coordinates.size()-1);
        }
        return s;
    }
}