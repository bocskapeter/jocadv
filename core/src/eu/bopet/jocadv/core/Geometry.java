package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.features.Feature;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.List;

public interface Geometry {

    double distance (Line pickingLine);

    List<Feature> getFeatures();

}
