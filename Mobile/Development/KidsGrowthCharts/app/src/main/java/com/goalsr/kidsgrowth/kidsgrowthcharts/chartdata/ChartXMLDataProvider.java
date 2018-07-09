package com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata;

/**
 * Created by Vinayaka on 12/23/2015.
 */

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.XMLHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ChartXMLDataProvider {

    public static ArrayList<String> xVals = new ArrayList<String>();
    public static ArrayList<Entry> yValsFirstPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsThirdPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsFiftyathPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsNinetySeventhPercentile = new ArrayList<Entry>();
    public static ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    public static int arrayIndex = 0 ;

    public ChartXMLDataProvider(Context context,String gender, String chartType)
    {

        //clear prevoius values;
        //dataSets.clear();
        if (chartType.equals("LenghtHeightWeightHeadCircumference"))
        {
            AssetManager assetManager = context.getAssets() ;
            try
            {

                InputStream inputStream = assetManager.open(gender + "VKModified_0_to_5Years_Weight.xml") ;
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                XMLHandler xmlHandler = new XMLHandler() ;
                xr.setContentHandler(xmlHandler);
                InputSource inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                LineDataSet firstPercentile = new LineDataSet(yValsFirstPercentile,"") ;
                firstPercentile.setDrawCircles(false);
                firstPercentile.setColor(Color.BLACK);
                /*firstPercentile.setDrawFilled(false);
                firstPercentile.setDrawCubic(false);
                firstPercentile.setDrawCircleHole(false);*/

                LineDataSet thirdPercentile = new LineDataSet(yValsThirdPercentile, "3 (-2)") ;
                thirdPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.BLACK);

                LineDataSet fiftyathPercentile = new LineDataSet(yValsFiftyathPercentile, "50 (0)") ;
                fiftyathPercentile.setDrawCircles(false);
                fiftyathPercentile.setColor(Color.BLACK);

                LineDataSet ninetyathPercentile = new LineDataSet(yValsNinetySeventhPercentile, "97 (+2)") ;
                ninetyathPercentile.setDrawCircles(false);
                ninetyathPercentile.setColor(Color.BLACK);

                dataSets.add(firstPercentile) ;
                dataSets.add(thirdPercentile);
                dataSets.add(fiftyathPercentile);
                dataSets.add(ninetyathPercentile);

                inputStream.close();

                inputStream = assetManager.open(gender+"VKModified_0_to_5Years_HeadCircumference.xml") ;
                xmlHandler = new XMLHandler() ;
                xr.setContentHandler(xmlHandler);
                inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                firstPercentile = new LineDataSet(yValsFirstPercentile,"1 (-3)") ;
                firstPercentile.setDrawCircles(false);
                firstPercentile.setColor(Color.GRAY);
                thirdPercentile = new LineDataSet(yValsThirdPercentile, "3 (-2)") ;
                thirdPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.GRAY);
                fiftyathPercentile = new LineDataSet(yValsFiftyathPercentile, "50 (0)") ;
                fiftyathPercentile.setDrawCircles(false);
                fiftyathPercentile.setColor(Color.GRAY);
                ninetyathPercentile = new LineDataSet(yValsNinetySeventhPercentile, "97 (+2)") ;
                ninetyathPercentile.setDrawCircles(false);
                ninetyathPercentile.setColor(Color.GRAY);
               // dataSets.add(firstPercentile) ; //NOT Adding this delibrately since only 3rd, 50th and 97th to be shown.
                dataSets.add(thirdPercentile);
                dataSets.add(fiftyathPercentile);
                dataSets.add(ninetyathPercentile);

                inputStream.close();

                inputStream = assetManager.open(gender+"VKModified_0_to_5Years_Height.xml") ;
                xmlHandler = new XMLHandler() ;
                xr.setContentHandler(xmlHandler);
                inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                firstPercentile = new LineDataSet(yValsFirstPercentile,"1 (-3)") ;
                firstPercentile.setDrawCircles(false);
                firstPercentile.setColor(Color.BLUE);
                thirdPercentile = new LineDataSet(yValsThirdPercentile, "3 (-2)") ;
                thirdPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.BLUE);
                fiftyathPercentile = new LineDataSet(yValsFiftyathPercentile, "50 (0)") ;
                fiftyathPercentile.setDrawCircles(false);
                fiftyathPercentile.setColor(Color.BLUE);
                ninetyathPercentile = new LineDataSet(yValsNinetySeventhPercentile, "97 (+2)") ;
                ninetyathPercentile.setDrawCircles(false);
                ninetyathPercentile.setColor(Color.BLUE);

                dataSets.add(firstPercentile) ;
                dataSets.add(thirdPercentile);
                dataSets.add(fiftyathPercentile);
                dataSets.add(ninetyathPercentile);

                inputStream.close();


            } catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ParserConfigurationException pe)
            {

            }
            catch (SAXException saxe)
            {

            }
        }
        else if (chartType.equals("WeightForHeight"))
        {
            AssetManager assetManager = context.getAssets() ;

            try
            {
                InputStream inputStream = assetManager.open(gender + "VKModified_0_to_5Years_WeightForHeight.xml") ;
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                XMLHandler xmlHandler = new XMLHandler() ;
                xr.setContentHandler(xmlHandler);
                InputSource inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                LineDataSet firstPercentile = new LineDataSet(yValsFirstPercentile,"1 (-3)") ;
                firstPercentile.setDrawCircles(false);
                firstPercentile.setColor(Color.BLACK);

                LineDataSet thirdPercentile = new LineDataSet(yValsThirdPercentile, "3 (-2)") ;
                thirdPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.BLACK);


                LineDataSet fiftyathPercentile = new LineDataSet(yValsFiftyathPercentile, "50 (0)") ;
                fiftyathPercentile.setDrawCircles(false);
                fiftyathPercentile.setColor(Color.BLACK);

                LineDataSet ninetyathPercentile = new LineDataSet(yValsNinetySeventhPercentile, "99 (+3)") ;
                ninetyathPercentile.setDrawCircles(false);
                ninetyathPercentile.setColor(Color.BLACK);

                dataSets.add(firstPercentile) ;
                dataSets.add(thirdPercentile);
                dataSets.add(fiftyathPercentile);
                dataSets.add(ninetyathPercentile);

                inputStream.close();

            } catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ParserConfigurationException pe)
            {

            }
            catch (SAXException saxe)
            {

            }

        }
    }

    public ArrayList<LineDataSet> getDataSet()
    {

        return dataSets ;
    }
}
