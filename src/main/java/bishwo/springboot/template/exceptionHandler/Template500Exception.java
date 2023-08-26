package bishwo.springboot.template.exceptionHandler;

public class Template500Exception extends RuntimeException {
    public Template500Exception(String errorMessage) {
        super(errorMessage);
    }
}