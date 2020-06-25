package moe.sui.datastruct;

public class Position {
    public int posId;
    public String posName;
    public int floor;

    public Position(int pI, String pN, int f){

        posId=pI;
        posName = pN+f+"层";
        floor = f;
    }
}
