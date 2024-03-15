package moe.moti.simplewindow.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.micrometer.core.instrument.util.StringUtils;
import moe.moti.simplewindow.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class NuoInvoiceHelper {
    private final JFrame frame;
    private final JFrame fatherFrame;
    private JTextArea textArea;
//    private String invoParamPreset = "";
    private Map<String, JTextArea> queryParamsMap;
//    private Map<String, JTextArea> invoParamsMap;
    private JTextArea orderno;
    private JTextArea tableName;
    private JTextArea subTag;
    private JTextArea rawParam;
    private static final Logger log = LogManager.getLogger();
    protected static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 构造方法，初始设定
     * @param title 窗口标题
     * @param fatherFrame 父窗口，用于关闭窗口后返回
     */
    public NuoInvoiceHelper(String title, JFrame fatherFrame) {

        frame = new JFrame(title);
        this.fatherFrame = fatherFrame;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 初始化
        initialize();

        // 显示窗口
        setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void closeWay() {
        fatherFrame.setVisible(true);
    }

    /**
     * 初始化整个面板
     */
    private void initialize() {

        frame.setSize(1400, 400);
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
        textArea.setTabSize(4);

        // 设置自动换行
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // 创建生成数据按钮：3个(暂定)执行数据库操作的按钮
        JButton button;
        JPanel buttonPanel_1 = new JPanel();
        JPanel buttonPanel_2 = new JPanel();
        JPanel buttonPanel_3 = new JPanel();
        for (int i = 0; i < Constant.INVO_HELPER_BUTTON.length; i++) {
            final int index = i;
            button = new JButton(Constant.INVO_HELPER_BUTTON[i]);
            button.addActionListener(e -> {
                buttonDeal(index);
            });
            if (i < Constant.INVO_HELPER_BUTTON.length / 3) {
                buttonPanel_1.add(button);
            } else if (i < Constant.INVO_HELPER_BUTTON.length / 3 * 2){
                buttonPanel_2.add(button);
            } else {
                buttonPanel_3.add(button);
            }
        }

        // 创建主面板，并设置布局为网格布局
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 创建一个面板，用于容纳文本框和左右空白面板
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.add(leftSpacer, BorderLayout.WEST); // 添加左侧空白面板

        // 创建一个 JScrollPane 并将文本框添加到其中
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 垂直滚动条根据需要显示

        textAreaPanel.add(scrollPane, BorderLayout.CENTER); // 添加文本框到滚动面板中
        textAreaPanel.add(rightSpacer, BorderLayout.EAST); // 添加右侧空白面板

        // 将文本框和上方空白面板添加到主面板的北部
        mainPanel.add(topSpacer, BorderLayout.NORTH); // 添加上方空白面板
        mainPanel.add(textAreaPanel, BorderLayout.CENTER); // 添加文本框和左右空白面板

        // 创建一个面板，用于容纳按钮面板，并设置布局为网格布局，1行2列
        JPanel buttonContainerPanel = new JPanel(new GridLayout(3, 1));
        buttonContainerPanel.add(buttonPanel_1); // 添加第一个按钮面板
        buttonContainerPanel.add(buttonPanel_2); // 添加第二个按钮面板
        buttonContainerPanel.add(buttonPanel_3); // 添加第二个按钮面板

        // 左侧选项面板，中间文本面板，右侧功能按钮面板，都放到底部面板中
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        JPanel bottomPanelR = new JPanel(new GridBagLayout());
//        bottomPanelR.add(initializeOptionPanel(), new GridBagProxy().setGridy(0).getConstraints());
        bottomPanelR.add(buttonContainerPanel, new GridBagProxy().setGridy(0).getConstraints());
        bottomPanel.add(initializeParamArea(), new GridBagProxy().setGridx(0).setWeightx(0.5).getConstraints());
        bottomPanel.add(initializeInfoArea() , new GridBagProxy().setGridx(1).setWeightx(0.3).getConstraints());
        bottomPanel.add(bottomPanelR, new GridBagProxy().setGridx(2).setWeightx(0.2).getConstraints());

        // 将按钮容器面板添加到主面板的南部
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // 添加按钮容器面板

        frame.add(mainPanel);

        // 窗口居中显示
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea.setText("");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWay();
            }
        });
    }

