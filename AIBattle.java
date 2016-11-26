
/**
 * Write a description of class AIBattle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AIBattle
{
    // instance variables - replace the example below with your own
    private MNK mnk;
    private SimpleAI ai;
    private MC mc;

    /**
     * Constructor for objects of class AIBattle
     */
    public AIBattle(int num)
    {
        mnk = new MNK(num);
        ai  = new SimpleAI(num);
        mc  = new MC();
    }

    public void battleOne(int num)
    {
        mnk.place(ai.setze(mnk.getFields(),0));
        if(mnk.Won(0)){
            System.out.println("0");
        }
        mnk.place(mc.chooseBestMove(mnk,num));
        if(mnk.Won(1)){
            System.out.println("1");
        }
    }

    public void battle(int num)
    {
        while(!mnk.checkEnd()){
            mnk.place(ai.setze(mnk.getFields(),0));
            //mnk.place(ai.randF(mnk.getFields()));
            if(mnk.Won(0)){
                System.out.println("0");
            }
            mnk.place(mc.chooseBestMove(mnk,num));
            if(mnk.Won(1)){
                System.out.println("1");
            }
        }
    }

    public void print(){
        mnk.print();
    }
}
