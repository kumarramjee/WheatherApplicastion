package teleportscreenlatest.mobimedia.com.wheatherapplicastion;

/**
 * Created by ram on 9/6/15.
 */
public class Validation {
    public String CityNameValidation(String name) {
        String message = "";
        if (name.trim().length() == 0) {
            return message = "Please enter City Name";
        } else {
            message = name;
        }
        return message;
    }
}
