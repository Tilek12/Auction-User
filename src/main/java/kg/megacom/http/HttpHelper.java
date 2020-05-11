package kg.megacom.http;

import kg.megacom.http.impl.HttpHelperImpl;
import kg.megacom.models.Lot;

import java.io.IOException;
import java.util.List;

public interface HttpHelper {

    HttpHelper INSTANCE = new HttpHelperImpl();

    Lot getLot(Lot lot) throws IOException;

    Lot saveLot(Lot lot) throws Exception;

    Lot updateLot(Lot lot) throws Exception;

    List<Lot> getAllLots() throws IOException;



}
