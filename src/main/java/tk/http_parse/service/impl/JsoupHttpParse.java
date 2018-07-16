package tk.http_parse.service.impl;

import tk.http_parse.domain.HttpRequestInfo;
import tk.http_parse.service.HttpParseInterface;

import java.util.Map;

/**
 * Create by JIUN LIU on 2018/4/28.
 */
public class JsoupHttpParse implements HttpParseInterface {

    @Override
    public String httpParse(HttpRequestInfo httpRequestInfo) {
        StringBuilder jsoupStr = new StringBuilder("Connection.Response response = Jsoup.connect(\"");
        jsoupStr.append(httpRequestInfo.getUrl() + "\")\n");
        jsoupStr.append("\t.ignoreContentType(true)\n");
        jsoupStr.append("\t.ignoreHttpErrors(true)\n");
        jsoupStr.append("\t.method(Connection.Method." + httpRequestInfo.getMethod() + ")\n");
        if (httpRequestInfo.getHeaders() != null && httpRequestInfo.getHeaders().size() > 0) {
            jsoupStr.append("\t.headers(new HashMap<String, String>() {{\n");
            for (Map.Entry<String, String> entry : httpRequestInfo.getHeaders().entrySet()) {
                jsoupStr.append("\t\tput(\"" + entry.getKey() + "\",\"" + entry.getValue() + "\");\n");
            }
            jsoupStr.append("\t}})\n");
        }

        if (httpRequestInfo.getCookies() != null && httpRequestInfo.getCookies().size() > 0) {
            jsoupStr.append("\t.cookies(new HashMap<String, String>() {{\n");
            for (Map.Entry<String, String> entry : httpRequestInfo.getCookies().entrySet()) {
                jsoupStr.append("\t\tput(\"" + entry.getKey() + "\",\"" + entry.getValue() + "\");\n");
            }
            jsoupStr.append("\t}})\n");
        }

        if (httpRequestInfo.getMethod() != null &&
                !httpRequestInfo.getMethod().equalsIgnoreCase("") &&
                httpRequestInfo.getMethod().equalsIgnoreCase("post")) {
            if (httpRequestInfo.getRequestStr() != null ||
                    !httpRequestInfo.getRequestStr().equalsIgnoreCase("")) {
                jsoupStr.append(" .requestBody(\"" + httpRequestInfo.getRequestStr() + "\")");
            } else if (httpRequestInfo.getRequestData() != null ||
                    httpRequestInfo.getRequestData().size() > 0) {
                jsoupStr.append("\t.cookies(new HashMap<String, String>() {{\n");
                for (Map.Entry<String, String> entry : httpRequestInfo.getRequestData().entrySet()) {
                    jsoupStr.append("\t\tput(\"" + entry.getKey() + "\",\"" + entry.getValue() + "\");\n");
                }
                jsoupStr.append("\t}})\n");
            }
        }
        jsoupStr.append("\t.execute();\n");
        jsoupStr.append("String body = response.body();\n" +
                "Document document = Jsoup.parse(body);");
        return jsoupStr.toString();
    }
}
