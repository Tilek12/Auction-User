package kg.megacom.models;

import lombok.Data;

import java.util.Date;

@Data
public class Lot {

    private Long id;
    private String name;
    private double minPrice;
    private double maxPrice;
    private double step;
    private Date startDate;
    private Date endDate;
    private Status status;

}
