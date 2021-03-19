package eu.bopet.jocadv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.Part;
import eu.bopet.jocadv.core.features.Extrude;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.features.Revolve;
import eu.bopet.jocadv.core.features.Sketch;
import eu.bopet.jocadv.core.geometries.datums.JoAxis;
import eu.bopet.jocadv.core.geometries.datums.JoCoordinateSystem;
import eu.bopet.jocadv.core.geometries.datums.JoPlane;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JoCADv extends ApplicationAdapter {


    private JoRenderer renderer;
    private List<Part> parts;
    private Part currentPart;
    private List<Feature> selected;
    private Map<String,Class> featureTypes;

    private Environment environment;
    private OrthographicCamera cam;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private String text;
    private String part="Current part: ";
    private String command="Ready";
    private String selection = "Selection:\n";

    public ModelBatch modelBatch;

    private Map<JoPoint, Decal> points;

    private Texture texture;
    private DecalBatch decalBatch;
    private float zoomFactor = 1;

    @Override
    public void create() {

        init();

        parts = new ArrayList<>();
        currentPart = new Part("Test");
        selected = new ArrayList<>();
        points = new HashMap<>();
        part = part + currentPart.getName();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        spriteBatch = new SpriteBatch();


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(
                "Isonorm-3098-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.borderColor = JoColors.FONT_BORDER;
        parameter.color = JoColors.FONT;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);
        generator.dispose();

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

        initGUI();
    }

    private void initGUI() {
        VisUI.load();
        VisTable table = new VisTable(true);
        table.addSeparator(true);
    }

    private void init() {
        featureTypes = new HashMap<>();
        featureTypes.put("Datum Point", JoPoint.class);
        featureTypes.put("Datum Axis", JoAxis.class);
        featureTypes.put("Datum Plane", JoPlane.class);
        featureTypes.put("Datum CS", JoCoordinateSystem.class);
        featureTypes.put("Sketch", Sketch.class);
        featureTypes.put("Extrude", Extrude.class);
        featureTypes.put("Revolve", Revolve.class);
    }

    @Override
    public void render() {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(JoColors.BACKGROUND.r, JoColors.BACKGROUND.g, JoColors.BACKGROUND.b, JoColors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        cam.update();

        Gdx.gl.glLineWidth(2.0f);


        modelBatch.begin(cam);
        for (ModelInstance modelInstance : renderer.getModelInstances()) {
            modelBatch.render(modelInstance, environment);
        }
        modelBatch.end();

        decalBatch = new DecalBatch(new CameraGroupStrategy(cam));
        Iterator it = points.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            JoPoint point = (JoPoint) pair.getKey();
            Decal decal = (Decal) pair.getValue();
            if (point.isSelected()){
                decal.setColor(JoColors.POINT_SELECTED);
            } else {
                decal.setColor(JoColors.POINT);
            }
            decal.lookAt(cam.position, cam.up);
            decal.setScale(zoomFactor*0.7f);
            decalBatch.add(decal);
        }
        decalBatch.flush();


        spriteBatch.begin();
        text = "FPS: " + Gdx.graphics.getFramesPerSecond();
        font.draw(spriteBatch, text, 10, 30);
        font.draw(spriteBatch, part, 10, Gdx.graphics.getHeight() - 10);
        font.draw(spriteBatch, command, 10, Gdx.graphics.getHeight() - 30);
        font.draw(spriteBatch, selection, 10, Gdx.graphics.getHeight() - 50);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        spriteBatch.dispose();
        decalBatch.dispose();
        VisUI.dispose();
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
                    if (!selected.contains(feature)){
                        selected.add(feature);
                        String name = feature.getName();
                        if (name==null) {
                            name = feature.toString();
                        }
                        selection = selection + name + "\n";
                    }
                }
            }
        }
        renderFeatures();
    }

    public float getZoomFactor() {
        return zoomFactor;
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
        selection = "Selection:\n";
        renderFeatures();
    }

    public void renderFeatures() {
        renderer.renderFeatures();
    }

    public void addPoint(JoPoint joPoint) {
        Decal decal = Decal.newDecal(1, 1, new TextureRegion(texture));
        decal.setColor(JoColors.POINT);
        decal.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        decal.setPosition(joPoint.getVector().getVector3());
        points.put(joPoint,decal);
    }

    public void commandNew() {
        command = "New feature, select feature typ";

    }

    public void commandEdit() {
        command = "Edit feature, select feature to edit";
    }
}
