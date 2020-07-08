package com.company;

/*
* Diese Klasse repräsentiert einen Spielzettel.
*/
import java.awt.*;

/**
 * @author Daniela Leder, Version 1.0
 */
public class Spielzettel {

    // ob alle Felder im Spielfeld ausgefüllt wurden. Markiert das Ende des Spiels.
    private boolean spielFertig;
    // hält die aktuellen Einträge im Spielzettel.
    //jeder Index im Feld spielstand entspricht einer bestimmten Wertung:
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
    private int[] spielstand = new int[17];
    // legt fest, in welchem Feld des Spielzettels ein Wurf gewertet werden soll.
    private int wurfWertung = -1;
    // wie viele Runden schon gespielt wurden
    private int spielZaehler;
    // das zugehörige Panel, das den Spielzettel auf der Oberfläche darstellt
    private SpielzettelPanel panel;

    /**
     * Erzeugt einen neuen Spielzettel und setzt die Anfangswerte
     */
    Spielzettel(SpielzettelPanel einPanel) {
        spielZaehler = 0;
        panel = einPanel;
        for (int i = 0; i < 17; i++) {
            spielstand[i] = -1;
        }
    }

    /**
     * Berechnet die Summen für Spielstand oben, Bonus, Spielstand unten und
     * Gesamtsumme.
     */
    void summeBerechnen() {
        int spielstandOben = 0;
        int spielstandUnten = 0;
        for (int i = 0; i < 6; i++) {
            if (spielstand[i] > 0) {
                spielstandOben = spielstandOben + spielstand[i];
            }
        }
        spielstand[6] = spielstandOben;
        if (spielstand[6] > 62) {
            spielstand[7] = 35;
        } else {
            spielstand[7] = 0;
        }
        for (int i = 8; i < 15; i++) {
            if (spielstand[i] > 0) {
                spielstandUnten = spielstandUnten + spielstand[i];
            }
        }
        spielstand[15] = spielstandUnten;
        spielstand[16] = spielstand[6] + spielstand[7] + spielstand[15];
    }

    /**
     * Steuert, in welchem Feld des Spielzettels ein Wurf gewertet wird und
     * überprüft ob die Wertung gültig oder 0 (das heißt das Feld zu streichen)
     * ist.
     */
    void wurfWerten(Wurf einWurf) {
        Oberflaeche o = Oberflaeche.oberflaecheLiefern();
        o.textSetzen("Wo soll der Wurf eingetragen werden? Button drücken.");
        Button[] buttons = panel.buttonFeldLiefern();
        for (int i = 0; i < 6; i++) {
            buttons[i].setVisible(true);
            if (spielstand[i] != -1) {
                buttons[i].setEnabled(false);
            }
        }
        for (int i = 6; i < 13; i++) {
            buttons[i].setVisible(true);
            if (spielstand[i + 2] != -1) {
                buttons[i].setEnabled(false);
            }
        }
        o.eingabeButtonAktiv(false);
        int[] wurfErgebnis = einWurf.wurfErgebnisLiefern();
        while (wurfWertung == -1)
        {
            //Busy waiting: wir warten 100ms und testen dann noch mal, ob die Eingabe schon fertig ist.
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }

        switch (wurfWertung) {
            case 1:
                spielstand[0] = wurfErgebnis[0];
                break;

            case 2:
                spielstand[1] = wurfErgebnis[1] * 2;
                break;

            case 3:
                spielstand[2] = wurfErgebnis[2] * 3;
                break;

            case 4:
                spielstand[3] = wurfErgebnis[3] * 4;
                break;

            case 5:
                spielstand[4] = wurfErgebnis[4] * 5;
                break;

            case 6:
                spielstand[5] = wurfErgebnis[5] * 6;
                break;

            case 7:
                dreierPaschSetzen(wurfErgebnis);
                break;

            case 8:
                viererPaschSetzen(wurfErgebnis);
                break;

            case 9:
                fullHouseSetzen(wurfErgebnis);
                break;

            case 10:
                kleineStrasseSetzen(wurfErgebnis);
                break;

            case 11:
                grosseStrasseSetzen(wurfErgebnis);
                break;

            case 12:
                kniffelSetzen(wurfErgebnis);
                break;

            case 13:
                chanceSetzen(wurfErgebnis);
                break;

        }
        spielZaehler = spielZaehler + 1;
        if (spielZaehler == 13) {
            spielFertig = true;
        }
        wurfWertung = -1;
        for (int i = 0; i < 13; i++) {
            buttons[i].setVisible(false);
        }
        spielzettelAnzeigen();
        o.eingabeButtonAktiv(true);
    }

