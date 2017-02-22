import com.goldmsg.ftputil.bean.GetFileBean;
import com.google.gson.Gson;

import static com.goldmsg.ftputil.commonUtils.HttpUtils.parseJsonWithGson;


/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2017/1/4
 * Time: 16:26
 */
public class CC {
    public static void main(String[] args) {
        String jsonString ="{    \"code\": 0,    \"message\": \"SUCCESS\",    \"body\": [        {            \"taskId\": \"a\",            \"fileId\": \"a\",            \"storageId\": \"a\",            \"uploadProtocol\": \"a\",            \"uploadUrl\": \"a\"        },        {            \"taskId\": \"b\",            \"fileId\": \"b\",            \"storageId\": \"b\",            \"uploadProtocol\": \"b\",            \"uploadUrl\": \"b\"        }    ]}";
        Gson gson = new Gson();
        GetFileBean getFileBean =parseJsonWithGson(jsonString, GetFileBean.class);
        for (GetFileBean.Body body : getFileBean.getBody()){
            System.out.println(new Gson().toJson(body));
        }
    }

}
