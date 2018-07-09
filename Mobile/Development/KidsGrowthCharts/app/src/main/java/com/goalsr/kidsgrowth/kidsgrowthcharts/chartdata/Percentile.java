package com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata;

/**
 * Created by 140013 on 04-04-2016.
 */


public class Percentile {

    Double thirdPercentile; //ThirdPercentile Ht Wt BMI
    Double fifthPercentile; // FifthPercentile BMI only
    Double tenthPercentile; //TenthPercentile  Ht Wt BMI
    Double twentyFifthPercentile; //TwentyFifthPercentile  Ht Wt BMI
    Double fiftyathPercentile; // FiftyathPercentile  Ht Wt BMI
    Double seventyFifthPercentile; //SeventyFifthPercentile  Ht Wt BMI
    Double ninetyathPercentile; //NinetyathPercentile  Ht Wt
    Double ninetyFifthPercentile; //NinetyFifthPercentile only BMI



    static Double height;





    public static Double getHeight() {
        return height;
    }

    public static void setHeight(Double height) {
        height = height;
        System.out.println(height);
    }



    Double ninetySeventhPercentile; //NinetySeventhPercentile  Ht Wt

    public Double getThirdPercentile() {
        return thirdPercentile;
    }

    public void setThirdPercentile(Double thirdPercentile) {
        this.thirdPercentile = thirdPercentile;
    }


    public Double getFifthPercentile() {
        return fifthPercentile;
    }

    public void setFifthPercentile(Double fifthPercentile) {
        this.fifthPercentile = fifthPercentile;
    }

    public Double getTenthPercentile() {
        return tenthPercentile;
    }

    public void setTenthPercentile(Double tenthPercentile) {
        this.tenthPercentile = tenthPercentile;
    }

    public Double getTwentyFifthPercentile() {
        return twentyFifthPercentile;
    }

    public void setTwentyFifthPercentile(Double twentyFifthPercentile) {
        this.twentyFifthPercentile = twentyFifthPercentile;
    }

    public Double getFiftyathPercentile() {
        return fiftyathPercentile;
    }

    public void setFiftyathPercentile(Double fiftyathPercentile) {
        this.fiftyathPercentile = fiftyathPercentile;
    }

    public Double getSeventyFifthPercentile() {
        return seventyFifthPercentile;
    }

    public void setSeventyFifthPercentile(Double seventyFifthPercentile) {
        this.seventyFifthPercentile = seventyFifthPercentile;
    }

    public Double getNinetyathPercentile() {
        return ninetyathPercentile;
    }

    public void setNinetyathPercentile(Double ninetyathPercentile) {
        this.ninetyathPercentile = ninetyathPercentile;
    }

    public Double getNinetyFifthPercentile() {
        return ninetyFifthPercentile;
    }

    public void setNinetyFifthPercentile(Double ninetyFifthPercentile) {
        this.ninetyFifthPercentile = ninetyFifthPercentile;
    }

    public Double getNinetySeventhPercentile() {
        return ninetySeventhPercentile;
    }

    public void setNinetySeventhPercentile(Double ninetySeventhPercentile) {
        this.ninetySeventhPercentile = ninetySeventhPercentile;
    }

    @Override
    public String toString() {
        return "Percentile [\nthirdPercentile=" + thirdPercentile
                + ", \nfifthPercentile=" + fifthPercentile + ", \ntenthPercentile="
                + tenthPercentile + ", \ntwentyFifthPercentile="
                + twentyFifthPercentile + ", \nfiftyathPercentile="
                + fiftyathPercentile + ", \nseventyFifthPercentile="
                + seventyFifthPercentile + ", \nninetyathPercentile="
                + ninetyathPercentile + ", \nninetyFifthPercentile="
                + ninetyFifthPercentile + ", \nninetySeventhPercentile="
                + ninetySeventhPercentile + "]";
    }

    public String getLiesBetweenValue(Double value, String xmlType){

        if(xmlType.equals("Weight") || xmlType.equals("Height")){




            if(this.getThirdPercentile() != null && value < this.getThirdPercentile()){
                return " is below 3rd Percentile";
            }else if(this.getThirdPercentile() !=null &&  this.getTenthPercentile() != null && value >=  this.getThirdPercentile() && value <  this.getTenthPercentile()){
                return "Lies between 3rd and 10th Percentile";
            }else if(this.getTenthPercentile() != null &&  this.getTwentyFifthPercentile() != null && value >=  this.getTenthPercentile() && value <  this.getTwentyFifthPercentile() ){
                return "Lies between 10th and 25th Percentile";
            }else if( this.getTwentyFifthPercentile() != null && this.getFiftyathPercentile() != null && value >=  this.getTwentyFifthPercentile() && value <  this.getFiftyathPercentile() ){
                return "Lies between 25th and 50th Percentile";
            }else if(  this.getFiftyathPercentile() != null && this.getSeventyFifthPercentile() != null && value >=  this.getFiftyathPercentile() && value <  this.getSeventyFifthPercentile()){
                return "Lies between 50th and 75th Percentile";
            }else if(  this.getSeventyFifthPercentile() != null &&   this.getNinetyathPercentile() != null && value >=  this.getSeventyFifthPercentile() && value <  this.getNinetyathPercentile() ){
                return "Lies between 75th  and 90th Percentile";
            }else if(  this.getNinetyathPercentile()!= null &&   this.getNinetySeventhPercentile() != null && value >=  this.getNinetyathPercentile() && value <  this.getNinetySeventhPercentile() ){
                return "Lies between 90th  and 97th Percentile";
            }else if(  this.getNinetySeventhPercentile() != null && value >   this.getNinetySeventhPercentile() ){
                return "is above 97th Percentile";
            }
        }
        if(xmlType.equals("BMI")){
            if(this.getThirdPercentile() != null && value < this.getThirdPercentile()){
                return "is below 3rd Percentile";
            }else if(this.getThirdPercentile() !=null &&  this.getTenthPercentile() != null && value >=  this.getThirdPercentile() && value <  this.getTenthPercentile()){
                return "Lies between 3rd and 10th Percentile";
            }else if(this.getTenthPercentile() != null &&  this.getTwentyFifthPercentile() != null && value >=  this.getTenthPercentile() && value <  this.getTwentyFifthPercentile() ){
                return "Lies between 10th and 25th Percentile";
            }else if(this.getTwentyFifthPercentile() != null && this.getFiftyathPercentile() != null && value >=  this.getTwentyFifthPercentile() && value <  this.getFiftyathPercentile() ){
                return "Lies between 25th and 50th Percentile";
            }else if(this.getFiftyathPercentile() != null && this.getSeventyFifthPercentile() != null && value >=  this.getFiftyathPercentile() && value <  this.getSeventyFifthPercentile()){
                return "Lies between 50th and 75th Percentile";
            }else if(this.getSeventyFifthPercentile() != null &&   this.getNinetyFifthPercentile() != null && value >=  this.getSeventyFifthPercentile() && value <  this.getNinetyFifthPercentile() ){
                return "Lies between 75th and 95th Percentile";
            } else if(this.getNinetyFifthPercentile() != null && value >  this.getNinetyFifthPercentile() ){
                return "is above 95th Percentile";
            }
        }
        return "No reference value found!!";
    }
}
