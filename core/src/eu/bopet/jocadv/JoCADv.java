package eu.bopet.jocadv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.collision.Ray;
import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.Part;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.datums.JoAxis;
import eu.bopet.jocadv.core.vector.JoVector;
import eu.bopet.jocadv.core.vector.Value;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_POINTS;

public class JoCADv extends ApplicationAdapter {


    private JoRenderer renderer;
    private List<Part> parts;
    private Part currentPart;

    private Environment environment;
    private OrthographicCamera cam;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private String text;

    public ModelBatch modelBatch;

    private    ShapeRenderer shapeRenderer;
    ImmediateModeRenderer20 immediateRenderer;
    private float zoomFactor=1;

    @Override
    public void create() {

        parts = new ArrayList<>();
        currentPart = new Part();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        spriteBatch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(
                "Isonorm-3098-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.borderColor = JoColors.FONT_BORDER;
        parameter.color = JoColors.FONT;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);
        generator.dispose();

        modelBatch = new ModelBatch();

        cam = new OrthographicCamera(640, 640 * ((float) Gdx.graphics.getHeight()
                / (float) Gdx.graphics.getWidth()));

        cam.position.set(100f, 100f, 100f);
        cam.lookAt(0, 0, 0);
        cam.near = -3000f;
        cam.far = 3000f;
        cam.update();

        JoInput input = new JoInput(this);
        Gdx.input.setInputProcessor(input);

        renderer = new JoRenderer(this, currentPart.getFeatures(),cam);
        renderer.renderFeatures();

        immediateRenderer = new ImmediateModeRenderer20(50000, false, true, 0);
    }

    @Override
    public void render() {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(JoColors.BACKGROUND.r, JoColors.BACKGROUND.g, JoColors.BACKGROUND.b, JoColors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        cam.update();

        Gdx.gl.glLineWidth(2.0f);

        modelBatch.begin(cam);
        for (ModelInstance modelInstance : renderer.getModelInstances()) {
            modelBatch.render(modelInstance, environment);
        }
        modelBatch.end();

        spriteBatch.begin();
        text = "FPS: " + Gdx.graphics.getFramesPerSecond();
        text = text + "\n" + FreeTypeFontGenerator.DEFAULT_CHARS;
        font.draw(spriteBatch, text, 10, Gdx.graphics.getHeight() - 10);
        spriteBatch.end();

        immediateRenderer.begin(cam.combined,GL_POINTS);
        immediateRenderer.color(Color.WHITE);
        immediateRenderer.vertex(1,1,1);
        immediateRenderer.end();

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
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
        for (Feature feature: currentPart.getFeatures()){

            if (feature instanceof Geometry){
                Geometry geometry = (Geometry) feature;
                if (geometry.distance(pickingRay)<distance){
                    ((Feature)geometry).setSelected(true);
                    System.out.println("Selected: " + feature.toString());
                }
            }
        }
        renderer.renderFeatures();
    }

    public float getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(float zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
}
