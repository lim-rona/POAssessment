package vttp2022.ssf.POAssessment.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.apache.catalina.connector.Request;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.POAssessment.model.Quotation;

@Service
@RestController
public class QuotationService {
    
    public Optional<Quotation> getQuotations(List<String> items){
        JsonArrayBuilder b = Json.createArrayBuilder();
        for(int i=0;i<items.size();i++){
            b.add(items.get(i));
        }
        JsonArray arrItems = b.build();

        try{RequestEntity<String> req = RequestEntity
            .post("https://quotation.chuklee.com/quotation")
            .contentType(MediaType.APPLICATION_JSON)
            // .headers("Accept", MediaType.APPLICATION_JSON)
            .body(arrItems.toString(),String.class);
        
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        // JsonObject content = resp.getBody();

        System.out.println(resp.toString());
        System.out.println(resp.getBody());
        
        JsonObject o = null;
        try(InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())){
            JsonReader reader = Json.createReader(is);
            o = reader.readObject();
        } catch(Exception ex) {
           ex.printStackTrace();
        }
        System.out.println(o.toString());
        System.out.println(o.getString("quoteId"));
        System.out.println(o.getJsonArray("quotations"));


        JsonArray h = o.getJsonArray("quotations");
        Quotation q = new Quotation();
        q.setQuoteId(o.getString("quoteId"));

        for(int i=0;i<h.size();i++){
            JsonObject item = h.getJsonObject(i);
            String name = item.getString("item");
            Double unitPrice = item.getJsonNumber("unitPrice").doubleValue();
            Float price = unitPrice.floatValue();
            q.addQuotation(name, price);
            System.out.println(name);
            System.out.println(price);
        }

        return Optional.of(q);
        
    } catch (Exception ex) {
        return Optional.empty();
    }

        
    }


}
