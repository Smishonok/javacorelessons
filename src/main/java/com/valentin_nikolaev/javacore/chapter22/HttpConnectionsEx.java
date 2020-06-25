package com.valentin_nikolaev.javacore.chapter22;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Entities {

    long   typeID;
    String typeName;

    public Entities(long id, String name) {
        this.typeID   = id;
        this.typeName = name;
    }

    public long getTypeID() {
        return typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return "Entities{" + "id=" + typeID + ", name='" + typeName + '\'' + '}';
    }
}


public class HttpConnectionsEx {

    public static void main(String[] args) throws IOException {

        URL url = new URL("https://www.fuzzwork.co.uk/api/typeid2.php?typename=Tritanium|Pyerite");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int responseCode = connection.getResponseCode();
        System.out.println("Response code: "+ responseCode);

        Map<String, List<String>> headerFieldsWithValues = connection.getHeaderFields();
        Set<String> headerFields = headerFieldsWithValues.keySet();
        StringBuilder headerText = new StringBuilder();
        for (String field : headerFields) {
            headerText.append(field).append("\n");
            List<String> fieldValues = headerFieldsWithValues.get(field);
            fieldValues.forEach(value->headerText.append("\t").append(value).append(":\n"));
        }
        System.out.println(headerText);

        Entities[] goods;
        try (BufferedInputStream inputStream = new BufferedInputStream(
                connection.getInputStream())) {
            Gson json = new Gson();
            String response = new String(inputStream.readAllBytes());
            System.out.println(response);
            goods = json.fromJson(response,Entities[].class);

            for (Entities good : goods) {
                System.out.println(good.toString());
            }

            System.out.println(json.fromJson(json.toJson(goods[0]),Entities.class));
        } catch (IOException e) {
            e.printStackTrace();
        }





    }



}
