package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GridBagLayout extends JFrame {

    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JButton[] jButton = new JButton[5];
    private JButton[] jButtonSpiel = new JButton[3];

    /**
     * Der Konstruktor.
     */
    public GridBagLayout() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setLayout( new BorderLayout() );
        jPanel2.setLayout(new GridLayout());
        jPanel3.setLayout(new GridLayout());
        ButtonListener bl = new ButtonListener();
        for ( int i = 0; i<5; i++ ) {
            jButton[i] = new JButton ( "JButton" + (i+1) );
            jButton[i].addActionListener ( bl );
            jPanel2.add ( jButton[i] );
        }

        for ( int i = 0; i<3; i++ ) {
            jButtonSpiel[i] = new JButton ( "JButton" + (i+1) );
            jButtonSpiel[i].addActionListener ( bl );
            jPanel3.add(jButtonSpiel[i]);
        }

        this.getContentPane().add ( jPanel1 ) ;
        jPanel1.add(jPanel2, BorderLayout.EAST);
        jPanel1.add(jPanel3,BorderLayout.SOUTH);
        pack();

    }

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i=0; i<5; i++) {
                if( e.getSource() == jButton[i] ){
                    System.out.println("JButton" + (i+1) + " wurde geklickt.");
                }
            }
            for (int i=0; i<3; i++) {
                if( e.getSource() == jButtonSpiel[i] ){
                    System.out.println("JButtonSpiel" + (i+1) + " wurde geklickt.");
                }
            }
        }
    }

    /**
     * Zeigt ein JFrame mit einem 3x3-Grid aus JButtons an.
     * Jedes Java-Programm beginnt bei einer Methode main() zu laufen, so auch
     * dieses. Beachten Sie, dass die Methode main() in jeder beliebigen
     * Klasse stehen könnte, die Zugriff auf GridLayoutDemo hat, also auch in
     * ihr selbst – also hier:
     */
    public static void main(String args[]) {
        // Ein Objekt der Klasse erzeugen und sichtbar machen.
        new GridBagLayout().setVisible ( true );
    }
}