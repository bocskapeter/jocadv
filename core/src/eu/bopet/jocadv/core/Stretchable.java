package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.vector.JoVector;

public interface Stretchable {
    void stretchTo(JoVector min, JoVector max);
}
