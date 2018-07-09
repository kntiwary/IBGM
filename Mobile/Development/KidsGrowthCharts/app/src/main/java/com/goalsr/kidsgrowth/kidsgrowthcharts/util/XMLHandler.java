package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata.ChartXMLDataProvider;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Vinayaka on 12/23/2015.
 */
public class XMLHandler extends DefaultHandler {

    boolean bFirstPercentile = false;
    boolean bThirdPercentile = false;
    boolean bFiftyathPercentile = false;
    boolean bNinetySeventhPercentile = false;

    private ArrayList<String> xVals = new ArrayList<String>();
    private ArrayList<Entry> yValsFirstPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsThirdPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsFiftyathPercentile = new ArrayList<Entry>();
    private ArrayList<Entry> yValsNinetySeventhPercentile = new ArrayList<Entry>();
    private ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

   // int arrayIndex = ChartXMLDataProvider.arrayIndex ;
    int arrayIndex = 0 ;

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Month")) {
            xVals.add(arrayIndex, attributes.getValue("MonthName")) ;
            arrayIndex++ ;
        } else if (qName.equalsIgnoreCase("FirstPercentile")) {
            bFirstPercentile = true;
        } else if (qName.equalsIgnoreCase("ThirdPercentile")) {
            bThirdPercentile = true;
        } else if (qName.equalsIgnoreCase("FiftyathPercentile")) {
            bFiftyathPercentile = true;
        }
        else if (qName.equalsIgnoreCase("NinetySeventhPercentile")) {
            bNinetySeventhPercentile = true;
        }


    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Month")) {
            //System.out.println("End Element :" + qName);

            ChartXMLDataProvider.xVals = this.xVals ;
            ChartXMLDataProvider.yValsFirstPercentile = this.yValsFirstPercentile ;
            ChartXMLDataProvider.yValsThirdPercentile = this.yValsThirdPercentile ;
            ChartXMLDataProvider.yValsFiftyathPercentile = this.yValsFiftyathPercentile ;
            ChartXMLDataProvider.yValsNinetySeventhPercentile = this.yValsNinetySeventhPercentile ;

        }
    }

    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {
        if (bFirstPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsFirstPercentile.add(new Entry(ft, arrayIndex-1));
            bFirstPercentile = false;
        } else if (bThirdPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsThirdPercentile.add(new Entry(ft, arrayIndex-1));
            bThirdPercentile = false;
        } else if (bFiftyathPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsFiftyathPercentile.add(new Entry(ft, arrayIndex-1));
            bFiftyathPercentile = false;
        } else if (bNinetySeventhPercentile) {
            String bfirstpvalue = new String(ch, start, length) ;
            float ft = Float.valueOf(bfirstpvalue) ;
            yValsNinetySeventhPercentile.add(new Entry(ft, arrayIndex-1));
            bNinetySeventhPercentile = false;
        }
    }
}
