package pages.staticdata;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 6/3/13
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ErrorMessages {

    INCORRECT_USER_NAME_OR_PASSWORD("That user name or password did not match our records. Please try again"),
    ;

    private String messageText;

    private ErrorMessages(String strValue){
        this.messageText = strValue;
    }

    public String getMessageText(){
        return messageText;
    }
}
