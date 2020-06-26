package moe.sui.datastruct;

public class DineTraffic {
    public int increaseNum;
    public int currentNum;
    public int posId;
    public String posName;
    public String updateTime;

    public DineTraffic(int iNum, int cNum, String pName, String uTime,int pId){
        increaseNum =iNum;
        currentNum=cNum;
        posName = pName;
        updateTime=uTime;
        posId =pId;
    }

}
