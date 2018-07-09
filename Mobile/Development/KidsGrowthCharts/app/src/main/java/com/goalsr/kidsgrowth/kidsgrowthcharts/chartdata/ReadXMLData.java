package com.goalsr.kidsgrowth.kidsgrowthcharts.chartdata;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by 140013 on 04-04-2016.
 */
public class ReadXMLData {

    public Percentile readPercentileValues(Context context, double age, String gender, String xmlType) throws ParserConfigurationException, SAXException {

        Percentile percentile = new Percentile();
//        System.out.println("dashValue: " +dashName+"-"+dashValue+ "percentile.getAge()"+Double.valueOf(percentile.getHeight()));



        try {
            if(gender.equalsIgnoreCase("male")){
                gender = "WHOBoys";
            }else{
                gender = "WHOGirls";
            }
            if(xmlType.equals("Height")){
                xmlType = "_0_to_18_Height.xml";
            }
            if(xmlType.equals("Weight")){
                xmlType = "_0_to_18_Weight.xml";
            }
            if(xmlType.equals("BMI")){
                xmlType = "BMI_0_to_18_Years_BMI.xml";
            }

            AssetManager assetManager = context.getAssets() ;

          //  String filePath = "D:\\CHM_XML\\"+gender+xmlType;
           // InputStream inputStream = assetManager.open(gender+xmlType) ;

           /* System.out.println("gender: "+gender);
            System.out.println("xmlType: "+xmlType);
            System.out.println("age: "+age);*/
            assetManager.open(gender+xmlType);
            //File xmlFile = new File(inputStream.toString());
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document doc = documentBuilder.parse(assetManager.open(gender+xmlType));

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            System.out.println("age: " +age);
//            System.out.println("getHeight"+percentile.getHeight().toString());

                try
                {
                    double dashValue =0.0;


                    if(gender == "WHOBoys" &&  xmlType == "BMI_0_to_18_Years_BMI.xml"){
                        XPathExpression xpr  = xPath.compile("/WHOGirls_0_to_18_Height/Year[@YearName = '" + age + "']");
                        NodeList nl = (NodeList)xpr.evaluate(doc, XPathConstants.NODESET);
                        Node dashboard = (Node) nl.item(0);
//                    System.out.println("dashboard: " +dashboard.getChildNodes());


                        for (int j=0;j<dashboard.getChildNodes().getLength();j++)
                        {
                            j++;
                            dashValue =Double.valueOf(dashboard.getChildNodes().item(j).getTextContent());
//                    String dashValue =dashboard.getChildNodes().item(j).getTextContent();
                            String dashName =dashboard.getChildNodes().item(j).getNodeName();
                            System.out.println("dashValue: " +dashName+"-"+dashValue);
                            Element newdashboard = (Element) nl.item(0);
//                    if(    Double.valueOf(dashValue)!=0.0 && percentile.getAge()< Double.valueOf(dashValue)   )
                            if(    Double.valueOf(dashValue)!=0.0 )
                            {
                                System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
                            }



//
//                        System.out.println("newdashboard: " +newdashboard);
//                        System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
//



                            if(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()) != null)
                                percentile.setThirdPercentile(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()));

                            if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }

                            if(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()) != null)
                                percentile.setTenthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setTwentyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()));


                            if(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()) != null)
                                percentile.setFiftyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setSeventyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()));
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()));

                            }
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetySeventhPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()));
                            }

//                    if(Double.valueOf(dashValue ) != null)
//                        percentile.setThirdPercentile(Double.valueOf(dashValue ));
//
//                    if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
//                        if(Double.valueOf(dashValue ) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//                    if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
//                        if(Double.valueOf(dashValue) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//
//                    if(Double.valueOf(dashValue) != null)
//                        percentile.setTenthPercentile(Double.valueOf(dashValue));
                            return percentile;


                        }

                    }





                    if(gender == "WHOGirls" &&  xmlType == "BMI_0_to_18_Years_BMI.xml"){
                        XPathExpression xpr  = xPath.compile("/WHOGirls_0_to_18_Height/Year[@YearName = '" + age + "']");
                        NodeList nl = (NodeList)xpr.evaluate(doc, XPathConstants.NODESET);
                        Node dashboard = (Node) nl.item(0);
//                    System.out.println("dashboard: " +dashboard.getChildNodes());


                        for (int j=0;j<dashboard.getChildNodes().getLength();j++)
                        {
                            j++;
                            dashValue =Double.valueOf(dashboard.getChildNodes().item(j).getTextContent());
//                    String dashValue =dashboard.getChildNodes().item(j).getTextContent();
                            String dashName =dashboard.getChildNodes().item(j).getNodeName();
                            System.out.println("dashValue: " +dashName+"-"+dashValue);
                            Element newdashboard = (Element) nl.item(0);
//                    if(    Double.valueOf(dashValue)!=0.0 && percentile.getAge()< Double.valueOf(dashValue)   )
                            if(    Double.valueOf(dashValue)!=0.0 )
                            {
                                System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
                            }



//
//                        System.out.println("newdashboard: " +newdashboard);
//                        System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
//



                            if(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()) != null)
                                percentile.setThirdPercentile(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()));

                            if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }

                            if(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()) != null)
                                percentile.setTenthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setTwentyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()));


                            if(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()) != null)
                                percentile.setFiftyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setSeventyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()));
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()));

                            }
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetySeventhPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()));
                            }

