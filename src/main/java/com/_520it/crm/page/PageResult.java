package com._520it.crm.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class PageResult {
    private  int total;
    private List rows;

}
