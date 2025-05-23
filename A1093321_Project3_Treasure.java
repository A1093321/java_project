import java.util.ArrayList;

public abstract class A1093321_Project3_Treasure extends A1093321_Project3_Card
{
    // The original value of this treasure card.
    private final int value;
    
    public A1093321_Project3_Treasure(int type, int value)
    {
        super(type);
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public abstract void share(ArrayList<A1093321_Project3_Agent> receivers);
}
