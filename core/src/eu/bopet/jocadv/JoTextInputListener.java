package eu.bopet.jocadv;

import com.badlogic.gdx.Input;

public class JoTextInputListener implements Input.TextInputListener {

    private final JoCADv joCADv;

    public JoTextInputListener(JoCADv joCADv) {
        this.joCADv = joCADv;
    }

    @Override
    public void input(String text) {
        joCADv.userInput(text);
    }

    @Override
    public void canceled() {
    }

}
