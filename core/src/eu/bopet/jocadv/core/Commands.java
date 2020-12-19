package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.features.Sketch;
import eu.bopet.jocadv.core.geometries.Axis3D;
import eu.bopet.jocadv.core.geometries.CSys3D;
import eu.bopet.jocadv.core.geometries.Line3D;
import eu.bopet.jocadv.core.geometries.Plane3D;
import eu.bopet.jocadv.core.vector.Vector3D;

import java.util.HashMap;
import java.util.Map;

public class Commands {

    private Map<String, Class> map;

    public Commands() {
        this.map = new HashMap<>();
        init();
    }

    private void init() {
        registerCommand("point", Vector3D.class);
        registerCommand("axis", Axis3D.class);
        registerCommand("csys", CSys3D.class);
        registerCommand("plane", Plane3D.class);
        registerCommand("line", Line3D.class);
        registerCommand("sketch", Sketch.class);
        registerCommand("part", Part.class);
    }

    public void registerCommand(String name, Class c) {
        this.map.put(name, c);
    }

    public Class getClass(String name) {
        return map.get(name);
    }
}
