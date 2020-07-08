package com.company;

/*
 * Diese Klasse repräsentiert die Darstellung des Spielzettels auf der Oberfläche.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Daniela Leder, Version 1.0
 */
public class SpielzettelPanel extends Panel {

    private Label label;
    // das Namensfeld
    private TextField tFName;
    // diese Panel innerhalb des SpielzettelsPanels helfen bei einer optisch
    // passenden Anzeige
    private Panel panel1, panel2, panel3;
    // dieses Feld hält alle Textfelder, die zur Ausgabe des Spielstands nötig sind.
    //jeder Index im ausgabeFeld entspricht einer bestimmten Wertung:
    //i = 0: einer
    //i = 1: zweier
    //i = 2: dreier
    //i = 3: vierer
    //i = 4: fuenfer
    //i = 5: sechser
    //i = 6: Summe oben
    //i = 7: Bonus
    //i = 8: Dreierpasch
    //i = 9: Viererpasch
    //i = 10: Full House
    //i = 11: Kleine Straße
    //i = 12: Große Straße
    //i = 13: Kniffel
    //i = 14: Chance
    //i = 15: Summe unten
    //i = 16: Summe gesamt
    private TextField[] ausgabeFeld = new TextField[17];
    // dieses Feld hält alle Buttons, die zur Auswahl der Wertung nötig sind.
    private Button[] buttonFeld = new Button[13];
    // der zugehörige Spielzettel
    private Spielzettel spielzettel;

    /**
     * Erzeugt ein neues SpielzettelPanel und setzt die Anfangswerte.
     */
    SpielzettelPanel() {
        setSize(300, 450);
        setLayout(new BorderLayout());
        setBackground(new Color(235, 247, 239));
        panel1 = new Panel();
        panel2 = new Panel();
        panel3 = new Panel();
        add(panel1);
        add(panel2);
        add(panel3);
        panel1.setSize(150, 450);
        panel1.setLocation(0, 0);
        panel1.setLayout(new GridLayout(19, 1));
        panel2.setSize(100, 450);
        panel2.setLocation(150, 0);
        panel2.setLayout(new GridLayout(19, 1));
        panel3.setSize(20, 450);
        panel3.setLocation(250, 0);
        panel3.setLayout(new GridLayout(19, 1));

        tFName = new TextField();
        tFName.setEditable(true);
        label = new Label("Spielzettel");
        label.setFont(new Font("SANS-SERIF", Font.BOLD, 18));
        panel1.add(label);
        label = new Label(" ");
        panel2.add(label);
        label = new Label(" ");
        panel3.add(label);
        label = new Label("Name Spieler: ");
        label.setFont(new Font("SANS-SERIF", Font.BOLD, 18));
        panel1.add(label);
        panel2.add(tFName);
        label = new Label(" ");
        panel3.add(label);

        // initialisiert das Ausgabefeld
        for (int i = 0; i < 17; i++) {
            ausgabeFeld[i] = new TextField(50);
            ausgabeFeld[i].setEditable(false);
            panel2.add(ausgabeFeld[i]);
        }

        // initialisiert das Buttonfeld
        for (int i = 0; i < 13; i++) {
            buttonFeld[i] = new Button(" ");
            buttonFeld[i].setBackground(new Color(43, 114, 74));
            final int weitergabe = i + 1;
            buttonFeld[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    spielzettel.wurfWertungSetzen(weitergabe);
                }
            });
            buttonFeld[i].setVisible(false);
        }

        for (int i = 0; i < 6; i++) {
            panel3.add(buttonFeld[i]);
        }
        label = new Label(" ");
        panel3.add(label);
        label = new Label(" ");
        panel3.add(label);

        for (int i = 6; i < 13; i++) {
            panel3.add(buttonFeld[i]);
        }
        label = new Label(" ");
        panel3.add(label);
        label = new Label(" ");
        panel3.add(label);
        panel2.setVisible(true);
        panel3.setVisible(true);
        label = new Label("Einer: ");
        panel1.add(label);
        label = new Label("Zweier: ");
        panel1.add(label);
        label = new Label("Dreier: ");
        panel1.add(label);
        label = new Label("Vierer: ");
        panel1.add(label);
        label = new Label("Fünfer: ");
        panel1.add(label);
        label = new Label("Sechser: ");
        panel1.add(label);
        label = new Label("Summe oben: ");
        label.setFont(new Font("SANS-SERIF", Font.BOLD, 12));
        panel1.add(label);
        label = new Label("Bonus: ");
        label.setFont(new Font("SANS-SERIF", Font.BOLD, 12));
        panel1.add(label);
        label = new Label("Dreierpasch: ");
        panel1.add(label);
        label = new Label("Viererpasch: ");
        panel1.add(label);
        label = new Label("Full House: ");
        panel1.add(label);
        label = new Label("Kleine Straße: ");
        panel1.add(label);
        label = new Label("Große Straße: ");
        panel1.add(label);
        label = new Label("Kniffel: ");
        panel1.add(label);
        label = new Label("Chance: ");
        panel1.add(label);
        label = new Label("Summe unten: ");
        label.setFont(new Font("SANS-SERIF", Font.BOLD, 12));
        panel1.add(label);
        label = new Label("Summe gesamt: ");
        label.setFont(new Font("SANS-SERIF", Font.BOLD, 12));
        panel1.add(label);
        panel1.setVisible(true);
        setVisible(true);
    }

    /**
     * Wird der Spieler aktiv gesetzt, wird er rot dargestellt, für inaktiv
     * schwarz.
     */
    void spielerAktivSetzen(boolean aktiv) {
        if (aktiv) {
            tFName.setForeground(Color.RED);
            tFName.setFont(new Font("SANS-SERIF", Font.BOLD, 12));
        } else {
            tFName.setForeground(Color.BLACK);
            tFName.setFont(new Font("SANS-SERIF", Font.BOLD, 12));
        }
    }

    /**
     * Gibt den Spielstand auf dem Spielzettel aus.
     */
    void spielstandSetzen(int[] spielstand) {
        for (int i = 0; i < 17; i++) {
            if (spielstand[i] >= 0) {
                ausgabeFeld[i].setText(new Integer(spielstand[i]).toString());
            }
        }
    }

    /**
     * Setter: Setzt das Spielzettel-Attribut.
     */
    void spielzettelSetzen(Spielzettel einZettel) {
        spielzettel = einZettel;
    }

    /**
     * Getter: Liefert das Button-Feld.
     */
    Button[] buttonFeldLiefern() {
        return buttonFeld;
    }

    /**
     * Getter: Liefert den Namen des Spielers, der im Spielzettel eingetragen
     * wurde.
     */
    String nameLiefern() {
        return tFName.getText();
    }

    /**
     * Deaktiviert das Namensfeld.
     */
    void namensfeldDeaktivieren() {
        tFName.setEditable(false);
    }
}
