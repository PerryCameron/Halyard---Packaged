package com.ecsail.structures;

import java.util.Objects;

public class AgesDTO {

    int zeroToTen;
    int tenToNineteen;
    int twentyToTwentyNine;
    int thrityToThirtyNine;
    int fourtyToFourtyNine;
    int fiftyToFiftyNine;
    int sixtyToSixtyNine;
    int seventyToSeventyNine;
    int overEighty;

    public AgesDTO(int zeroToTen, int tenToNineteen, int twentyToTwentyNine, int thrityToThirtyNine, int fourtyToFourtyNine, int fiftyToFiftyNine, int sixtyToSixtyNine, int seventyToSeventyNine, int overEighty) {
        this.zeroToTen = zeroToTen;
        this.tenToNineteen = tenToNineteen;
        this.twentyToTwentyNine = twentyToTwentyNine;
        this.thrityToThirtyNine = thrityToThirtyNine;
        this.fourtyToFourtyNine = fourtyToFourtyNine;
        this.fiftyToFiftyNine = fiftyToFiftyNine;
        this.sixtyToSixtyNine = sixtyToSixtyNine;
        this.seventyToSeventyNine = seventyToSeventyNine;
        this.overEighty = overEighty;
    }

    public int getZeroToTen() {
        return zeroToTen;
    }

    public void setZeroToTen(int zeroToTen) {
        this.zeroToTen = zeroToTen;
    }

    public int getTenToNineteen() {
        return tenToNineteen;
    }

    public void setTenToNineteen(int tenToNineteen) {
        this.tenToNineteen = tenToNineteen;
    }

    public int getTwentyToTwentyNine() {
        return twentyToTwentyNine;
    }

    public void setTwentyToTwentyNine(int twentyToTwentyNine) {
        this.twentyToTwentyNine = twentyToTwentyNine;
    }

    public int getThrityToThirtyNine() {
        return thrityToThirtyNine;
    }

    public void setThrityToThirtyNine(int thrityToThirtyNine) {
        this.thrityToThirtyNine = thrityToThirtyNine;
    }

    public int getFourtyToFourtyNine() {
        return fourtyToFourtyNine;
    }

    public void setFourtyToFourtyNine(int fourtyToFourtyNine) {
        this.fourtyToFourtyNine = fourtyToFourtyNine;
    }

    public int getFiftyToFiftyNine() {
        return fiftyToFiftyNine;
    }

    public void setFiftyToFiftyNine(int fiftyToFiftyNine) {
        this.fiftyToFiftyNine = fiftyToFiftyNine;
    }

    public int getSixtyToSixtyNine() {
        return sixtyToSixtyNine;
    }

    public void setSixtyToSixtyNine(int sixtyToSixtyNine) {
        this.sixtyToSixtyNine = sixtyToSixtyNine;
    }

    public int getSeventyToSeventyNine() {
        return seventyToSeventyNine;
    }

    public void setSeventyToSeventyNine(int seventyToSeventyNine) {
        this.seventyToSeventyNine = seventyToSeventyNine;
    }

    public int getOverEighty() {
        return overEighty;
    }

    public void setOverEighty(int overEighty) {
        this.overEighty = overEighty;
    }

    @Override
    public String toString() {
        return "AgesDTO{" +
                "zeroToTen=" + zeroToTen +
                ", tenToNineteen=" + tenToNineteen +
                ", twentyToTwentyNine=" + twentyToTwentyNine +
                ", thrityToThirtyNine=" + thrityToThirtyNine +
                ", fourtyToFourtyNine=" + fourtyToFourtyNine +
                ", fiftyToFiftyNine=" + fiftyToFiftyNine +
                ", sixtyToSixtyNine=" + sixtyToSixtyNine +
                ", seventyToSeventyNine=" + seventyToSeventyNine +
                ", overEighty=" + overEighty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgesDTO agesDTO = (AgesDTO) o;
        return zeroToTen == agesDTO.zeroToTen && tenToNineteen == agesDTO.tenToNineteen && twentyToTwentyNine == agesDTO.twentyToTwentyNine && thrityToThirtyNine == agesDTO.thrityToThirtyNine && fourtyToFourtyNine == agesDTO.fourtyToFourtyNine && fiftyToFiftyNine == agesDTO.fiftyToFiftyNine && sixtyToSixtyNine == agesDTO.sixtyToSixtyNine && seventyToSeventyNine == agesDTO.seventyToSeventyNine && overEighty == agesDTO.overEighty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zeroToTen, tenToNineteen, twentyToTwentyNine, thrityToThirtyNine, fourtyToFourtyNine, fiftyToFiftyNine, sixtyToSixtyNine, seventyToSeventyNine, overEighty);
    }
}
