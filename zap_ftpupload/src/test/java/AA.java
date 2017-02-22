/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2017/1/4
 * Time: 15:47
 */
public class AA {

    public String rootPath = formatRootPath("aaaaaa");

    public String formatRootPath(String str) {
        if (!(str.endsWith("/") || str.endsWith("\\"))) {
            str = str + "/";
        }
        return str;
    }

    public void df() {
        System.out.println(rootPath);
    }
}