    /**
     * Berechnet die Summen des Spielstands neu und zeigt den Spielzettel an.
     */
    void spielzettelAnzeigen() {
        summeBerechnen();
        panel.spielstandSetzen(spielstand);
    }

    /**
     * Hilfsmethode: Überprüft, ob ein Dreierpasch vorliegt und trägt ihn im
     * Spielzettel ein. Falls der Wurf kein Dreierpasch ist und dieses Feld für
     * die Wurfwertung ausgewählt wurde, wird 0 eingetragen.
     */
    private void dreierPaschSetzen(int[] wurfErgebnis) {

        int eins = wurfErgebnis[0];
        int zwei = wurfErgebnis[1];
        int drei = wurfErgebnis[2];
        int vier = wurfErgebnis[3];
        int fuenf = wurfErgebnis[4];
        int sechs = wurfErgebnis[5];

        //wir überprüfen, ob ein Dreierpasch vorliegt
        if (eins >= 3
                || zwei >= 3
                || drei >= 3
                || vier >= 3
                || fuenf >= 3
                || sechs >= 3) {
            spielstand[8] = eins + zwei * 2 + drei * 3 + vier * 4 + fuenf * 5 + sechs * 6;

        } else {
            //das entspricht dem Streichen des Eintrags
            spielstand[8] = 0;
        }
    }

    /**
     * Hilfsmethode: Überprüft, ob ein Viererpasch vorliegt und trägt ihn im
     * Spielzettel ein. Falls der Wurf kein Viererpasch ist und dieses Feld für
     * die Wurfwertung ausgewählt wurde, wird 0 eingetragen.
     */
    private void viererPaschSetzen(int[] wurfErgebnis) {

        int eins = wurfErgebnis[0];
        int zwei = wurfErgebnis[1];
        int drei = wurfErgebnis[2];
        int vier = wurfErgebnis[3];
        int fuenf = wurfErgebnis[4];
        int sechs = wurfErgebnis[5];

        //wir überprüfen, ob ein Viererpasch vorliegt
        if (eins >= 4
                || zwei >= 4
                || drei >= 4
                || vier >= 4
                || fuenf >= 4
                || sechs >= 4) {
            spielstand[9] = eins + zwei * 2 + drei * 3 + vier * 4 + fuenf * 5 + sechs * 6;

        } else {
            //das entspricht dem Streichen des Eintrags
            spielstand[9] = 0;
        }
    }

    /**
     * Hilfsmethode: Überprüft, ob ein Kniffel vorliegt und trägt ihn im
     * Spielzettel ein. Falls der Wurf kein Kniffel ist und dieses Feld für die
     * Wurfwertung ausgewählt wurde, wird 0 eingetragen.
     */
    private void kniffelSetzen(int[] wurfErgebnis) {

        int eins = wurfErgebnis[0];
        int zwei = wurfErgebnis[1];
        int drei = wurfErgebnis[2];
        int vier = wurfErgebnis[3];
        int fuenf = wurfErgebnis[4];
        int sechs = wurfErgebnis[5];

        //wir überprüfen, ob ein Kniffel vorliegt
        if (eins == 5
                || zwei == 5
                || drei == 5
                || vier == 5
                || fuenf == 5
                || sechs == 5) {
            spielstand[13] = 50;

        } else {
            //das entspricht dem Streichen des Eintrags
            spielstand[13] = 0;
        }
    }

