package com.example.historicalplacesuserapp.Comman;

public class Config {

    private static String OnlineAddress = "http://192.168.1.5:80/HistoricalPlacesAPI/UserApp/";
    public static String OnlineImageAddress = "http://192.168.1.5:80/HistoricalPlacesAPI/AdminApp/Images/";

    public static String url_register = OnlineAddress+ "register_user.php";
    public static String url_login = OnlineAddress+ "login_user.php";
    public static String urlGetHistoricalPlacesCategory = OnlineAddress+ "getHistoricalPlacesCategory.php";
    public static String urlGetCity = OnlineAddress+ "get_city.php";
    public static String urlGetCategorywiseHistoricalPlaces = OnlineAddress+ "getCategorywiseHistoricalPlaces.php";
    public static String urlGetAllHistoricalPlaces = OnlineAddress+ "getAllHistoricalPlaces.php";
    public static String urlGetCitywiseHistoricalPlaces = OnlineAddress+ "getCitywiseHistoricalPlaces.php";
    public static String urlGetMyProfile = OnlineAddress+ "getMyProfile.php";
    public static String url_add_payment = OnlineAddress+ "addpaymenttourorginazationapp.php";
    public static String urlGetPlacePackage = OnlineAddress+ "getPlacePackagetourtourorginazationapp.php";
    public static String urlGetBookingandReciepttourorganizationapp = OnlineAddress+ "getBookingandReciepttourorganizationapp.php";

}
