/**
 * This is the main Class
 * 
 * @author Lukas Sabatschus
 */
import java.util.*;
public class MNK
{
    private ArrayList<Field> Fields = new ArrayList<Field>();
    private int dimensions;

    public MNK(int dimensions)
    {
        this.dimensions = dimensions;
    }
    
    public MNK(MNK Mnk)
    {
        this.dimensions = Mnk.getDimensions();
        this.Fields = new ArrayList<Field>(Mnk.getFields());
    }

    /**
     * This Class returns -1, if the Field is empty and the player, if he has placed on this Field
     * 
     * @param  Field   The Field to be checked
     * @return     -1, if the Field is empty and the player, if he has placed on this Field
     */
    public int realPlayerOf(Field F){
        for(int i=0;i<Fields.size();i++){
            int dim = 0;
            for(int j=0;j<dimensions;j++){
                if(Fields.get(i).getCoordinates(j) == F.getCoordinates(j))
                    dim++;
            }
            if(dim == dimensions){
                return Fields.get(i).player;
            }
        }
        return -1;
    }
    
    public int realPlayerOf(int o,int p){
        ArrayList<Integer> K = new ArrayList<Integer>();
        K.add(o);
        K.add(p);
        Field Field = new Field(K);
        for(int i=0;i<Fields.size();i++){
            int dim = 0;
            for(int j=0;j<dimensions;j++){
                if(Fields.get(i).getCoordinates(j) == Field.getCoordinates(j))
                    dim++;
            }
            if(dim == dimensions){
                return Fields.get(i).player;
            }
        }
        return -1;
    }

    public ArrayList<Field> getFields(){
        return Fields;
    }

    public int getDimensions(){
        return dimensions;
    }

    public int getPlayer(){
        return(Fields.size()%2);
    }

    public boolean check(Field Field){
        if(Field.dimensions == dimensions && !(Fields.size() >= Math.pow(dimensions+1,dimensions))){
            try{
                for(int i=0;i<dimensions;i++){
                    if(!(Field.getCoordinates(i)>=0&&Field.getCoordinates(i)<=dimensions)){
                        return false;
                    }
                }
                if(realPlayerOf(Field) == -1){
                    return true;
                }
                else{
                    return false;
                }
            }
            catch(ArrayIndexOutOfBoundsException e){
                return false;
            }
        }
        else
            return false;
    }

    public boolean place(Field field){
        if(check(field)&&!checkEnd()){
            field.player = Fields.size()%2;
            Fields.add(field);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkEnd(){
        if((Fields.size() >= Math.pow(dimensions+1,dimensions))){
            return true;
        }
        else if(Fields.size() >= dimensions){
            if(checkWin()){
                return true;
            }
            else {return false;}
        }
        else{return false;}
    }
    
    public void print(){
        for(int i=0;i<Fields.size();i++){
            System.out.println(Fields.get(i).toString());
        }
    }

    public int hasWon(int player){
        if(player == Fields.size()%2 && checkWin()){
            return -1;
        }
        else if(player != Fields.size()%2 && checkWin()){
            return 1;
        }
        return 0;
    }
    
    public boolean Won(int player){
        if(player == Fields.size()%2 && checkWin()){
            return false;
        }
        else if(player != Fields.size()%2 && checkWin()){
            return true;
        }
        return false;
    }

    public boolean checkWin(){
        boolean won = false;
        if(Fields.size()==0)
            return false;
        if(!(Fields.size() >= Math.pow(dimensions+1,dimensions))){
            Field lastField = Fields.get(Fields.size()-1);
            int[] P = new int[dimensions];
            int[] D = new int[dimensions];
            int sp = lastField.player;
            for(int j=0; j<(int)(Math.pow(3,dimensions-1)/2)+Math.pow(3,dimensions-1) ; j++){
                for (int i=0;i<dimensions;i++){
                    if( (j/(int)(Math.pow(3,i))) % 3 == 0){P[i] = 0;D[i] = 1;}
                    else if((j/(int)(Math.pow(3,i)))%3==1){P[i] = lastField.getCoordinates(i);D[i] = 0;}
                    else{P[i] = dimensions;D[i] = -1;}
                }
                for(int i=0; i<Fields.size() -1;i++){
                    int counter = 0;
                    for(int h=0;h<dimensions;h++){
                        if(lastField.getCoordinates(h) + D[h] == Fields.get(i).getCoordinates(h)){
                            counter++;
                        }
                    }
                    int counter2 = 0;
                    for(int h=0;h<dimensions;h++){
                        if(lastField.getCoordinates(h) - D[h] == Fields.get(i).getCoordinates(h)){
                            counter2++;
                        }
                    }
                    if(counter == dimensions || counter2 == dimensions){
                        for (int m=0;m<dimensions;m++){
                            if( (j/(int)(Math.pow(3,m))) % 3 == 0){P[m] = 0;}
                            else if((j/(int)(Math.pow(3,m)))%3==1){P[m] = lastField.getCoordinates(m);}
                            else{P[m] = dimensions;}
                        }
                        int counter3 = 0;
                        for(int k=0;k<dimensions+1;k++){
                            ArrayList<Integer> CheckArray = new ArrayList<Integer>();
                            for(int m=0;m<dimensions;m++){
                                CheckArray.add(P[m]);
                            }
                            Field CheckField = new Field(CheckArray);
                            if(realPlayerOf(CheckField) == sp){
                                counter3 ++;
                            }
                            for(int m=0;m<=dimensions-1;m++){
                                P[m] += D[m];
                            }
                        }
                        if(counter3 == dimensions+1)
                        {
                            won = true;
                        }
                    }
                    if(won)break; // prevent unnecessary loops
                }
            }
            return won;
        }
        else 
            return true;
    }
}