    /**
     * Hilfsmethode: Überprüft, ob ein Full House vorliegt und trägt dies im
     * Spielzettel ein. Falls der Wurf kein Full House ist und dieses Feld für
     * die Wurfwertung ausgewählt wurde, wird 0 eingetragen.
     */
    private void fullHouseSetzen(int[] wurfErgebnis) {

        int eins = wurfErgebnis[0];
        int zwei = wurfErgebnis[1];
        int drei = wurfErgebnis[2];
        int vier = wurfErgebnis[3];
        int fuenf = wurfErgebnis[4];
        int sechs = wurfErgebnis[5];

        //wir überprüfen, ob ein Full House vorliegt
        if ((eins == 2
                || zwei == 2
                || drei == 2
                || vier == 2
                || fuenf == 2
                || sechs == 2)
                && (eins == 3
                || zwei == 3
                || drei == 3
                || vier == 3
                || fuenf == 3
                || sechs == 3)) {
            spielstand[10] = 25;

        } else {
            //das entspricht dem Streichen des Eintrags
            spielstand[10] = 0;
        }
    }

    /**
     * Hilfsmethode: Trägt den Wurf im Feld Chance ein.
     */
    private void chanceSetzen(int[] wurfErgebnis) {

        int eins = wurfErgebnis[0];
        int zwei = wurfErgebnis[1];
        int drei = wurfErgebnis[2];
        int vier = wurfErgebnis[3];
        int fuenf = wurfErgebnis[4];
        int sechs = wurfErgebnis[5];

        spielstand[14] = eins + zwei * 2 + drei * 3 + vier * 4 + fuenf * 5 + sechs * 6;
    }

    /**
     * Hilfsmethode: Überprüft, ob eine Große Straße vorliegt und trägt den Wurf
     * im Spielzettel ein. Falls der Wurf keine Große Straße ist und dieses Feld
     * für die Wurfwertung ausgewählt wurde, wird 0 eingetragen.
     */
    private void grosseStrasseSetzen(int[] wurfErgebnis) {

        int eins = wurfErgebnis[0];
        int zwei = wurfErgebnis[1];
        int drei = wurfErgebnis[2];
        int vier = wurfErgebnis[3];
        int fuenf = wurfErgebnis[4];
        int sechs = wurfErgebnis[5];

        //wir überprüfen, ob eine Große Straße vorliegt
        if ((eins == 1
                && zwei == 1
                && drei == 1
                && vier == 1
                && fuenf == 1)
                || (zwei == 1
                && drei == 1
                && vier == 1
                && fuenf == 1
                && sechs == 1)) {
            spielstand[12] = 40;

        } else {
            //das entspricht dem Streichen des Eintrags
            spielstand[12] = 0;
        }
    }

    /**
     * Hilfsmethode: Überprüft, ob eine Kleine Straße vorliegt und trägt den Wurf
     * im Spielzettel ein. Falls der Wurf keine Kleine Straße ist und dieses Feld
     * für die Wurfwertung ausgewählt wurde, wird 0 eingetragen.
     */
    private void kleineStrasseSetzen(int[] wurfErgebnis) {

        int eins = wurfErgebnis[0];
        int zwei = wurfErgebnis[1];
        int drei = wurfErgebnis[2];
        int vier = wurfErgebnis[3];
        int fuenf = wurfErgebnis[4];
        int sechs = wurfErgebnis[5];

        //wir überprüfen, ob eine Kleine Straße vorliegt
        if ((eins >= 1
                && zwei >= 1
                && drei >= 1
                && vier >= 1)
                || (zwei >= 1
                && drei >= 1
                && vier >= 1
                && fuenf >= 1)
                || (drei >= 1
                && vier >= 1
                && fuenf >= 1
                && sechs >= 1)) {
            spielstand[11] = 30;

        } else {
            //das entspricht dem Streichen des Eintrags
            spielstand[11] = 0;
        }
    }

    /**
     * Getter: Liefert das Attribut spielFertig.
     */
    boolean spielFertigLiefern() {
        return spielFertig;
    }

    /**
     * Getter: Liefert das zugehörige Panel
     */
    SpielzettelPanel panelLiefern() {
        return panel;
    }

    /**
     * Setter: Setzt das Attribut wurfWertung.
     */
    void wurfWertungSetzen(int i) {
        wurfWertung = i;
    }

    /**
     * Liefert die Gesamtsumme des Spielstands.
     */
    int gesamtsummeLiefern() {
        return spielstand[16];
    }
}
