package eri;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by enimahe on 6/1/15.
 */
public class StartDate {

    public static void main(String[] args) {
        String startDate = "2015-05-21";
        String endDate = "2015-12-21";
        String reportGroup = "daily";

        String dailyDate = "";
        String hourDate = "";
        String yearlyDate = "";

        boolean isTrackingReport = false;

        try {
            hourDate = calcStartDate(startDate, "hourly", isTrackingReport);
            dailyDate = calcStartDate(startDate, "daily", isTrackingReport);
            yearlyDate = calcStartDate(startDate, "monthly", isTrackingReport);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(hourDate + " ");
        System.out.println(dailyDate + " ");
        System.out.println(yearlyDate + " ");
    }

    private static String calcStartDate(String date, String reportGroup, boolean isTrackingReport) throws ParseException {
        Calendar currentCal = Calendar.getInstance();
        String startDate = date;    // Task Start Date from the Table.
        String CUSTOM_DATE_FORMAT = "yyyy-MM-dd";
        DateFormat customDateFormatter = new SimpleDateFormat(CUSTOM_DATE_FORMAT);
        Date currentDate = customDateFormatter.parse(customDateFormatter.format(currentCal.getTime()));
        Date taskStartDate = customDateFormatter.parse(date);
        if (taskStartDate.after(currentDate))
            return date;
        if (isTrackingReport) {
            currentCal.add(Calendar.DATE, -1);
            startDate = customDateFormatter.format(currentCal.getTime());
            return startDate;
        }


        //For Calculating First Day of the Year
        Calendar helperCal = Calendar.getInstance();
        helperCal.set(Calendar.MONTH, 0);
        helperCal.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayOfTheCurrentYear = customDateFormatter.format(helperCal.getTime());

        // For calculating Current Date Year and Month
        int currentYear = currentCal.get(Calendar.YEAR);
        int currentMonth = currentCal.get(Calendar.MONTH);
        int currentDay = currentCal.get(Calendar.DATE);

        //For Calculating Task year and month
        helperCal.setTime(taskStartDate);
        int taskStartYear = helperCal.get(Calendar.YEAR);
        int taskStartMonth = helperCal.get(Calendar.MONTH);


        if (reportGroup.equals("monthly")) {
            if ((currentYear - taskStartYear) > 0 && currentMonth > 0)                    //Handling New Year date with currentMonth = 0 means January
                startDate = firstDayOfTheCurrentYear;
            else
                startDate = date;
        } else if (reportGroup.equals("daily")) {
            if ((currentYear - taskStartYear) > 0) {
                if (currentMonth > 0)                                                //Handling New Year date with currentMonth = 0 means January
                {
                    helperCal = Calendar.getInstance();                               // this takes current date
                    helperCal.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = customDateFormatter.format(helperCal.getTime());            // should be the first day of the Current Month
                } else if (currentMonth == 0)                                            // handling January Month
                {
                    if (currentDay > 1) {
                        startDate = firstDayOfTheCurrentYear;                        //should be the first day of the January current year
                    } else                                                            //    handling New Year run 01/01/YYYY 00:10 AM
                    {
                        helperCal = Calendar.getInstance();
                        // add -1 month to current month
                        helperCal.add(Calendar.MONTH, -1);
                        // set DATE to 1, so first date of previous month
                        helperCal.set(Calendar.DATE, 1);
                        startDate = customDateFormatter.format(helperCal.getTime());    // should be the first day of the December last year
                    }
                }
            } else {
                if ((currentMonth - taskStartMonth) > 0) {
                    helperCal = Calendar.getInstance();
                    helperCal.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = customDateFormatter.format(helperCal.getTime());            //should be first day of the current month
                } else
                    startDate = date;                                                // should be the task start day
            }
        } else if (reportGroup.equals("hourly")) {
            currentCal.add(Calendar.DATE, -1);
            startDate = customDateFormatter.format(currentCal.getTime());                    // should be the previous day
        }
        return startDate;

    }
}
