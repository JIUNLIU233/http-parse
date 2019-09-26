package tk.http_parse.service.impl;

import tk.http_parse.HttpParseMain;
import tk.http_parse.domain.HttpRequestInfo;
import tk.http_parse.service.HttpParseInterface;

import java.util.Map;

/**
 * Create by JIUN 2018/7/16
 */
public class HttpClientParse implements HttpParseInterface {
    @Override
    public String httpParse(HttpRequestInfo httpRequestInfo) {
        String httpclientStr = "CookieStore cookieStore = new BasicCookieStore(); \n" +
                "CloseableHttpClient client" + HttpParseMain.clickOrder + " = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build(); \n";

        String method = httpRequestInfo.getMethod();
        if (method != null && method.equalsIgnoreCase("post")) {
            httpclientStr += "HttpPost httpRequest" + HttpParseMain.clickOrder + " = new HttpPost(\"" + httpRequestInfo.getUrl() + "\"); \n\n";
            Map<String, String> requestData = httpRequestInfo.getRequestData();
            if (requestData == null) {
                String requestStr = httpRequestInfo.getRequestStr();
                httpclientStr += "String requestString" + HttpParseMain.clickOrder + " = \"" + requestStr + "\" ;\n";
                httpclientStr += "HttpEntity entity" + HttpParseMain.clickOrder + " = EntityBuilder.create().setText(requestString" + HttpParseMain.clickOrder + ").build(); \n" +
                        "httpRequest" + HttpParseMain.clickOrder + ".setEntity(entity" + HttpParseMain.clickOrder + "); \n\n";
            } else {
                httpclientStr += "List<NameValuePair> nameValuePairs" + HttpParseMain.clickOrder + " = new ArrayList<>();\n";
                for (Map.Entry<String, String> entry : requestData.entrySet()) {
                    httpclientStr += "nameValuePairs" + HttpParseMain.clickOrder + ".add(new BasicNameValuePair(\"" + entry.getKey() + "\", \"" + entry.getValue() + "\"));\n";
                }
                httpclientStr += "HttpEntity entity" + HttpParseMain.clickOrder + " = EntityBuilder.create().setParameters(nameValuePairs" + HttpParseMain.clickOrder + ").build(); \n" +
                        "httpRequest" + HttpParseMain.clickOrder + ".setEntity(entity" + HttpParseMain.clickOrder + "); \n\n";
            }
        } else {
            httpclientStr += "HttpGet httpRequest" + HttpParseMain.clickOrder + " = new HttpGet(\"" + httpRequestInfo.getUrl() + "\"); \n\n";
        }
        Map<String, String> headers = httpRequestInfo.getHeaders();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key != null && key.contains("Content-Length")) continue;
            httpclientStr += "httpRequest" + HttpParseMain.clickOrder + ".setHeader(\"" + key + "\", \"" + entry.getValue() + "\");\n";
        }

        httpclientStr += "\nCloseableHttpResponse execute" + HttpParseMain.clickOrder + " = client" + HttpParseMain.clickOrder + ".execute(httpRequest" + HttpParseMain.clickOrder + "); \n" +
                "String body" + HttpParseMain.clickOrder + " = EntityUtils.toString(execute" + HttpParseMain.clickOrder + ".getEntity());";


        return httpclientStr;
    }
}
