package kg.megacom.http.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import kg.megacom.http.HttpHelper;
import kg.megacom.models.Lot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpHelperImpl implements HttpHelper {

    private OkHttpClient httpClient = new OkHttpClient();

    private ObjectMapper om = new ObjectMapper();

    @Override
    public Lot getLot(Lot lot) throws IOException {

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url("http://localhost:8080/lot/get/id")
                .build();

        Response response = httpClient.newCall(request).execute();

        int code = response.code();
        System.out.println(code);

        if (response.isSuccessful()) {
            lot = om.readValue(response.body().string(), Lot.class);
        } else {
            System.out.println("Ошибка. Нет связи");
        }

        return lot;
    }


    @Override
    public Lot saveLot(Lot lot) throws Exception {

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), om.writeValueAsString(lot));

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url("http://localhost:8080/lot/save")
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();

        if(response.isSuccessful()){
            lot = om.readValue(response.body().string(), Lot.class);
        }else{
            System.out.println("Ошибка. Нет связи");
        }

        return lot;
    }

    @Override
    public Lot updateLot(Lot lot) throws Exception{

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), om.writeValueAsString(lot));

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url("http://localhost:8080/lot/update")
                .put(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();

        if(response.isSuccessful()){
            lot = om.readValue(response.body().string(), Lot.class);
        }else{
            System.out.println("Ошибка. Нет связи");
        }

        return lot;
    }


    @Override
    public List<Lot> getAllLots() throws IOException {

        List<Lot> lotList = new ArrayList<>();

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url("http://localhost:8080/lot/list")
                .build();

        Response response = httpClient.newCall(request).execute();

        if(response.isSuccessful()){
            lotList = om.readValue(response.body().string(), new TypeReference<List<Lot>>(){});

        }

        return lotList;
    }

}

