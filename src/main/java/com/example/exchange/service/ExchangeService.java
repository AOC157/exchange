package com.example.exchange.service;

import com.example.exchange.model.Exchange;
import com.example.exchange.model.Person;
import com.example.exchange.model.Product;
import com.example.exchange.repository.ExchangeRepository;
import com.example.exchange.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    public Exchange save(Exchange exchange) throws IOException {
        Person buyer = getPerson(exchange.getBuyerId());
        Person seller = getPerson(exchange.getSellerId());

        for(Product product : exchange.getExchangedProducts()){
            for(Product sellerProduct : seller.getProducts()){
                if(sellerProduct.getId() == product.getId()){
                    seller.getProducts().remove(sellerProduct);
                    break;
                }
            }
        }
        buyer.getProducts().addAll(exchange.getExchangedProducts());

        updatePerson(seller);
        updatePerson(buyer);

        return exchangeRepository.save(exchange);
    }

    private Person getPerson(int personId) throws IOException {
        URL url = new URL("http://localhost:8080/person/get/" + personId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type" , "application/json");
        StringBuilder builder = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
            String response = null;
            while ((response = br.readLine()) != null){
                builder.append(response.trim());
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(builder.toString(),Person.class);
    }

    private void updatePerson(Person person) throws IOException {
        try {
            URL url = new URL("http://localhost:8080/person/insert");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            String json = person.toString();
            OutputStream os = con.getOutputStream();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.close();

            Reader reader = new BufferedReader(new InputStreamReader(con.getInputStream() , StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (int c; (c = reader.read()) >= 0;){
                builder.append((char) c);
            }
        }
        catch (Exception exp){
            exp.printStackTrace();
        }

    }

    public Exchange get(int id) throws NotFoundException {
        if (exchangeRepository.existsById(id)){
            return exchangeRepository.getOne(id);
        }
        throw new NotFoundException("");
    }

    public List<Exchange> getAll(){
        return exchangeRepository.findAll();
    }
}
