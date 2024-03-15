package moe.moti.simplewindow.service;

import moe.moti.simplewindow.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SQLDataCreator {
    private final JFrame frame;
    private final JFrame fatherFrame;
    private JTextArea textArea;
    private JTextArea lpArea;
    private JTextArea numberArea;

    // 添加静态布尔变量用于控制选项的选中状态
    private static boolean aikkModeSelected = false;
    private static boolean ec20ModeSelected = false;
    private static boolean randConfSelected = false;
    private static boolean NoneConfSelected = false;

    /**
     * 构造方法，初始设定
     * @param title 窗口标题
     * @param fatherFrame 父窗口，用于关闭窗口后返回
     */
    public SQLDataCreator(String title, JFrame fatherFrame) {

        frame = new JFrame(title);
        this.fatherFrame = fatherFrame;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 初始化
        initialize();

        // 显示窗口
        setVisible(true);
    }

    /**
     * 初始化整个面板
     */
    private void initialize() {

        frame.setSize(800, 400);
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

        // 创建生成数据按钮：6个(暂定)执行数据库操作的按钮
        JButton button;
        JPanel buttonPanel_1 = new JPanel();
        JPanel buttonPanel_2 = new JPanel();
        for (int i = 0; i < Constant.SQL_DATA_CREATOR_BUTTON.length; i++) {
            final int index = i;
            button = new JButton(Constant.SQL_DATA_CREATOR_BUTTON[i]);
            button.addActionListener(e -> {
                buttonDeal(index);
            });
            if (i < Constant.SQL_DATA_CREATOR_BUTTON.length / 2) {
                buttonPanel_1.add(button);
            } else {
                buttonPanel_2.add(button);
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

        // 创建一个面板，用于容纳按钮面板，并设置布局为网格布局，2行1列
        JPanel buttonContainerPanel = new JPanel(new GridLayout(2, 1));
        buttonContainerPanel.add(buttonPanel_1); // 添加第一个按钮面板
        buttonContainerPanel.add(buttonPanel_2); // 添加第二个按钮面板

        // 左侧选项面板，中间文本面板，右侧功能按钮面板，都放到底部面板中
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.add(initializeOptionPanel(), new GridBagProxy().setGridx(0).setWeightx(0.4).getConstraints());
        bottomPanel.add(initializeLpArea(), new GridBagProxy().setGridx(1).setWeightx(0.2).getConstraints());
        bottomPanel.add(buttonContainerPanel, new GridBagProxy().setGridx(2).setWeightx(0.4).getConstraints());

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

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void closeWay() {
        fatherFrame.setVisible(true);
    }

    /**
     * 初始化选项面板
     */
    private JPanel initializeOptionPanel() {
        // 创建一个面板，用于容纳单选框和多选框，并设置布局为网格布局，2行2列
        JPanel optionPanel = new JPanel(new GridLayout(2, 2));

        // 创建两个互斥的单选框
        JRadioButton radioButton1 = new JRadioButton(Constant.SQL_DATA_OPTION_RADIO[0]);
        JRadioButton radioButton2 = new JRadioButton(Constant.SQL_DATA_OPTION_RADIO[1]);

        // 创建两个自由的多选框
        JCheckBox checkBox1 = new JCheckBox(Constant.SQL_DATA_OPTION_CHECKBOX[0]);
        JCheckBox checkBox2 = new JCheckBox(Constant.SQL_DATA_OPTION_CHECKBOX[1]);

        // 添加单选框和多选框的选中状态监听器
        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aikkModeSelected = radioButton1.isSelected();
                ec20ModeSelected = !aikkModeSelected;
                radioButton2.setSelected(ec20ModeSelected);
            }
        });

        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ec20ModeSelected = radioButton2.isSelected();
                aikkModeSelected = !ec20ModeSelected;
                radioButton1.setSelected(aikkModeSelected);
            }
        });

        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randConfSelected = checkBox1.isSelected();
            }
        });

        checkBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NoneConfSelected = checkBox2.isSelected();
            }
        });

        radioButton1.setSelected(true);
        aikkModeSelected = true;

        // 将单选框和多选框添加到面板
        optionPanel.add(radioButton1);
        optionPanel.add(checkBox1);
        optionPanel.add(radioButton2);
        optionPanel.add(checkBox2);

        return optionPanel;
    }

    /**
     * 初始化车牌文本框
     */
    private JPanel initializeLpArea() {

        lpArea = new JTextArea();
        lpArea.setLineWrap(true);
        lpArea.setWrapStyleWord(true);

        // 设置边框
        lpArea.setBorder(BorderFactory.createEtchedBorder());

        // 设置高度和长度
        lpArea.setPreferredSize(new Dimension(100, 24));

        // 默认值显示和消失的效果
        String defaultValue = Constant.SQL_DATA_LP_AREA_DEFAULT_VALUE;
        lpArea.setText(defaultValue);
        lpArea.setForeground(Color.GRAY);

        numberArea = new JTextArea();
        numberArea.setLineWrap(true);
        numberArea.setWrapStyleWord(true);

        // 设置边框
        numberArea.setBorder(BorderFactory.createEtchedBorder());

        // 设置高度和长度
        numberArea.setPreferredSize(new Dimension(100, 24));

        // 默认值显示和消失的效果
        String defaultNumberValue = "1";
        numberArea.setText(defaultNumberValue);
        numberArea.setForeground(Color.GRAY);

        lpArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lpArea.getText().equals(defaultValue)) {
                    lpArea.setText("");
                    lpArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (lpArea.getText().isEmpty()) {
                    lpArea.setText(defaultValue);
                    lpArea.setForeground(Color.GRAY);
                }
            }
        });

        numberArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (numberArea.getText().equals(defaultNumberValue)) {
                    numberArea.setText("");
                    numberArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (numberArea.getText().isEmpty()) {
                    numberArea.setText(defaultNumberValue);
                    numberArea.setForeground(Color.GRAY);
                }
            }
        });

        JPanel lpPanel = new JPanel();
        lpPanel.add(new JLabel(Constant.SQL_DATA_LP_LABEL));
        lpPanel.add(lpArea);

        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel(Constant.SQL_DATA_NUMBER_LABEL));
        numberPanel.add(numberArea);

        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.add(lpPanel, new GridBagProxy().setGridx(0).setGridy(0).setAnchor(GridBagConstraints.EAST).getConstraints());
        middlePanel.add(numberPanel, new GridBagProxy().setGridx(0).setGridy(1).setAnchor(GridBagConstraints.EAST).getConstraints());

        return middlePanel;
    }

    private void buttonDeal(int index) {
        switch (index) {
            case 0:
                switchCreatedChannelInt(
                        Constant.SQL_DATA_AIKK_ID_PLG,
                        aikkModeSelected ? Constant.SQL_DATA_CREATED_CHANNEL_INT_AIKK : Constant.SQL_DATA_CREATED_CHANNEL_INT_EC20
                );
                break;
            case 1:
                switchCreatedChannelInt(
                        Constant.SQL_DATA_EC20_ID_PLG,
                        aikkModeSelected ? Constant.SQL_DATA_CREATED_CHANNEL_INT_AIKK : Constant.SQL_DATA_CREATED_CHANNEL_INT_EC20
                );
                break;
            case 2:
                break;
            case 3:
                insertParkingRecord(
                        numberArea.getText().matches("[0-9]+") ? Integer.parseInt(numberArea.getText()) : 1,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_TABLE_TAG : Constant.SQL_DATA_EC20_TABLE_TAG,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_ID_PLG : Constant.SQL_DATA_EC20_ID_PLG,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_ID_PL : Constant.SQL_DATA_EC20_ID_PL,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_ID_PLGA : Constant.SQL_DATA_EC20_ID_PLGA,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_DEVICE_IN : Constant.SQL_DATA_EC20_DEVICE_IN,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_DEVICE_OUT : Constant.SQL_DATA_EC20_DEVICE_OUT,
                        lpArea.getText()
                );
                break;
            case 4:
                insertWhiteRecord(
                        numberArea.getText().matches("[0-9]+") ? Integer.parseInt(numberArea.getText()) : 1,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_TABLE_TAG : Constant.SQL_DATA_EC20_TABLE_TAG,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_ID_PLG : Constant.SQL_DATA_EC20_ID_PLG,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_ID_PL : Constant.SQL_DATA_EC20_ID_PL,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_ID_PLGA : Constant.SQL_DATA_EC20_ID_PLGA,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_DEVICE_IN : Constant.SQL_DATA_EC20_DEVICE_IN,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_DEVICE_OUT : Constant.SQL_DATA_EC20_DEVICE_OUT,
                        lpArea.getText()
                );
                break;
            case 5:
                insertParkSpaceRecord(
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_TABLE_TAG : Constant.SQL_DATA_EC20_TABLE_TAG,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_ID_PLG : Constant.SQL_DATA_EC20_ID_PLG,
                        aikkModeSelected ? Constant.SQL_DATA_AIKK_ID_PL : Constant.SQL_DATA_EC20_ID_PL
                );
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private String newLp() {
        StringBuilder sb = new StringBuilder();
        sb.append("苏");
        sb.append((char) (((int) (Math.random() * 26)) + 65));
        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * 36);
            if (random < 10) {
                sb.append(random);
            } else {
                sb.append((char) (random + 55));
            }
        }
        return sb.toString();
    }

    /**
     * 生成临停数据
     */
    @SuppressWarnings("SameParameterValue")
    private void insertParkingRecord(int num, String tableTag, String plgId, String plId, String plgaId, String deviceIn, String deviceOut, String lpConf) {
        if (num <= 0) {
            return;
        }
        List<String> sqlList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < num; i++) {
            String lp = lpConf;
            if (null == lp || lp.isEmpty() || Constant.SQL_DATA_LP_AREA_DEFAULT_VALUE.equalsIgnoreCase(lp)) {
                lp = newLp();
            } else if (!lp.matches("^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$") && !lp.matches("^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{6}$") && !lp.matches("^[\\u4e00-\\u9fa5]{1}[A-Z_0-9]{1}[A-Z_0-9]{4}(学|港|澳|台|领|警)$")) {
                textArea.append("车牌格式错误！\n");
                return;
            }
            int offset = randConfSelected ? (int) (Math.random() * 60 * 60 * 24 * 30) : 0;
            int stayTime = randConfSelected ? (int) (Math.random() * 60 * 60 * 4) : 60 * 30;
            String sql = "INSERT INTO parking_record" + tableTag + " (id, version, date_created, last_updated, enter_time, enter_time_int, exit_time_int, license_plate, enter_open_code, exit_open_code, real_charge, charge, pay_charge, pay_type, entrance_id, ticket_charge, deduction_rate, exit_id, exit_time, full_text_flag, is_free, parking_lot_group_id, status, vehicle_type, in_device, out_device, date_created_int, last_updated_int, parking_lot_id, car_brand, car_color, trigger_mode) VALUES (" +

                    /* id                     */  " UNHEX(REPLACE(UUID(), '-', ''))," +
                    /* version                */  " '0'," +
                    /* date_created           */  " FROM_UNIXTIME(UNIX_TIMESTAMP() - " + (offset + stayTime) + ")," +
                    /* last_updated           */  " FROM_UNIXTIME(UNIX_TIMESTAMP() - " + (offset) + ")," +
                    /* enter_time             */  " FROM_UNIXTIME(UNIX_TIMESTAMP() - " + (offset + stayTime) + ")," +
                    /* enter_time_int         */  " (UNIX_TIMESTAMP() - " + (offset + stayTime) + ")," +
                    /* exit_time_int          */  " (UNIX_TIMESTAMP() - " + (offset) + ")," +
                    /* license_plate          */  " '" + lp + "'," +
                    /* enter_open_code        */  " '03 AA'," +
                    /* exit_open_code         */  " '04 AB'," +
                    /* real_charge            */  " '0.02'," +
                    /* charge                 */  " '0.01'," +
                    /* pay_charge             */  " '0.00'," +
                    /* pay_type               */  " '0'," +
                    /* entrance_id            */  " UNHEX('" + plgaId + "')," +
                    /* ticket_charge          */  " '0.00'," +
                    /* deduction_rate         */  " '0.01'," +
                    /* exit_id                */  " UNHEX('" + plgaId + "')," +
                    /* exit_time              */  " FROM_UNIXTIME(UNIX_TIMESTAMP() - " + (offset) + ")," +
                    /* full_text_flag         */  " ''," +
                    /* is_free                */  " '\\0'," +
                    /* parking_lot_group_id   */  " UNHEX('" + plgId + "')," +
                    /* status                 */  " '\\0'," +
                    /* vehicle_type           */  " '0'," +
                    /* in_device              */  " '" + deviceIn + "'," +
                    /* out_device             */  " '" + deviceOut + "'," +
                    /* date_created_int       */  " (UNIX_TIMESTAMP() - " + (offset + stayTime) + ")," +
                    /* last_updated_int       */  " (UNIX_TIMESTAMP() - " + (offset) + ")," +
                    /* parking_lot_id         */  " UNHEX('" + plId + "')," +
                    /* car_brand              */  " '0'," +
                    /* car_color              */  " '0'," +
                    /* trigger_mode)          */  " '0'" +
                    ");";
            sqlList.add(sql);
        }
        try {
            count += new SQLUtil().executeBatch(sqlList);
        } catch (Exception e) {
            textArea.append(String.valueOf(e));
            e.printStackTrace();
        }
        textArea.append(sqlList.get(sqlList.size() - 1) + "\n");
        textArea.append("insert count: " + count + "\n");
    }

    /**
     * 生成内部车数据
     */
    @SuppressWarnings("SameParameterValue")
    private void insertWhiteRecord(int num, String tableTag, String plgId, String plId, String plgaId, String deviceIn, String deviceOut, String lp) {
        String wlId = "";
        String wlLp = "";
        String subSql = "";
        if (null != lp && !lp.isEmpty() && !Constant.SQL_DATA_LP_AREA_DEFAULT_VALUE.equalsIgnoreCase(lp)) {
            if (lp.matches("^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$") || lp.matches("^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{6}$") || lp.matches("^[\\u4e00-\\u9fa5]{1}[A-Z_0-9]{1}[A-Z_0-9]{4}(学|港|澳|台|领|警)$")) {
                subSql = " AND wl.license_plate = '" + lp + "'";
            } else {
                textArea.append("车牌格式错误！\n");
                return;
            }
        }
        List<Map<String, Object>> result = new SQLUtil().query("" +
                "SELECT HEX(wl.id) AS id, wl.license_plate AS lp\n" +
                " FROM white_list wl\n" +
                " LEFT JOIN parking_space_white_list pswl ON pswl.whitelist_id = wl.id\n" +
                " LEFT JOIN parking_space ps ON ps.id = pswl.parking_space_id\n" +
                " WHERE wl.parking_lot_group_id = UNHEX('" + plgId + "')\n" +
                " AND ps.parking_lot_id = UNHEX('" + plId + "')\n" +
                subSql +
//                " AND ps.expire_time_int > UNIX_TIMESTAMP()\n" +
                " ORDER BY ps.last_updated DESC\n" +
                " LIMIT 1");
        if (null != result && result.size() > 0) {
            wlId = (String) result.get(0).get("id");
            wlLp = (String) result.get(0).get("lp");
        }

        if (null == wlId || wlId.isEmpty() || "null".equalsIgnoreCase(wlId) || null == wlLp || wlLp.isEmpty() || "null".equalsIgnoreCase(wlLp)) {
            if (subSql.isEmpty()) {
                textArea.append("获取 " + (aikkModeSelected ? "AIKK" : "EC20") + " 默认白名单车牌失败！\n");
            } else {
                textArea.append("获取 " + (aikkModeSelected ? "AIKK" : "EC20") + " 指定车牌 " + lp + " 对应的白名单失败！\n");
            }
            return;
        }

        int offset = randConfSelected ? (int) (Math.random() * 60 * 60 * 24 * 30) : 0;
        int stayTime = randConfSelected ? (int) (Math.random() * 60 * 60 * 6) : 60 * 30;
        String sql = "INSERT INTO white_record" + tableTag + " (id, version, date_created, enter_time, entrance_id, exit_id, exit_time, last_updated, parking_lot_group_id, whitelist_id, license_plate, status, enter_open_code, exit_open_code, in_device, out_device, enter_time_int, exit_time_int, date_created_int, last_updated_int, parking_lot_id, car_brand, car_color, trigger_mode) VALUES (" +

                /* id                    */     " UNHEX(REPLACE(UUID(), '-', ''))," +
                /* version               */     " '0'," +
                /* date_created          */     " FROM_UNIXTIME(UNIX_TIMESTAMP() - " + (offset + stayTime) + ")," +
                /* enter_time            */     " FROM_UNIXTIME(UNIX_TIMESTAMP() - " + (offset + stayTime) + ")," +
                /* entrance_id           */     " UNHEX('" + plgaId + "')," +
                /* exit_id               */     " UNHEX('" + plgaId + "')," +
                /* exit_time             */     " FROM_UNIXTIME(UNIX_TIMESTAMP() - " + (offset) + ")," +
                /* last_updated          */     " FROM_UNIXTIME(UNIX_TIMESTAMP() - " + (offset) + " - " + ((int) (Math.random() * 60)) + ")," +
                /* parking_lot_group_id  */     " UNHEX('" + plgId + "')," +
                /** whitelist_id         */     " UNHEX('" + wlId + "')," +
                /** license_plate        */     " '" + wlLp + "'," +
                /* status                */     " '\\0'," +
                /** enter_open_code       */    " '02 AA'," +
                /* exit_open_code        */     " '02 AB'," +
                /* in_device             */     " '" + deviceIn + "'," +
                /* out_device            */     " '" + deviceOut + "'," +
                /* enter_time_int        */     " (UNIX_TIMESTAMP() - " + (offset + stayTime) + ")," +
                /* exit_time_int         */     " (UNIX_TIMESTAMP() - " + (offset) + ")," +
                /* date_created_int      */     " (UNIX_TIMESTAMP() - " + (offset + stayTime) + ")," +
                /* last_updated_int      */     " (UNIX_TIMESTAMP() - " + (offset) + " - " + ((int) (Math.random() * 60)) + ")," +
                /* parking_lot_id        */     " UNHEX('" + plId + "')," +
                /* car_brand             */     " '0'," +
                /* car_color             */     " '0'," +
                /* trigger_mode          */     " '0'" +

                ");";
        textArea.append(sql + "\n");
        int count = 0;
        try {
            count += new SQLUtil().execute(sql);
        } catch (Exception e) {
            textArea.append(String.valueOf(e));
            e.printStackTrace();
        }
        textArea.append("insert count: " + count + "\n");
    }

    @SuppressWarnings("SameParameterValue")
    private void insertParkSpaceRecord(String tableTag, String plgId, String plId) {
        List<Map<String, Object>> result = new SQLUtil().query("" +
                " SELECT HEX(wl.id) AS wlId, HEX(ps.id) AS psId, wr.date_created_int AS dateCreatedInt, wr.exit_time_int AS lastUpdatedInt\n" +
                " FROM white_record" + tableTag + " wr\n" +
                " LEFT JOIN white_list wl ON wl.id = wr.whitelist_id\n" +
                " LEFT JOIN parking_space_white_list pswl ON pswl.whitelist_id = wl.id\n" +
                " LEFT JOIN parking_space ps ON ps.id = pswl.parking_space_id" +
                " LEFT JOIN park_space_record psr ON psr.white_list_id = wl.id AND psr.date_created_int = wr.date_created_int\n" +
                " WHERE wr.parking_lot_group_id = UNHEX('" + plgId + "')\n" +
                " AND ps.parking_lot_id = UNHEX('" + plId + "')\n" +
                " AND wr.exit_time_int > 0\n" +
                " AND psr.id IS NULL\n");
        if (null == result || result.size() == 0) {
            textArea.append("insert count: 0\n");
            return;
        }
        Set<String> sqlSet = new HashSet<>();
        DataUtil.distinct(result, "wlId", "dateCreatedInt");
        for (Map<String, Object> resultMap : result) {
            String wlId = ValueUtil.valueOf(resultMap.get("wlId"));
            String psId = ValueUtil.valueOf(resultMap.get("psId"));
            int dateCreatedInt = ValueUtil.parseInt(resultMap.get("dateCreatedInt"));
            int exitTimeInt = ValueUtil.parseInt(resultMap.get("lastUpdatedInt"));
            if (dateCreatedInt == 0 || exitTimeInt == 0) {
                continue;
            }
            String sql = "INSERT INTO park_space_record (id, version, date_created, is_left, last_updated, parking_space_id, white_list_id, status, date_created_int, last_updated_int) VALUES (" +

                    /* id                    */     " UNHEX(REPLACE(UUID(), '-', ''))," +
                    /* version               */     " '0'," +
                    /* date_created          */     " FROM_UNIXTIME(" + dateCreatedInt + ")," +
                    /* is_left               */     " 1," +
                    /* last_updated          */     " FROM_UNIXTIME(" + exitTimeInt + ")," +
                    /* parking_space_id      */     " UNHEX('" + psId + "')," +
                    /* white_list_id         */     " UNHEX('" + wlId + "')," +
                    /* status                */     " '\\0'," +
                    /* date_created_int      */     " " + dateCreatedInt + "," +
                    /* last_updated_int      */     " " + exitTimeInt +
                    ");";

            textArea.append(sql + "\n");
            sqlSet.add(sql);
        }
        int count = 0;
        try {
            count += new SQLUtil().executeBatch(sqlSet);
        } catch (Exception e) {
            textArea.append(String.valueOf(e));
            e.printStackTrace();
        }
        textArea.append("insert count: " + count + "\n");

    }

    @SuppressWarnings("SameParameterValue")
    private void switchCreatedChannelInt(String plgId, int mode) {
        String sql = "UPDATE parking_lot_group SET created_channel_int = " + mode + " WHERE id = UNHEX('" + plgId + "');";
        textArea.append(sql + "\n");
        int count = 0;
        try {
            count += new SQLUtil().execute(sql);
        } catch (Exception e) {
            textArea.append(String.valueOf(e));
            e.printStackTrace();
        }
        textArea.append("update count: " + count + "\n");
    }
}
