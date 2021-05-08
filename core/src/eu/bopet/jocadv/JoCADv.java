package eu.bopet.jocadv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.Part;
import eu.bopet.jocadv.core.features.*;
import eu.bopet.jocadv.core.geometries.JoArc;
import eu.bopet.jocadv.core.geometries.JoCircle;
import eu.bopet.jocadv.core.geometries.JoLine;
import eu.bopet.jocadv.core.geometries.JoSphere;
import eu.bopet.jocadv.core.geometries.datums.JoAxis;
import eu.bopet.jocadv.core.geometries.datums.JoCoordinateSystem;
import eu.bopet.jocadv.core.geometries.datums.JoPlane;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoCADv extends ApplicationAdapter {


    private JoRenderer renderer;
    private List<Part> parts;
    private Part currentPart;
    private List<Feature> selected;
    private Map<String, Feature> features;
    private Feature currentSelected;
    private Map<String, Class> featureTypes;

    private Environment environment;
    private OrthographicCamera cam;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private BitmapFont fontSelected;
    private String text;
    private String part = "Current part: ";
    private String command = "Ready";
    private String selection = "Current selection: ";
    private String selectionList = "Selection:\n";

    public ModelBatch modelBatch;

    private Map<JoPoint, Decal> points;

    private Texture texture;
    private DecalBatch decalBatch;
    private float zoomFactor = 1;

    private ModelInstance objModel;

    DirectionalLight light;

    @Override
    public void create() {

        init();

        parts = new ArrayList<>();
        currentPart = new Part("Test");
        features = new HashMap<>();
        features.put(currentPart.getName(), currentPart);
        for (Feature feature : currentPart.getFeatures()) {
            features.put(featureGetName(feature), feature);
        }
        selected = new ArrayList<>();
        points = new HashMap<>();
        part = part + currentPart.getName();

        getObjModel();

        environment = new Environment();
        Vector3 lightDirection = new Vector3(-1f, -0.8f, -0.2f);
        Color lightColor = new Color(0.8f, 0.9f, 0.9f, 1.0f);
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        light = new DirectionalLight();
        light.set(lightColor, lightDirection);
        environment.add(light);

        spriteBatch = new SpriteBatch();

        generateFonts();

        texture = new Texture(Gdx.files.internal("dot.png"));
        decalBatch = new DecalBatch(new CameraGroupStrategy(cam));

        modelBatch = new ModelBatch();

        cam = new OrthographicCamera(640, 640 * ((float) Gdx.graphics.getHeight()
                / (float) Gdx.graphics.getWidth()));

        cam.position.set(100f, -100f, 100f);
        cam.lookAt(0, 0, 0);
        cam.up.set(new Vector3(0, 0, 1));
        cam.near = -3000f;
        cam.far = 3000f;
        cam.update();

        JoInput input = new JoInput(this);
        Gdx.input.setInputProcessor(input);

        renderer = new JoRenderer(this, currentPart.getFeatures(), cam);
        renderFeatures();
    }

    private void getObjModel() {
        ObjLoader objLoader = new ObjLoader();
        FileHandle fileHandle = Gdx.files.internal("cavity.obj");
        List<Body> bodies = Body.importModel(fileHandle);
        for (Body body : bodies){
            for (Feature point : body.getPoints()){
                currentPart.addFeature(point);
            }
            for ( Feature face : body.getFaces()){
                currentPart.addFeature(face);
            }
        }
        Model model = objLoader.loadModel(fileHandle);
        objModel = new ModelInstance(model);
    }

    private void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(
                "Isonorm-3098-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.borderColor = JoColors.FONT_BORDER;
        parameter.color = JoColors.FONT;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);
        parameter.color = JoColors.FONT_SELECTED;
        fontSelected = generator.generateFont(parameter);
        generator.dispose();
    }

    private void init() {
        featureTypes = new HashMap<>();
        featureTypes.put("Part", Part.class);
        featureTypes.put("Datum Point", JoPoint.class);
        featureTypes.put("Datum Axis", JoAxis.class);
        featureTypes.put("Datum Plane", JoPlane.class);
        featureTypes.put("Datum CS", JoCoordinateSystem.class);
        featureTypes.put("Extrude", Extrude.class);
        featureTypes.put("Revolve", Revolve.class);
        featureTypes.put("Sketch", Sketch.class);
        featureTypes.put("Line", JoLine.class);
        featureTypes.put("Sphere", JoSphere.class);
        featureTypes.put("Circle", JoCircle.class);
        featureTypes.put("Arc", JoArc.class);
    }

    @Override
    public void render() {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(JoColors.BACKGROUND.r, JoColors.BACKGROUND.g, JoColors.BACKGROUND.b, JoColors.BACKGROUND.a);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));


        cam.update();
        Vector3 right = cam.up.cpy().crs(cam.direction.cpy()).scl(-1.0f);
        Vector3 lightDirection = right.add(cam.direction.cpy()).sub(cam.up.cpy());
        light.setDirection(lightDirection);

        Gdx.gl.glLineWidth(2.0f);


        modelBatch.begin(cam);
        for (ModelInstance modelInstance : renderer.getModelInstances()) {
            modelBatch.render(modelInstance, environment);
        }
        if (objModel != null) {
            modelBatch.render(objModel, environment);
        }
        modelBatch.end();

        decalBatch = new DecalBatch(new CameraGroupStrategy(cam));
        for (Map.Entry<JoPoint, Decal> joPointDecalEntry : points.entrySet()) {
            JoPoint point = joPointDecalEntry.getKey();
            Decal decal = joPointDecalEntry.getValue();
            if (point.isSelected()) {
                decal.setColor(JoColors.POINT_SELECTED);
            } else {
                decal.setColor(JoColors.POINT);
            }
            decal.lookAt(cam.position, cam.up);
            decal.setScale(zoomFactor * 1.7f);
            decalBatch.add(decal);
        }
        decalBatch.flush();


        spriteBatch.begin();
        text = "FPS: " + Gdx.graphics.getFramesPerSecond();
        font.draw(spriteBatch, text, 10, 30);
        font.draw(spriteBatch, part, 10, Gdx.graphics.getHeight() - 10);
        font.draw(spriteBatch, command, 10, Gdx.graphics.getHeight() - 30);
        fontSelected.draw(spriteBatch, selection, 10, Gdx.graphics.getHeight() - 50);
        font.draw(spriteBatch, selectionList, 10, Gdx.graphics.getHeight() - 70);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        spriteBatch.dispose();
        decalBatch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 640;
        cam.viewportHeight = 640 * (float) height / (float) width;
        cam.update();
    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public void pickFeature(Line pickingRay, double distance) {
        for (Feature feature : currentPart.getFeatures()) {
            if (feature instanceof Geometry) {
                Geometry geometry = (Geometry) feature;
                if (geometry.distance(pickingRay) < distance) {
                    ((Feature) geometry).setSelected(true);
                    if (!selected.contains(feature)) {
                        selected.add(feature);
                        selectionList = selectionList + featureGetName(feature) + "\n";
                    }
                }
            }
        }
        if (!selected.isEmpty()) {
            currentSelected = selected.get(0);
            selection = "Current selection: " + featureGetName(currentSelected);
        }
        renderFeatures();
    }

    private String featureGetName(Feature feature) {
        String name = feature.getName();
        if (name == null) {
            name = feature.toString();
            String[] s = name.split("\\.");
            name = s[s.length - 1];
        }
        return name;
    }

    public void setZoomFactor(float zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public void deSelect() {
        for (Feature feature : currentPart.getFeatures()) {
            if (feature instanceof Geometry) {
                Geometry geometry = (Geometry) feature;
                ((Feature) geometry).setSelected(false);
            }
        }
        selected = new ArrayList<>();
        selectionList = "Selection:\n";
        selection = "Current selection: ";
        renderFeatures();
    }

    public void renderFeatures() {
        renderer.renderFeatures();
    }

    public void addPoint(JoPoint joPoint) {
        /*Decal decal = Decal.newDecal(1, 1, new TextureRegion(texture));
        decal.setColor(JoColors.POINT);
        decal.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        decal.setPosition(joPoint.getVector().getVector3());
        points.put(joPoint, decal);*/
    }

    public void commandNew() {
        command = "Select feature typ to create new";
        selectionList = "Selection:\n";
        for (Map.Entry<String, Class> stringClassEntry : featureTypes.entrySet()) {
            String key = stringClassEntry.getKey();
            selectionList = selectionList + key + "\n";
        }
    }

    public void commandEdit() {
        command = "Select feature to edit";
        selectionList = "Selection:\n";
        for (Map.Entry<String, Feature> stringFeatureEntry : features.entrySet()) {
            String key = stringFeatureEntry.getKey();
            selectionList = selectionList + key + "\n";
        }
        JoTextInputListener listener = new JoTextInputListener(this);
        Gdx.input.getTextInput(listener, "Enter", "", "");
    }

    public void selectionConfirmed() {
        command = "OK";
        deSelect();
        currentSelected.setSelected(true);
        selection = "Current selection: " + featureGetName(currentSelected);
        renderFeatures();
    }

    public void nextSelection(float amountY) {
        if (!selected.isEmpty()) {
            int pos = selected.indexOf(currentSelected);
            if (amountY > 0) {
                pos++;
            } else {
                pos--;
            }
            if (pos < 0) pos = selected.size() - 1;
            if (pos >= selected.size()) pos = 0;
            currentSelected = selected.get(pos);
            for (Feature feature : selected) {
                feature.setSelected(false);
            }
            currentSelected.setSelected(true);
            selection = "Current selection: " + featureGetName(currentSelected);
            renderFeatures();
        }
    }
}
