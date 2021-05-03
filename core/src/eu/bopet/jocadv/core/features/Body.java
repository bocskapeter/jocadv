package eu.bopet.jocadv.core.features;

import com.badlogic.gdx.files.FileHandle;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import eu.bopet.jocadv.core.vector.JoVector;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Body extends Feature {
    private List<JoPoint> points;
    private List<Feature> surfaces;
    private List<Feature> faces;

    public Body() {
        points = new ArrayList<>();
        surfaces = new ArrayList<>();
        faces = new ArrayList<>();
    }

    public List<JoPoint> getPoints() {
        return points;
    }

    public List<Feature> getFaces() {
        return faces;
    }

    public static List<Body> importModel(FileHandle file) {
        List<Body> bodies = new ArrayList<>();
        List<JoVector> normals = new ArrayList<>();
        Body body = new Body();
        String line;
        String[] tokens;
        char firstChar;

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()), 4096);

        try {
            while ((line = reader.readLine()) != null) {
                tokens = line.split("\\s+");
                if (tokens.length < 1) break;
                if (tokens[0].length() == 0) {
                    continue;
                } else if ((firstChar = tokens[0].toLowerCase().charAt(0)) == '#') {
                    continue;
                } else if (firstChar == 'v') {
                    if (tokens[0].length() == 1) {
                        JoPoint joPoint = new JoPoint(new JoVector(new Vector3D(
                                Double.parseDouble(tokens[1]),
                                Double.parseDouble(tokens[2]),
                                Double.parseDouble(tokens[3]))));
                        body.points.add(joPoint);
                    } else if (tokens[0].charAt(1) == 'n') {
                        JoVector joVector = new JoVector(new Vector3D(
                                Double.parseDouble(tokens[1]),
                                Double.parseDouble(tokens[2]),
                                Double.parseDouble(tokens[3])));
                        normals.add(joVector);
                    }
                } else if (firstChar == 'f') {
                    List<Integer> ps = new ArrayList<>();
                    List<Integer> ns = new ArrayList<>();
                    for (String token : tokens) {
                        if (token.equals("f")) continue;
                        String[] parts = token.split("/");
                        ps.add(Integer.parseInt(parts[0]));
                        if (parts.length>2){
                            int i = Integer.parseInt(parts[2]);
                            if (!ns.contains(i))ns.add(i);
                        }
                    }
                    if (ns.size()==1) {
                        JoPoint joPointA = body.points.get(ps.get(0)-1);
                        JoPoint joPointB = body.points.get(ps.get(1)-1);
                        JoPoint joPointC = body.points.get(ps.get(2)-1);
                        Edge edge1 = new Edge(joPointA,joPointB);
                        Edge edge2 = new Edge(joPointB,joPointC);
                        Edge edge3 = new Edge(joPointC,joPointA);
                        List<Edge> edges = new ArrayList<>();
                        edges.add(edge1);
                        edges.add(edge2);
                        edges.add(edge3);
                        JoVector normal = normals.get(ns.get(0)-1);
                        Face face = new Face(edges,normal);
                        body.faces.add(face);
                    } else if (ns.size()>1){
                        System.out.println("surface :" + line);
                        //TODO: create Surface
                    }
                }
            }
            if (!bodies.contains(body)) bodies.add(body);

        } catch (IOException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        System.out.println("bodies");
        return bodies;
    }
}
