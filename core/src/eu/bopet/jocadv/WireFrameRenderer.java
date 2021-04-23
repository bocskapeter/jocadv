package eu.bopet.jocadv;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;

public class WireFrameRenderer extends ModelInstance {
    public WireFrameRenderer(Model model) {
        super(model);
    }

    @Override
    public Renderable getRenderable(Renderable out, Node node, NodePart nodePart) {
        super.getRenderable(out, node, nodePart);
        out.meshPart.primitiveType = GL20.GL_LINES;
        return out;
    }
}
