/*
 * Class Name : TransactionStorage
 *
 * Description : Store the Transactions as well as the fairValues
 *
 * Version : 1.0
 *
 * Date : 26/09/2022
 *
 * Copyright : Victor Perrault
 */

package hsoft;
import java.util.*;
import hsoft.*;

//I used a Singleton for this because of the mutli-threads subject
class TransactionStorage {

    private static TransactionStorage Storage;

    private Dictionary fairValueDictionnary = new Hashtable();
    //Dictionnary of the transaction (for each key:ProductID a TransactionHistory object)
    private Dictionary transactionDictionnary = new Hashtable<String,TransactionHistory>();

    private TransactionStorage() {
    }

    //get Singleton Instance
    public static TransactionStorage getInstance() {

        // create object if it's not already created
        if(Storage == null) {
            Storage = new TransactionStorage();
        }

        // returns the singleton object
        return Storage;
    }

    //Get the fairValue for a productId
    public Double getFairValue(String _productId){
        Double temporary = (Double) fairValueDictionnary.get(_productId);
        if (temporary!=null){
            return temporary;
        }
        else{
            return 0.0;
        }
    }

    //Get the last VwapValue calculated for a productId
    public Double getLastVwapValue(String _productId){
        TransactionHistory temporary = (TransactionHistory) transactionDictionnary.get(_productId);
        if(temporary!=null) {
            return temporary.getLastVwapValue();
        }
        else{
            return 0.0;
        }
    }

    //Calculate the current Vwap value for a productId
    public Double vwapCalculate(String _productId){
        TransactionHistory temporary = (TransactionHistory) transactionDictionnary.get(_productId);
        if(temporary!=null) {
            return temporary.vwapCalculate();
        }
        else{
            return 0.0;
        }
    }

    //Add transacation to the history of a productId
    public void addTransaction(String _productId,Long _quantityHistory, Double _priceHistory){

        TransactionHistory temporary = (TransactionHistory) transactionDictionnary.get(_productId);
        if(temporary==null){
            TransactionHistory newTransaction = new TransactionHistory();
            newTransaction.addNewTransaction(_quantityHistory,_priceHistory);
            transactionDictionnary.put(_productId,newTransaction);
        }
        else{
            temporary.addNewTransaction(_quantityHistory,_priceHistory);
        }

    }

    //add a fair value to the history of a productId
    public void addFairValue(String productId, Double fairValue){
        fairValueDictionnary.put(productId, fairValue);
    }
}