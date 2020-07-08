package com.company;

import javax.swing.*;

public class Wuerfel{

        // die Zahl der gewürfelten Augen
        private int augen;
        // ob der Würfel in einer Runde "behalten" oder erneut ausgewürfelt werden soll.
        private boolean behalten;
        // das zugehörige WürfelSymbol
        private WuerfelSymbol symbol;
        // jeder Würfel hat eine Nummer zwischen 1 und 5, um seine Position auf der
        // Oberfläche zu definieren.
        private int wuerfelNummer;

        /**
         * Erzeugt einen Würfel und setzt die Anfangswerte
         */
        Wuerfel(int wuerfelNr) {
            behalten = false;
            augen = 0;
            wuerfelNummer = wuerfelNr;
        }

        /**
         * Erzeugt einen zufälligen Wert für den Würfel zwischen 1 und 6 und
         * aktualisiert das zugehörige WürfelSymbol entsprechend.
         */
        void wuerfeln() {
            //Math.random erzeugt eine zufällig Zahl zwischen 0.0 and 0.9999.
            //Multipliziert man diese mit 6 liegt die erzeugte Zahl daher zwischen 0.0 und 5.999
            //(int) ist ein sogenannter "Cast". Kommazahlen werden auf volle Integerzahlen abgeschnitten.
            //Der erzeugte Wert ist damit zwischen 0 und 5. Wir addieren noch 1, um einen
            //zufälligen Wert zwischen 1 und 6 zu erhalten.

            augen = (int) (Math.random() * 6) + 1;
            if (symbol == null) {
                symbol = new WuerfelSymbol(augen, wuerfelNummer);
            } else {
                symbol.aktualisieren(augen);
            }
        }

        /**
         * Setter: Setzt den Wert für Attribut behalten.
         */
        void behaltenSetzen(boolean wert) {
            behalten = wert;
        }

        /**
         * Getter: Liefert den Wert für Attribut behalten.
         */
        boolean behaltenLiefern() {
            return behalten;
        }

        /**
         * Getter: Liefert den Wert für Attribut augen.
         */
        int augenLiefern() {
            return augen;
        }

        /**
         * Getter: Liefert das zugehörige Würfelsymbol.
         */
        WuerfelSymbol wuerfelSymbolLiefern() {
            return symbol;
        }
    }




