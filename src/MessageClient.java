package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class MessageClient {
    public static void main(String[] args) throws IOException {
        String hostName;
        int portNumber = 0;
        boolean asking = true;
        boolean real = true;
        JOptionPane.showMessageDialog(null, "Welcome to the App",
                "Message Client", JOptionPane.INFORMATION_MESSAGE);
        do {
            hostName = (JOptionPane.showInputDialog(null, "Enter a host name to connect",
                    "Message Client", JOptionPane.PLAIN_MESSAGE));
        } while (hostName.isBlank() || hostName == null);
        do {
            try {
                portNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a port number to connect",
                        "Message Client", JOptionPane.PLAIN_MESSAGE));
            } catch (Exception e) {
                real = false;
                JOptionPane.showMessageDialog(null, "Error, enter an integer",
                        "Message Client", JOptionPane.ERROR_MESSAGE);
            }
        } while (!real);
        Socket socket;
        try {
            socket = new Socket(hostName, portNumber);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error, can't connect",
                    "Message Client", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "You have connection with the server",
                "Calculator Server", JOptionPane.INFORMATION_MESSAGE);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        String equation = "";
        do {
            equation = getEquation();
            writer.write(equation);
            writer.println();
            writer.flush();
            String end = reader.readLine();
            JOptionPane.showMessageDialog(null, "Answer: " + end,
                    "Calculator Client", JOptionPane.PLAIN_MESSAGE);
            int yesOrNo = JOptionPane.showConfirmDialog(null, "Do you want to enter an equation?",
                    "Calculator Server", JOptionPane.YES_NO_OPTION);
            if (yesOrNo == JOptionPane.NO_OPTION) {
                JOptionPane.showConfirmDialog(null, "Thank you for using the Calculator, come again!",
                        "Calculator Client", JOptionPane.PLAIN_MESSAGE);
                writer.write("End");
                writer.println();
                writer.flush();
                asking = false;
            } else {
                asking = true;
            }
        } while (asking);
    }

    public static String getEquation() {
        boolean correct = true;
        boolean exists = false;
        String equation = "";
        do {
            try {
                correct = true;
                equation = JOptionPane.showInputDialog(null, "Enter an equation with a space" +
                                "between the value and operand",
                        "Calculator Client", JOptionPane.PLAIN_MESSAGE);
                String[] parts = equation.split(" ");
                Double.parseDouble(parts[0]);
                Double.parseDouble(parts[2]);
                if (parts.length != 3) {
                    throw new Exception();
                }
                for (int i = 0; i < operandChoices.length; i++) {
                    if (parts[1].equals(operandChoices[i])) {
                        exists = true;
                    }
                }
                if (!exists) {
                    throw new Exception();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Can't be solved, try again",
                        "Calculator Client", JOptionPane.ERROR_MESSAGE);
                correct = false;
            }
        } while (!correct);
        return equation;
    }
}



}
