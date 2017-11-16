package com._520it.crm.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
@ToString
@Getter@Setter
public class RoleQueryObject extends QueryObject {
   private String keyword;

    public String getKeyword() {
        return StringUtils.isNotBlank(keyword)?keyword:null;
    }
}
