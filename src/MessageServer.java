package main;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class MessageServer {
    public static ArrayList<User> users = new ArrayList<>();


    public ArrayList<User> getUsers() {
        return users;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242);
        System.out.println("Waiting for the client to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        while (true) {
            String equations = reader.readLine();
            if (equations.equals("End")) {
                serverSocket.close();
                break;
            }
            String[] partsOfEquation = equations.split(" ");
            double one = Double.parseDouble(partsOfEquation[0]);
            double two = Double.parseDouble(partsOfEquation[2]);
            String operand = partsOfEquation[1];
            String allTogether = doCalculation(one, two, operand);
            writer.write(allTogether);
            writer.println();
            writer.flush();
        }
    }
    public static String loadUsers(){
        String fileName= "User.txt";
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine())!=null){
                String[] user= line.split(", ");
                User newUser = new User(user[0], user[1], user[2], Integer.parseInt(user[3]), user[3], user[4]);
                users.add(newUser);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