//                    if(Double.valueOf(dashValue ) != null)
//                        percentile.setThirdPercentile(Double.valueOf(dashValue ));
//
//                    if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
//                        if(Double.valueOf(dashValue ) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//                    if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
//                        if(Double.valueOf(dashValue) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//
//                    if(Double.valueOf(dashValue) != null)
//                        percentile.setTenthPercentile(Double.valueOf(dashValue));
                            return percentile;


                        }

                    }
















                    if(gender == "WHOGirls" &&  xmlType == "_0_to_18_Weight.xml"){
                        XPathExpression xpr  = xPath.compile("/WHOGirls_0_to_18_Height/Year[@YearName = '" + age + "']");
                        NodeList nl = (NodeList)xpr.evaluate(doc, XPathConstants.NODESET);
                        Node dashboard = (Node) nl.item(0);
//                    System.out.println("dashboard: " +dashboard.getChildNodes());


                        for (int j=0;j<dashboard.getChildNodes().getLength();j++)
                        {
                            j++;
                            dashValue =Double.valueOf(dashboard.getChildNodes().item(j).getTextContent());
//                    String dashValue =dashboard.getChildNodes().item(j).getTextContent();
                            String dashName =dashboard.getChildNodes().item(j).getNodeName();
                            System.out.println("dashValue: " +dashName+"-"+dashValue);
                            Element newdashboard = (Element) nl.item(0);
//                    if(    Double.valueOf(dashValue)!=0.0 && percentile.getAge()< Double.valueOf(dashValue)   )
                            if(    Double.valueOf(dashValue)!=0.0 )
                            {
                                System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
                            }



//
//                        System.out.println("newdashboard: " +newdashboard);
//                        System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
//



                            if(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()) != null)
                                percentile.setThirdPercentile(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()));

                            if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }

                            if(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()) != null)
                                percentile.setTenthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setTwentyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()));


                            if(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()) != null)
                                percentile.setFiftyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setSeventyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()));
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()));

                            }
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetySeventhPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()));
                            }

//                    if(Double.valueOf(dashValue ) != null)
//                        percentile.setThirdPercentile(Double.valueOf(dashValue ));
//
//                    if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
//                        if(Double.valueOf(dashValue ) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//                    if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
//                        if(Double.valueOf(dashValue) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//
//                    if(Double.valueOf(dashValue) != null)
//                        percentile.setTenthPercentile(Double.valueOf(dashValue));
                            return percentile;


                        }

                    }
                    if(gender == "WHOGirls" &&  xmlType == "_0_to_18_Height.xml"){
                        XPathExpression xpr  = xPath.compile("/WHOGirls_0_to_18_Height/Year[@YearName = '" + age + "']");
                        NodeList nl = (NodeList)xpr.evaluate(doc, XPathConstants.NODESET);
                        Node dashboard = (Node) nl.item(0);
//                    System.out.println("dashboard: " +dashboard.getChildNodes());


                        for (int j=0;j<dashboard.getChildNodes().getLength();j++)
                        {
                            j++;
                            dashValue =Double.valueOf(dashboard.getChildNodes().item(j).getTextContent());
//                    String dashValue =dashboard.getChildNodes().item(j).getTextContent();
                            String dashName =dashboard.getChildNodes().item(j).getNodeName();
                            System.out.println("dashValue: " +dashName+"-"+dashValue);
                            Element newdashboard = (Element) nl.item(0);
//                    if(    Double.valueOf(dashValue)!=0.0 && percentile.getAge()< Double.valueOf(dashValue)   )
                            if(    Double.valueOf(dashValue)!=0.0 )
                            {
                                System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
                            }



//
//                        System.out.println("newdashboard: " +newdashboard);
//                        System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
//



                            if(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()) != null)
                                percentile.setThirdPercentile(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()));

                            if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }

                            if(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()) != null)
                                percentile.setTenthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setTwentyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()));


                            if(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()) != null)
                                percentile.setFiftyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setSeventyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()));
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()));

                            }
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetySeventhPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()));
                            }

