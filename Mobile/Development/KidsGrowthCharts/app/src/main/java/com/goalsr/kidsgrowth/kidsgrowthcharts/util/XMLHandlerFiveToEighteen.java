package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProvider;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProviderFiveToEighteen;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Vinayaka on 12/23/2015.
 */
public class XMLHandlerFiveToEighteen extends DefaultHandler {

    boolean bThirdPercentile = false;
    boolean bTenthPercentile = false;
    boolean bTwentyFifthPercentile = false;
    boolean bFiftyathPercentile = false;
    boolean bSeventyFifthPercentile = false ;
    boolean bNinetyathPercentile = false ;
    boolean bNinetySeventhPercentile = false;

    private ArrayList<String> xVals = new ArrayList<String>();
    private ArrayList<Entry> yValsThirdPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsTenthPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsTwentyFifthPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsFiftyathPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsSeventyFifthPercentile = new ArrayList<>() ;
    private ArrayList<Entry> yValsNinetyathPercentile = new ArrayList<>() ;
    private ArrayList<Entry> yValsNinetySeventhPercentile = new ArrayList<>() ;

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
        } else if (qName.equalsIgnoreCase("TenthPercentile")) {
            bTenthPercentile = true;
        } else if (qName.equalsIgnoreCase("TwentyFifthPercentile")) {
            bTwentyFifthPercentile = true;
        }
        else if (qName.equalsIgnoreCase("FiftyathPercentile")) {
            bFiftyathPercentile = true;
        }
        else if(qName.equalsIgnoreCase("SeventyFifthPercentile")) {
            bSeventyFifthPercentile = true;
        }
        else if(qName.equalsIgnoreCase("NinetyathPercentile")) {
            bNinetyathPercentile = true;
        }
        else if(qName.equalsIgnoreCase("NinetySeventhPercentile")) {
            bNinetySeventhPercentile = true;
        }

    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Year")) {
            //System.out.println("End Element :" + qName);

            ChartXMLDataProviderFiveToEighteen.xVals = this.xVals ;
            ChartXMLDataProviderFiveToEighteen.yValsThirdPercentile = this.yValsThirdPercentile ;
            ChartXMLDataProviderFiveToEighteen.yValsTenthPercentile = this.yValsTenthPercentile ;
            ChartXMLDataProviderFiveToEighteen.yValsTwentyFifthPercentile = this.yValsTwentyFifthPercentile ;
            ChartXMLDataProviderFiveToEighteen.yValsFiftyathPercentile = this.yValsFiftyathPercentile ;
            ChartXMLDataProviderFiveToEighteen.yValsSeventyFifthPercentile = this.yValsSeventyFifthPercentile ;
            ChartXMLDataProviderFiveToEighteen.yValsNinetyathPercentile = this.yValsNinetyathPercentile ;
            ChartXMLDataProviderFiveToEighteen.yValsNinetySeventhPercentile = this.yValsNinetySeventhPercentile ;
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
        } else if (bTenthPercentile) {
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
        } else if (bNinetyathPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsNinetyathPercentile.add(new Entry(ft, arrayIndex-1));
            bNinetyathPercentile = false;
        } else if (bNinetySeventhPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsNinetySeventhPercentile.add(new Entry(ft, arrayIndex-1));
            bNinetySeventhPercentile = false;
        }

    }
}
