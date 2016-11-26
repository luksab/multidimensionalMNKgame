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
    /**
     * Constructor for objects of class MC
     */
    public MC()
    {
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
                Koord = new ArrayList<Integer>();
                for(int i=0;i<board.getDimensions();i++){
                    Koord.add(ThreadLocalRandom.current().nextInt(0, board.getDimensions()+1));
                }
                ja = board.check(new Field(Koord));
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
    public int[] simulatePlays(MNK board,int number)
    {
        int[] ret = new int[3];
        while(number > 0){
            MNK b = new MNK(board);
            ret[playRandomly(b)+1] += 1;
            number -= 1;
        }
        return ret;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int[][] evaluateMoves(MNK board,int number)
    {
        int[][] vals = new int[(int)Math.pow(board.getDimensions()+1,board.getDimensions())][3];
        for(int i=0;i<(int)Math.pow(board.getDimensions()+1,board.getDimensions());i++){
            MNK b = new MNK(board);
            if(!b.place(selectMove(i,board)))
                break;
            else
                vals[i] = simulatePlays(board,number);
        }
        return vals;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public Field chooseBestMove(MNK board,int number)
    {
        int[][] vals = evaluateMoves(board,number);
        int max = -10000;
        int maxM= 0;
        for(int i=0;i<(int)Math.pow(board.getDimensions()+1,board.getDimensions());i++){
            if(board.check(selectMove(i,board))){
                if(vals[i][2]-vals[i][0] > max){
                    max = vals[i][0];
                    maxM= i;
                }
            }
        }
        return selectMove(maxM,board);
    }
}
