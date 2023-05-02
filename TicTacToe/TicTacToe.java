package TicTacToe;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener{

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel ueberschrift_feld = new JPanel();
    JPanel spielfeld = new JPanel();
    JLabel textfeld = new JLabel();
    JButton[] felder = new JButton[9];

    JPanel reset_feld = new JPanel();
    JButton reset_button = new JButton();

    boolean zugSpieler_X;

    int[][] gewinnKombinationen = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    ArrayList<Integer> xListe = new ArrayList<Integer>();
    ArrayList<Integer> yListe = new ArrayList<Integer>();
    int gewinnKombination;

    TicTacToe(){

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                   
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfeld.setBackground(new Color(250,250,115));
        textfeld.setForeground(new Color(45,45,45));
        textfeld.setFont(new Font("Rockwell Condensed", Font.BOLD,75));
        textfeld.setHorizontalAlignment(JLabel.CENTER);
        textfeld.setText("Tic-Tac-Toe");
        textfeld.setOpaque(true);

        ueberschrift_feld.setLayout(new BorderLayout());
        ueberschrift_feld.setBounds(0,0,800,100);

        spielfeld.setLayout(new GridLayout(3,3));            //erstellt Bereich, in dem die Buttons sind
        spielfeld.setBackground(new Color(10,10,10));         

        for(int i = 0; i<9; i++){
            felder[i] = new JButton();
            spielfeld.add(felder[i]);
            felder[i].setBackground(new Color(225,225,225));
            felder[i].setFont(new Font("Algerian", Font.BOLD, 120));
            felder[i].setFocusable(false);
            felder[i].addActionListener(this);

        }

        reset_feld.setLayout(new BorderLayout());
        reset_button.setText("Reset");
        reset_button.setSize(100,50);
        reset_button.addActionListener(this);
        reset_button.setBackground(new Color(180,180,180));

        ueberschrift_feld.add(textfeld);
        reset_feld.add(reset_button);                         
        frame.add(ueberschrift_feld, BorderLayout.NORTH);
        frame.add(spielfeld, BorderLayout.CENTER);
        frame.add(reset_feld, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);


        ersterZug(3000);
    }

    public void actionPerformed(ActionEvent action) {
        
        for(int i=0; i<9; i++){
            if(action.getSource()==felder[i]){
                if(zugSpieler_X){
                    if(felder[i].getText()==""){
                        felder[i].setForeground(new Color(255,0,0));
                        felder[i].setText("X");
                        zugSpieler_X = false;
                        textfeld.setText("O ist dran");
                        xListe.add(i);

                        // Schauen ob X gewinnt:
                        if(checkResult(xListe)){
                            textfeld.setText("X gewinnt!");
                            disableButtons();
                            for(int x : gewinnKombinationen[gewinnKombination]){
                                felder[x].setBackground(Color.cyan);
                            }
                        }
                        else{
                        // Schauen ob unentschieden:
                            for(int x = 0; x < 9; x++){
                                if(felder[x].getText().isBlank()){
                                    break;
                                }
    
                                if(x==8){
                                    disableButtons();
                                    textfeld.setText("Unentschieden!");
                                }   
                            }

                        }
                    }
                }
                else{
                    if(felder[i].getText()==""){
                        felder[i].setForeground(new Color(0,0,255));
                        felder[i].setText("O");
                        zugSpieler_X = true;
                        textfeld.setText("X ist an der Reihe");
                        yListe.add(i);
                        // Schauen ob O gewinnt:
                        if(checkResult(yListe)){
                            textfeld.setText("O gewinnt!");
                            disableButtons();
                            for(int x : gewinnKombinationen[gewinnKombination]){
                                felder[x].setBackground(Color.cyan);
                            }
                        }
                        else{
                        // Schauen ob unentschieden:
                            for(int x = 0; x < 9; x++){
                                if(felder[x].getText().isBlank()){
                                    break;
                                }

                                if(x==8){
                                    disableButtons();
                                    textfeld.setText("Unentschieden!");
                                }   
                            }
                        }
                    }
                break;
                }
            }

        }
        if(action.getSource() == reset_button){
            reset();
        }
    }
    
    public void ersterZug(int sleepTime){
        //Entscheiden, wer anfängt

        disableButtons();
        reset_button.setEnabled(false);
        try{
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException action){
            action.printStackTrace();
        }
        enableButtons();
        reset_button.setEnabled(true);

        if(random.nextInt(2)==0){
            zugSpieler_X = true;
            textfeld.setText("X ist an der Reihe");
        }
        else{
            zugSpieler_X = false;
            textfeld.setText("O ist dran");
        }
    }


    public boolean checkResult(ArrayList<Integer> arrayList){
        
        boolean win = false;

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 3; y++){
                if(arrayList.contains(gewinnKombinationen[x][y])){
                    if(y == 2){
                        win = true;
                        gewinnKombination = x;
                        x = 7;
                    }
                }
                else{
                    win = false;
                    break;
                }
            }
        }
        return win;
    }  

    private void disableButtons() {
        for(int i = 0; i < 9; i++){
            felder[i].setEnabled(false);
            }
        }

    private void enableButtons() {
            for(int i = 0; i < 9; i++){
            felder[i].setEnabled(true);
            }
        }

    public void reset(){
        frame.remove(spielfeld);
        spielfeld = new JPanel();
        spielfeld.setLayout(new BorderLayout());
        spielfeld.setLayout(new GridLayout(3,3));
        spielfeld.setBackground(new Color(50,50,50));
        frame.add(spielfeld, BorderLayout.CENTER);

        for(int i = 0; i < 9; i++){
            felder[i] = new JButton();
            spielfeld.add(felder[i]);
            felder[i].setFont(new Font("Algerian", Font.BOLD, 120));
            felder[i].setFocusable(false);
            felder[i].addActionListener(this);
        }
        xListe.clear();
        yListe.clear();

        textfeld.setText("Tic-Tac-Toe");
        ersterZug(500);
        SwingUtilities.updateComponentTreeUI(frame);
    }
}