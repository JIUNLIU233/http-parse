package tk.http_parse.service;

import com.alibaba.fastjson.JSONObject;
import tk.http_parse.domain.HttpRequestInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by JIUN 2018/7/16
 */
public class HttpParseService {

    /**
     * 输入HTTP请求报文和对应请求库service，返对应的请求库代码
     *
     * @param raw                HTTP 请求报文
     * @param httpParseInterface 相关语言对应的service
     * @return
     */
    public static String httpParse(String raw, HttpParseInterface httpParseInterface) {
        HttpRequestInfo httpRequestInfo = filterInfo(raw, httpParseInterface);
        return httpParseInterface.httpParse(httpRequestInfo);
    }

    /**
     * 筛选http中的请求信息,将相关信息封装到具体实体类中
     *
     * @param raw
     * @param httpParseInterface
     * @return
     */
    public static HttpRequestInfo filterInfo(String raw, HttpParseInterface httpParseInterface) {
        HttpRequestInfo httpRequestInfo = new HttpRequestInfo();
        String[] split = raw.split("\n");
        String[] firstLines = split[0].split(" ");

        Map<String, String> headers = new HashMap<>();
        String cookieStr = null;

        for (int i = 1; i < split.length; i++) {
            if (split[i].split(":").length < 2) break;
            String substring = split[i].substring(0, split[i].indexOf(":"));
            if (!substring.equalsIgnoreCase("Cookie")) {
                headers.put(substring, split[i].substring(split[i].indexOf(":") + 1).trim());
            } else {
                cookieStr = split[i];
            }
        }

        Map<String, String> cookies = new HashMap<>();
        if (cookieStr != null && !cookieStr.equalsIgnoreCase("")) {
            cookieStr = cookieStr.substring(cookieStr.indexOf(":") + 1);
            String[] strings = cookieStr.split(";");
            mapDeal(strings, cookies);
        }

        String requestStr = "";
        if (!split[split.length - 1].equalsIgnoreCase("") && split[split.length - 2].equalsIgnoreCase("")) {
            try {
                requestStr = URLDecoder.decode(split[split.length - 1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Map<String, String> data = new HashMap<>();
        if (firstLines[0].equalsIgnoreCase("post")) {// post请求体设置。
            if (!requestStr.equalsIgnoreCase("")) {

                String requestBody = "";
                try {
                    JSONObject parseObject = JSONObject.parseObject(requestStr);
                    httpRequestInfo.setRequestStr(requestStr);
                } catch (Exception e) {// 证明不是json体请求，需要将其进行其他方式的转换
                    String[] strings = requestStr.split("&");
                    mapDeal(strings, data);
                    httpRequestInfo.setRequestData(data);
                }
            }
        }

        httpRequestInfo.setMethod(firstLines[0]);
        httpRequestInfo.setUrl(firstLines[1]);
        httpRequestInfo.setHeaders(headers);
        httpRequestInfo.setCookies(cookies);
        return httpRequestInfo;
    }


    public static void mapDeal(String[] strings, Map map) {
        for (int i = 0; i < strings.length; i++) {// 相关cookie设置
            String[] keyValue = strings[i].split("=", strings[i].indexOf("=") + 1);
            if (keyValue.length < 2) {
                map.put(keyValue[0], "");
            } else {
                map.put(keyValue[0], keyValue[1]);
            }
        }
    }
}
