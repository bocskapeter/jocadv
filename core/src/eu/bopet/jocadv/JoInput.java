package eu.bopet.jocadv;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class JoInput implements InputProcessor {

    private static final float zoomFactor = 10;
    private static final float rotateFactor = 2;


    private OrthographicCamera camera;
    private int rotateX = -1;
    private int rotateY = -1;
    private int clickX = -1;
    private int clickY = -1;
    private int dx = -1;
    private int dy = -1;
    private int clickedButton = -1;
    private Vector3 up = new Vector3();
    private Vector3 cross = new Vector3();
    private Vector3 position = new Vector3();
    private Vector3 direction = new Vector3();
    private Vector3 newPosition = new Vector3();
    private Vector3 centerOfRotation;
    private float viewportWidth;
    private float viewportHeight;
    private float width;
    private float height;
    private float ratio;
    private float zoom;
    private float scale2Rotate;
    private Ray pickingRay;


    public JoInput(OrthographicCamera camera, Vector3 centerOfRotation) {
        this.camera = camera;
        this.centerOfRotation = centerOfRotation;
        ratio = ((float) Gdx.graphics.getHeight()) / ((float) Gdx.graphics.getWidth());
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.HOME:
                resetView();
                break;
            case Input.Keys.X:
                viewX();
                break;
            case Input.Keys.Y:
                viewY();
                break;
            case Input.Keys.Z:
                viewZ();
                break;
        }
        return false;
    }

    private void viewZ() {
        camera.position.set(0f, 0f, 100f);
        camera.lookAt(0, 0, 0);
        camera.up.set(new Vector3(0, 1, 0));
        camera.near = -3000f;
        camera.far = 3000f;
        camera.update();
    }

    private void viewY() {
        camera.position.set(0f, 100f, 0f);
        camera.lookAt(0, 0, 0);
        camera.up.set(new Vector3(0, 0, -1));
        camera.near = -3000f;
        camera.far = 3000f;
        camera.update();
    }

    private void viewX() {
        camera.position.set(100f, 0f, 0f);
        camera.lookAt(0, 0, 0);
        camera.up.set(new Vector3(0, 1, 0));
        camera.near = -3000f;
        camera.far = 3000f;
        camera.update();
    }

    private void resetView() {
        camera.position.set(100f, 100f, 100f);
        camera.lookAt(0, 0, 0);
        camera.up.set(new Vector3(0, 1, 0));
        camera.near = -3000f;
        camera.far = 3000f;
        camera.update();
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        clickX = screenX;
        clickY = screenY;
        rotateX = 0;
        rotateY = 0;
        clickedButton = button;
        if (clickedButton == Input.Buttons.LEFT) {
            // TODO: Select objects
            return true;
        }
        position = new Vector3(camera.position);
        direction = new Vector3(camera.direction);
        up = new Vector3(camera.up);
        cross = new Vector3();
        cross = cross.add(up);
        cross = cross.crs(direction);
        pickingRay = camera.getPickRay(clickX, clickY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        clickedButton = -1;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dx = screenX - clickX;
        dy = screenY - clickY;
        switch (clickedButton) {
            case Input.Buttons.RIGHT:
                move(dx, dy);
                break;
            case Input.Buttons.MIDDLE:
                rotate(dx, dy);
                break;
            default:
        }
        return true;
    }

    private void rotate(int dx, int dy) {
        rotateX = rotateX - dx;
        rotateY = rotateY - dy;
        camera.rotateAround(centerOfRotation, camera.up, rotateX / rotateFactor);
        up = new Vector3(camera.up);
        direction = new Vector3(camera.direction);
        cross = up.crs(direction);
        camera.rotateAround(centerOfRotation, cross, -rotateY / rotateFactor);
        rotateX = dx;
        rotateY = dy;

    }

    private void move(int dx, int dy) {
        newPosition = new Vector3(position);
        up = new Vector3(camera.up);
        cross = new Vector3();
        cross = cross.add(up);
        cross = cross.crs(direction);
        up.scl(dy);
        up.scl(camera.viewportHeight / (float) Gdx.graphics.getHeight());
        cross.scl(dx);
        cross.scl(camera.viewportWidth / (float) Gdx.graphics.getWidth());
        newPosition = newPosition.add(up);
        newPosition = newPosition.add(cross);
        camera.position.set(newPosition);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        return true;
    }
}
