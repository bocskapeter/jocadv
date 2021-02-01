package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.vector.JoVector;

public interface Stretchable {
    void stretchTo(JoVector min, JoVector max);
}
