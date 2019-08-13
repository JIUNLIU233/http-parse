package tk.http_parse;

import tk.http_parse.service.HttpParseService;
import tk.http_parse.service.impl.HttpClientParse;
import tk.http_parse.service.impl.JsoupHttpParse;

import javax.swing.*;
import java.awt.*;

/**
 * Create by JIUN LIU on 2018/4/28.
 */
public class HttpParseMain {


    public static Integer clickOrder = 0;

    public static void main(String[] args) {
        JFrame httpParseFrame = new JFrame("Http-Parse");

        httpParseFrame.setIconImage(new ImageIcon("./0.jpg").getImage());

        final JsoupHttpParse jsoupHttpParse = new JsoupHttpParse();

        JPanel inputPannel = new JPanel();

        /**
         * http raw 输入部分，
         */
        JLabel httpRawLabel = new JLabel("HTTP Raw:");
        httpRawLabel.setBounds(10, 50, 80, 25);
        inputPannel.add(httpRawLabel);

        JScrollPane httpRawScro = new JScrollPane();
        final JTextArea httpRawText = new JTextArea(15, 40);
        httpRawText.setBounds(100, 200, 165, 500);
        httpRawScro.setViewportView(httpRawText);
        inputPannel.add(httpRawScro);


        /**
         * http 请求转换后的输出部分
         */

        JPanel outPanel = new JPanel();
        JLabel httpOutLabel = new JLabel("HTTP Raw:");
        httpOutLabel.setBounds(10, 50, 80, 25);
        outPanel.add(httpOutLabel);

        JScrollPane httpOutScro = new JScrollPane();
        final JTextArea httpOutText = new JTextArea(15, 40);
        httpOutText.setBounds(100, 200, 165, 500);
        httpOutText.setEditable(false);
        httpOutScro.setViewportView(httpOutText);
        outPanel.add(httpOutScro);

        /**
         * 转换规则按钮部分
         */

        JPanel buttonPannel = new JPanel();

        /**
         * jsoup 转换
         */
        JButton jsoupButton = new JButton("Jsoup");
        jsoupButton.setBounds(10, 80, 80, 25);
        jsoupButton.addActionListener(e -> {
            String httpRawStr = httpRawText.getText();
            clickOrder++;
            httpOutText.setText(HttpParseService.httpParse(httpRawStr, new JsoupHttpParse()));

        });
        buttonPannel.add(jsoupButton);

        /**
         * HttpClient转换
         */
        JButton httpClientButton = new JButton("HttpClient");
        httpClientButton.setBounds(10, 80, 80, 25);
        httpClientButton.addActionListener(e -> {
            String httpRawStr = httpRawText.getText();
            clickOrder++;
            httpOutText.setText(HttpParseService.httpParse(httpRawStr, new HttpClientParse()));
        });
        buttonPannel.add(httpClientButton);

        /**
         * okhttp转换
         */
        JButton okHttpButton = new JButton("OKhttp");
        okHttpButton.setBounds(10, 80, 80, 25);
        okHttpButton.addActionListener(e -> httpOutText.setText("该功能暂不支持"));
        buttonPannel.add(okHttpButton);

        /**
         * Unirest 转换
         */
        JButton unirestButton = new JButton("序号重置");
        unirestButton.setBounds(10, 80, 80, 25);
        unirestButton.addActionListener(e -> {
            clickOrder = 0;
            httpOutText.setText("当前序号为：" + clickOrder);
        });
        buttonPannel.add(unirestButton);


        httpParseFrame.add(inputPannel);
        httpParseFrame.add(buttonPannel);
        httpParseFrame.add(outPanel);

        httpParseFrame.setLocation(700, 200);
        httpParseFrame.setSize(550, 650);
        httpParseFrame.setLayout(new FlowLayout());
        httpParseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        httpParseFrame.setVisible(true);
    }
}