//                    if(Double.valueOf(dashValue ) != null)
//                        percentile.setThirdPercentile(Double.valueOf(dashValue ));
//
//                    if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
//                        if(Double.valueOf(dashValue ) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//                    if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
//                        if(Double.valueOf(dashValue) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//
//                    if(Double.valueOf(dashValue) != null)
//                        percentile.setTenthPercentile(Double.valueOf(dashValue));
                            return percentile;


                        }

                    }
                    if(gender == "WHOBoys" && xmlType == "_0_to_18_Weight.xml")
                    {
                        XPathExpression xpr  = xPath.compile("/WHOBoys_0_to_18_Height/Year[@YearName = '" + age + "']");
                        NodeList nl = (NodeList)xpr.evaluate(doc, XPathConstants.NODESET);
                        Node dashboard = (Node) nl.item(0);
//                    System.out.println("dashboard: " +dashboard.getChildNodes());


                        for (int j=0;j<dashboard.getChildNodes().getLength();j++)
                        {
                            j++;
                            dashValue =Double.valueOf(dashboard.getChildNodes().item(j).getTextContent());
//                    String dashValue =dashboard.getChildNodes().item(j).getTextContent();
                            String dashName =dashboard.getChildNodes().item(j).getNodeName();
                            System.out.println("dashValue: " +dashName+"-"+dashValue);
                            Element newdashboard = (Element) nl.item(0);
//                    if(    Double.valueOf(dashValue)!=0.0 && percentile.getAge()< Double.valueOf(dashValue)   )
                            if(    Double.valueOf(dashValue)!=0.0 )
                            {
                                System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
                            }


                            if(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()) != null)
                                percentile.setThirdPercentile(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()));

                            if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }

                            if(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()) != null)
                                percentile.setTenthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setTwentyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()));


                            if(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()) != null)
                                percentile.setFiftyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setSeventyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()));
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()));

                            }
                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()) != null)
                                    percentile.setNinetySeventhPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()));
                            }

                    if(Double.valueOf(dashValue ) != null)
                        percentile.setThirdPercentile(Double.valueOf(dashValue ));

                    if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
                        if(Double.valueOf(dashValue ) != null)
                            percentile.setFifthPercentile(Double.valueOf(dashValue));
                    }
                    if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
                        if(Double.valueOf(dashValue) != null)
                            percentile.setFifthPercentile(Double.valueOf(dashValue));
                    }

                    if(Double.valueOf(dashValue) != null)
                        percentile.setTenthPercentile(Double.valueOf(dashValue));
                            return percentile;


                        }




                    }

                    if(gender == "WHOBoys" && xmlType == "_0_to_18_Height.xml")
                    {
                        XPathExpression xpr  = xPath.compile("/WHOBoys_0_to_18_Height/Year[@YearName = '" + age + "']");
                        NodeList nl = (NodeList)xpr.evaluate(doc, XPathConstants.NODESET);
                        Node dashboard = (Node) nl.item(0);
//                    System.out.println("dashboard: " +dashboard.getChildNodes());


                        for (int j=0;j<dashboard.getChildNodes().getLength();j++)
                        {
                            j++;
                            dashValue =Double.valueOf(dashboard.getChildNodes().item(j).getTextContent());
//                    String dashValue =dashboard.getChildNodes().item(j).getTextContent();
                            String dashName =dashboard.getChildNodes().item(j).getNodeName();
                            System.out.println("dashValue: " +dashName+"-"+dashValue);
                            Element newdashboard = (Element) nl.item(0);
//                    if(    Double.valueOf(dashValue)!=0.0 && percentile.getAge()< Double.valueOf(dashValue)   )
                            if(    Double.valueOf(dashValue)!=0.0 )
                            {
                                System.out.println("After newdashboard"+Double.valueOf(newdashboard.getElementsByTagName(dashName).item(0).getTextContent()));
                            }


                            if(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()) != null)
                                percentile.setThirdPercentile(Double.valueOf(newdashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()));

                            if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }
                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
                                if(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                                    percentile.setFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                            }

                            if(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()) != null)
                                percentile.setTenthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setTwentyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()));


                            if(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()) != null)
                                percentile.setFiftyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()));

                            if(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()) != null)
                                percentile.setSeventyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()));
