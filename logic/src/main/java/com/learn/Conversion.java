package com.learn;

import java.io.Serializable;

public class Conversion implements Serializable {
    private final static long serialVersionUID = -1;

    private double value;
    private Currency currencyA;
    private Currency currencyB;

    public Conversion() {}

    public Conversion(double value, Currency currencyA, Currency currencyB) {
        this.value = value;
        this.currencyA = currencyA;
        this.currencyB = currencyB;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getCurrencyA() {
        return currencyA;
    }

    public void setCurrencyA(Currency currencyA) {
        this.currencyA = currencyA;
    }

    public Currency getCurrencyB() {
        return currencyB;
    }

    public void setCurrencyB(Currency currencyB) {
        this.currencyB = currencyB;
    }

    @Override
    public String toString() {
        return "Conversion of " +
            "value=" + value +
            "from currencyA=" + currencyA +
            "to currencyB=" + currencyB;
    }

    public enum  Currency {
        USD,
        MAD,
        EUR
    }
}
