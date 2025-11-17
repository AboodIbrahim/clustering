package algo;

import model.DistanceMatrix;
import model.TreeClass;

/**
 * Definiert Funktionen für Clustering-Algorithmen.
 */
public interface Clustering {

    /**
     * Gibt den aus der Distanzmatrix erstellten Baum aus.
     * @return eigene Datenstruktur zum Darstellen von gewurzelten
     * oder ungewurzelten Bäumen.
     */
    TreeClass getTree();
    /**
     * Gibt Distanzmatrix vor dem Clustern raus
     * @return Distanzmatrix
     */
    DistanceMatrix getDM();

}