//    /**
//     * 初始化选项面板
//     */
//    private JPanel initializeOptionPanel() {
//        // 创建一个面板，用于容纳下拉选择框，并设置布局为网格布局，1行1列
//        JPanel optionPanel = new JPanel(new GridLayout(1, 1));
//
//        // 创建一个下拉选择框
//        JComboBox<String> comboBox = new JComboBox<>();
//        comboBox.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
//
//        // 添加下拉选择框的选项和选中状态监听器
//        comboBox.addItem("请选择……");
//        for (String s : Constant.INVO_HELPER_PARAM_PRESET) {
//            comboBox.addItem(s);
//        }
//        comboBox.addItemListener(e -> {
//            // 获取选中的选项
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                invoParamPreset = (String) e.getItem();
//                setInvoParamPreset(invoParamPreset);
//            }
//        });
//
//        optionPanel.add(new JLabel(Constant.INVO_HELPER_PRESET_LABEL));
//        optionPanel.add(comboBox);
//
//        return optionPanel;
//    }

    /**
     * 初始化数票查询信息文本框
     */
    private JPanel initializeInfoArea() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        orderno = new JTextArea();
        tableName = new JTextArea();
        subTag = new JTextArea();
        rawParam = new JTextArea();

        infoPanel.add(createTextAreaWithLabel(Constant.INVO_HELPER_ORDER_NO_LABEL, orderno, Constant.INVO_HELPER_ORDER_NO_DEFAULT,
                Constant.INVO_HELPER_TEXT_AREA_DEFAULT_WIDTH + 100, Constant.INVO_HELPER_TEXT_AREA_DEFAULT_HEIGHT), new GridBagProxy().setGridx(0).setGridy(0).setAnchor(GridBagConstraints.EAST).getConstraints());
        infoPanel.add(createTextAreaWithLabel(Constant.INVO_HELPER_TABLE_NAME_LABEL, tableName, Constant.INVO_HELPER_TABLE_NAME_DEFAULT,
                Constant.INVO_HELPER_TEXT_AREA_DEFAULT_WIDTH + 100, Constant.INVO_HELPER_TEXT_AREA_DEFAULT_HEIGHT), new GridBagProxy().setGridx(0).setGridy(1).setAnchor(GridBagConstraints.EAST).getConstraints());
        infoPanel.add(createTextAreaWithLabel(Constant.INVO_HELPER_SUB_TAG_LABEL, subTag, Constant.INVO_HELPER_SUB_TAG_DEFAULT,
                Constant.INVO_HELPER_TEXT_AREA_DEFAULT_WIDTH + 100, Constant.INVO_HELPER_TEXT_AREA_DEFAULT_HEIGHT), new GridBagProxy().setGridx(0).setGridy(2).setAnchor(GridBagConstraints.EAST).getConstraints());
        infoPanel.add(createTextAreaWithLabel(Constant.INVO_HELPER_RAW_PARAM_LABEL, rawParam, Constant.INVO_HELPER_RAW_PARAM_DEFAULT,
                Constant.INVO_HELPER_TEXT_AREA_DEFAULT_WIDTH + 100, Constant.INVO_HELPER_TEXT_AREA_DEFAULT_HEIGHT), new GridBagProxy().setGridx(0).setGridy(3).setAnchor(GridBagConstraints.EAST).getConstraints());

        analyzeRawParam();

        return infoPanel;
    }

    /**
     * 分析 raw 参数并拆分成各个参数
     */
    private void analyzeRawParam() {
        rawParam.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // 这个方法不处理纯文本的改变
            }

            public void removeUpdate(DocumentEvent e) {
                // 当从文档中删除文本时调用
                updateParams();
            }

            public void insertUpdate(DocumentEvent e) {
                // 当向文档中添加文本时调用
                updateParams();
            }

            private void updateParams() {
                SwingUtilities.invokeLater(() -> {
                    String text = rawParam.getText();
                    try {
                    log.info("-----------> analyzeRawParam text={}", text);
                        text = ValueUtil.ObjectStrToJson(text);
                    log.info("-----------> transfer text={}", text);
                        Map<String, String> map = gson.fromJson(text, Map.class);
                        for (String key : map.keySet()) {
                            if (queryParamsMap.containsKey(key)) {
                                queryParamsMap.get(key).setText((String) map.get(key));
                                queryParamsMap.get(key).setForeground(Color.BLACK);
                            } else if ("orderno".equalsIgnoreCase(key)) {
                                orderno.setText((String) map.get(key));
                                orderno.setForeground(Color.BLACK);
                            } else if ("tableName".equalsIgnoreCase(key)) {
                                tableName.setText((String) map.get(key));
                                tableName.setForeground(Color.BLACK);
                            } else if ("renew".equalsIgnoreCase(key)) {
                                subTag.setText((String) map.get(key));
                                subTag.setForeground(Color.BLACK);
                            }
                        }
                    } catch (Exception ignored) {
                    }
                });
            }
        });
    }

    /**
     * 初始化数票查询参数文本框
     */
    @SuppressWarnings("all")
    private JPanel initializeParamArea() {
        queryParamsMap = new HashMap<>();
        JPanel paramPanel = new JPanel(new GridBagLayout());
        String[] paramKey = ValueUtil.subArr(Constant.INVO_HELPER_QUERY_PARAM_KEY, Constant.INVO_HELPER_QUERY_SPLIT, Constant.INVO_HELPER_QUERY_PARAM_KEY.length);
        String[] paramStr = ValueUtil.subArr(Constant.INVO_HELPER_QUERY_PARAM_STR, Constant.INVO_HELPER_QUERY_SPLIT, Constant.INVO_HELPER_QUERY_PARAM_STR.length);
        String[] paramDefault = ValueUtil.subArr(Constant.INVO_HELPER_QUERY_PARAM_DEFAULT, Constant.INVO_HELPER_QUERY_SPLIT, Constant.INVO_HELPER_QUERY_PARAM_DEFAULT.length);
        int tab = (paramKey.length - 1) / 3 + 1;
        for (int i = 0; i < paramKey.length; i++) {
            JTextArea textArea = new JTextArea();
            queryParamsMap.put(paramKey[i], textArea);
            paramPanel.add(createTextAreaWithLabel(paramStr[i], textArea, i >= paramDefault.length ? "" : paramDefault[i],
                    Constant.INVO_HELPER_TEXT_AREA_DEFAULT_WIDTH + (i >= (paramKey.length - 1) / 3 + 4 ? 0 : (i >= (paramKey.length - 1) / 3 + 1 ? 100 : 200)), Constant.INVO_HELPER_TEXT_AREA_DEFAULT_HEIGHT),
                    new GridBagProxy().setGridx(i / tab).setGridy(i % tab).setAnchor(GridBagConstraints.EAST).getConstraints());
        }
        JPanel paramBottom = new JPanel(new GridBagLayout());
        JPanel paramAll = new JPanel(new GridBagLayout());
        paramAll.add(paramPanel, new GridBagProxy().setGridx(0).setGridy(0).getConstraints());
        for (int i = 0; i < Constant.INVO_HELPER_QUERY_SPLIT; i++) {
            JTextArea textArea = new JTextArea();
            queryParamsMap.put(Constant.INVO_HELPER_QUERY_PARAM_KEY[i], textArea);
            paramBottom.add(createTextAreaWithLabel(Constant.INVO_HELPER_QUERY_PARAM_KEY[i], textArea, i >= Constant.INVO_HELPER_QUERY_PARAM_DEFAULT.length ? "" : Constant.INVO_HELPER_QUERY_PARAM_DEFAULT[i],
                    770, Constant.INVO_HELPER_TEXT_AREA_DEFAULT_HEIGHT),
                    new GridBagProxy().setGridx(0).setGridy(i).setAnchor(GridBagConstraints.EAST).getConstraints());
        }
        paramAll.add(paramBottom, new GridBagProxy().setGridx(0).setGridy(1).getConstraints());
        return paramAll;
    }

