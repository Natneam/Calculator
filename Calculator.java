import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator extends JFrame {

    JPanel upperPanel = new JPanel(new GridLayout(2,1));
    JPanel lowerPanel = new JPanel(new GridLayout(5,4));

    // Labels to show result and input
    JLabel inputLabel = new JLabel("",SwingConstants.RIGHT);
    JLabel resultLabel = new JLabel("",SwingConstants.RIGHT);

    // buttons
    JButton jButton0 = new JButton("0");
    JButton jButton1 = new JButton("1");
    JButton jButton2 = new JButton("2");
    JButton jButton3 = new JButton("3");
    JButton jButton4 = new JButton("4");
    JButton jButton5 = new JButton("5");
    JButton jButton6 = new JButton("6");
    JButton jButton7 = new JButton("7");
    JButton jButton8 = new JButton("8");
    JButton jButton9 = new JButton("9");
    JButton jButtonPlus = new JButton("+");
    JButton jButtonMinus = new JButton("-");
    JButton jButtonTimes = new JButton("*");
    JButton jButtonOver = new JButton("/");
    JButton jButtonEquals = new JButton("=");
    JButton jButtonDot = new JButton(".");
    JButton jButtonOpenBracket = new JButton("(");
    JButton jButtonClosedBracket = new JButton(")");
    JButton jButtonClear = new JButton("C");
    JButton jButtonBackspace = new JButton("AC");



    public Calculator(){
        super("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(210,183);
        setResizable(false);
        setVisible(true);


        // adding components to the upperPanel
        upperPanel.add(inputLabel);
        upperPanel.add(resultLabel);

        // adding components to the lowerPanel
        lowerPanel.add(jButtonOpenBracket);
        lowerPanel.add(jButtonClosedBracket);
        lowerPanel.add(jButtonBackspace);
        lowerPanel.add(jButtonClear);
        lowerPanel.add(jButton9);
        lowerPanel.add(jButton8);
        lowerPanel.add(jButton7);
        lowerPanel.add(jButtonPlus);
        lowerPanel.add(jButton6);
        lowerPanel.add(jButton5);
        lowerPanel.add(jButton4);
        lowerPanel.add(jButtonMinus);
        lowerPanel.add(jButton3);
        lowerPanel.add(jButton2);
        lowerPanel.add(jButton1);
        lowerPanel.add(jButtonTimes);
        lowerPanel.add(jButtonEquals);
        lowerPanel.add(jButton0);
        lowerPanel.add(jButtonDot);
        lowerPanel.add(jButtonOver);



        // adding the two upperPanel and lowerPanel to the mainPanel
        add(upperPanel,BorderLayout.NORTH);
        add(lowerPanel,BorderLayout.SOUTH);


        // adding action Listener to every Button
        MyActionListener actionListener = new MyActionListener();
        jButtonBackspace.addActionListener(actionListener);
        jButtonOpenBracket.addActionListener(actionListener);
        jButtonClosedBracket.addActionListener(actionListener);
        jButtonClear.addActionListener(actionListener);
        jButton0.addActionListener(actionListener);
        jButton1.addActionListener(actionListener);
        jButton2.addActionListener(actionListener);
        jButton3.addActionListener(actionListener);
        jButton4.addActionListener(actionListener);
        jButton5.addActionListener(actionListener);
        jButton6.addActionListener(actionListener);
        jButton7.addActionListener(actionListener);
        jButton8.addActionListener(actionListener);
        jButton9.addActionListener(actionListener);
        jButtonPlus.addActionListener(actionListener);
        jButtonMinus.addActionListener(actionListener);
        jButtonDot.addActionListener(actionListener);
        jButtonTimes.addActionListener(actionListener);
        jButtonOver.addActionListener(actionListener);
        jButtonEquals.addActionListener(actionListener);

    }

    public static void main(String[] args) {
        new Calculator();
    }

    // Action Listener for the buttons using inner Class
    class MyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton source = (JButton)actionEvent.getSource();
            if(source.equals(jButtonEquals)){
                String result = Calculate(inputLabel.getText());

                resultLabel.setText(result);
            }
            else if(source.equals(jButtonClear)){
                inputLabel.setText("");
                resultLabel.setText("");
            }
            else if (source.equals(jButtonBackspace)){
                String text = inputLabel.getText();
                text = text.substring(0,text.length()-1);
                inputLabel.setText(text);
            }
            else{
                inputLabel.setText(inputLabel.getText()+ source.getText());
            }

        }
    }

    // function to calculate the given result

    private String Calculate(String input){

        // changing the infix expression to post fix expression
        ArrayList<String> infixExpression = new ArrayList<>();
        ArrayList<String> postfixExpression = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        StringTokenizer stringTokenizer = new StringTokenizer(input,"+/*-()",true);
        while(stringTokenizer.hasMoreTokens()){
            infixExpression.add(stringTokenizer.nextToken());
        }
        try{for (String token:
             infixExpression) {
            if("+/*-()".contains(token)){
                if(token.equals("(")){
                    stack.push(token);
                }
                else if(token.equals(")")){
                    String x = stack.pop();
                    while(!x.equals("(")){
                        postfixExpression.add(x);
                        x = stack.pop();
                    }
                }
                else if((stack.isEmpty() || stack.peek().equals("(") || isPrecede(token,stack.peek())) ){
                    stack.push(token);
                }else{
                    while((stack.peek().equals("(")) && isPrecede(stack.peek(),token) && !stack.isEmpty()){
                        postfixExpression.add(stack.pop());
                    }
                }
            }else{
                postfixExpression.add(token);
            }
        }
        while(!stack.isEmpty() && "+-/*".contains(stack.peek())){
            postfixExpression.add(stack.pop());
        }}catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Error in the expression, please add the expression again","Error",JOptionPane.ERROR_MESSAGE);
        }

        if(!stack.isEmpty()){
            JOptionPane.showMessageDialog(this,"Error in the expression, please add the expression again","Error",JOptionPane.ERROR_MESSAGE);
        }

        System.out.println(postfixExpression.toString());
        //calculating the value using stack
        String miniResult;
        stack = new Stack<>();
        try {
        for (String var : postfixExpression) {
            if("+*/-".contains(var)){
                String varB = stack.pop();
                String varA = stack.pop();
                switch (var) {
                    case "+":
                        miniResult = (Double.valueOf(varA) + Double.valueOf(varB)) + "";
                        break;
                    case "-":
                        miniResult = (Double.valueOf(varA) - Double.valueOf(varB)) + "";
                        break;
                    case "*":
                        miniResult = (Double.valueOf(varA) * Double.valueOf(varB)) + "";
                        break;
                    default:
                        miniResult = (Double.valueOf(varA) / Double.valueOf(varB)) + "";
                }
                stack.push(miniResult);

            }
            else{
                stack.push(var);
            }
        }
        }catch (Exception e){
            e.printStackTrace();
            return "Err";
        }
        return stack.pop();
    }

    private boolean isPrecede(String a, String b){
        if ((a.equals("+") || a.equals("-")) && (b.equals("*") || b.equals("/"))){return false;}
        else return true;
    }

}
