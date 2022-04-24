package wordsearcher.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Client {

    public JFrame frame;
    private JTextField textInput;
    private static JTextArea textResponse;
    private static Socket con;
    DataInputStream input;
    DataOutputStream output;
    private JScrollPane scrollPane;

    public static void main(String[] args) throws UnknownHostException, IOException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Client window = new Client();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        con = new Socket("127.0.0.1", 8080);
        while (true) {
            try {
                DataInputStream input = new DataInputStream(con.getInputStream());
                String string = input.readUTF();
                textResponse.setText(textResponse.getText() + " is present in lines: \n" + string);
            } catch (Exception ev) {
                textResponse.setText(textResponse.getText() + " \n" + "Network issues ");
                try {
                    Thread.sleep(2000);
                    System.exit(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Client() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(UIManager.getColor("MenuBar.highlight"));
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("WordSearcher : Client");

        textInput = new JTextField();
        textInput.setBounds(12, 45, 380, 38);
        frame.getContentPane().add(textInput);
        textInput.setColumns(10);

        JButton btnSend = new JButton("Transmit");
        btnSend.setForeground(Color.black);
        btnSend.setBackground(new Color(50, 181, 255));
        btnSend.setBounds(412, 45, 196, 38);
        frame.getContentPane().add(btnSend);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 149, 608, 290);
        frame.getContentPane().add(scrollPane);

        textResponse = new JTextArea();
        scrollPane.setViewportView(textResponse);

        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (textInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please write some text !");
                } else {
                    textResponse.setText("\n" + textInput.getText());
                    try {
                        DataOutputStream output = new DataOutputStream(con.getOutputStream());
                        output.writeUTF(textInput.getText());
                    } catch (IOException e1) {
                        textResponse.setText(textResponse.getText() + "\n " + " Network issues");
                        try {
                            Thread.sleep(2000);
                            System.exit(0);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    }
                    textInput.setText("");
                }
            }
        });
    }
}