//    /**
//     * 应用数票参数预设
//     * @param invoParamPreset
//     */
//    private void setInvoParamPreset(String invoParamPreset) {
//        if (invoParamPreset.isEmpty()) {
//            return;
//        }
//        String[] param = Constant.INVO_HELPER_PARAM_PRESET_MAP.get(invoParamPreset);
//        if (null == param) {
//            for (int i = 0; i < Constant.INVO_HELPER_PARAM_CONF_KEY.length; i++) {
//                invoParamsMap.get(Constant.INVO_HELPER_PARAM_CONF_KEY[i]).setText(i >= Constant.INVO_HELPER_PARAM_CONF_DEFAULT.length ? "" : Constant.INVO_HELPER_PARAM_CONF_DEFAULT[i]);
//                invoParamsMap.get(Constant.INVO_HELPER_PARAM_CONF_KEY[i]).setForeground(Color.GRAY);
//            }
//            return;
//        }
//        for (int i = 0; i < Constant.INVO_HELPER_PARAM_CONF_KEY.length; i++) {
//            invoParamsMap.get(Constant.INVO_HELPER_PARAM_CONF_KEY[i]).setText(param[i]);
//            invoParamsMap.get(Constant.INVO_HELPER_PARAM_CONF_KEY[i]).setForeground(Color.BLACK);
//        }
//    }

    @SuppressWarnings("all")
    private JPanel createTextAreaWithLabel(String labelStr, JTextArea textArea, String defaultStr, int width, int height) {
        textArea.setLineWrap(false);

        // 设置边框
        textArea.setBorder(BorderFactory.createEtchedBorder());

        // 设置高度和长度
        textArea.setPreferredSize(new Dimension(width, height));

        // 默认值显示和消失的效果
        textArea.setText(defaultStr);
        textArea.setForeground(Color.GRAY);

        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(defaultStr)) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                } else {
                    textArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText(defaultStr);
                    textArea.setForeground(Color.GRAY);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel(labelStr));
        panel.add(textArea);

        return panel;
    }

    private void buttonDeal(int index) {
        switch (index) {
            case 0:
                checkInvoice(
                        orderno.getText(),
                        tableName.getText()
                );
                break;
            case 2:
                renewInvoice(
                        orderno.getText(),
                        tableName.getText(),
                        subTag.getText(),
                        getParamsMap()
                );
                break;
            case 1:
                checkInvoice(
                        orderno.getText(),
                        tableName.getText(),
                        subTag.getText()
                );
                break;
            case 3:
                renewInvoice(
                        orderno.getText(),
                        tableName.getText(),
                        getParamsMap()
                );
                break;
            case 4:
                delInvoice(
                        orderno.getText(),
                        tableName.getText(),
                        subTag.getText()
                );
                break;
            case 5:
                break;
            default:
                break;
        }
    }

    private SortedMap<String, String> getParamsMap() {
        SortedMap<String, String> paramsMap = new TreeMap<>();
        for (String key : Constant.INVO_HELPER_QUERY_PARAM_KEY) {
            paramsMap.put(key, queryParamsMap.get(key).getText());
        }
        return paramsMap;
    }

    private SortedMap<String, String> sign(Map<String, String> map) {
        SortedMap<String, String> signMap = new TreeMap<>(map);
        signMap.put("key", EnvironmentConstant.INVO_HELPER_ALL_ELEC_INVOICE_KEY);
        signMap.put("sign", ValueUtil.MD5(gson.toJson(signMap)));
        signMap.remove("key");
        return signMap;
    }

    private void checkInvoice(String orderno, String tableName) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("orderno", orderno);
            map.put("tableName", tableName);
