package com.valentin_nikolaev.javacore.chapter22;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ItemDataLoader {

    public static void main(String[] args) throws IOException {

        String url = "https://www.fuzzwork.co.uk/api/traits.php?typeid=683";
        Document doc       = Jsoup.connect(url).get();
        System.out.println(doc.title());
        System.out.println(doc.html());
        Element div = doc.getElementById("infoView");
        System.out.println(div.html());

        Elements materials = div.getAllElements();


        for (Element item : materials) {
            System.out.println(item.tag());
        }

    }


}
