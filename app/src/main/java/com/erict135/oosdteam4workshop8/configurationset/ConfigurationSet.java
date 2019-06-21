package com.erict135.oosdteam4workshop8.configurationset;

public class ConfigurationSet {
    private static final String HTTP = "http://";
    private static final String IP = "10.187.200.193";
//    private static final String IP = "192.168.0.12";
    private static final String RESTUrl = ":8080/travelexpertsREST/rs";

    private static final String CustomerBase = "/cust";

    ///
    // DO
    ///
    private static final String CustomerLogin = "/login";
    private static final String CustomerRegister = "/registercustomer";
    private static final String CustomerCreateBooking = "/createbooking";
    private static final String CustomerUpdate = "/update";
    private static final String CustomerAssignAgent = "/assignagent";
    ///
    // GET
    ///
    private static final String Packages = "/getpackages";
    private static final String Package_ = "/getpackage/";
    private static final String Bookings_ = "/mybookings/";
    private static final String BookingsWPackage_ = "/mybookingWpackages/";

    public static final int TravelerGroupCount = 4;

    public static final String getRESTCustomerAssignAgentURL(){
        return HTTP+IP+RESTUrl+CustomerBase+CustomerAssignAgent;
    }
    public static String getRESTCustomerUpdateURL(){
        return HTTP+IP+RESTUrl+CustomerBase+CustomerUpdate;
    }

    public static String getRESTCustomerCreateBookingURL(){
        return HTTP+IP+RESTUrl+CustomerBase+CustomerCreateBooking;
    }
    public static String getRESTCustomerRegisterURL(){
        return HTTP+IP+RESTUrl+CustomerBase+CustomerRegister;
    }
    public static String getRESTCustomerLoginUrl() {
        return HTTP + IP + RESTUrl + CustomerBase + CustomerLogin;
    }

    public static String getRESTPackages() {
        return HTTP + IP + RESTUrl + CustomerBase + Packages;
    }

    public static String getRESTBookings_() {
        return HTTP + IP + RESTUrl + CustomerBase + Bookings_;
    }

    public static String getRESTBookingsWPackage_() {
        return HTTP + IP + RESTUrl + CustomerBase + BookingsWPackage_;
    }

    public static String getRESTPackage_() {
        return HTTP + IP + RESTUrl + CustomerBase + Package_;
    }

}