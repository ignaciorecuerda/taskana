package pro.taskana.rest;

/**
 * Example Application showing the implementation of taskana-rest-spring.
 */

/*
@SpringBootApplication
@Import({RestConfiguration.class})
public class ExampleRestHistoryApplication {

    @Value("${taskana.schemaName:TASKANA}")
    public String schemaName;

    @Autowired
    private SampleDataHistoryGenerator sampleDataGenerator;

    public static void main(String[] args) {
        SpringApplication.run(ExampleRestHistoryApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource")
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties props = new DataSourceProperties();
        props.setUrl("jdbc:h2:mem:taskana;IGNORECASE=TRUE;LOCK_MODE=0;INIT=CREATE SCHEMA IF NOT EXISTS " + schemaName);
        return props;
    }

    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @DependsOn("getTaskanaEngine") // generate sample sampledata after schema was inserted
    public SampleDataHistoryGenerator generateSampleData(DataSource dataSource) throws SQLException {
        sampleDataGenerator = new SampleDataHistoryGenerator(dataSource);
        return sampleDataGenerator;
    }

    @PostConstruct
    private void init() {
        sampleDataGenerator.generateSampleData(schemaName);
    }
}
*/
