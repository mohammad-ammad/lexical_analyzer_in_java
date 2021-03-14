package com.company;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lexser {
    JTextArea code_area = new JTextArea();
    JFrame frame = new JFrame();
    //font
    Font font_1 = new Font("SansSerif",Font.BOLD,30);
    Font font_2 = new Font("SansSerif",Font.BOLD,20);
    Font font_3 = new Font("SansSerif",Font.PLAIN,15);
    //scrollpane
    ScrollPane scroll = new ScrollPane();
    ScrollPane code_pane = new ScrollPane();
    JPanel header = new JPanel();
    JLabel header_label = new JLabel("Lexical Analyzer");
    JPanel body = new JPanel();
    JLabel b_heading_1 = new JLabel("Write your code here!");
    JButton all_new = new JButton("New");
    JButton scan = new JButton("Scan");
    JButton copy = new JButton("Copy");
    JButton paste = new JButton("paste");
    JButton comnt = new JButton("Comment Remover");
    JLabel token_label = new JLabel("Total tokens = 0");
    DefaultTableModel model = new DefaultTableModel(0,0);
    JTable table = new JTable();
    //backend working
    final String[] keywords = {"abstract", "assert",
            "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "uper",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while", "true", "NULL", "false"};
    String[] num =  {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    final String[] symbols = {"&", "|", "+", "-", "*", ",", "/", "%", "+", "-", "=", "<","<=", ">",">=","==","!=","!", "[", "]", "{", "}", "(", ")", ".", ";", ":","&&", "||"};
    Boolean flag = true;
    Boolean numflag=true;
    Boolean symbflag=true;
    int tokenCount;
    String[] output;
    public void scanMethod(){
        String value = code_area.getText();
        String output1 = value.replaceAll("//.*?\n", "\n");
        String output2 = output1.replaceAll("/\\*([^*]|[\\r\\n])*\\*/", "\n");
        output = output2.split("\\s+");
        for (int i = 0; i < output.length; i++) {
            if (output[i].matches("[ a-zA-Z]+")) {
                for (int j = 0; j < keywords.length; j++) {
                    if (output[i].equals(keywords[j])) {
                        flag = false;
                    }
                }
                if (!flag) {
                    tokenCount++;
                    model.addRow(new Object[]{output[i], "keyword", tokenCount});

                    flag = true;
                } else {
                    tokenCount++;
                    model.addRow(new Object[]{output[i], "identifier", tokenCount});

                }
                table.setModel(model);
            }
            for (int z=0;z<num.length; z++){
                if (output[i].contains(num[z])) {
                    numflag=false; }}
            if (!numflag)
            {
                tokenCount++;
                model.addRow(new Object[]{output[i], "number",tokenCount});
                numflag = true;
                table.setModel(model);
            }


            for (int j = 0; j < symbols.length; j++) {
                if (output[i].equals(symbols[j])) { symbflag=false; }}
            if(!symbflag){
                switch (output[i])
                {
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        tokenCount++;
                        model.addRow(new Object[]{output[i], "Arithmetic Operator", tokenCount});
                        symbflag=true;
                        break;
                    case "++":
                    case "--":
                    case "!":
                        tokenCount++;
                        model.addRow(new Object[]{output[i], "Unary Operator", tokenCount});
                        symbflag=true;
                        break;
                    case "<=":
                    case ">":
                    case "<":
                    case ">=":
                    case "!=":
                    case "==":
                        tokenCount++;
                        model.addRow(new Object[]{output[i], "Relational Operator", tokenCount});
                        symbflag=true;
                        break;
                    case "=":
                        tokenCount++;
                        model.addRow(new Object[]{output[i], "Assignment Operator", tokenCount});
                        symbflag=true;
                        break;
                    default:
                        tokenCount++;
                        model.addRow(new Object[]{output[i], "Symbols", tokenCount});
                        symbflag=true;
                        break;
                }

                table.setModel(model);

            }
            Pattern pattern = Pattern.compile("\".*?\"");
            Matcher m = pattern.matcher(output[i]);
            while (m.find()) {
                tokenCount++;
                model.addRow(new Object[]{output[i], "String Literal", tokenCount});
            }
            table.setModel(model);

            Pattern p = Pattern.compile("'[\\w\\s]+'");
            Matcher match = p.matcher(output[i]);
            while (match.find()) {
                tokenCount++;
                model.addRow(new Object[]{output[i], "character Literal", tokenCount});
            }
            table.setModel(model);


        }
        token_label.setText("Total tokens = "+tokenCount);
    }
    public void comntRemoverMethod(){
        b_heading_1.setText("code without comments is:");
        String value = code_area.getText();
        Pattern pattern = Pattern.compile("(?<!\\\")\\/\\/.*|(?<!\\\")\\/\\*(?:.*?\\n*)*\\*\\/");
        Matcher matcher = pattern.matcher(value);
        while(matcher.find()) {
            value  = value.replace(matcher.group(), "");
        }
        code_area.setText(value);
    }
    public void newMethod(){
        code_area.setText("");
        tokenCount = 0;
        model.setRowCount(0);
        token_label.setText("Total token = 0");
        b_heading_1.setText("Write your code here!");
    }
    lexser(){
        //creating frame
        frame.setSize(1366,900);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setting header panel
        header.setBounds(0,0,1366,60);
        header.setBackground(Color.decode("#069C54"));
        header.setLayout(null);
        //header label
        header_label.setBounds(580,3,400,60);
        header_label.setFont(font_1);
        header_label.setForeground(Color.decode("#FBFEFD"));
        //body panel
        body.setBounds(0,8,1388,820);
        body.setLayout(null);
//        body.setBackground(Color.decode("#FBFEFD"));
        //code area
        b_heading_1.setBounds(260,140,250,30);
        b_heading_1.setFont(font_2);
        b_heading_1.setForeground(Color.decode("#069C54"));
        //code text area
        code_pane.setBounds(150,170,450,500);
        code_area.setBackground(Color.decode("#FBFEFD"));
        code_area.setForeground(Color.BLACK);
        code_area.setFont(font_3);
        code_pane.add(code_area);
        //new
        all_new.setBounds(350,75,100,40);
        all_new.setBackground(Color.decode("#069C54"));
        all_new.setForeground(Color.decode("#FBFEFD"));
        //scan button
        scan.setBounds(470,75,100,40);
        scan.setBackground(Color.decode("#069C54"));
        scan.setForeground(Color.decode("#FBFEFD"));
        //copy
        copy.setBounds(590,75,100,40);
        copy.setBackground(Color.decode("#069C54"));
        copy.setForeground(Color.decode("#FBFEFD"));
        //paste
        paste.setBounds(710,75,100,40);
        paste.setBackground(Color.decode("#069C54"));
        paste.setForeground(Color.decode("#FBFEFD"));
        //comment remover
        comnt.setBounds(830,75,200,40);
        comnt.setBackground(Color.decode("#069C54"));
        comnt.setForeground(Color.decode("#FBFEFD"));

        //token label
        token_label.setForeground(Color.decode("#069C54"));
        token_label.setFont(font_2);
        token_label.setBounds(830,130,200,40);
        //table
        String headers[] = new String[] {"token","description","token count"};
        model.setColumnIdentifiers(headers);
        table.setModel(model);
        JScrollPane scrolls = new JScrollPane(table);
        scrolls.setBounds(650,170,500,500);
        //panel adder
        header.add(header_label);
        body.add(b_heading_1);
        body.add(code_pane);
        body.add(scan);
        body.add(comnt);
        body.add(token_label);
        body.add(all_new);
        body.add(copy);
        body.add(paste);
        body.add(scroll);
        body.add(scrolls);
        // frame adder
        frame.add(header);
        frame.add(body);
        frame.setVisible(true);
        //methods
        scan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scanMethod();
            }
        });
        comnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comntRemoverMethod();
            }
        });
        all_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMethod();
            }
        });
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                code_area.copy();
            }
        });
        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                code_area.paste();
            }
        });
    }
}
