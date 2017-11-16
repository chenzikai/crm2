package com._520it.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

@Getter@Setter
public class EmployeeQueryObject extends QueryObject {
    private String keyword;
    private Long currentUserId;
    public String getKeyword() {
        return StringUtils.isNotBlank(keyword)?keyword:null;
    }
}

