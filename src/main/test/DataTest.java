import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.Test;

/**
 * create by JIUN·LIU
 * create time 2019/8/13
 */
public class DataTest {

    @Test
    public void test(){




        JSONObject object = new JSONObject(){{
            put("test","test1");
            put("test2","test2");
            put("test3","test3");
            put("test4","test4");
            put("test5",new JSONArray(){{
                add("test6");
            }});
        }};
        System.err.println(StringEscapeUtils.escapeJson(object.toString()));

    }


}
