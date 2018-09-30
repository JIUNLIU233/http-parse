package tk.http_parse.service.impl;

import com.alibaba.fastjson.JSONObject;
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
                "CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build(); \n";

        String method = httpRequestInfo.getMethod();
        if (method != null && method.equalsIgnoreCase("post")) {
            httpclientStr += "HttpPost httpRequest = new HttpPost(\"" + httpRequestInfo.getUrl() + "\"); \n\n";
            Map<String, String> requestData = httpRequestInfo.getRequestData();
            if (requestData == null) {
                String requestStr = httpRequestInfo.getRequestStr();
                httpclientStr += "String requestString = \"" + requestStr.replaceAll("\"", "\\\\\"") + "\" ;\n";
                System.out.println(JSONObject.parseObject(requestStr).toJSONString());
                httpclientStr += "HttpEntity entity = EntityBuilder.create().setText(requestString).build(); \n" +
                        "httpRequest.setEntity(entity); \n\n";
            } else {
                httpclientStr += "List<NameValuePair> nameValuePairs = new ArrayList<>();\n";
                for (Map.Entry<String, String> entry : requestData.entrySet()) {
                    httpclientStr += "nameValuePairs.add(new BasicNameValuePair(\"" + entry.getKey() + "\", \"" + entry.getValue() + "\"));\n";
                }
                httpclientStr += "HttpEntity entity = EntityBuilder.create().setParameters(nameValuePairs).build(); \n" +
                        "httpRequest.setEntity(entity); \n\n";
            }
        } else {
            httpclientStr += "HttpGet httpRequest = new HttpGet(\"" + httpRequestInfo.getUrl() + "\"); \n\n";
        }
        Map<String, String> headers = httpRequestInfo.getHeaders();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key != null && key.contains("Content-Length")) continue;
            httpclientStr += "httpRequest.setHeader(\"" + key + "\", \"" + entry.getValue() + "\");\n";
        }

        httpclientStr += "\nCloseableHttpResponse execute = client.execute(httpRequest); \n" +
                "String body = EntityUtils.toString(execute.getEntity());";


        return httpclientStr;
    }
}