//            map.put("renew", renew);
            SortedMap<String, String> signMap = sign(map);
            String params = gson.toJson(signMap);
            log.info("-----------> checkInvoice signMap={}", params);
            String respond = RequestUtil.post(EnvironmentConstant.INVO_HELPER_ALL_ELEC_INVOICE_CHECK_URL, gson.toJson(signMap));
            if (StringUtils.isNotBlank(respond)) {
                textArea.append(ValueUtil.printMapNew(respond));
                textArea.append("\n");
            } else {
                textArea.append("查询失败！\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkInvoice(String orderno, String tableName, String renew) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("orderno", orderno);
            map.put("tableName", tableName);
            map.put("renew", renew);
            SortedMap<String, String> signMap = sign(map);
            String params = gson.toJson(signMap);
            log.info("-----------> checkInvoice signMap={}", params);
            String respond = RequestUtil.post(EnvironmentConstant.INVO_HELPER_ALL_ELEC_INVOICE_CHECK_URL, gson.toJson(signMap));
            if (StringUtils.isNotBlank(respond)) {
                textArea.append(ValueUtil.printMapNew(respond));
                textArea.append("\n");
            } else {
                textArea.append("查询失败！\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void renewInvoice(String orderno, String tableName, String subTag, Map<String, String> map) {
        map.put("orderno", orderno);
        map.put("tableName", tableName);
        map.put("renew", subTag);
        try {
            SortedMap<String, String> signMap = sign(map);
            String params = gson.toJson(signMap);
            log.info("-----------> renewInvoice signMap={}", params);
            String respond = RequestUtil.post(EnvironmentConstant.INVO_HELPER_ALL_ELEC_INVOICE_RENEW_URL, gson.toJson(signMap));
            if (StringUtils.isNotBlank(respond)) {
                textArea.append(ValueUtil.printMapNew(respond));
                textArea.append("\n");
            } else {
                textArea.append("开票失败！\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void renewInvoice(String orderno, String tableName, Map<String, String> map) {
        map.put("orderno", orderno);
        map.put("tableName", tableName);
        try {
            SortedMap<String, String> signMap = sign(map);
            String params = gson.toJson(signMap);
            log.info("-----------> renewInvoice signMap={}", params);
            String respond = RequestUtil.post(EnvironmentConstant.INVO_HELPER_ALL_ELEC_INVOICE_RENEW_URL, gson.toJson(signMap));
            if (StringUtils.isNotBlank(respond)) {
                textArea.append(ValueUtil.printMapNew(respond));
                textArea.append("\n");
            } else {
                textArea.append("开票失败！\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void delInvoice(String orderno, String tableName, String renew) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("orderno", orderno);
            map.put("tableName", tableName);
            map.put("renew", renew);
            SortedMap<String, String> signMap = sign(map);
            String params = gson.toJson(signMap);
            log.info("-----------> checkInvoice signMap={}", params);
            String respond = RequestUtil.post(EnvironmentConstant.INVO_HELPER_ALL_ELEC_INVOICE_DEL_URL, gson.toJson(signMap));
            if (StringUtils.isNotBlank(respond)) {
                textArea.append(ValueUtil.printMapNew(respond));
                textArea.append("\n");
            } else {
                textArea.append("删除失败！\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
