package teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper;

public class TemperatureFormatter {

    public static String format(float temperature) {
        return String.valueOf(Math.round(temperature)) + "°";
    }
}
