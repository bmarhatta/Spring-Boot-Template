package bishwo.springboot.template.entity;

public class Constants {

    private Constants() {
    }

    //Headers
    public static final String AUTH = "Authorization";

    //error and error log messages
    public static final String INVALID_REQUEST = "Invalid Request";
    public static final String DOWNSTREAM_ERROR = "Downstream error";
    public static final String DB_ERROR = "Error while making DB call";
    public static final String OPEN_AI_BLANK_RESPONSE = "openAi response is blank";

    //model constant
    public static final String MODEL = "text-davinci-003";

    //successful response message
    public static final String SUCCESSFUL_RESPONSE = "Response from openAi {}";

}
