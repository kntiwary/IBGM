package com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.XMLHandlerFiveToEighteenBMI;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by 140013 on 09-02-2016.
 */
public class ChartXMLDataProviderZeroToEighteenBMI {

    public static ArrayList<String> xVals = new ArrayList<String>();

    public static ArrayList<Entry> yValsThirdPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsFifthPercentile = new ArrayList<Entry>() ;
    public static ArrayList<Entry> yValsTenthPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsTwentyFifthPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsFiftyathPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsSeventyFifthPercentile = new ArrayList<Entry>() ;
    public static ArrayList<Entry> yValsNinetyFifthPercentile = new ArrayList<Entry>() ;

    public static ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    public static int arrayIndex = 0 ;

    public ChartXMLDataProviderZeroToEighteenBMI(Context context, String gender, String chartType) {

        if (chartType.equals("0_to_18_BMI")) {
            AssetManager assetManager = context.getAssets();
            try {
                InputStream inputStream = assetManager.open(gender + "BMI_0_to_18_Years_BMI.xml");
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                XMLHandlerFiveToEighteenBMI xmlHandlerFiveToEighteenBMI = new XMLHandlerFiveToEighteenBMI();
                xr.setContentHandler(xmlHandlerFiveToEighteenBMI);
                InputSource inputSource = new InputSource(inputStream);
                xr.parse(inputSource);


                LineDataSet thirdPercentile = new LineDataSet(yValsThirdPercentile, "3");
                thirdPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.BLACK);

                LineDataSet fifthPercentile = new LineDataSet(yValsFifthPercentile, "5");
                fifthPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.BLACK);

                LineDataSet tenthPercentile = new LineDataSet(yValsTenthPercentile, "10");
                tenthPercentile.setDrawCircles(false);
                tenthPercentile.setColor(Color.BLACK);

                LineDataSet twentyFifthPercentile = new LineDataSet(yValsTwentyFifthPercentile, "25");
                twentyFifthPercentile.setDrawCircles(false);
                twentyFifthPercentile.setColor(Color.BLACK);

                LineDataSet fiftyathPercentile = new LineDataSet(yValsFiftyathPercentile, "50");
                fiftyathPercentile.setDrawCircles(false);
                fiftyathPercentile.setColor(Color.BLACK);

                LineDataSet seventyFifthPercentile = new LineDataSet(yValsSeventyFifthPercentile, "75");
                seventyFifthPercentile.setDrawCircles(false);
                seventyFifthPercentile.setColor(Color.BLACK);

                LineDataSet ninetyFifthPercentile = new LineDataSet(yValsNinetyFifthPercentile, "95");
                ninetyFifthPercentile.setDrawCircles(false);
                ninetyFifthPercentile.setColor(Color.BLACK);


                dataSets.add(thirdPercentile);
                dataSets.add(fifthPercentile);
                dataSets.add(tenthPercentile);
                dataSets.add(twentyFifthPercentile);
                dataSets.add(fiftyathPercentile);
                dataSets.add(seventyFifthPercentile);
                dataSets.add(ninetyFifthPercentile);

                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException pe) {

            } catch (SAXException saxe) {

            }
        }

    }

}
