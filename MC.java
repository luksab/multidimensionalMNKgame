/**
 * This is an implementation of the Monte Carlo Tree Search for n-Dimensional TicTacToe
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class MC
{
    private MNK Mnk;

    /**
     * Constructor for objects of class MC
     */
    public MC()
    {
        Mnk = new MNK(2);
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int playRandomly(MNK board)
    {
        int val = board.hasWon(0);
        ArrayList<Integer> Koord = new ArrayList<Integer>();
        boolean ja = false;
        while(val == 0){
            if(board.getFields().size() >= Math.pow(board.getDimensions()+1,board.getDimensions())){
                return 0;
            }
            ja = false;
            while(!ja){
                Koord.clear();
                for(int i=0;i<board.getDimensions();i++){
                    Koord.add(ThreadLocalRandom.current().nextInt(0, board.getDimensions()+1));
                }
                ja = board.check(new Field(Koord));
                System.out.println(new Field(Koord).toString());
            }
            board.place(new Field(Koord));
            val = board.hasWon(0);
        }
        return val;
    }

    private Field selectMove(int mv,MNK board)
    {
        ArrayList<Integer> Coord = new ArrayList<Integer>();
        for(int i=0;i<board.getDimensions();i++)
            Coord.add((int)((mv/(int)(Math.pow(board.getDimensions()+2,i)))%(board.getDimensions()+2)));
        return new Field(Coord);
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int simulatePlays(int y)
    {
        // put your code here
        return y;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int evaluateMoves(int y)
    {
        // put your code here
        return y;
    }
}
