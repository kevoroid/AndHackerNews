package com.kevoroid.andhackernews;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevin on 6/3/17.
 */

public class TestResponse {

    public static final String mainStoriesIds = "[[14471931, 14474956, 14473377, 14474663, 14468818, 14474426, 14459536, 14473077, 14473886, 14472265]]";
    public static final String mainStoryItem = "{\n" +
            "\n" +
            "    \"by\": \"mpweiher\",\n" +
            "    \"descendants\": 9,\n" +
            "    \"id\": 14433687,\n" +
            "    \"kids\": [\n" +
            "        14435028,\n" +
            "        14435942,\n" +
            "        14434504,\n" +
            "        14434619\n" +
            "    ],\n" +
            "    \"score\": 81,\n" +
            "    \"time\": 1495963915,\n" +
            "    \"title\": \"Live Literate Programming\",\n" +
            "    \"type\": \"story\",\n" +
            "    \"url\": \"https://www.youtube.com/watch?v=HW7sqMjTkI0&feature=youtu.be\"\n" +
            "\n" +
            "}";

    public static String convertToArray() {
        return mainStoriesIds.replace("[[", "[").replace("]]", "]");
    }
}