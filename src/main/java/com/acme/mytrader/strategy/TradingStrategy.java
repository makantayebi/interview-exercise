package com.acme.mytrader.strategy;

import java.util.HashMap;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy implements PriceListener {
    private int lot;
    // private double buyThreshold = 55.0;
    
    private ExecutionService myBroker;

    private HashMap<String, Double> buyThresholds;
    private HashMap<String, Double> sellThresholds;
    public TradingStrategy(ExecutionService broker) {
        myBroker = broker;
        // TODO: set variable in test:
            // setLot 100
            // put buy threshold 55.0 for security "IBM"
        buyThresholds = new HashMap<String,Double>();
        sellThresholds = new HashMap<String,Double>();
    }

    public void priceUpdate(String security, double price)
    {
        buyLowSellHigh(security, price);
    }

    /**
     * This method uses the Mean Reversion strategy to trigger trading ordres.
     * This is implied in the given exercise: Assume the price will bounce back or
     * fall back from current price.
     * @param security The name of the security (instrument)
     * @param price The current market price of the instrument.
     */
    private void buyLowSellHigh(String security, Double price){
        Double buyThreshold = buyThresholds.get(security);
        Double sellThreshold = sellThresholds.get(security);
        if(buyThreshold != null && price < buyThreshold){
            myBroker.buy(security, price, getLot());
        }
        if (sellThreshold != null && price > sellThreshold) {
            myBroker.sell(security, price, getLot());
        }
    }
    
    /**
     * TODO: doc
     * @param security The name of the security (instrument)
     * @param threshold The threshold, below which a buy order should be placed.
     */
    public void setBuyThreshold(String security, Double buyThreshold){
        // IDEA: Check if the buy threshold is lower than an existing sell threshold.
        // Such case is undefined in the user story.

        buyThresholds.put(security, buyThreshold);
    }

    /**
     * Sets the sell threshold for the security.
     * @param security The name of the security (instrument)
     * @param threshold The threshold, above which a buy order should be placed.
     */
    public void setSellThreshold(String security, Double sellThreshold){
        // IDEA: Check if the buy threshold is lower than an existing sell threshold.
        // Such case is undefined in the user story.
        
        sellThresholds.put(security, sellThreshold);
    }

    /**
     * This method clears all the buy and sell price thresholds.
     */
    public void clearTresholds(){
        sellThresholds.clear();
        buyThresholds.clear();
    }

    /**
     * Return the lot attribute.
     * @param lot The volume with which we would like to trade.
     */
    public void setLot(int lot) {
        this.lot = lot;
    }

    /**
     * Set the lot attribute.
     * @return The volume with which we would like to trade.
     */
    public Integer getLot(){
        return lot;
    }
}
