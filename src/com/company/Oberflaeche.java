package com.company;

/*
 * Diese Klasse repräsentiert die Oberfläche des Kniffelspiels
 */

import java.awt.*;
import java.awt.event.*;

/**
 * @author Daniela Leder, Version 1.0
 */
public class Oberflaeche {

    // Höhe des Fensters
    private static final int hoeheFenster = 600;
    // Breite des Fensters
    private static final int breiteFenster = 800;
    // das Oberflächen-Objekt: nur ein Objekt soll erzeugt werden/Singleton
    private static Oberflaeche oberflaeche = null;
    // das Fenster der Oberfläche
    private Frame fenster = null;
    // Buttons für Wurf werten und Eingabe
    private Button wurfWerten;
    private Button eingabe;
    // Ausgabe-Textbereich
    private TextArea ausgabe = new TextArea("", 4, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);
    // Ob der Button gedrückt wurde
    private boolean buttonClicked = false;
    // Ob der Button gedrückt wurde
    private boolean wurfWertenClicked = false;
    // SpielzettelPanel für Spieler 1 und 2
    private SpielzettelPanel panel2;
    private SpielzettelPanel panel3;

    /**
     * Erzeugt die Oberfläche.
     */
    private Oberflaeche() {
        //das Fenster einrichten
        fenster = new Frame("Mein Kniffelspiel");
        fenster.setResizable(false);
        fenster.setSize(breiteFenster, hoeheFenster);
        fenster.setLayout(null);
        fenster.setBackground(new Color(235, 247, 239));

        //Fenster schließen und Programm beenden
        fenster.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //Panel oben definieren
        Panel panel1 = new Panel();
        panel1.setSize(breiteFenster, 100);
        panel1.setLocation(0, 0);
        panel1.setBackground(new Color(223, 237, 227));
        panel1.setLayout(new FlowLayout());
        panel1.setVisible(true);
        fenster.add(panel1);

        //Panel links Mitte definieren
        panel2 = new SpielzettelPanel();
        panel2.setSize(300, 445);
        panel2.setLocation(20, 100);
        panel2.setBackground(new Color(235, 247, 239));
        fenster.add(panel2);

        //Panel rechts Mitte definieren
        panel3 = new SpielzettelPanel();
        panel3.setSize(300, 440);
        panel3.setLocation(480, 100);
        panel3.setBackground(new Color(235, 247, 239));
        fenster.add(panel3);

        //Panel unten definieren
        Panel panel4 = new Panel();
        panel4.setSize(breiteFenster, 50);
        panel4.setLocation(20, 545);
        panel4.setBackground(new Color(235, 247, 239));
        panel4.setLayout(new GridLayout(1, 2));
        Panel panel5 = new Panel();
        ausgabe.setColumns(50);
        ausgabe.setEditable(false);
        panel4.add(ausgabe);
        wurfWerten = new Button("Wurf werten");
        wurfWerten.setEnabled(false);
        wurfWerten.setPreferredSize(new Dimension(80, 50));
        wurfWerten.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wurfWertenClicked = true;
            }
        });
        panel5.add(wurfWerten);
        eingabe = new Button("Eingabe");
        eingabe.setPreferredSize(new Dimension(80, 50));
        eingabe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonClicked = true;
            }
        });
        panel5.add(eingabe);
        panel5.setVisible(true);
        panel4.add(panel5);
        panel4.setVisible(true);
        fenster.add(panel4);
        fenster.setVisible(true);
    }

    /**
     * Erstellt ein Oberflächen-Objekt, falls nocht nicht vorhanden. Gibt sonst
     * das Oberflächen-Objekt zurück.
     *
     * @return Oberflaeche
     */
    static Oberflaeche oberflaecheLiefern() {

        if (oberflaeche == null) {
            oberflaeche = new Oberflaeche();
        }
        return oberflaeche;
    }

    /**
     * Getter: Liefert den Frame der Oberfläche.
     */
    Frame fensterLiefern() {
        return oberflaeche.fenster;
    }

    /**
     * Setzt einen Text im Ausgabefenster und wartet, bis der Benutzer die
     * betreffende Aktion bestätigt hat.
     *
     * @param text: Der Text, der im Ausgabefenster angezeigt werden soll.
     */
    void eingabeAbfragen(String text) {
        ausgabe.setText(text);
        while (buttonClicked == false) {
            //Busy Waiting: wir warten 100ms und testen dann noch mal, ob die Eingabe schon fertig ist.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        buttonClicked = false;
        ausgabe.setText(" ");
    }

    /**
     * Setzt einen Text im Ausgabefenster.
     *
     * @param text: Der Text, der im Ausgabefenster angezeigt werden soll.
     */
    void textSetzen(String text) {
        ausgabe.setText(text);
    }

    /**
     * Dient zur Abfrage der Wurfwertung. Setzt den Text im Ausgabefenster,
     * überprüft, ob der Button "Wurf werten" oder der Eingabe-Button gedrückt
     * wurden. Gibt den Wert für wurfWerten zurück.
     *
     * @param text: Der Text, der im Ausgabefenster angezeigt werden soll.
     * @return Ob der Wurf gewertet werden soll.
     */
    boolean wurfWertenAbfragen(String text) {
        ausgabe.setText(text);
        wurfWerten.setEnabled(true);
        while (wurfWertenClicked == false && buttonClicked == false) {
            //Busy Waiting: wir warten 100ms und testen dann noch mal, ob die Eingabe schon fertig ist.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        boolean werten = wurfWertenClicked;

        wurfWertenClicked = false;
        buttonClicked = false;
        wurfWerten.setEnabled(false);
        ausgabe.setText(" ");
        return werten;
    }

    /**
     * Getter: Gibt das SpielzettelPanel des linken Spielers zurück.
     *
     * @return SpielzettelPanel
     */
    SpielzettelPanel spielzettel1Liefern() {
        return panel2;

    }

    /**
     * Getter: Gibt das SpielzettelPanel des rechten Spielers zurück.
     *
     * @return SpielzettelPanel
     */
    SpielzettelPanel spielzettel2Liefern() {
        return panel3;
    }

    /**
     * Setter: Setzt den Eingabe-Button aktiv oder inaktiv.
     */
    void eingabeButtonAktiv(boolean aktiv)
    {
        eingabe.setEnabled(aktiv);
    }


}
