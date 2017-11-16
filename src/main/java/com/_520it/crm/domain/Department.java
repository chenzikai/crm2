package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class Department {
    private Long id;

    private String sn;

    private String name;

    private Boolean state;

}