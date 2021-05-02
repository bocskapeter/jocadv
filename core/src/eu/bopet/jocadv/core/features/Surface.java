package eu.bopet.jocadv.core.features;

import java.util.ArrayList;
import java.util.List;

public class Surface extends Feature{
    private List<Feature> faces;
    private List<Feature> edges;

    public Surface() {
        faces = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
