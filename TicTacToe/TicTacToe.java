package TicTacToe;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.ArrayList;

public class TicTacToe implements ActionListener{

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];

    JPanel rpanel = new JPanel();
    JButton resButton = new JButton();

    boolean player1_turn;

    int[][] gewinnKombinationen = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    ArrayList<Integer> xListe = new ArrayList<Integer>();
    ArrayList<Integer> yListe = new ArrayList<Integer>();
    int winComb;

    TicTacToe(){

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                   //Grafisches Spielfeld ?!
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground(new Color(25,255,0));
        textfield.setFont(new Font("Ink Free", Font.BOLD,75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0,0,800,100);

        button_panel.setLayout(new GridLayout(3,3));            //erstellt Bereich, in dem die Buttons sind
        button_panel.setBackground(new Color(150,150,150));         //

        for(int i = 0; i<9; i++){
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);

        }

        rpanel.setLayout(new BorderLayout());
        resButton.setText("Reset");
        resButton.setSize(100,50);
        resButton.addActionListener(this);

        title_panel.add(textfield);
        rpanel.add(resButton);                         
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel, BorderLayout.CENTER);
        frame.add(rpanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);


        firstTurn(3000);
    }

    public void actionPerformed(ActionEvent e) {
        
        for(int i=0; i<9; i++){
            if(e.getSource()==buttons[i]){
                if(player1_turn){
                    if(buttons[i].getText()==""){
                        buttons[i].setForeground(new Color(255,0,0));
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O ist dran");
                        xListe.add(i);

                        // Schauen ob X gewinnt:
                        if(checkResult(xListe)){
                            textfield.setText("X gewinnt!");
                            disableButtons();
                            for(int x : gewinnKombinationen[winComb]){
                                buttons[x].setBackground(Color.cyan);
                            }
                        }
                        else{
                                            // Schauen ob unentschieden:
                            for(int x = 0; x < 9; x++){
                                if(buttons[x].getText().isBlank()){
                                    break;
                                }
    
                                if(x==8){
                                    disableButtons();
                                    textfield.setText("Unentschieden!");
                                }   
                            }

                        }
                    }
                }
                else{
                    if(buttons[i].getText()==""){
                        buttons[i].setForeground(new Color(0,0,255));
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X ist an der Reihe");
                        yListe.add(i);
                        // Schauen ob O gewinnt:
                        if(checkResult(yListe)){
                            textfield.setText("O gewinnt!");
                            disableButtons();
                            for(int x : gewinnKombinationen[winComb]){
                                buttons[x].setBackground(Color.cyan);
                            }
                        }
                        else{
                            // Schauen ob unentschieden:
                            for(int x = 0; x < 9; x++){
                                if(buttons[x].getText().isBlank()){
                                    break;
                                }

                                if(x==8){
                                    disableButtons();
                                    textfield.setText("Unentschieden!");
                                }   
                            }
                        }
                    }
                break;
                }
            }

        }
        if(e.getSource() == resButton){
            reset();
        }
    }
    
    public void firstTurn(int sleepTime){
        //Entscheiden, wer anfängt

        disableButtons();
        resButton.setEnabled(false);
        try{
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        enableButtons();
        resButton.setEnabled(true);

        if(random.nextInt(2)==0){
            player1_turn = true;
            textfield.setText("X ist an der Reihe");
        }
        else{
            player1_turn = false;
            textfield.setText("O ist dran");
        }
    }


    public boolean checkResult(ArrayList<Integer> arrayList){
        
        boolean win = false;

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 3; y++){
                if(arrayList.contains(gewinnKombinationen[x][y])){
                    if(y == 2){
                        win = true;
                        winComb = x;
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
            buttons[i].setEnabled(false);
            }
        }

    private void enableButtons() {
            for(int i = 0; i < 9; i++){
            buttons[i].setEnabled(true);
            }
        }

    public void reset(){
        frame.remove(button_panel);
        button_panel = new JPanel();
        button_panel.setLayout(new BorderLayout());
        button_panel.setLayout(new GridLayout(3,3));
        button_panel.setBackground(new Color(150,150,150));
        frame.add(button_panel, BorderLayout.CENTER);

        for(int i = 0; i < 9; i++){
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
        xListe.clear();
        yListe.clear();

        textfield.setText("Tic-Tac-Toe");
        firstTurn(500);
        SwingUtilities.updateComponentTreeUI(frame);
    }
}