import java.io.Console;
import java.util.*;
import java.io.IOException;

public class CMD {
    public static void main (String args[]) throws IOException {
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        String Read = c.readLine("How many Dimensions would you like? ");
        int dim = 2;
        if(Integer.parseInt(Read) != 0){
            dim = Integer.parseInt(Read);
        }
        MNK Mnk = new MNK(dim);
        System.out.println("You will play on "+dim+" dimensions");

        Read = c.readLine("Would you like to play against my AI? ");
        boolean AI = false;
        MC mc = new MC();
        if(Read.equals("yes") || Read.equals("y")){
            AI=!AI;
            System.out.println("You will play against my AI");
        }
        else{
            System.out.println("You will play against yourself");
        }
        boolean gameHasEnded = false;
        while(!gameHasEnded){
            Read = c.readLine("Enter your Coodinates: ");
            if(Read.equals("exit")){
                gameHasEnded=true;
            }
            else{
                String[] Coords = Read.split("\\|");
                if(Coords.length == dim){
                    boolean works = true;
                    for(int i=0;i<Coords.length;i++){
                        if(!isInteger(Coords[i])){
                            works = false;
                        }
                    }
                    if(works){
                        ArrayList<Integer> Array = new ArrayList<Integer>();
                        for(int i=0;i<Coords.length;i++){
                            Array.add(Integer.parseInt(Coords[i]));
                        }
                        Field field = new Field(Array);
                        boolean click = Mnk.place(field);
                        if(click){
                            System.out.println("Player "+(Mnk.getFields().size()+1)%2+" has drawn");
                            if(!Mnk.checkEnd())
                                if(AI){
                                    if(!Mnk.place(mc.chooseBestMove(Mnk,1000)))
                                        System.out.println("PROBLEM!!");
                                    int ai = Mnk.hasWon(1);
                                    if(ai == 0){
                                        System.out.print("AI has drawn with ");
                                        System.out.println(""+Mnk.getFields().get(Mnk.getFields().size()-1).toString()+"");
                                    }
                                    if(ai == 1){
                                        System.out.println("You lost against my AI :P");
                                        gameHasEnded=true;
                                    }
                                }
                                else{}
                            else{
                                gameHasEnded=true;
                            }
                            if(Mnk.Won(0)){
                                System.out.println("Player "+0+" has won");
                                gameHasEnded=true;
                            }
                        }
                        else{
                            System.out.println("You put in invalid Coordinates");
                        }
                        if(Mnk.getFields().size() >= Math.pow(Mnk.getDimensions()+1,Mnk.getDimensions())){
                            System.out.println("That is a Tie!");
                            gameHasEnded=true;
                        }
                        if(Mnk.Won(0)){
                            System.out.println("Player "+0+" has won");
                            gameHasEnded=true;
                        }
                    }
                    else{
                        System.out.println("You put in sth that is not a number");
                    }
                }
                else{
                    System.out.println("You put in an incorrect number of Coordinates");
                }
            }
        }
    }

    public static boolean isInteger(String s) {
        int radix = 10;
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}