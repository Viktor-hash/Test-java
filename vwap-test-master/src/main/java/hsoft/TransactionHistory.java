/*
 * Class Name : TransactionHistory
 *
 * Description : Store the 5 last Transaction values for a ProductId
 *
 * Version : 1.0
 *
 * Date : 26/09/2022
 *
 * Copyright : Victor Perrault
 */


package hsoft;
import java.util.*;

public class TransactionHistory {

    public ArrayList<Long> quantityHistory = new ArrayList<Long>();
    private ArrayList<Double> priceHistory = new ArrayList<Double>();
    private Double lastVwapValue = 0.0;

    //calculate the vwap value of the current ProductId corresponding to this instance
    public Double vwapCalculate(){
        Double numerateur= 0.0;
        Double denominateur= 0.0;
        for(int i=0; i<quantityHistory.size();i++){
            numerateur+=quantityHistory.get(i)*priceHistory.get(i);
            denominateur+=quantityHistory.get(i);
        }
        if(denominateur!=0.0) {
            lastVwapValue = numerateur / denominateur;
            return lastVwapValue;
        }
        else{
            return 0.0;
        }
    }

    public TransactionHistory(){
    }

    //get the last vwap value of the current ProductId corresponding to this instance
    public Double getLastVwapValue(){
        return lastVwapValue;
    }

    //add a new transaction to the history of the current ProductId corresponding to this instance
    public void addNewTransaction(Long _quantityHistory, Double _priceHistory){
        quantityHistory.add(_quantityHistory);
        priceHistory.add(_priceHistory);
        //check if the quantity is more than 5 and remove the first one if it's the case
        if(quantityHistory.size()>5){
            quantityHistory.remove(0);
            priceHistory.remove(0);
        }
    }
}