package eu.bopet.jocadv.core.features;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import eu.bopet.jocadv.core.vector.JoVector;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Body extends Feature {
    private List<Feature> points;
    private List<Feature> faces;
    private List<Feature> edges;

    public Body() {
        points = new ArrayList<>();
        faces = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public List<Feature> getPoints() {
        return points;
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
                }else if (firstChar == 'v') {
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
