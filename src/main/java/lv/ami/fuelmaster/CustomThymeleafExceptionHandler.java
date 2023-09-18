package lv.ami.fuelmaster;

import org.springframework.stereotype.Component;

@Component("exceptionHandler")
public class CustomThymeleafExceptionHandler /*implements ExceptionHandler*/ {

/*    private final ThymeleafExceptionHandlingProperties properties;

    public CustomThymeleafExceptionHandler(ThymeleafExceptionHandlingProperties properties) {
        this.properties = properties;
    }

    public void handleException(ITemplateContext context, Throwable throwable) {
        if (throwable instanceof TemplateEngineException) {
            // Handle exceptions thrown by the Thymeleaf template engine.
            // Example: TemplateInputException, TemplateProcessingException, etc.
            TemplateEngineException ex = (TemplateEngineException) throwable;
            String templateName = ex.getTemplateName();
            int line = ex.getLine();
            int col = ex.getCol();
            String message = ex.getMessage();

            // Do something with the exception (e.g., log it).
            System.err.printf("TemplateEngineException at %s:%d:%d: %s\n", templateName, line, col, message);
        } else {
            // Handle other exceptions.
            // Example: NullPointerException, IOException, etc.

            // Do something with the exception (e.g., log it).
            System.err.printf("Exception: %s\n", throwable.getMessage());
        }
    }*/
}
