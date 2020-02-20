package long_arithmetic;

import java.awt.*;
import java.awt.event.*;

class Calculator implements ActionListener
{
    Frame f=new Frame("Calculate Big Numbers");
    Label l1=new Label("First Number");
    Label l2=new Label("Second Number");
    Label l3=new Label("^X");
    Label l4=new Label("Mod");
    Label l5=new Label("Result");

    TextField t1=new TextField();
    TextField t2=new TextField();
    TextField t3=new TextField();
    TextField t4=new TextField();
    TextField t5=new TextField();

    Button btnAdd=new Button("Add");
    Button btnSub=new Button("Sub");
    Button btnMul=new Button("Mul");
    Button btnDiv=new Button("Div");

    Button btnPow=new Button("pow(num1,x)");
    Button btnGre=new Button(">");
    Button btnLess=new Button("<");
    Button btnEq=new Button("=");

    Button btnAddMod=new Button("Add_Mod_x");
    Button btnSubMod=new Button("Sub_Mod_x");
    Button btnMulMod=new Button("Mul_Mod_x");
    Button btnDivMod=new Button("Div_Mod_x");
    Button btnPowMod=new Button("Pow_Mod_x");

    Button btnSqrt=new Button("sqrt(num1)");

    Calculator()
    {
        //Giving Coordinates
        l1.setBounds(50,50,100,20);
        l2.setBounds(50,80,100,20);
        l3.setBounds(50,110,100,20);
        l4.setBounds(50,140,100,20);
        l5.setBounds(50,170,100,20);

        t1.setBounds(200,50,350,20);
        t2.setBounds(200,80,350,20);
        t3.setBounds(200,110,350,20);
        t4.setBounds(200,140,350,20);
        t5.setBounds(200,170,350,20);

        btnAdd.setBounds(50,210,50,20);
        btnSub.setBounds(110,210,50,20);
        btnMul.setBounds(170,210,50,20);
        btnDiv.setBounds(230,210,50,20);

        btnPow.setBounds(50,250,95,20);
        btnGre.setBounds(155,250,35,20);
        btnLess.setBounds(205,250,35,20);
        btnEq.setBounds(255,250,35,20);

        btnAddMod.setBounds(30,290,90,20);
        btnSubMod.setBounds(130,290,90,20);
        btnMulMod.setBounds(230,290,90,20);

        btnPowMod.setBounds(30,330,90,20);
        btnDivMod.setBounds(130,330,90,20);
        btnSqrt.setBounds(230,330,90,20);

        f.add(l1);
        f.add(l2);
        f.add(l3);
        f.add(l4);
        f.add(l5);

        f.add(t1);
        f.add(t2);
        f.add(t3);
        f.add(t4);
        f.add(t5);

        f.add(btnAdd);
        f.add(btnSub);
        f.add(btnMul);
        f.add(btnDiv);

        f.add(btnPow);
        f.add(btnGre);
        f.add(btnLess);
        f.add(btnEq);

        f.add(btnAddMod);
        f.add(btnSubMod);
        f.add(btnMulMod);

        f.add(btnPowMod);
        f.add(btnDivMod);
        f.add(btnSqrt);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.add(new BigNumber(second));
                t5.setText(result+"");
            }
        });

        btnSub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.subtract(new BigNumber(second));
                t5.setText(result+"");
            }
        });

        btnMul.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.multiply(new BigNumber(second));
                t5.setText(result+"");
            }
        });

        btnDiv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.divide(new BigNumber(second));
                t5.setText(result+"");
            }
        });

        btnPow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                String x = t3.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.pow(Long.parseLong(x));
                t5.setText(result+"");
            }
        });

        btnGre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                BigNumber num = new BigNumber(first);
                int result = num.compareTo(new BigNumber(second));
                t5.setText(result > 0 ? "true" : "false");
            }
        });

        btnLess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                BigNumber num = new BigNumber(first);
                int result = num.compareTo(new BigNumber(second));
                t5.setText(result < 0 ? "true" : "false");
            }
        });

        btnEq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                BigNumber num = new BigNumber(first);
                int result = num.compareTo(new BigNumber(second));
                t5.setText(result == 0 ? "true" : "false");
            }
        });

        btnAddMod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                String x = t3.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.add(new BigNumber(second));
                t5.setText(result.mod(Integer.parseInt(x))+"");
            }
        });

        btnSubMod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                String mod = t4.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.subtract(new BigNumber(second));
                t5.setText(result.mod(Integer.parseInt(mod))+"");
            }
        });

        btnMulMod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                String mod = t4.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.multiply(new BigNumber(second));
                t5.setText(result.mod(Integer.parseInt(mod))+"");
            }
        });

        btnDivMod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String second=t2.getText();
                String m = t4.getText();
                BigNumber num = new BigNumber(first);
                int mod = Integer.parseInt(m);
                BigNumber result = num.divMod(new BigNumber(second), mod);
                t5.setText(result.toString());
            }
        });

        btnPowMod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                String x = t3.getText();
                String m = t4.getText();
                BigNumber num = new BigNumber(first);
                long mod = Long.parseLong(m);
                int power = Integer.parseInt(x);
                BigNumber result = num.powMod(power, mod);
                t5.setText(result.toString());
            }
        });

        btnSqrt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String first=t1.getText();
                BigNumber num = new BigNumber(first);
                BigNumber result = num.sqrt();
                t5.setText(result+"");
            }
        });

        f.setLayout(null);
        f.setVisible(true);
        f.setSize(600,400);
    }
    public void actionPerformed(ActionEvent e){}

    public static void main(String...s)
    {
        new Calculator();
    }
}
