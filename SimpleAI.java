import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.util.concurrent.*;
public class SimpleAI
{
    int dim;
    int hardness;
    ArrayList<Field> Possible;
    public SimpleAI(int dim)
    {
        this.dim = dim;
    }

    public Field setze(ArrayList<Field> Fields, int sp)
    {
        int gg;
        if(sp == 1)
            gg = 0;
        else
            gg = 1;
        if(Fields.size() > 1){
            Field FastIch = fast(Fields,2);
            if(FastIch.player == 0){
                FastIch.note = "FastIch";
                return FastIch;
            }
            Field FastGegner = fast(Fields,1);
            if(FastGegner.player == 0){
                FastIch.note = "FastIch";
                return FastGegner;
            }
            Field Zwickmühle = Zwickmühle(Fields,gg);
            if(Zwickmühle.player == 0){
                Zwickmühle.note = "Zwickmühle";
                return Zwickmühle;
            }
            Field LL = FindLargestLineThread(Fields,sp);
            if(LL.player == 0){
                LL.note = "LL";
                return LL;
            }
            else{
                Field randf = randF(Fields);
                randf.note = "randF";
                return randf;
            }
        }
        else{
            Field randf = randF(Fields);
            randf.note = "randF";
            return randf;
        }
    }

    public Field randF(ArrayList<Field> Fields){
        ArrayList<Integer> Koord = new ArrayList<Integer>();
        boolean ja = false;
        if(dim%2 == 0){
            //grade dimensionszahl = es gibt EINE Mitte
            for(int i=0;i<dim;i++){
                Koord.add((int)dim/2);
            }
            if(check(new Field(Koord),Fields)){
                return new Field(Koord);
            }
        }
        else{
            //UNgrade dimensionszahl = es gibt 2^dim "Mitten"
            for(int k=0;k<Math.pow(2,dim);k++){
                Koord.clear();
                for (int i=0;i<dim;i++){
                    if( (k/(int)(Math.pow(2,i))) % 2 == 0){Koord.add((int)(dim/2));}
                    else{Koord.add((int)(dim/2)+1);}
                }
                ja = check(new Field(Koord),Fields);
                if(ja){
                    return new Field(Koord);
                }
            }
        }

        for(int j=0; j<(int)(Math.pow(3,dim-1)/2)+Math.pow(3,dim-1) ; j++){
            Koord.clear();
            for (int i=0;i<dim;i++){
                if( (j/(int)(Math.pow(2,i))) % 2 == 0){Koord.add(0);}
                else{Koord.add(dim+1);}
            }
            if(check(new Field(Koord),Fields)){
                return new Field(Koord);
            }
        }
        while(!ja){
            Koord.clear();
            for(int i=0;i<dim;i++){
                Koord.add(ThreadLocalRandom.current().nextInt(0, dim+1));
            }      
            ja = check(new Field(Koord),Fields);
        }
        return new Field(Koord);
    }

