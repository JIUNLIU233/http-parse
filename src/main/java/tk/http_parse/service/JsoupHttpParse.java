package tk.http_parse.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Create by JIUN LIU on 2018/4/28.
 */
public class JsoupHttpParse {


    public String httpParse(String raw) {

        String[] split = raw.split("\n");

        String jsoupStr = "Connection.Response response = Jsoup.connect(\"";

        String[] firstLines = split[0].split(" ");
        String method = ".method(Connection.Method." + firstLines[0] + ")\n";
        jsoupStr += firstLines[1] + "\")\n";
        jsoupStr += "\t.ignoreContentType(true)\n";
        jsoupStr += "\t" + method;

        String headerStr = "\t.headers(new HashMap<String, String>() {{\n";
        String requestStr = "";
        String cookieStr = "";

        for (int i = 1; i < split.length; i++) {
            if (split[i].split(":").length < 2) break;
            String substring = split[i].substring(0, split[i].indexOf(":"));
            if (!substring.equalsIgnoreCase("Cookie")) {
                headerStr += "\t\tput(\"" + substring + "\",\"" + split[i].substring(split[i].indexOf(":") + 1).trim() + "\");\n";
            } else {
                cookieStr = split[i];
            }
        }
        headerStr += "\t}})\n";
        jsoupStr += headerStr;

        if (!split[split.length - 1].equalsIgnoreCase("") || split[split.length - 2].equalsIgnoreCase("")) {
            requestStr = split[split.length - 1];
        }

        if (!cookieStr.equalsIgnoreCase("")) {
            String cookie = "\t.cookies(new HashMap<String, String>() {{\n";
            cookieStr = cookieStr.substring(cookieStr.indexOf(":") + 1);
            String[] strings = cookieStr.split(";");

            for (int i = 0; i < strings.length; i++) {// 相关cookie设置
                String[] keyValue = strings[i].split("=", strings[i].indexOf("=") + 1);
                if (keyValue.length < 2) {
                    cookie += "\t\tput(\"" + keyValue[0] + "\",\"" + "\");\n";
                } else {
                    cookie += "\t\tput(\"" + keyValue[0] + "\",\"" + keyValue[1] + "\");\n";
                }
            }
            cookie += "\t}})\n";
            jsoupStr += cookie;
        }

        if (firstLines[0].equalsIgnoreCase("post")) {// post请求体设置。
            if (!requestStr.equalsIgnoreCase("")) {

                String requestBody = "";
                try {
                    JSONObject parseObject = JSONObject.parseObject(requestStr);
                    requestBody += " .requestBody(\"" + requestStr + "\")";
                } catch (Exception e) {// 证明不是json体请求，需要将其进行其他方式的转换
                    String[] strings = requestStr.split("&");
                    requestBody = "\t .data(new HashMap<String, String>() {{\n";
                    for (int i = 0; i < strings.length; i++) {// 相关cookie设置
                        String[] keyValue = strings[i].split("=", strings[i].indexOf("=") + 1);
                        if (keyValue.length < 2) {
                            requestBody += "\t\tput(\"" + keyValue[0] + "\",\"" + "\");\n";
                        } else {
                            requestBody += "\t\tput(\"" + keyValue[0] + "\",\"" + keyValue[1] + "\");\n";
                        }
                    }
                    requestBody += "\t\t}})\n";
                }
                jsoupStr += requestBody;
            }
        }
        jsoupStr += "\t.execute();";
        return jsoupStr;
    }
}
