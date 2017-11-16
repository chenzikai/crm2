package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter@Setter
public class SystemLog {
    private Long id;

    private Employee opuser;

    private Date optime;

    private String oplp;

    private String function;

    private String params;

}