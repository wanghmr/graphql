package com.example.graphql.model;

/**
 * @author pcs
 * Date         2020/4/20
 * Description:
 */
public enum Episode {
    /**
     * a
     */
    NEWHOPE("NEWHOPE"),
    EMPIRE("EMPIRE"),
    JEDI("JEDI");

    private String code;

    private Episode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
