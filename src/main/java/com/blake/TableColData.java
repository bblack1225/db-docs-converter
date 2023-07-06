package com.blake;

import lombok.Data;

@Data
public class TableColData {
    private String colSeq;
    private String colName;

    private String colType;

    private String colLength;

    private String enableNull;

    private String enableUnique;

    private String colDefault;
    private String colComment;

    private String checkRuleOrDomain;
}
