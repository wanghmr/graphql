package com.example.graphql.model;

import lombok.Data;

import java.util.List;

/**
 * @author pcs
 * Date         2020/4/20
 * Description:
 */
@Data
public class Droid implements Character {

    private Long id;

    private String name;

    private List<Episode> appearsIn;

    private String primaryFunction;
}
