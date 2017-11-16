package com._520it.crm.query;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class QueryObject {

    private Integer page = 1;
    private Integer rows = 10;

    public int getStart(){
        return (page-1)*rows;
    }
}
