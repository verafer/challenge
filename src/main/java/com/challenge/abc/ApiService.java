package com.challenge.abc;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ApiService {

    public ArrayList<Object> retrieveNewsData(String query) throws Throwable{
        ArrayList<Object> newsDtoList = new ArrayList<Object>();
    
        try {
            Document webPage = Jsoup.connect("https://www.abc.com.py/buscar/"+query).get();
    
            Element scriptElement = webPage.getElementById("fusion-metadata");

            String scriptData = scriptElement.data();
            String from = "Fusion.globalContent={\"data\":";
            String to = ",\"metadata\":";

            String jsonString = scriptData.substring(scriptData.indexOf(from) + from.length(), scriptData.indexOf(to));
            JSONArray rawNewsJsonArray = new JSONArray(jsonString);        

            NewsDto newsDto = new NewsDto();
            JSONObject rawNewsJsonObject;
            for (int i = 0; i < rawNewsJsonArray.length(); i++) {
                newsDto = new NewsDto();
                rawNewsJsonObject = new JSONObject();
                rawNewsJsonObject = rawNewsJsonArray.getJSONObject(i);

                if (rawNewsJsonObject.has("publish_date") ) {
                    newsDto.setFecha(rawNewsJsonObject.getString("publish_date"));
                } else {
                    newsDto.setFecha("");
                }
                
                if (rawNewsJsonObject.has("_website_urls") && rawNewsJsonObject.getJSONArray("_website_urls").length() > 0) {
                    newsDto.setEnlace(rawNewsJsonObject.getJSONArray("_website_urls").getString(0));
                } else {
                    newsDto.setEnlace("");
                }

                if (rawNewsJsonObject.has("promo_items") && 
                        rawNewsJsonObject.getJSONObject("promo_items").has("basic") &&
                        rawNewsJsonObject.getJSONObject("promo_items").getJSONObject("basic").has("url") ) {
                    newsDto.setEnlaceFoto(rawNewsJsonObject.getJSONObject("promo_items").getJSONObject("basic").getString("url"));
                }else {
                    newsDto.setEnlaceFoto("");
                }

                if (rawNewsJsonObject.has("headlines") && rawNewsJsonObject.getJSONObject("headlines").has("basic")) {
                    newsDto.setTitulo(rawNewsJsonObject.getJSONObject("headlines").getString("basic"));
                } else {
                    newsDto.setTitulo("");
                }
                
                if (rawNewsJsonObject.has("subheadlines") && rawNewsJsonObject.getJSONObject("subheadlines").has("basic")) {
                    newsDto.setResumen(rawNewsJsonObject.getJSONObject("subheadlines").getString("basic"));
                } else {
                    newsDto.setResumen("");
                }
                newsDtoList.add(newsDto);
            }
           
        } catch (Throwable e) {   
            System.out.println(e); 
            throw e;
        }
        return newsDtoList;
    }

    public Object validateQuery(String query, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        try {
            if (query.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                map.put("codigo", "g268");
                map.put("error", "Parámetros inválidos");
                return map;
            } else {
                ArrayList<Object> newsDtoList = retrieveNewsData(query);
                if (newsDtoList.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    map.put("codigo", "g267");
                    map.put("error", "No se encuentran noticias para el texto: " + query);
                    return map;
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    return newsDtoList;
                }
            }
        } catch (Throwable e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            map.put("codigo", "g100");
            map.put("error", "Error interno del servidor");
            return map;
        }
    }

   
}
