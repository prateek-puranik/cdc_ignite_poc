package com.example.IgniteTrial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "company_3")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class company_3 implements Serializable {
    @Id
    @QuerySqlField(index = true)
    private int company_code;
    @QuerySqlField
    private String comp_name;
    @QuerySqlField
    private int share_price;

}
