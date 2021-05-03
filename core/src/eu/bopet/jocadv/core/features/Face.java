package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.vector.JoVector;

import java.util.ArrayList;
import java.util.List;

public class Face extends Feature {
    private List<Edge> edges;
    private JoVector normal;

    public Face(List<Edge> edges, JoVector normal) {
        this.edges = edges;
        this.normal = normal;
    }

    public Face() {
        edges = new ArrayList<>();
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
