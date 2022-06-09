/*
 * Class Name : VwapTrigger
 *
 * Description : Receive data from Market Data and Pricing Data
 *
 * Version : 1.0
 *
 * Date : 26/09/2022
 *
 * Copyright : Victor Perrault
 */

package hsoft;

import java.util.*;

import com.hsoft.codingtest.DataProvider;
import com.hsoft.codingtest.DataProviderFactory;
import com.hsoft.codingtest.MarketDataListener;
import com.hsoft.codingtest.PricingDataListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import hsoft.*;


public class VwapTrigger {
    //get the logger log4j for this class
    private static final Logger logger = LogManager.getLogger(VwapTrigger.class);

    public static void main(String[] args) {

        //get the Instance of the Transacation and faivalue storage Manager
        TransactionStorage dataStorage = TransactionStorage.getInstance();

        DataProvider provider = DataProviderFactory.getDataProvider();

        provider.addMarketDataListener(new MarketDataListener() {
            public synchronized void transactionOccured(String productId, long quantity, double price) {

                //add the new transaction to the history
                dataStorage.addTransaction(productId, quantity, price);

                //store the result of the vwap calculation
                Double resultVwap = dataStorage.vwapCalculate(productId);
                //get the last fairValue
                Double resultFairValue = dataStorage.getFairValue(productId);

                //test the condition and put the result in the info log if it's TEST_PRODUCT or debug otherwise
                //ps : the log file generated is next to the README.md
                if (productId == "TEST_PRODUCT" && resultVwap > resultFairValue){
                    logger.info("VWAP(" + resultVwap + ") > Fairvalue(" + resultFairValue + ")");
                }
                else if (resultVwap > resultFairValue){
                    logger.debug("VWAP(" + resultVwap + ") > Fairvalue(" + resultFairValue + ")");
                }
            }
        });
        provider.addPricingDataListener(new PricingDataListener() {
            public synchronized void fairValueChanged(String productId, double fairValue) {

                //add the new fairValue to the history
                dataStorage.addFairValue(productId, fairValue);

                //get the result of the vwap calculation for the current productId
                Double resultVwap = dataStorage.getLastVwapValue(productId);

                //test the condition and put the result in the info log if it's TEST_PRODUCT or debug otherwise
                //ps : the log file generated is next to the README.md
                if (productId == "TEST_PRODUCT" && resultVwap > fairValue){
                    logger.info("VWAP(" + resultVwap + ") > Fairvalue(" + fairValue + ")");
                }
                else if (resultVwap > fairValue){
                    logger.debug("VWAP(" + resultVwap + ") > Fairvalue(" + fairValue + ")");
                }
            }
        });

        provider.listen();
        // When this method returns, the test is finished and you can check your results in the console
        LogManager.shutdown();
    }

}