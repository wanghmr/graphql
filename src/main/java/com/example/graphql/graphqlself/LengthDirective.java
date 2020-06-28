package com.example.graphql.graphqlself;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

/**
 * @author pcs
 * Date         2020/4/20
 * Description:
 */
public class LengthDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        int minValue = (int) environment.getDirective().getArgument("min").getValue();
        int maxValue = (int) environment.getDirective().getArgument("max").getValue();

        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();
        //
        // build a data fetcher that first checks authorisation roles before then calling the original data fetcher
        // 因为每个字段都对应一个DataFetcher对象，所以这里得到新旧DataFetcher，并做转换
        DataFetcher originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher lengthDataFetcher = DataFetcherFactories.wrapDataFetcher(originalDataFetcher,
                (dataFetchingEnvironment, value) -> {
                    if (value == null) {
                        return null;
                    } else {
                        String val = (String) value;
                        if (val.length() > maxValue || val.length() < minValue) {
                            throw new RuntimeException(field.getName() + "字段长度不符");
                        }
                        return val;
                    }
                });
        // now change the field definition to have the new authorising data fetcher
        environment.getCodeRegistry().dataFetcher(parentType, field, lengthDataFetcher);
        return field;
    }
}
