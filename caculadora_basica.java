import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
public class caculadora_basica {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                CalculatorFrame frame = new CalculatorFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}//Se organiza la seccion de interfaz grafica para obtener un diseño de tamaño medio-grande
class CalculatorFrame extends JFrame {
    public CalculatorFrame() {
        setTitle("Calculadora Basica");
        CalculatorPanel panel = new CalculatorPanel();
        add(panel);
        pack();
        int width = 350;
        int height = 350;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        setBounds(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2, width, height);
    }
}
//Una vez se organiza el diseño se procede a realizar las respectivas adecuaciones visuales del sistema de la calculadora.
class CalculatorPanel extends JPanel {
    private JButton display;
    private JPanel panel;
    private BigDecimal result;
    private String lastCommand;
    private boolean start;
    public CalculatorPanel() {
        setLayout(new BorderLayout());
        result = BigDecimal.ZERO;
        lastCommand = "=";
        start = true;
        display = new JButton("0");
        display.setEnabled(false);
        display.setFont(display.getFont().deriveFont(50f));
        add(display, BorderLayout.NORTH);
        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));
        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("/", command);
        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);
        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("-", command);
        addButton("0", insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("+", command);
        addButton("MOD", command);
        add(panel, BorderLayout.CENTER);
    }
    //Metodo para el ingreso de cada uno de los botones, que queden funcionales 
    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.setFont(button.getFont().deriveFont(20f));
        button.addActionListener(listener);
        panel.add(button);
    }
    //Realiza la extracion de la informacion que se coloca de la calculadora en formato grafico (insertar valor)
    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }
    //Realiza la extracion de la informacion que se coloca de la calculadora en formato grafico (comado valor)
    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                } else lastCommand = command;
            } else {
                calculate(new BigDecimal(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }
//anexo de los valores que ingresan y como deben ser entendidas las operaciones que se plantean.
    public void calculate(BigDecimal x) {
        if (lastCommand.equals("+")) result = result.add(x);
        else if (lastCommand.equals("-")) result = result.subtract(x);
        else if (lastCommand.equals("*")) result = result.multiply(x);
        else if (lastCommand.equals("/")) result = result.divide(x);
        else if (lastCommand.equals("MOD")) result = result.remainder(x);
        else if (lastCommand.equals("=")) result = x;
        if (result.compareTo(BigDecimal.ZERO) == 0) {
            result = BigDecimal.ZERO;
        }
        display.setText(result.toString());
    }
}
