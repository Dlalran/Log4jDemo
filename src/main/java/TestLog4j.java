import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog4j {
//    通过slf4j的Logger工厂类获得具体的Logger实现类，通过log4j实现
    private static final Logger logger = LoggerFactory.getLogger(TestLog4j.class);

//    通过log4j直接获得Logger
//    private static final org.apache.log4j.Logger logger2 = org.apache.log4j.Logger.getLogger(TestLog4j.class);

    public static void main(String[] args) {
        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warn message");
        logger.error("Error message");

//        slf4j的logger可以通过占位符对日志内容进行拼接
        String msg = "Test message";
        logger.info("The message is {}", msg);
    }
}
