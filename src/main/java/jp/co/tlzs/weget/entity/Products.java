package jp.co.tlzs.weget.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
//@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_products")
public class Products {

    @Id
    private String id;
    private String distributorId;
    private String brandName;
    private String product_name;
    private String general_price ;
    private int price_amount_1 ;
    private int  price_amount_2to10;
    private int  price_amount_11to20;
    private int  price_amount_21to30 ;
    private int  price_amount_31to40;
    private int  price_amount_41to50;
    private int   price_amount_51to60;
    private int  price_amount_61to70;
    private int  price_amount_71to80 ;
    private int  price_amount_81to90 ;
    private int  price_amount_91to100 ;
    private String  image_url_1;
    private String  image_url_2 ;
    private String  image_url_3 ;
    private String  image_url_4 ;
    private String  image_url_5 ;
    private String  product_lp_url ;
    private String  youtube_url ;
    private String  discription ;
    private String  product_number ;
    private String  introduction ;
    private LocalDate announce_date ;
    private LocalDate start_date ;
    private LocalDate finish_date;
    private LocalDate delivery_schedule;
    private int  sub_category_id;
    private int  product_total_amount;
    private int  product_sold_total_amount;
    private int  transporter_id;
    private int  tax;
    private int  current_price ;
    private boolean limit_item;
    private int  limit_quantity ;
    private boolean  off_shelf ;
    private LocalDate  registration_date;
    private LocalDate  settlement_date;
    private boolean  population;
    private LocalDate  population_modify_datetime;
}
