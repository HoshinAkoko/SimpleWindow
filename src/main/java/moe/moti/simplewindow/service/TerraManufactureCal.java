package moe.moti.simplewindow.service;

import moe.moti.simplewindow.util.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class TerraManufactureCal {

    private final Map<String, Integer> backMap = new HashMap<>();
    private final List<Integer> line = new ArrayList<>();
    private final List<Integer> lineEnd = new ArrayList<>();
    private final List<List<Integer>> answer = new ArrayList<>();
    private final int[] targetArr = {0, 0};
    private int part = 0;
    private String text = "输入初始品质：";
    private final List<List<Integer>> endStep = new ArrayList<>();

    private final JFrame frame;
    private final JFrame fatherFrame;
    private JTextArea textArea;
    public TerraManufactureCal(JFrame fatherFrame) {
        frame = new JFrame(Constant.TITLE_1);
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

        // 创建计算器按钮：8个打铁按键，1个重置按键，1个切换步骤按键
        JButton button;
        JPanel buttonPanel_1 = new JPanel();
        JPanel buttonPanel_2 = new JPanel();
        for (int i = 0; i < Constant.TERRA_CAL_BUTTON.length; i++) {
            final int num = Constant.TERRA_CAL_SORT[i];
            button = new JButton(Constant.TERRA_CAL_BUTTON[Constant.TERRA_CAL_SORT[i]]);
            button.addActionListener(e -> {
                line.add(num);
                sum();
                showLine();
            });
            if (i < Constant.TERRA_CAL_BUTTON.length / 2) {
                buttonPanel_1.add(button);
            } else {
                buttonPanel_2.add(button);
            }
        }
        button = new JButton("重置");
        button.addActionListener(e -> {
            //重置
            reset();
        });
        buttonPanel_1.add(button);
        button = new JButton("分步收尾");
        button.addActionListener(e -> {
            //分步收尾
            endStep();
        });
        buttonPanel_1.add(button);
        button = new JButton("继续");
        button.addActionListener(e -> {
            //继续
            next();
        });
        buttonPanel_2.add(button);
        button = new JButton("分步求解");
        button.addActionListener(e -> {
            //分步求解
            fixEnd(0, 0);
            showLineByStep();
        });
        buttonPanel_2.add(button);

        // 创建主面板，并设置布局为网格布局
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 创建一个面板，用于容纳文本框和左侧空白面板
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

        // 将按钮容器面板添加到主面板的南部
        mainPanel.add(buttonContainerPanel, BorderLayout.SOUTH); // 添加按钮容器面板

        frame.add(mainPanel);

        // 窗口居中显示
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWay();
            }
        });

        reset();
    }

    public Map<String, Integer> test(int target) {
        System.out.println("目标是：" + target);
        List<Integer> ans = fix(target);
        int work = 0;
        if (null != ans) {
            for (int i = 1; i < ans.size(); i++) {
                System.out.println(String.format("%3d", work) + " → " + Constant.TERRA_CAL_BUTTON[ans.get(i)] + " " + (Constant.TERRA_CAL_VALUE[ans.get(i)] < 0 ? "-" : "+") + " " + String.format("%2d", Math.abs(Constant.TERRA_CAL_VALUE[ans.get(i)])) + " → " + String.format("%3d", (work += Constant.TERRA_CAL_VALUE[ans.get(i)])));
            }
        } else {
            System.out.println("Not found!");
        }
        return backMap;
    }

    public List<Integer> fix(int target) {
        List<List<Integer>> bfs = new ArrayList<>();
        List<Integer> top = new ArrayList<>();
        top.add(target);
        bfs.add(top);
        int i = 0;
        int cut = 0;
        int add = 0;
        while (i < bfs.size()) {
            //进行剪枝处理
            if (bfs.get(i).get(0) - bfs.get(0).get(0) > Constant.TERRA_CAL_CUT_MAX_OFFSET || -bfs.get(i).get(0) > Constant.TERRA_CAL_CUT_MAX_OFFSET) {
                bfs.remove(i);
                cut++;
                continue;
            }
            //扩展当前节点 i
            if (bfs.size() < Constant.MEMORY_LIMIT) {
                int lastStep = bfs.get(i).size() > 1 ? bfs.get(i).get(bfs.get(i).size() - 1) : -1;
                int lastTarget = bfs.get(i).get(0);
                if (lastTarget > 0) {
                    for (int j = (lastStep < 0 || Constant.TERRA_CAL_VALUE[lastStep] < 0 ? Constant.TERRA_CAL_VALUE.length - 1 : lastStep); j >= Constant.TERRA_CAL_VALUE.length / 2; j--) {
                        top = new ArrayList<>(bfs.get(i));
                        top.set(0, bfs.get(i).get(0) - Constant.TERRA_CAL_VALUE[j]);
                        top.add(j);
                        bfs.add(top);
                        add++;
                        if (bfs.get(bfs.size() - 1).get(0) == 0) {
                            backMap.put("node", i);
                            backMap.put("cut", cut);
                            backMap.put("add", add);
                            return bfs.get(bfs.size() - 1);
                        }
                    }
                }
                if (lastTarget < 0) {
                    for (int j = (lastStep < 0 || Constant.TERRA_CAL_VALUE[lastStep] > 0 ? 0 : lastStep); j < Constant.TERRA_CAL_VALUE.length / 2; j++) {
                        top = new ArrayList<>(bfs.get(i));
                        top.set(0, bfs.get(i).get(0) - Constant.TERRA_CAL_VALUE[j]);
                        top.add(j);
                        bfs.add(top);
                        add++;
                        if (bfs.get(bfs.size() - 1).get(0) == 0) {
                            backMap.put("node", i);
                            backMap.put("cut", cut);
                            backMap.put("add", add);
                            return bfs.get(bfs.size() - 1);
                        }
                    }
                }
            }
            i++;
        }
        backMap.put("node", i);
        backMap.put("cut", cut);
        backMap.put("add", add);
        return null;
    }

    public void sum() {
        int ans = 0;
        for (int i: line) {
            if (i >= 0 && i < Constant.TERRA_CAL_VALUE.length) {
                ans += Constant.TERRA_CAL_VALUE[i];
            }
        }
        targetArr[part] = ans;
    }

    public void showLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append(text);
        sb.append("\n------------------------------------------------------------------------------------------\n");
        sb.append("目标：");
        sb.append(targetArr[0]);
        sb.append("\t\t");
        sb.append("收尾：");
        sb.append(targetArr[1]);
        sb.append("\n");
        for (int i: line) {
            sb.append(Constant.TERRA_CAL_BUTTON[i]);
            sb.append("\t");
        }
        sb.append("\n");
        for (int i: line) {
            sb.append(Constant.TERRA_CAL_VALUE[i]);
            sb.append("\t");
        }
        sb.append("\n==================================================\n");
        textArea.setText(sb.toString());
    }

    public void reset() {
        line.clear();
        lineEnd.clear();
        endStep.clear();
        answer.clear();
        targetArr[0] = 0;
        targetArr[1] = 0;
        part = 0;
        text = "输入初始品质：";
        showLine();
    }

    public void next() {
        if (part == 0) {
            line.clear();
            text = "输入收尾步骤：";
            part = 1;
            showLine();
        } else if (part == 1) {
            lineEnd.addAll(line);
            line.clear();
            text = "求解中……";
            List<Integer> ans = fix(targetArr[0] - targetArr[1]);
            if (null != ans) {
                ans.remove(0);
                line.addAll(ans);
                line.addAll(lineEnd);
                text = "求解成功！";
                showLine();
                line.clear();
                lineEnd.clear();
                endStep.clear();
                answer.clear();
                targetArr[0] = 0;
                targetArr[1] = 0;
                part = 0;
            } else {
                text = "求解失败！";
                showLine();
            }
        }
    }
    
    public void endStep() {
        if (part == 0) {
            line.clear();
            text = "输入收尾步骤：";
            part = 1;
            showLine();
        } else if (part == 1) {
            if (line.size() > 0) {
                endStep.add(new ArrayList<>(line));
                line.clear();
                showLine();
            } else {
                fixEnd(0, 0);
                line.clear();
                if (answer.size() > 0) {
                    text = "求解成功！显示所有可用结果：";
                    showAllLineByStep();
                    line.clear();
                    lineEnd.clear();
                    endStep.clear();
                    answer.clear();
                    targetArr[0] = 0;
                    targetArr[1] = 0;
                    part = 0;
                } else {
                    text = "求解失败！";
                    showLine();
                }
            }
        }
    }

    public void fixEnd(int value, int step) {
        if (step >= endStep.size()) {
            List<Integer> ans = fix(targetArr[0] - value);
            if (null != ans) {
                int sum = 0;
                for (int i: lineEnd) {
                    sum += Constant.TERRA_CAL_VALUE[i];
                }
                ans.set(0, sum);
                ans.addAll(lineEnd);
                answer.add(ans);
            }
        } else {
            for (int i: endStep.get(step)) {
                lineEnd.add(i);
                fixEnd(value + Constant.TERRA_CAL_VALUE[i], step + 1);
                lineEnd.remove(step);
            }
        }
    }

    public void showLineByStep() {
        line.clear();
        if (answer.size() > 0) {
            List<Integer> ans = answer.get(0);
            int min = answer.get(0).size();
            for (List<Integer> i : answer) {
                if (i.size() < min) {
                    ans = i;
                    min = i.size();
                }
            }
            ans.remove(0);
            line.addAll(ans);
            line.addAll(lineEnd);
            text = "求解成功！";
            showLine();
            line.clear();
            lineEnd.clear();
            endStep.clear();
            answer.clear();
            targetArr[0] = 0;
            targetArr[1] = 0;
            part = 0;
        } else {
            text = "求解失败！";
            showLine();
        }
    }

    public void showAllLineByStep() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append(text);
        sb.append("\n");
        for (List<Integer> line: answer) {
            sb.append("------------------------------------------------------------------------------------------\n");
            sb.append("目标：");
            sb.append(targetArr[0]);
            sb.append("\t\t");
            sb.append("收尾：");
            sb.append(line.get(0));
            sb.append("\n");
            line.remove(0);
            for (int i: line) {
                sb.append(Constant.TERRA_CAL_BUTTON[i]);
                sb.append("\t");
            }
            sb.append("\n");
            for (int i: line) {
                sb.append(Constant.TERRA_CAL_VALUE[i]);
                sb.append("\t");
            }
            sb.append("\n");
        }
        sb.append("==================================================\n");
        textArea.setText(sb.toString());
    }
}
