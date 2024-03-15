package moe.moti.simplewindow.service;

import moe.moti.simplewindow.util.Constant;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SwingDemo {
    private final JFrame frame;
    private final JTextArea textArea;

    public SwingDemo() {
        // 设置全局字体
        Font globalFont = new Font("Microsoft YaHei", Font.PLAIN, 14); // 修改为你想要的字体
        UIManager.put("Button.font", globalFont);
        UIManager.put("RadioButton.font", globalFont);
        UIManager.put("CheckBox.font", globalFont);
        UIManager.put("Label.font", globalFont);
        UIManager.put("textArea.font", globalFont);

        // 创建主窗口
        frame = new JFrame("Simple Window");
        frame.setSize(460, 180); // 增加窗口高度以容纳上方空白区域

        // 创建上方、左侧和右侧的空白面板，各高宽20px
        JPanel topSpacer = new JPanel();
        topSpacer.setPreferredSize(new Dimension(1, 20)); // 设置上方空白面板大小
        JPanel leftSpacer = new JPanel();
        leftSpacer.setPreferredSize(new Dimension(20, 1)); // 设置左侧空白面板大小
        JPanel rightSpacer = new JPanel();
        rightSpacer.setPreferredSize(new Dimension(20, 1)); // 设置右侧空白面板大小

        // 创建文本显示框
        textArea = new JTextArea();
        textArea.setEditable(false); // 禁止编辑
        textArea.setText(Constant.GUIDANCE);

        // 创建按钮1：群峦打铁器
        JButton button1 = new JButton(Constant.BUTTON_1);
        button1.addActionListener(e -> {
            if (!Objects.equals(textArea.getText(), Constant.FUNCTION_1)) {
                textArea.setText(Constant.FUNCTION_1);
            } else {
                textArea.setText(Constant.GUIDANCE);
                new TerraManufactureCal(frame);
                setVisible(false);
            }
        });

        // 创建按钮2
        JButton button2 = new JButton(Constant.BUTTON_2);
        button2.addActionListener(e -> {
            if (!Objects.equals(textArea.getText(), Constant.FUNCTION_2)) {
                textArea.setText(Constant.FUNCTION_2);
            } else {
                textArea.setText(Constant.GUIDANCE);
                new SQLDataCreator(Constant.TITLE_2, frame);
                setVisible(false);
            }
        });

        // 创建按钮3
        JButton button3 = new JButton(Constant.BUTTON_3);
        button3.addActionListener(e -> {
            if (!Objects.equals(textArea.getText(), Constant.FUNCTION_3)) {
                textArea.setText(Constant.FUNCTION_3);
            } else {
                textArea.setText(Constant.GUIDANCE);
                new NuoInvoiceHelper(Constant.TITLE_3, frame);
                setVisible(false);
            }
        });

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        // 创建主面板，并设置布局为网格布局
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 创建一个面板，用于容纳文本框和左侧空白面板
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.add(leftSpacer, BorderLayout.WEST); // 添加左侧空白面板
        textAreaPanel.add(textArea, BorderLayout.CENTER); // 添加文本框
        textAreaPanel.add(rightSpacer, BorderLayout.EAST); // 添加右侧空白面板

        // 将文本框和上方空白面板添加到主面板的北部
        mainPanel.add(topSpacer, BorderLayout.NORTH); // 添加上方空白面板
        mainPanel.add(textAreaPanel, BorderLayout.CENTER); // 添加文本框和左右空白面板
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // 添加按钮面板

        frame.add(mainPanel);

        // 窗口居中显示
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 显示窗口
        frame.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
