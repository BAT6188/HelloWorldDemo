import com.goldmsg.ftputil.FtpUploadMain;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/30
 * Time: 14:14
 */
public class Body {
    public static Logger logger = LogManager.getLogger(FtpUploadMain.class);

    public static void main(String[] args) {
        String etcDir = args[0];
        if (!(etcDir.endsWith("/") || etcDir.endsWith("\\"))) {
            etcDir = etcDir + "/";
        }
        PropertyConfigurator.configure(etcDir + "log4j.properties");
        new Body().a();

    }
    public void a() {
        try {
            b();
        } catch (Exception e) {
            System.out.println("a这里错误了");
            e.printStackTrace();
//            logger.error("a这里错误了",e);
        } finally {
            System.out.println("a错误了");
        }
    }

    public void b() {

        try {
            int i = 1;
            int j = 0;
            int f;
            f = i / j;
        } catch (Exception e) {
            System.out.println("b这里错误了");
            e.printStackTrace();
            logger.error("b这里错误了",e);
        } finally {
            System.out.println("b错误了");
        }

    }
}