    public boolean check(Field feld, ArrayList<Field> Fields){
        if(feld.dimensions == dim){
            try{
                for(int i=0;i<dim;i++){
                    if(!(feld.getCoordinates(i)>=0&&feld.getCoordinates(i)<=dim)){
                        return false;
                    }
                }
                if(realPlayerOf(feld,Fields) == -1){
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

    private Field fast(ArrayList<Field> Fields,int IOD){
        boolean fast = false;
        boolean breaking = false;
        Field letztesField = Fields.get(Fields.size()-IOD);
        int[] P = new int[dim];
        int[] D = new int[dim];
        int sp = letztesField.player;
        Field BitteZiehen = new Field();
        for(int j=0; j<(int)(Math.pow(3,dim-1)/2)+Math.pow(3,dim-1) ; j++){
            for (int i=0;i<dim;i++){
                if( (j/(int)(Math.pow(3,i))) % 3 == 0){P[i] = 0;D[i] = 1;}
                else if((j/(int)(Math.pow(3,i)))%3==1){P[i] = letztesField.getCoordinates(i);D[i] = 0;}
                else{P[i] = dim;D[i] = -1;}
            }
            for(int i=0; i<Fields.size() -1;i++){
                int zähler = 0;
                for(int h=0;h<dim;h++){
                    if(letztesField.getCoordinates(h) + D[h] == Fields.get(i).getCoordinates(h)){
                        zähler++;
                    }
                }
                int zähler2 = 0;
                for(int h=0;h<dim;h++){
                    if(letztesField.getCoordinates(h) - D[h] == Fields.get(i).getCoordinates(h)){
                        zähler2++;
                    }
                }
                boolean jaa=false;
                for (int m=0;m<dim;m++){
                    if( (j/(int)(Math.pow(3,m))) % 3 == 0){P[m] = 0;}
                    else if((j/(int)(Math.pow(3,m)))%3==1){P[m] = letztesField.getCoordinates(m);}
                    else{P[m] = dim;}
                }
                int zaehler = 0;
                for(int k=0;k<dim+1;k++){
                    ArrayList<Integer> PArray = new ArrayList<Integer>();
                    for(int m=0;m<dim;m++){
                        PArray.add(P[m]);
                    }
                    Field PField = new Field(PArray);
                    if(realPlayerOf(PField,Fields) == sp){
                        zaehler ++;
                    }
                    else if(realPlayerOf(PField,Fields) == -1){
                        jaa=true;
                        BitteZiehen=PField;
                    }
                    for(int m=0;m<=dim-1;m++){
                        P[m] += D[m];
                    }
                }
                if(zaehler == dim && jaa)
                {
                    fast = true;
                    breaking = true;
                    BitteZiehen.player = 0;
                }
                else{
                    BitteZiehen.player = -1;
                }
                if(breaking){break;}
            }
            if(breaking){break;}
        }
        return BitteZiehen;
    }

    private Field FindLargestLine(ArrayList<Field> Fields,int sp){
        int[] P = new int[dim];
        int[] D = new int[dim];
        Field BitteZiehen = new Field();
        int longestLine = 0;
        BitteZiehen.player = -1;
        for(int var=1;var<Fields.size()+1;var++){
            Field letztesField = Fields.get(Fields.size()-var);
            for(int j=0; j<(int)(Math.pow(3,dim-1)/2)+Math.pow(3,dim-1) ; j++){
                for (int i=0;i<dim;i++){
                    if( (j/(int)(Math.pow(3,i))) % 3 == 0){P[i] = 0;D[i] = 1;}
                    else if((j/(int)(Math.pow(3,i)))%3==1){P[i] = letztesField.getCoordinates(i);D[i] = 0;}
                    else{P[i] = dim;D[i] = -1;}
                }
                for(int i=0; i<Fields.size() -1;i++){
                    int zähler = 0;
                    for(int h=0;h<dim;h++){
                        if(letztesField.getCoordinates(h) + D[h] == Fields.get(i).getCoordinates(h)){
                            zähler++;
                        }
                    }
                    int zähler2 = 0;
                    for(int h=0;h<dim;h++){
                        if(letztesField.getCoordinates(h) - D[h] == Fields.get(i).getCoordinates(h)){
                            zähler2++;
                        }
                    }
                    boolean jaa=true;
                    for (int m=0;m<dim;m++){
                        if( (j/(int)(Math.pow(3,m))) % 3 == 0){P[m] = 0;}
                        else if((j/(int)(Math.pow(3,m)))%3==1){P[m] = letztesField.getCoordinates(m);}
                        else{P[m] = dim;}
                    }
                    int zaehler = 0;
                    Field CacheField = new Field();
                    for(int k=0;k<dim+1;k++){
                        ArrayList<Integer> PArray = new ArrayList<Integer>();
                        for(int m=0;m<dim;m++){
                            PArray.add(P[m]);
                        }
                        Field PField = new Field(PArray);
                        if(realPlayerOf(PField,Fields) == sp){
                            zaehler ++;
                        }
                        else if(realPlayerOf(PField,Fields) == -1){
                            //Frei:Ignorieren;
                            CacheField = PField;
                        }
                        else{
                            //Hier nicht hinsetzen
                            jaa = false;
                            //break;
                        }
                        for(int m=0;m<=dim-1;m++){
                            P[m] += D[m];
                        }
                    }
                    if(zaehler > longestLine && jaa){
                        longestLine = zaehler;
                        BitteZiehen=CacheField;
                        BitteZiehen.player = 0;
                        BitteZiehen.row = longestLine;
                    }
                }
            }
        }
        return BitteZiehen;
    }

    private Field Zwickmühle(ArrayList<Field> Fields,int sp){
        int[] P = new int[dim];
        int[] D = new int[dim];
        ArrayList<ZweiField> Reihen = new ArrayList<ZweiField>();
        Field BitteZiehen = new Field();
        BitteZiehen.player = -1;
        for(int var=1;var<Fields.size()+1;var++){
            Field letztesField = Fields.get(Fields.size()-var);
            if(letztesField.player == sp){
                for(int j=0; j<(int)(Math.pow(3,dim-1)/2)+Math.pow(3,dim-1) ; j++){
                    for (int i=0;i<dim;i++){
                        if( (j/(int)(Math.pow(3,i))) % 3 == 0){P[i] = 0;D[i] = 1;}
                        else if((j/(int)(Math.pow(3,i)))%3==1){P[i] = letztesField.getCoordinates(i);D[i] = 0;}
                        else{P[i] = dim;D[i] = -1;}
                    }
                    for(int i=0; i<Fields.size() -1;i++){
                        boolean jaa=true;
                        for (int m=0;m<dim;m++){
                            if( (j/(int)(Math.pow(3,m))) % 3 == 0){P[m] = 0;}
                            else if((j/(int)(Math.pow(3,m)))%3==1){P[m] = letztesField.getCoordinates(m);}
                            else{P[m] = dim;}
                        }
                        int zaehler = 0;
                        ZweiField CacheField = new ZweiField(dim);
                        for(int k=0;k<dim+1;k++){
                            ArrayList<Integer> PArray = new ArrayList<Integer>();
                            for(int m=0;m<dim;m++){
                                PArray.add(P[m]);
                            }
                            Field PField = new Field(PArray);
                            if(realPlayerOf(PField,Fields) == sp){
                                zaehler ++;
                            }
                            else if(realPlayerOf(PField,Fields) == -1){
                                //Frei:Ignorieren;
                                CacheField.add(PField);
                                //if(zaehler == 3)
                                //jaa = true;
                            }
                            else{
                                //Hier nicht hinsetzen
                                jaa = false;
                                break;
                            }
                            for(int m=0;m<=dim-1;m++){
                                P[m] += D[m];
                            }
                        }
                        if(zaehler == dim-1 && jaa){
                            Reihen.add(CacheField);
                        }
                    }
                }
            }
        }
        for(int i=0;i<Reihen.size()-1;i++){
            for(int j=0;j<Reihen.size()-1;j++){
                if(!Reihen.get(i).equals(Reihen.get(j))){
                    if(Reihen.get(i).hit(Reihen.get(j)) == 0){
                        BitteZiehen = Reihen.get(i).get(1);
                        BitteZiehen.player = 0;
                    }
                    if(Reihen.get(i).hit(Reihen.get(j)) == 1){
                        BitteZiehen = Reihen.get(i).get(0);
                        BitteZiehen.player = 0;
                    }
                }
            }
        }
        return BitteZiehen;
    }

    private Field FindLargestLineThread(ArrayList<Field> Fields,int sp){
        Possible = new ArrayList<Field>();
        Field BitteZiehen = new Field();
        BitteZiehen.player = -1;
        int LL = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i=0;i<Fields.size();i++){
            threadPool.submit(new LongLine(Fields,sp,i,dim,this));
        } 
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        for(int i=0;i<Possible.size();i++){
            if(Possible.get(i).row > LL){
                LL = Possible.get(i).row;
                BitteZiehen = Possible.get(i);
            }
        }
        return BitteZiehen;
    }

    private int realPlayerOf(Field Field,ArrayList<Field> Fields){
        for(int i=0;i<Fields.size();i++){
            int zähler = 0;
            for(int j=0;j<dim;j++){
                if(Fields.get(i).getCoordinates(j) == Field.getCoordinates(j))
                    zähler++;
            }
            if(zähler == dim){
                return Fields.get(i).player;
            }
        }
        return -1;
    }
}

class ZweiField{
    public ArrayList<Field> F = new ArrayList<Field>();
    private int dim;
    public ZweiField(int dim){this.dim=dim;}

    public void add(Field Field2){
        F.add(Field2);
    }

    public boolean equals(ZweiField ander){
        int zähler = 0;
        for(int i=0;i<2;i++){
            for(int j=0;j<dim;j++){
                if(F.get(i).getCoordinates(j) == ander.F.get(i).getCoordinates(j))
                    zähler++;
            }
        }
        if(zähler > (dim*2)-1){
            return true;
        }
        return false;
    }

    public byte hit(ZweiField ander){
        for(int i=0;i<2;i++){
            int zähler = 0;
            for(int j=0;j<dim;j++){
                if(F.get(0).getCoordinates(j) == ander.F.get(i).getCoordinates(j))
                    zähler++;
            }
            if(zähler == dim){
                return (byte)(0);
            }

            zähler = 0;
            for(int j=0;j<dim;j++){
                if(F.get(0).getCoordinates(j) == ander.F.get(i).getCoordinates(j))
                    zähler++;
            }
            if(zähler == dim){
                return (byte)(1);
            }
        }
        return -1;
    }

    public Field get(int i){
        return F.get(i);
    }
}

class LongLine implements Runnable {
    ArrayList<Field> Fields;
    int sp;
    int var;
    int dim;
    SimpleAI ai;
    public LongLine(ArrayList<Field> Fields,int sp,int var,int dim,SimpleAI ai) {
        this.Fields = Fields;
        this.sp = sp;
        this.var = var;
        this.dim = dim;
        this.ai = ai;
    }

    public void run(){
        int[] P = new int[dim];
        int[] D = new int[dim];
        Field BitteZiehen = new Field();
        int longestLine = 0;
        BitteZiehen.player = -1;
        Field letztesField = Fields.get(Fields.size()-var);
        for(int j=0; j<(int)(Math.pow(3,dim-1)/2)+Math.pow(3,dim-1) ; j++){
            for (int i=0;i<dim;i++){
                if( (j/(int)(Math.pow(3,i))) % 3 == 0){P[i] = 0;D[i] = 1;}
                else if((j/(int)(Math.pow(3,i)))%3==1){P[i] = letztesField.getCoordinates(i);D[i] = 0;}
                else{P[i] = dim;D[i] = -1;}
            }
            for(int i=0; i<Fields.size() -1;i++){
                boolean jaa=true;
                for (int m=0;m<dim;m++){
                    if( (j/(int)(Math.pow(3,m))) % 3 == 0){P[m] = 0;}
                    else if((j/(int)(Math.pow(3,m)))%3==1){P[m] = letztesField.getCoordinates(m);}
                    else{P[m] = dim;}
                }
                int zaehler = 0;
                Field CacheField = new Field();
                for(int k=0;k<dim+1;k++){
                    ArrayList<Integer> PArray = new ArrayList<Integer>();
                    for(int m=0;m<dim;m++){
                        PArray.add(P[m]);
                    }
                    Field PField = new Field(PArray);
                    if(realPlayerOf(PField,Fields) == sp){
                        zaehler ++;
                    }
                    else if(realPlayerOf(PField,Fields) == -1){
                        //Frei:Ignorieren;
                        CacheField = PField;
                    }
                    else{
                        //Hier nicht hinsetzen
                        jaa = false;
                        //break;
                    }
                    for(int m=0;m<=dim-1;m++){
                        P[m] += D[m];
                    }
                }
                if(zaehler > longestLine && jaa){
                    longestLine = zaehler;
                    BitteZiehen=CacheField;
                    BitteZiehen.player = 0;
                    BitteZiehen.row = longestLine;
                }
            }
        }
        ai.Possible.add(BitteZiehen);
    }

    private int realPlayerOf(Field Field,ArrayList<Field> Fields){
        for(int i=0;i<Fields.size();i++){
            int zähler = 0;
            for(int j=0;j<dim;j++){
                if(Fields.get(i).getCoordinates(j) == Field.getCoordinates(j))
                    zähler++;
            }
            if(zähler == dim){
                return Fields.get(i).player;
            }
        }
        return -1;
    }
}