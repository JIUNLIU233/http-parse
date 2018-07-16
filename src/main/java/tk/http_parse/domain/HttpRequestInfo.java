package tk.http_parse.domain;

import java.util.Map;

/**
 * Create by JIUN 2018/7/16
 */
public class HttpRequestInfo {

    String method; //请求方法
    String url;//请求地址
    Map<String, String> headers;//请求头
    Map<String, String> cookies;//请求cookie信息

    String requestStr;//post请求参数，如json等
    Map<String, String> requestData;//post请求参数，如form表单，键值对请求


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getRequestStr() {
        return requestStr;
    }

    public void setRequestStr(String requestStr) {
        this.requestStr = requestStr;
    }

    public Map<String, String> getRequestData() {
        return requestData;
    }

    public void setRequestData(Map<String, String> requestData) {
        this.requestData = requestData;
    }
}
