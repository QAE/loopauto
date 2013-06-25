package fw;

import fw.basic.ApplicationManager;
import fw.basic.helpers.BaseHelper;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;


/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 23/01/13
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class for quick start by select Role
 **/
public class Start extends BaseHelper {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(Start.class);

    public Start(ApplicationManager manager) throws MalformedURLException {
        super(manager);
    }

}
