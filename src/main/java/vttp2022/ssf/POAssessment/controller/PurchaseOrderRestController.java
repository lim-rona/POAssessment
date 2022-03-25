package vttp2022.ssf.POAssessment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.POAssessment.model.Quotation;
import vttp2022.ssf.POAssessment.services.QuotationService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.buf.ByteChunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/po")
public class PurchaseOrderRestController {
    
    @Autowired
    QuotationService quoteSvc;

    @PostMapping
    public ResponseEntity<String> getItems(@RequestBody String payload){
        JsonObject thePayload = null;
        try(InputStream is = new ByteArrayInputStream(payload.getBytes())){
            JsonReader reader = Json.createReader(is);
            thePayload = reader.readObject();
        } catch(Exception ex) {
           ex.printStackTrace();
        }

        System.out.println(payload);
        System.out.println("the item: >>" + thePayload.getJsonArray("lineItems"));
        JsonArray itemArray = thePayload.getJsonArray("lineItems");
        System.out.println("the object>>" + itemArray.getJsonObject(0).getString("item"));
        List<String> items = new LinkedList<>();
        for(int i=0;i<itemArray.size();i++){
            JsonObject item = itemArray.getJsonObject(i);
            String name = item.getString("item");
            items.add(name);
        }
        
        Optional<Quotation> q = null;
        q = quoteSvc.getQuotations(items);
        System.out.println(q.toString());
        return null;

    }
    

}
