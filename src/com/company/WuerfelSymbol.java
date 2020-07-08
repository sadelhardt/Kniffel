package com.company;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class WuerfelSymbol extends Canvas {

    // die Zahl der gewürfelten Augen
    private int augen;
    // x-Position des Würfels auf der Oberfläche
    private int posX;
    // y-Position des Würfels auf der Oberfläche
    private int posY = 50;
    // ob der Würfel in einer Runde als "behalten" angeklickt wurde.
    private int behaltenClicked = 0;
    // ob der Würfel "inaktiv", das heißt nicht anklickbar ist.
    private boolean wuerfelInaktiv = false;

    /**
     * Erzeugt ein WürfelSymbol und setzt die Anfangswerte
     *
     * @param anzahl: Anzahl der gewürfelten Augen
     * @param wuerfelNummer: definiert die Position des Würfels auf dem Canvas
     */
    WuerfelSymbol(int anzahl, int wuerfelNummer) {
        augen = anzahl;
        setSize(56, 56);
        setVisible(true);
        switch (wuerfelNummer) {
            case 1:
                posX = 173;
                break;
            case 2:
                posX = 273;
                break;
            case 3:
                posX = 373;
                break;
            case 4:
                posX = 473;
                break;
            case 5:
                posX = 573;
                break;
        }
        setLocation(posX, 30);
        this.addMouseListener(new MouseListener() {
            //alle Methoden des MouseListener müssen überschrieben werden,
            //wenn auch nur leer, mouseReleased wird als einzige implementiert.
            //mouseClicked wäre auch möglich, aber dann führen unklare Maus-Aktionen
            //zeitweise dazu, dass Klicks nicht erkannt werden. mouseReleased ist
            //meist eindeutiger.
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (wuerfelInaktiv == false) {
                    if (behaltenClicked == 0) {
                        behaltenClicked = 1;
                    } else {
                        behaltenClicked = 0;
                    }
                }
                invalidate();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        Frame fenster = Oberflaeche.oberflaecheLiefern().fensterLiefern();
        fenster.add(this, 0);
    }

    /**
     * Zeichnet das eigentliche WürfelSymbol. Standard: mit schwarzem Rand. Wurde
     * das Attribut behalten gesetzt, wird das Symbol mit einem roten Rand
     * dargestellt.
     *
     * @param g: Graphics-Objekt
     */
    @Override
    public void paint(Graphics g) {
        switch (behaltenClicked) {
            case 0:
                g.setColor(new Color(223, 237, 227));
                g.fillRoundRect(0, 0, 56, 56, 10, 10);
                g.setColor(Color.DARK_GRAY);
                g.fillRoundRect(2, 2, 54, 54, 10, 10);
                g.setColor(Color.WHITE);
                g.fillRoundRect(4, 4, 50, 50, 10, 10);
                g.setColor(Color.DARK_GRAY);
                break;

            case 1:
                g.setColor(new Color(223, 237, 227));
                g.fillRoundRect(0, 0, 56, 56, 10, 10);
                g.setColor(Color.RED);
                g.fillRoundRect(2, 2, 54, 54, 10, 10);
                g.setColor(Color.WHITE);
                g.fillRoundRect(4, 4, 50, 50, 10, 10);
                g.setColor(Color.DARK_GRAY);
                break;
        }

        int offset = 25;
        switch (augen) {
            case 0:
                break;
            case 1:
                g.fillOval(offset, offset, 8, 8);
                break;
            case 2:
                g.fillOval(offset + 12, offset - 12, 8, 8);
                g.fillOval(offset - 12, offset + 12, 8, 8);
                break;
            case 3:
                g.fillOval(offset + 12, offset - 12, 8, 8);
                g.fillOval(offset, offset, 8, 8);
                g.fillOval(offset - 12, offset + 12, 8, 8);
                break;
            case 4:
                g.fillOval(offset + 12, offset + 12, 8, 8);
                g.fillOval(offset + 12, offset - 12, 8, 8);
                g.fillOval(offset - 12, offset + 12, 8, 8);
                g.fillOval(offset - 12, offset - 12, 8, 8);
                break;
            case 5:
                g.fillOval(offset + 12, offset + 12, 8, 8);
                g.fillOval(offset + 12, offset - 12, 8, 8);
                g.fillOval(offset - 12, offset + 12, 8, 8);
                g.fillOval(offset - 12, offset - 12, 8, 8);
                g.fillOval(offset, offset, 8, 8);
                break;
            case 6:
                g.fillOval(offset + 12, offset + 12, 8, 8);
                g.fillOval(offset + 12, offset - 12, 8, 8);
                g.fillOval(offset - 12, offset + 12, 8, 8);
                g.fillOval(offset - 12, offset - 12, 8, 8);
                g.fillOval(offset - 12, offset, 8, 8);
                g.fillOval(offset + 12, offset, 8, 8);
                break;
        }
    }

    /**
     * Aktualisiert das Würfelsymbol, nachdem der Würfel neu gewürfelt wurde.
     *
     * @param neueAugen: die neu gewürfelten Augen
     */
    void aktualisieren(int neueAugen) {
        augen = neueAugen;
        invalidate();
        repaint();
    }

    /**
     * Getter: Liefert den Wert des Attributs behaltenClicked.
     */
    int behaltenLiefern() {
        return behaltenClicked;
    }

    /**
     * Setter: Setzt den Wert des Attributs wuerfelInaktiv
     *
     * @param wert: true für Würfel deaktivieren, false für Würfel aktivieren
     */
    void wuerfelInaktivSetzen(boolean wert) {
        wuerfelInaktiv = wert;
    }
}