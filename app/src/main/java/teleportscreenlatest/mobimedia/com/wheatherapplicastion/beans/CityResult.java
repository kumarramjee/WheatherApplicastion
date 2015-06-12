package teleportscreenlatest.mobimedia.com.wheatherapplicastion.beans;

/**
 * Created by ram on 12/6/15.
 */
public class CityResult {

    private String woeid;
    private String cityName;
    private String country;

    public CityResult() {
    }

    public CityResult(String woeid, String cityName, String country) {
        this.woeid = woeid;
        this.cityName = cityName;
        this.country = country;
    }

    public String getCityName() {

        return cityName;
    }

    public String getCountry() {


        return country;
    }

    public String getWoeid() {

        return woeid;
    }

    public void setCityName(String city) {

        cityName = city;
    }

    public void setCountry(String countryname) {

        country = countryname;
    }

    public void setWoeid(String mwoeid) {

        woeid = mwoeid;

    }


    // get and set methods

    @Override
    public String toString() {
        return cityName + "," + country;
    }
}
