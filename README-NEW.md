## graphql入门

[中文官网地址](https://graphql.org.cn/)
[英文官网地址](https://graphql.org/)
[graphql-java官网](https://www.graphql-java.com/)
[graphql-java-kickstart官网](https://graphql-java-kickstart.com/)

- graphql用来做什么
- graphql简单使用
- 概念
- 分页处理
- 添加自定义命令
- 使用的注意事项


### graphql用来做什么的

    GraphQL是facebook开源的一门为API和运行时而生的查询语言，GraphQL引擎也支持很多种不同的语言。
    简单的说就是服务端使用GraphQL定义数据全集的接口，客户端根据接口规范自定义需要获取的数据，可以精确地得到您想要的数据，去除不必要的字段，节省字节数的一种方法；
    GraphQL可以把多个资源整合到一个接口里，这样请求多个资源发送一个请求就可以了，不需要请求多次，从而减少请求响应的往返次数
    通过使用类型系统，你可以预判一个请求是否有效。这让服务器和客户端可以在无效查询创建时就有效地通知开发者，而不用依赖运行时检查。
    GraphQL的文档永远和代码同步，自动生成，开发无需维护散落多处的文档，调用者也无需担心过期问题。
    GraphQL权限验证需要集成其他框架实现
    订阅是一个GraphQL特性，允许服务器在特定事件发生时向其客户端发送数据，目前graphql-java只实现了解析订阅请求，实现订阅需要集成其他的框架实现
    提供了可视化的工具graphiql，项目里可以自己集成这个工具，下面会讲（下载地址：https://www.electronjs.org/apps/graphiql）

### graphql简单使用

- 框架：spring boot + graphql + ajax
- 添加依赖包

      <dependency>
          <groupId>com.graphql-java-kickstart</groupId>
          <artifactId>graphql-spring-boot-starter</artifactId>
          <version>7.0.1</version>
      </dependency>

- 如果使用项目里集成可视化graphiql工具可以添加依赖，版本高的话会有问题

         <dependency>
            <groupId>com.graphql-java-kickstart</groupId>
            <artifactId>graphiql-spring-boot-starter</artifactId>
            <version>5.5.0</version>  </dependency>

- 如果需要使用订阅功能，需要添加依赖，todo 具体使用哪个包待定

         <dependency>
            <groupId>io.reactivex.rxjava2</groupId>
            <artifactId>rxjava</artifactId>
            <version>2.2.7</version></dependency>

- idea安装"JS GraphQL"插件

- 配置文件"application.yml"配置参数 
    ​      
        graphql:
          servlet:
              # 访问路径，http://ip:port/contextPath/graphql，默认/graphql
              mapping: /graphql
              # 为true的时候才会自动配置GraphQLWebAutoConfiguration，否则不会自动配置，默认为true
              enabled: true
              # 是否为程序注册CorsFilter，是否允许跨域访问
              corsEnabled: true
              # 监听请求是否采取异步模式，默认为false
              asyncModeEnabled: false
              # 会在程序中添加MaxQueryDepthInstrumentation拦截器，验证查询的每个字段最大深度，如果超过这个值就报错，深度指字段属于第几层
              # maxQueryDepth: 10
              # 会在程序中添加MaxQueryComplexityInstrumentation拦截器，如果查询复杂度大于指定的max复杂度，则阻止执行。
              # maxQueryComplexity: 128
              # 如果自己定义了异常处理，使用自定义的异常处理程序处理graphql异常，默认是false，自定义异常处理需要实现GraphQLErrorHandler接口
              exception-handlers-enabled: false
              # 注册TracingInstrumentation拦截器 响应信息中会添加tracing为key的跟踪信息
              tracingEnabled: true
              # 不开启websocket，不发送订阅，默认是true
              websocket:
                enabled: false
          tools:
              # 配置schema文件的位置
              schema-location-pattern: "**/*.graphqls";
              # 是否使用默认的objectmapper（json转换的类），默认为true
              use-default-objectmapper: true
- 定义Schema

    - 在resources的graphqls目录下定义graphqls后缀文件，名称可以根据业务需求定义
    - Query类型是必须定义的，Query类型里定义的是查询操作的接口定义，可以定义参数和响应是否必须
    - Mutation类型不是必选定义的，如果没有增删改操作，可以不定义Mutation类型，Mutation类型里定义的是增删改操作的接口定义，可以定义参数和响应是否必须
    - 非基本类型（标量类型）需要通过type定义相应的类型
    - 可以使用@deprecated（代表废弃的属性）、@skip（根据出入的条件判断是否跳过这个变量）和@include（根据出入的条件判断是否包含这个变量）等命令
    - 变量和接口需要加 “# + 注释”，这样在可视化界面graphiql里会显示相关接口定义
    - 输入类型的定义是以input为修饰符，对象类型的定义是以type为修饰符，接口是以interface为修饰符，实现类必须包括接口的所有字段，也可以定义枚举等
    - 集合或列表可以通过“[]”表示，[String]!表示必须存在，元素可以不存在，[String!]表示集合可以不存在，但是集合存在时元素必须存在
    - 标量类型，相当于叶子节点
        - Int：有符号 32 位整数。
        - Float：有符号双精度浮点值。
        - String：UTF‐8 字符序列。
        - Boolean：true 或者 false。
        - ID：ID 标量类型表示一个唯一标识符，通常用以重新获取对象或者作为缓存中的键。ID 类型使用和 String 一样的方式序列化；然而将其定义为 ID 意味着并不需要人类可读型。
    - graphql-java添加了Long、Short、Byte、BigDecimal和BigInteger标量类型，所以这些类型可以直接使用
    - 可以自定义命令，通过自定义命令实现一些功能，比如属性的长度验证，具体实现，在下面有

- 定义解析器

    - Query的解析器是必须定义的，需要实现GraphQLQueryResolver接口，并Query类型里的相应接口的实现
    - 如果schema里有定义Mutation类型，需要定义相应的解析器，需实现GraphQLMutationResolver接口，并Mutation类型里的相应接口的实现
    - 对象类型需要定义相应的解析器和实体，实体是必须的，解析器不是一定要定义，以下两点不需要定义：
        - 比如实体里基本上都是基本类型可以不定义解析器
        - 比如如果实体有其他的实体对象，实体对象在query的解析器里没有处理，需要定义解析器，并定义一个属性的get方法
    - 定义Query和Mutation的解析器，且方法名称和schema里定义的一致
    - 对于入口的解析器，如query和mutation的解析器方法定义：method(*fieldArgs [, DataFetchingEnvironment])，DataFetchingEnvironment可以不定义，可以写多个解析器
    - 非入口的解析器，如BookResolver等解析器，方法定义：method(dataClassInstance, *fieldArgs [, DataFetchingEnvironment])，DataFetchingEnvironment可以不定义，dataClassInstance为解析器对应类的对象，如BookResolver对应的Book

- 客户端定义

    - 客户端需要定义参数query，无论时query还是mutation，参数名称都叫query，例如：

           const query =
           `query {
               findAllBooks{
                   id
                   title
               }
           }`;
           send(mutation, {}, function(data){
                           alert(data);
           })
    - 如果需要变量，需要定义variables变量，名称必须叫variables，并且参数的名称前需要加$

            const variables = {firstName: '张', lastName: '三'};
            const mutation =
                `mutation NewAuthor($firstName: String!, $lastName: String!) {
                    newAuthor(firstName: $firstName, lastName: $lastName){
                        id
                        firstName
                        lastName
                    }
                }`;
            send(mutation, variables, function(data){
                alert(data);
            })


            function send(query, variables, bizHandle) {
                $.ajax({
                    url: "/graphql",
                    data: JSON.stringify({
                        query: query, // 这里的名称必须是query
                        variables: variables
                    }),
                    accepts: {
                        customType: 'application/json'
                    },
                    contentType: 'application/json',
                    type: "post",
                    cache: false,
                    dataType: "json",
                    async: true,
                    success: function (data) {
                        bizHandle(data.data);
                    },
                    error: function (error) {
                        console.log(error)
                    }
                })
            }

### 概念

- 字段（Fields）：GraphQL 是关于请求对象上的特定字段
- 参数（Arguments）：在 GraphQL 中，每一个字段和嵌套对象都能有自己的一组参数，从而使得 GraphQL 可以完美替代多次 API 获取请求。甚至你也可以给 标量（scalar）字段传递参数，用于实现服务端的一次转换，而不用每个客户端分别转换。
- 别名（Aliases）：如果你眼睛够锐利，你可能已经发现，即便结果中的字段与查询中的字段能够匹配，但是因为他们并不包含参数，你就没法通过不同参数来查询相同字段。这便是为何你需要别名 —— 这可以让你重命名结果中的字段为任意你想到的名字。
- 片段（Fragments）：可复用单元，片段使你能够组织一组字段，然后在需要它们的的地方引入。
- 操作名称是你的操作的有意义和明确的名称。它仅在有多个操作的文档中是必需的，但我们鼓励使用它，因为它对于调试和服务器端日志记录非常有用。
- 操作类型可以是 query、mutation 或 subscription，描述你打算做什么类型的操作。操作类型是必需的，除非你使用查询简写语法，在这种情况下，你无法为操作提供名称或变量定义。
- 变量（Variables）我们将参数写在了查询字符串内。但是在很多应用中，字段的参数可能是动态的，将这些动态参数直接传进查询字符串并不是好主意，因为这样我们的客户端就得动态地在运行时操作这些查询字符串了，再把它序列化成 GraphQL 专用的格式。其实，GraphQL 拥有一级方法将动态值提取到查询之外，然后作为分离的字典传进去。这些动态值即称为变量。
- 指令（Directives）我们可能也需要一个方式使用变量动态地改变我们查询的结构。我们用了 GraphQL 中一种称作指令的新特性。
- 内联片段（Inline Fragments）如果你查询的字段返回的是接口或者联合类型，那么你可能需要使用内联片段来取出下层具体类型的数据。
- 元字段（Meta fields） 某些情况下，你并不知道你将从 GraphQL 服务获得什么类型，这时候你就需要一些方法在客户端来决定如何处理这些数据。GraphQL 允许你在查询的任何位置请求 __typename，一个元字段，以获得那个位置的对象类型名称。


​    

### 分页处理

- 自带relay分页（graphql-java-tools需要引用com.graphql-java-kickstart的，版本在5.4.0以上）

        #schema定义
        type AuthorConnection{
            edges: [AuthorEdge]
            pageInfo: PageInfo
        }
        
        type AuthorEdge{
            cursor: String
            node: Author
        }
           
        type PageInfo{
            hasPreviousPage:Boolean!
            hasNextPage:Boolean!
        }
        
        #query里定义,需要添加@connection命令
        findAuthorPage(first:Int, after:String): AuthorConnection @connection(for: "Author")
        
        #解析器定义
        public Connection<Author> findAuthorPage(int first, String after, DataFetchingEnvironment env) {
            return new SimpleListConnection<>(authorDao.findAll()).get(env);
        }

- 自定义分页

        #schema定义
        type BookPage {
            # 数据
            content: [Book]!
            # 分页信息
            pageBase: PageBase!
        }
        type PageBase {
            # 当前页数，从0开始
            pageNumber: Int!
            # 一页多少条，默认20条
            pageSize: Int!
            # 是否有下一页
            hasNextPage: Boolean!
            # 总页数
            totalPages: Int!
            # 总条数
            totalElements: Long!
        }
        
        findBookPage(pageNumber: Int!, pageSize: Int!): BookPage!
        
        # 定义实体
        @Data
        public class PageBase {
            /**
             * 当前页数，从0开始
             */
            private int pageNumber;
            /**
             * 一页多少条，默认20条
             */
            private int pageSize;
        
            /**
             * 是否有下一页
             */
            private boolean hasNextPage;
        
            /**
             * 总页数
             */
            private int totalPages;
        
            /**
             * 总条数
             */
            private long totalElements;
        }
        
        @Data
        public class BookPage {
        
            private List<Book> content;
        
            private PageBase pageBase;
        }
        
        #定义解析器
        
        public BookPage findBookPage(int pageNumber, int pageSize) {
                Pageable pageable = PageRequest.of(pageNumber, pageSize);
                Page<Book> page = bookDao.findAll(pageable);
                BookPage bookPage = new BookPage();
                PageBase pageBase = new PageBase();
                pageBase.setPageNumber(pageNumber);
                pageBase.setPageSize(pageSize);
                pageBase.setHasNextPage(page.hasNext());
                pageBase.setTotalPages(page.getTotalPages());
                pageBase.setTotalElements(page.getTotalElements());
                bookPage.setPageBase(pageBase);
                bookPage.setContent(page.getContent());
                return bookPage;
        }
- 自定义分页灵活，但是需要自己定义实体和分页查询逻辑，服务端实现的逻辑比较多，自带的分页，不够灵活，分页查询客户端实现起来麻烦，比如第一次查询的时候只传查询个数，以后需要传开始的游标

### 添加自定义命令

    自定义长度验证命令

- 定义LengthDirective类实现SchemaDirectiveWiring接口，并重写onField

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
                        if(value == null) {
                            return null;
                        } else {
                            String val = (String) value;
                            if(val.length() > maxValue || val.length() < minValue) {
                                throw new RuntimeException(field.getName() + "字段长度不符");
                            }
                            return val;
                        }
            });
            // now change the field definition to have the new authorising data fetcher
            environment.getCodeRegistry().dataFetcher(parentType, field, lengthDataFetcher);
            return field;
        }

- 添加SchemaDirective得bean，spring boot会自动注入到SchemaParser

        @Bean
        public SchemaDirective lengthSchemaDirective() {
            return new SchemaDirective("length", new LengthDirective());
        }

- schema定义命令

        directive @length(min: Int!, max: Int!) on FIELD_DEFINITION

- 需要添加验证的字段添加

        @length(min: 1, max: 100) 


### 使用的注意事项    

- 每一个 GraphQL 服务都有一个 query 类型，可能有一个 mutation 类型。这两个类型和常规对象类型无差，但是它们之所以特殊，是因为它们定义了每一个 GraphQL 查询的入口。
- 一个接口是一个抽象类型，它包含某些字段，而对象类型必须包含这些字段，才能算实现了这个接口。
- 如果想使用graphql自带的分页，需要使用groupId：com.graphql-java-kickstart， artifactId：graphql-java-tools的工具包，版本必须在5.4.0及以上
- schema中定义的接口必须和query和mutation的解析器里定义的一致，包括接口名称，参数，是否必输，响应类型（使用自带分页时除外）
- schema中有的接口，在解析器中一定要实现，就是个数相同
- 如果schema中定义了接口，后台实现的时候需要添加如下配置：
     ​      
         /**
          * 如果schema中定义接口，需要加这个bean
          */
         @Bean
         public SchemaParserDictionary characterSchemaParserDictionary() {
             return new SchemaParserDictionary()
                     .add(Human.class)
                     .add(Droid.class);
         }

- 调用N+1问题：例如，一个作者写了多本书籍，分页查询书籍的时候，这个作者可能被查询多次，这样是对于一个作者查询了n次，但是其实查询一次就可以
  可以通过dataLoader解决这个问题，默认缓存是存储到内存的，可以自定义cache
  ​      
  ​      
         /**
         * 会调用多次查询
         */
        public Author getAuthor(Book book) {
            return authorDao.findAuthorById(book.getAuthorId());
        }
        
         /**
          * 只会调用一次查询
          */
         public CompletableFuture<Author> getAuthor(Book book, DataFetchingEnvironment env) {
             env.getDataLoaderRegistry().computeIfAbsent("author", key -> DataLoader.newDataLoader((BatchLoader<String, Author>) keys -> CompletableFuture.supplyAsync(() -> authorDao.findByIdIn(keys))));
             return env.getDataLoader("author").load(book.getId()).thenApply(obj -> (Author) obj);
         }
- 错误处理：当查询语句出错或部分出错时，GraphQL 不会将错误直接上抛造成服务器 500 错误，而是依然会返回一个 json 对象，只是在这个对象中描述了发生怎样的错误       
    ​
- 订阅暂时没有实现，graphql现在默认使用的是apollo

- ​