//                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
//                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()) != null)
//                                    percentile.setNinetyathPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()));
//                            }
//                            if(xmlType.equals("BMI_0_to_18_Years_BMI.xml")){
//                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()) != null)
//                                    percentile.setNinetyFifthPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()));
//
//                            }
//                            if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
//                                if(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()) != null)
//                                    percentile.setNinetySeventhPercentile(Double.valueOf(newdashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()));
//                            }

//                    if(Double.valueOf(dashValue ) != null)
//                        percentile.setThirdPercentile(Double.valueOf(dashValue ));
//
//                    if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
//                        if(Double.valueOf(dashValue ) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//                    if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
//                        if(Double.valueOf(dashValue) != null)
//                            percentile.setFifthPercentile(Double.valueOf(dashValue));
//                    }
//
//                    if(Double.valueOf(dashValue) != null)
//                        percentile.setTenthPercentile(Double.valueOf(dashValue));
                            return percentile;


                        }




                    }



                }
                catch (XPathExpressionException e)
                {

                }






            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Year");
            System.out.println("nodelist:"+age+"nodelist:"+(nodeList));
            System.out.println("node:"+age+"node:"+(nodeList.getLength()));

            //System.out.println("READ FILE :"+ doc.getDocumentElement().getNodeName());
            //int newAge =

            for (int temp = 0; temp < ((nodeList.getLength())); temp++) {
                //Node node = nodeList.item(age);

//                int newAge = ((int)(age*4));
                double tempnew = 0.0;
                double newAge = 0.9;
//                Element dashboard = (Element) nodeList.item(newAge);
                Element dashboard = (Element) nodeList.item(temp);
                tempnew = (double) temp/10;

                System.out.println("Temp:"+tempnew);
                if(tempnew ==  newAge){

                    System.out.println("Temp:"+temp+"newAge:"+newAge);
                    if(Double.valueOf(dashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()) != null)
                        percentile.setThirdPercentile(Double.valueOf(dashboard.getElementsByTagName("ThirdPercentile").item(0).getTextContent()));

                    if(age >= 5.0 && xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOGirls")){
                        if(Double.valueOf(dashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                            percentile.setFifthPercentile(Double.valueOf(dashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                    }
                    if(xmlType.equals("BMI_0_to_18_Years_BMI.xml") && gender.equals("WHOBoys")){
                        if(Double.valueOf(dashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()) != null)
                            percentile.setFifthPercentile(Double.valueOf(dashboard.getElementsByTagName("FifthPercentile").item(0).getTextContent()));
                    }

                    if(Double.valueOf(dashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()) != null)
                        percentile.setTenthPercentile(Double.valueOf(dashboard.getElementsByTagName("TenthPercentile").item(0).getTextContent()));

                    if(Double.valueOf(dashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()) != null)
                        percentile.setTwentyFifthPercentile(Double.valueOf(dashboard.getElementsByTagName("TwentyFifthPercentile").item(0).getTextContent()));


                    if(Double.valueOf(dashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()) != null)
                        percentile.setFiftyathPercentile(Double.valueOf(dashboard.getElementsByTagName("FiftyathPercentile").item(0).getTextContent()));

                    if(Double.valueOf(dashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()) != null)
                        percentile.setSeventyFifthPercentile(Double.valueOf(dashboard.getElementsByTagName("SeventyFifthPercentile").item(0).getTextContent()));
                    if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                        if(Double.valueOf(dashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()) != null)
                            percentile.setNinetyathPercentile(Double.valueOf(dashboard.getElementsByTagName("NinetyathPercentile").item(0).getTextContent()));
                    }
                    if(xmlType.equals("BMI_0_to_18_Years_BMI.xml")){
                        if(Double.valueOf(dashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()) != null)
                            percentile.setNinetyFifthPercentile(Double.valueOf(dashboard.getElementsByTagName("NinetyFifthPercentile").item(0).getTextContent()));

                    }
                    if(!xmlType.equals("BMI_0_to_18_Years_BMI.xml") ){
                        if(Double.valueOf(dashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()) != null)
                            percentile.setNinetySeventhPercentile(Double.valueOf(dashboard.getElementsByTagName("NinetySeventhPercentile").item(0).getTextContent()));
                    }

                }



            }
            return percentile;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return percentile;
    }
}
