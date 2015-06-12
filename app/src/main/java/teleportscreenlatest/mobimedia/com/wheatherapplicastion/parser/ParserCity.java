package teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.beans.CityResult;

/**
 * Created by ram on 12/6/15.
 */
public class ParserCity {


    HttpURLConnection yahooHttpConn;
    String Urltext;


    String query;


    public ArrayList<CityResult> getCityList() throws IOException, XmlPullParserException {

        query = "http://where.yahooapis.com/v1/places.q(" + Urltext + ");count=MAX_RESULT_SIZE?appid=dj0yJmk9Qkl4WVI0WDgwendWJmQ9WVdrOWIycEtPRk5vTm5VbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD01Nw--";

        // Log.i("query", "urltext" + query);

        yahooHttpConn = (HttpURLConnection) (new URL(query)).openConnection();
        yahooHttpConn.connect();
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(new InputStreamReader(yahooHttpConn.getInputStream()));

        int event = parser.getEventType();

        ArrayList<CityResult> result = new ArrayList<CityResult>();

        CityResult cty = null;
        String tagName = null;
        String currentTag = null;

// We start parsing the XML
        while (event != XmlPullParser.END_DOCUMENT) {
            tagName = parser.getName();

            if (event == XmlPullParser.START_TAG) {
                if (tagName.equals("place")) {
                    // place Tag Found so we create a new CityResult
                    cty = new CityResult();
                    Log.d("Swa", "New City found");
                }
                currentTag = tagName;
                Log.d("Swa", "Tag [" + tagName + "]");
            } else if (event == XmlPullParser.TEXT) {
                // We found some text. let's see the tagName to know the tag related to the text
                if ("woeid".equals(currentTag))
                    cty.setWoeid(parser.getText());
                else if ("name".equals(currentTag))
                    cty.setCityName(parser.getText());
                else if ("country".equals(currentTag))
                    cty.setCountry(parser.getText());

                // We don't want to analyze other tag at the moment
            } else if (event == XmlPullParser.END_TAG) {
                if ("place".equals(tagName))
                    result.add(cty);
            }

            event = parser.next();
        }

        return result;

    }

    public void setText(String text) {

        Urltext = text;
    }
}
