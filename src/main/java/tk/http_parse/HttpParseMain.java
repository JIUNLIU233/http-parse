package tk.http_parse;

import tk.http_parse.service.JsoupHttpParse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create by JIUN LIU on 2018/4/28.
 */
public class HttpParseMain {

    public static void main(String[] args) {
        JFrame httpParseFrame = new JFrame("Http-Parse");

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

        JButton jsoupButton = new JButton("Jsoup");
        jsoupButton.setBounds(10, 80, 80, 25);
        jsoupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String httpRawStr = httpRawText.getText();
                String s = jsoupHttpParse.httpParse(httpRawStr);
                httpOutText.setText(s);

            }
        });
        buttonPannel.add(jsoupButton);

        JButton httpClientButton = new JButton("HttpClient");
        httpClientButton.setBounds(10, 80, 80, 25);
        httpClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                httpOutText.setText(httpRawText.getText());
            }
        });
        buttonPannel.add(httpClientButton);

        JButton okHttpButton = new JButton("OKhttp");
        okHttpButton.setBounds(10, 80, 80, 25);
        okHttpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonPannel.add(okHttpButton);

        JButton unirestButton = new JButton("Unirest");
        unirestButton.setBounds(10, 80, 80, 25);
        unirestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
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
