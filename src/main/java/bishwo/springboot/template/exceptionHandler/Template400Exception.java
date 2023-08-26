package bishwo.springboot.template.exceptionHandler;

public class Template400Exception extends RuntimeException {
    public Template400Exception(String errorMessage) {
        super(errorMessage);
    }
}