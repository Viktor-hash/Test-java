package hsoft;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VwapTriggerTest {
  private final TransactionStorage dataStorage = TransactionStorage.getInstance();

  @Test
  void FairValueAdded() {
    dataStorage.addFairValue("P1", 11.0);
    assertEquals((double)dataStorage.getFairValue("P1"),(double)11.0);
    dataStorage.addFairValue("P1", 10.9);
    assertEquals((double)dataStorage.getFairValue("P1"),(double)10.9);
    dataStorage.addFairValue("P2", 12.5);
    assertEquals((double)dataStorage.getFairValue("P1"),(double)10.9);
    assertEquals((double)dataStorage.getFairValue("P2"),(double)12.5);
  }

  @Test
  void TransactionOccured() {
    dataStorage.addTransaction("P1", (long)1000, 10.0);
    assertEquals((double)10.0,(double)dataStorage.vwapCalculate("P1"));
    dataStorage.addTransaction("P1", (long)2000, 11.0);
    assertEquals((double)10.666666666666666,(double)dataStorage.vwapCalculate("P1"));
    dataStorage.addTransaction("P2", (long)500, 12.0);
    assertEquals((double)10.666666666666666,(double)dataStorage.vwapCalculate("P1"));
    assertEquals((double)12.0,(double)dataStorage.vwapCalculate("P2"));
  }

}