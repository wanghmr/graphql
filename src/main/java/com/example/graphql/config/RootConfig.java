package com.example.graphql.config;

import com.example.graphql.graphqlself.LengthDirective;
import com.example.graphql.model.Droid;
import com.example.graphql.model.Human;
import graphql.kickstart.tools.SchemaParserDictionary;
import graphql.kickstart.tools.boot.SchemaDirective;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pcs
 * Date         2020/4/20
 * Description:
 */
@Configuration
public class RootConfig {

    /**
     * 注册到 SchemaParser
     */
    @Bean
    public SchemaDirective lengthSchemaDirective() {
        return new SchemaDirective("length", new LengthDirective());
    }

    /**
     * 如果schema中定义接口，需要加这个bean
     */
    @Bean
    public SchemaParserDictionary characterSchemaParserDictionary() {
        return new SchemaParserDictionary()
                .add(Human.class)
                .add(Droid.class);
    }
}
