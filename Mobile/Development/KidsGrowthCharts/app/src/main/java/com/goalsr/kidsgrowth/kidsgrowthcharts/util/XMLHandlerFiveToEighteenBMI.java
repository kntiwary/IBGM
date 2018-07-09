package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProviderFiveToEighteenBMI;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Vinayaka on 12/23/2015.
 */
public class XMLHandlerFiveToEighteenBMI extends DefaultHandler {

    boolean bThirdPercentile = false;
    boolean bFifthPercentile = false;
    boolean bTenthPercentile = false;
    boolean bTwentyFifthPercentile = false;
    boolean bFiftyathPercentile = false;
    boolean bSeventyFifthPercentile = false ;
    boolean bNinetyFifthPercentile = false ;


    private ArrayList<String> xVals = new ArrayList<String>();
    private ArrayList<Entry> yValsThirdPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsFifthPercentile = new ArrayList<>() ;
    private ArrayList<Entry> yValsTenthPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsTwentyFifthPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsFiftyathPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsSeventyFifthPercentile = new ArrayList<>() ;
    private ArrayList<Entry> yValsNinetyFifthPercentile = new ArrayList<>() ;


    private ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

   // int arrayIndex = ChartXMLDataProvider.arrayIndex ;
    int arrayIndex = 0 ;

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Year")) {
            xVals.add(arrayIndex, attributes.getValue("YearName")) ;
            arrayIndex++ ;
        } else if (qName.equalsIgnoreCase("ThirdPercentile")) {
            bThirdPercentile = true;
        } else if (qName.equalsIgnoreCase("FifthPercentile")) {
            bFifthPercentile = true;
        } else if (qName.equalsIgnoreCase("TenthPercentile")) {
            bTenthPercentile = true;
        } else if (qName.equalsIgnoreCase("TwentyFifthPercentile")) {
            bTwentyFifthPercentile = true;
        } else if (qName.equalsIgnoreCase("FiftyathPercentile")) {
            bFiftyathPercentile = true;
        }
        else if(qName.equalsIgnoreCase("SeventyFifthPercentile")) {
            bSeventyFifthPercentile = true;
        }
        else if(qName.equalsIgnoreCase("NinetyFifthPercentile")) {
            bNinetyFifthPercentile = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Year")) {
            //System.out.println("End Element :" + qName);

            ChartXMLDataProviderFiveToEighteenBMI.xVals = this.xVals ;
            ChartXMLDataProviderFiveToEighteenBMI.yValsThirdPercentile = this.yValsThirdPercentile ;
            ChartXMLDataProviderFiveToEighteenBMI.yValsFifthPercentile = this.yValsFifthPercentile ;
            ChartXMLDataProviderFiveToEighteenBMI.yValsTenthPercentile = this.yValsTenthPercentile ;
            ChartXMLDataProviderFiveToEighteenBMI.yValsTwentyFifthPercentile = this.yValsTwentyFifthPercentile ;
            ChartXMLDataProviderFiveToEighteenBMI.yValsFiftyathPercentile = this.yValsFiftyathPercentile ;
            ChartXMLDataProviderFiveToEighteenBMI.yValsSeventyFifthPercentile = this.yValsSeventyFifthPercentile ;
            ChartXMLDataProviderFiveToEighteenBMI.yValsNinetyFifthPercentile = this.yValsNinetyFifthPercentile ;


        }
    }

    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {
        if (bThirdPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsThirdPercentile.add(new Entry(ft, arrayIndex-1));
            bThirdPercentile = false;
        }else if (bFifthPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsFifthPercentile.add(new Entry(ft, arrayIndex-1));
            bFifthPercentile = false;
        }
        else if (bTenthPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsTenthPercentile.add(new Entry(ft, arrayIndex-1));
            bTenthPercentile = false;
        } else if (bTwentyFifthPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsTwentyFifthPercentile.add(new Entry(ft, arrayIndex-1));
            bTwentyFifthPercentile = false;
        } else if (bFiftyathPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsFiftyathPercentile.add(new Entry(ft, arrayIndex-1));
            bFiftyathPercentile = false;
        } else if (bSeventyFifthPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsSeventyFifthPercentile.add(new Entry(ft, arrayIndex-1));
            bSeventyFifthPercentile = false;
        } else if (bNinetyFifthPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsNinetyFifthPercentile.add(new Entry(ft, arrayIndex-1));
            bNinetyFifthPercentile = false;
        }

    }
}
