package cn.sangedon.boot.config;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-26 20:23
 */
/*@ToString
@Data
@Component
public class DemoConfig extends PropertySourcesPlaceholderConfigurer implements EnvironmentAware {
    @Autowired
    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            String path = environment.getProperty("config.path");
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            Properties props = new Properties();
            props.load(fileInputStream);
            System.out.println(111);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/
