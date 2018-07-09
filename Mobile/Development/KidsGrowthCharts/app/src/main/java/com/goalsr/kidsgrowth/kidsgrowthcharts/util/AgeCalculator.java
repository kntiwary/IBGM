package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AgeCalculator {

    public static Age calculateAge(Date birthDate, Date visitdate)
    {
        int years = 0;
        int months = 0;
        int days = 0;
        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());

        Calendar visitDay = Calendar.getInstance() ;
        visitDay.setTimeInMillis(visitdate.getTime());

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        //Get difference between years
        years = visitDay.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = visitDay.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0)
        {
            years--;
            months = 12 - birthMonth + currMonth;
            if (visitDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && visitDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            years--;
            months = 11;
        }
        //Calculate the days
        if (visitDay.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = visitDay.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (visitDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            int today = visitDay.get(Calendar.DAY_OF_MONTH);
            visitDay.add(Calendar.MONTH, -1);
            days = visitDay.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else
        {
            days = 0;
            if (months == 12)
            {
                years++;
                months = 0;
            }
        }
        //Create new Age object
        return new Age(days, months, years);
    }

    public static Age anotherAgeCalculation(Date birthDate, Date visitdate)
    {
        int monthDay[] = { 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int ageDays;int ageMonths;int ageYear;
        Calendar fromDate;Calendar toDate;
        int increment;

        fromDate=Calendar.getInstance();
        toDate=Calendar.getInstance();

        Date dt1=birthDate;
        //Date dt2 = new Date() ;
        Date dt2= visitdate ;
        /*
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        Calendar mycal = Calendar.getInstance();
        Date currentDate = new Date() ; //
        Date sampleDate = new Date() ;
        sampleDate = mycal.getTime();

        try {
            currentDate = sdf.parse(String.valueOf(sampleDate));
            dt2=currentDate ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */

        fromDate.setTime(dt1);
        toDate.setTime(dt2);

        increment=0;
        ///
        ///day calculation
        ///
        if(fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH))
        {
            increment=monthDay[fromDate.get(Calendar.MONTH)];
        }
        if (increment == -1)
        {
            if(fromDate.getActualMaximum(Calendar.DAY_OF_MONTH)==29)
            {
                increment=29;
            }
            else
            {
                increment=28;
            }
        }
        if (increment != 0)
        {
            ageDays = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        }
        else
        {

            ageDays= toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }
        ///
        ///month calculation
        ///
        if ((fromDate.get(Calendar.MONTH)+increment ) > toDate.get(Calendar.MONTH))
        {
            int mcal=fromDate.get(Calendar.MONTH)+increment;
            int mont = Calendar.MONTH ;
            toDate.add(Calendar.MONTH, - mcal + 12);
            ageMonths=toDate.get(Calendar.MONTH);
            increment = 1;
        }
        else
        {
            int mcal=fromDate.get(Calendar.MONTH)+increment;
            toDate.add(Calendar.MONTH, - mcal);
            ageMonths=toDate.get(Calendar.MONTH);
            increment = 0;
        }
        ///
        /// year calculation
        ///
        ageYear = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);

        ///
        /// display age in year, month,day
        ///

        return new Age(ageDays,ageMonths,ageYear) ;

    }
}

