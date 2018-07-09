package com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata;

/**
 * Created by Vinayaka on 12/23/2015.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.XMLHandler;
import com.goalsr.kidsgrowth.kidsgrowthcharts.util.XMLHandlerFiveToEighteen;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ChartXMLDataProviderFiveToEighteen {

    public static ArrayList<String> xVals = new ArrayList<String>();

    public static ArrayList<Entry> yValsThirdPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsTenthPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsTwentyFifthPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsFiftyathPercentile = new ArrayList<Entry>();
    public static ArrayList<Entry> yValsSeventyFifthPercentile = new ArrayList<Entry>() ;
    public static ArrayList<Entry> yValsNinetyathPercentile = new ArrayList<Entry>() ;
    public static ArrayList<Entry> yValsNinetySeventhPercentile = new ArrayList<Entry>() ;

    public static ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    public static int arrayIndex = 0 ;

    public ChartXMLDataProviderFiveToEighteen(Context context, String gender, String chartType)
    {


        if (chartType.equals("IAPHeightWeight"))
        {
            AssetManager assetManager = context.getAssets() ;
            try
            {
               InputStream inputStream = assetManager.open(gender + "IAP_5_to_18Years_Weight.xml") ;
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                XMLHandlerFiveToEighteen xmlHandlerFiveToEighteen = new XMLHandlerFiveToEighteen() ;
                xr.setContentHandler(xmlHandlerFiveToEighteen);
                InputSource inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                LineDataSet thirdPercentile = new LineDataSet(yValsThirdPercentile, "3") ;
                thirdPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.BLUE);

                LineDataSet tenthPercentile = new LineDataSet(yValsTenthPercentile, "10") ;
                tenthPercentile.setDrawCircles(false);
                tenthPercentile.setColor(Color.BLUE);

                LineDataSet twentyFifthPercentile = new LineDataSet(yValsTwentyFifthPercentile, "25") ;
                twentyFifthPercentile.setDrawCircles(false);
                twentyFifthPercentile.setColor(Color.BLUE);

                LineDataSet fiftyathPercentile = new LineDataSet(yValsFiftyathPercentile, "50") ;
                fiftyathPercentile.setDrawCircles(false);
                fiftyathPercentile.setColor(Color.BLUE);

                LineDataSet seventyFifthPercentile = new LineDataSet(yValsSeventyFifthPercentile, "75") ;
                seventyFifthPercentile.setDrawCircles(false);
                seventyFifthPercentile.setColor(Color.BLUE);

                LineDataSet ninetyathPercentile = new LineDataSet(yValsNinetyathPercentile, "90") ;
                ninetyathPercentile.setDrawCircles(false);
                ninetyathPercentile.setColor(Color.BLUE);

                LineDataSet ninetySeventhPercentile = new LineDataSet(yValsNinetySeventhPercentile, "97") ;
                ninetySeventhPercentile.setDrawCircles(false);
                ninetySeventhPercentile.setColor(Color.BLUE);

                dataSets.add(thirdPercentile) ;
                dataSets.add(tenthPercentile);
                dataSets.add(twentyFifthPercentile);
                dataSets.add(fiftyathPercentile);
                dataSets.add(seventyFifthPercentile);
                dataSets.add(ninetyathPercentile);
                dataSets.add(ninetySeventhPercentile);

                inputStream.close();

                inputStream = assetManager.open(gender+"IAP_5_to_18Years_Height.xml") ;
                xmlHandlerFiveToEighteen = new XMLHandlerFiveToEighteen() ;
                xr.setContentHandler(xmlHandlerFiveToEighteen);
                inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                LineDataSet thirdPercentile1 = new LineDataSet(yValsThirdPercentile, "3") ;
                thirdPercentile1.setDrawCircles(false);
                thirdPercentile1.setColor(Color.BLACK);

                LineDataSet tenthPercentile1 = new LineDataSet(yValsTenthPercentile, "10") ;
                tenthPercentile1.setDrawCircles(false);
                tenthPercentile1.setColor(Color.BLACK);

                LineDataSet twentyFifthPercentile1 = new LineDataSet(yValsTwentyFifthPercentile, "25") ;
                twentyFifthPercentile1.setDrawCircles(false);
                twentyFifthPercentile1.setColor(Color.BLACK);

                LineDataSet fiftyathPercentile1 = new LineDataSet(yValsFiftyathPercentile, "50") ;
                fiftyathPercentile1.setDrawCircles(false);
                fiftyathPercentile1.setColor(Color.BLACK);

                LineDataSet seventyFifthPercentile1 = new LineDataSet(yValsSeventyFifthPercentile, "75") ;
                seventyFifthPercentile1.setDrawCircles(false);
                seventyFifthPercentile1.setColor(Color.BLACK);

                LineDataSet ninetyathPercentile1 = new LineDataSet(yValsNinetyathPercentile, "90") ;
                ninetyathPercentile1.setDrawCircles(false);
                ninetyathPercentile1.setColor(Color.BLACK);

                LineDataSet ninetySeventhPercentile1 = new LineDataSet(yValsNinetySeventhPercentile, "97") ;
                ninetySeventhPercentile1.setDrawCircles(false);
                ninetySeventhPercentile1.setColor(Color.BLACK);

                dataSets.add(thirdPercentile1) ;
                dataSets.add(tenthPercentile1);
                dataSets.add(twentyFifthPercentile1);
                dataSets.add(fiftyathPercentile1);
                dataSets.add(seventyFifthPercentile1);
                dataSets.add(ninetyathPercentile1);
                dataSets.add(ninetySeventhPercentile1);

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
        }else if (chartType.equals("IAPHeightWeight0_18"))
        {
            AssetManager assetManager = context.getAssets() ;
            try
            {
                InputStream inputStream = assetManager.open(gender + "_0_to_18_Weight.xml") ;
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                XMLHandlerFiveToEighteen xmlHandlerFiveToEighteen = new XMLHandlerFiveToEighteen() ;
                xr.setContentHandler(xmlHandlerFiveToEighteen);
                InputSource inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                LineDataSet thirdPercentile = new LineDataSet(yValsThirdPercentile, "3") ;
                thirdPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.BLUE);

                LineDataSet tenthPercentile = new LineDataSet(yValsTenthPercentile, "10") ;
                tenthPercentile.setDrawCircles(false);
                tenthPercentile.setColor(Color.BLUE);

                LineDataSet twentyFifthPercentile = new LineDataSet(yValsTwentyFifthPercentile, "25") ;
                twentyFifthPercentile.setDrawCircles(false);
                twentyFifthPercentile.setColor(Color.BLUE);

                LineDataSet fiftyathPercentile = new LineDataSet(yValsFiftyathPercentile, "50") ;
                fiftyathPercentile.setDrawCircles(false);
                fiftyathPercentile.setColor(Color.BLUE);

                LineDataSet seventyFifthPercentile = new LineDataSet(yValsSeventyFifthPercentile, "75") ;
                seventyFifthPercentile.setDrawCircles(false);
                seventyFifthPercentile.setColor(Color.BLUE);

                LineDataSet ninetyathPercentile = new LineDataSet(yValsNinetyathPercentile, "90") ;
                ninetyathPercentile.setDrawCircles(false);
                ninetyathPercentile.setColor(Color.BLUE);

                LineDataSet ninetySeventhPercentile = new LineDataSet(yValsNinetySeventhPercentile, "97") ;
                ninetySeventhPercentile.setDrawCircles(false);
                ninetySeventhPercentile.setColor(Color.BLUE);

                dataSets.add(thirdPercentile) ;
                dataSets.add(tenthPercentile);
                dataSets.add(twentyFifthPercentile);
                dataSets.add(fiftyathPercentile);
                dataSets.add(seventyFifthPercentile);
                dataSets.add(ninetyathPercentile);
                dataSets.add(ninetySeventhPercentile);

                inputStream.close();

                inputStream = assetManager.open(gender+"_0_to_18_Height.xml") ;
                xmlHandlerFiveToEighteen = new XMLHandlerFiveToEighteen() ;
                xr.setContentHandler(xmlHandlerFiveToEighteen);
                inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                LineDataSet thirdPercentile1 = new LineDataSet(yValsThirdPercentile, "3") ;
                thirdPercentile1.setDrawCircles(false);
                thirdPercentile1.setColor(Color.BLACK);

                LineDataSet tenthPercentile1 = new LineDataSet(yValsTenthPercentile, "10") ;
                tenthPercentile1.setDrawCircles(false);
                tenthPercentile1.setColor(Color.BLACK);

                LineDataSet twentyFifthPercentile1 = new LineDataSet(yValsTwentyFifthPercentile, "25") ;
                twentyFifthPercentile1.setDrawCircles(false);
                twentyFifthPercentile1.setColor(Color.BLACK);

                LineDataSet fiftyathPercentile1 = new LineDataSet(yValsFiftyathPercentile, "50") ;
                fiftyathPercentile1.setDrawCircles(false);
                fiftyathPercentile1.setColor(Color.BLACK);

                LineDataSet seventyFifthPercentile1 = new LineDataSet(yValsSeventyFifthPercentile, "75") ;
                seventyFifthPercentile1.setDrawCircles(false);
                seventyFifthPercentile1.setColor(Color.BLACK);

                LineDataSet ninetyathPercentile1 = new LineDataSet(yValsNinetyathPercentile, "90") ;
                ninetyathPercentile1.setDrawCircles(false);
                ninetyathPercentile1.setColor(Color.BLACK);

                LineDataSet ninetySeventhPercentile1 = new LineDataSet(yValsNinetySeventhPercentile, "97") ;
                ninetySeventhPercentile1.setDrawCircles(false);
                ninetySeventhPercentile1.setColor(Color.BLACK);

                dataSets.add(thirdPercentile1) ;
                dataSets.add(tenthPercentile1);
                dataSets.add(twentyFifthPercentile1);
                dataSets.add(fiftyathPercentile1);
                dataSets.add(seventyFifthPercentile1);
                dataSets.add(ninetyathPercentile1);
                dataSets.add(ninetySeventhPercentile1);

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
        else if (chartType.equals("BMI"))
        {
            AssetManager assetManager = context.getAssets() ;

            try
            {
                InputStream inputStream = assetManager.open(gender + "IAP_5_to_18Years_BMI.xml") ;
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                XMLHandlerFiveToEighteen xmlHandlerFiveToEighteen = new XMLHandlerFiveToEighteen() ;
                xr.setContentHandler(xmlHandlerFiveToEighteen);
                InputSource inputSource = new InputSource(inputStream) ;
                xr.parse(inputSource);

                LineDataSet thirdPercentile = new LineDataSet(yValsThirdPercentile, "3") ;
                thirdPercentile.setDrawCircles(false);
                thirdPercentile.setColor(Color.GREEN);


                LineDataSet tenthPercentile = new LineDataSet(yValsTenthPercentile, "5") ;
                tenthPercentile.setDrawCircles(false);
                tenthPercentile.setColor(Color.GREEN);

                LineDataSet twentyFifthPercentile = new LineDataSet(yValsTwentyFifthPercentile, "25") ;
                twentyFifthPercentile.setDrawCircles(false);
                twentyFifthPercentile.setColor(Color.GREEN);

                LineDataSet fiftyathPercentile = new LineDataSet(yValsFiftyathPercentile, "50") ;
                fiftyathPercentile.setDrawCircles(false);
                fiftyathPercentile.setColor(Color.GREEN);

                LineDataSet seventyFifthPercentile = new LineDataSet(yValsSeventyFifthPercentile, "75") ;
                seventyFifthPercentile.setDrawCircles(false);
                seventyFifthPercentile.setColor(Color.GREEN);

                LineDataSet ninetyathPercentile = new LineDataSet(yValsNinetyathPercentile, "90") ;
                ninetyathPercentile.setDrawCircles(false);
                ninetyathPercentile.setColor(Color.GREEN);

                LineDataSet ninetySeventhPercentile = new LineDataSet(yValsNinetySeventhPercentile, "97") ;
                ninetySeventhPercentile.setDrawCircles(false);
                ninetySeventhPercentile.setColor(Color.GREEN);

                dataSets.add(thirdPercentile) ;
                dataSets.add(tenthPercentile);
                dataSets.add(twentyFifthPercentile);
                dataSets.add(fiftyathPercentile);
                dataSets.add(seventyFifthPercentile);
                dataSets.add(ninetyathPercentile);
                dataSets.add(ninetySeventhPercentile);

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
