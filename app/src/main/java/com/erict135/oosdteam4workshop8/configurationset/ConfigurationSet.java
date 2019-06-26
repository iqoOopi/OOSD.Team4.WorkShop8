package com.erict135.oosdteam4workshop8.configurationset;

public class ConfigurationSet {
    private static final String HTTP = "http://";
    private static final String IP = "10.163.112.148";
    private static final String RESTUrl = ":8080/Team4API/rest";

    private static final String CustomerBase = "/customers";
    public static final String  PackageBase = "/packages";

    ///
    // DO
    ///
    private static final String CustomerLogin = "/login";
    private static final String CustomerRegister = "/putcustomer";
    private static final String CustomerCreateBooking = "/create_booking";
    private static final String CustomerUpdate = "/update";
    private static final String CustomerAssignAgent = "/assignagent2cust";
    ///
    // GET
    ///
    private static final String Packages = "/getallpackages";
    private static final String Package_ = "/getpackage/";
    private static final String Bookings_ = "/mybookings/";
    private static final String BookingsWPackage_ = "/get_my_bookings/";

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
        return HTTP + IP + RESTUrl + PackageBase + Packages;
    }

    public static String getRESTBookings() {
        return HTTP + IP + RESTUrl + CustomerBase + Bookings_;
    }

    public static String getRESTBookingsWPackage_() {
        return HTTP + IP + RESTUrl + CustomerBase + BookingsWPackage_;
    }

    public static String getRESTPackage() {
        return HTTP + IP + RESTUrl + CustomerBase + Package_;
    }

}