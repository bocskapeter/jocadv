package eu.bopet.jocadv;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import eu.bopet.jocadv.core.JoColors;
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

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Isonorm-3098-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.borderColor = JoColors.FONT_BORDER;
        parameter.color = JoColors.FONT;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);
        generator.dispose();

        modelBatch = new ModelBatch();

        cam = new OrthographicCamera(640, 640 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

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
        Gdx.gl.glClearColor(JoColors.BACKGROUND.r, JoColors.BACKGROUND.g, JoColors.BACKGROUND.b, JoColors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        cam.update();

        modelBatch.begin(cam);
        modelBatch.render(instanceX, environment);
        modelBatch.render(instanceY, environment);
        modelBatch.render(instanceZ, environment);
        modelBatch.end();

        spriteBatch.begin();
        text = "FPS: " + Gdx.graphics.getFramesPerSecond();
        text = text + "\n" + FreeTypeFontGenerator.DEFAULT_CHARS;

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
        cam.viewportHeight = 640 * (float) height / (float) width;
        cam.update();
    }
}
