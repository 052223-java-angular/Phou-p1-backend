package com.revature.yield.utils.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    /* reads from input stream and output the input stream into a string and then create a new JsonObject from the string
    *
    * @param inputStream the input stream to read from
    * @return null or a JSON object created from the input stream
    * */
    public static JSONObject toJsonObject(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        // convert the String into a JSON object having key and value pair for each found key
        return new JSONObject(sb.toString());
    }

    /* transforms the object values of a mappedEntry into a String format
    *
    * @param mapObj mapped entries of key and value pairs
    * @return a hashMap containing the key and string values
    * */
    public static Map<String, String> mapObjectsToString(Map<String, Object> mapObj) {
        Map<String, String> mEntries = new HashMap<>();
        for (var entry : mapObj.entrySet()) {

            if (!(entry.getValue() instanceof JSONObject)) {
                mEntries.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return mEntries;
    }


    /*=============  HELPER METHODS FOR EXTRACTING VALUES FROM JSON OBJECTS =====================================*/

    /* Recursive method to extract all key value pairs within a JSON object including array types
     *
     * @param jsonObject the json object containing all of its nested key value pairs
     * @param objectMap an empty map for the recursive call; required for adding any found keys and its value pair
     * @param includeNonObjects set true to include all keys, i.e. object, array and string
     * @return a mapped object containing all found key and value pairs
     * */
    public static Map<String, Object> extractKeyAndJsonType(JSONObject jsonObject, Map<String, Object> objectMap, boolean includeNonObjects) {

        // iterate through the JSON key set(s)
        for (String key : jsonObject.keySet()) {

            // get the value using the key
            Object valueOrJsonObjectType = jsonObject.get(key);

            // include all non object types, i.e. values of same level
            if (includeNonObjects) {
                // when any type, add key
                objectMap.put(key, valueOrJsonObjectType.toString());
            }

            // when object is a JSON Object type, add key and value to objectMap
            if (valueOrJsonObjectType instanceof JSONObject) {
                objectMap.put(key, valueOrJsonObjectType);
                extractKeyAndJsonType((JSONObject) valueOrJsonObjectType, objectMap, includeNonObjects);
            }

            // when object or array type, add key
            if (valueOrJsonObjectType instanceof JSONArray) {
                // within this array, extract the object types
                objectMap.put(key, valueOrJsonObjectType);
                extractKeyAndJsonType((JSONArray) valueOrJsonObjectType, objectMap, includeNonObjects);
            }

        }
        return objectMap;
    }

    /* search for any json objects found within a JSON array type
     *
     * @param jsonArray the json object containing all of its nested key value pairs
     * @param objectMap an empty map for the recursive call; required for adding any found keys and its value pair
     * @param includeNonObjects set true to include all keys, i.e. object, array and string
     * */
    private static void extractKeyAndJsonType(JSONArray jsonArray, Map<String, Object> objectMap, boolean includeNonObjects) {

        // iterate through the JSON array type
        for (int i = 0; i < jsonArray.length(); i++) {

            // get the value using the key
            Object jsonObject = jsonArray.get(i);

            // when object is a JSON Object type, add key and value to objectMap
            if (jsonObject instanceof JSONObject) {
                extractKeyAndJsonType((JSONObject) jsonObject, objectMap, includeNonObjects);
            }
        }
    }

}
