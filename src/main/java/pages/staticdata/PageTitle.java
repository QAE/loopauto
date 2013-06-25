package pages.staticdata;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 6/24/13
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PageTitle {

    LOGIN("Loopme - Log in"),
    MAIN_PAGE("LoopMe Media - Mobile Ad Inbox Technology and Social Targeting"),
    ;

    private String titleValue;

    private PageTitle(String titleValue) {
        this.titleValue = titleValue;
    }

    public String getTitleValue() {
        return titleValue;
    }
}
