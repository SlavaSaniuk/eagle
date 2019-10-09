package by.bsac.conf;

public class LoggerDefaultLogs {

    public static final String INITIALIZE_CONFIGURATION = "Start to initialize [%s] configuration class.";

    public static class INITIALIZATION {

        public static String initConfig(Class clazz) {
            return String.format(INITIALIZE_CONFIGURATION, clazz.getSimpleName());
        }
    }

    public static final String CREATE_BEAN_START = "Start to create [%s] bean.";
    public static final String CREATE_BEAN_FINISH = "Bean [%s] successfully was created.";

    //Manually set spring beans
    public static final String DEPENDENCY_VIA_SETTER = "Set [%s] bean to [%s] bean via setter method.";
    public static final String DEPENDENCY_VIA_CONSTRUCTOR = "Set [%s] bean to [%s] bean via constructor.";

    //Autowire spring beans
    public static class AUTOWIRING {

        public static String viaConstructor(Class c1, Class c2) {
            String via_constructor = "[AUTOWIRE] :  [%s] bean to [%s] bean via bean CONSTRUCTOR. ";
            return String.format(via_constructor, c1.getSimpleName(), c2.getSimpleName());
        }

        public static String viaSetter(Class c1, Class c2) {
            String via_setter = "[AUTOWIRE] :  [%s] bean to [%s] bean via bean SETTER method. ";
            return String.format(via_setter, c1.getSimpleName(), c2.getSimpleName());
        }
    }

}
