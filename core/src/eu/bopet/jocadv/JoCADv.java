package eu.bopet.jocadv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import eu.bopet.jocadv.core.Part;

import java.util.ArrayList;
import java.util.List;

public class JoCADv extends ApplicationAdapter {

    private List<Part> parts;
    private Part currentPart;

    private Environment environment;
    private OrthographicCamera cam;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private String text;

    public ModelBatch modelBatch;
    public Model axisX;
    public Model axisY;
    public Model axisZ;
    public ModelInstance instanceX;
    public ModelInstance instanceY;
    public ModelInstance instanceZ;

    @Override
    public void create() {

        parts = new ArrayList<>();
        currentPart = new Part();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        spriteBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("jocadv.fnt"));

        modelBatch = new ModelBatch();

        cam = new OrthographicCamera(640, 640 * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));

        cam.position.set(100f, 100f, 100f);
        cam.lookAt(0, 0, 0);
        cam.near = -3000f;
        cam.far = 3000f;
        cam.update();

        JoInput input = new JoInput(cam, Vector3.Zero);
        Gdx.input.setInputProcessor(input);

        ModelBuilder modelBuilder = new ModelBuilder();
        axisX = modelBuilder.createArrow(new Vector3(0, 0, 0), new Vector3(200, 0, 0),
                new Material(ColorAttribute.createDiffuse(Color.RED)), VertexAttributes.Usage.Position);
        axisY = modelBuilder.createArrow(new Vector3(0, 0, 0), new Vector3(0, 200, 0),
                new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position);
        axisZ = modelBuilder.createArrow(new Vector3(0, 0, 0), new Vector3(0, 0, 200),
                new Material(ColorAttribute.createDiffuse(Color.BLUE)), VertexAttributes.Usage.Position);
        instanceX = new ModelInstance(axisX);
        instanceY = new ModelInstance(axisY);
        instanceZ = new ModelInstance(axisZ);
    }

    @Override
    public void render() {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        cam.update();

        modelBatch.begin(cam);
        modelBatch.render(instanceX, environment);
        modelBatch.render(instanceY, environment);
        modelBatch.render(instanceZ, environment);
        modelBatch.end();

        spriteBatch.begin();
        text = "FPS: " + Gdx.graphics.getFramesPerSecond();

        font.draw(spriteBatch, text, 10, Gdx.graphics.getHeight() - 10);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        axisX.dispose();
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 640;
        cam.viewportHeight = 640 * height / width;
        cam.update();
    }
}
