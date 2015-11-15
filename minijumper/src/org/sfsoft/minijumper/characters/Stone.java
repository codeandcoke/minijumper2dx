package org.sfsoft.minijumper.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.sfsoft.minijumper.managers.ResourceManager;

/**
 * Created by Santi on 15/02/15.
 */
public class Stone extends SpriteAnimation {

    public Stone(float x, float y) {
        super(x, y);
        currentFrame = new TextureRegion(ResourceManager.getRegion("stone").getTexture());
    }

    @Override
    public void update(float dt) {

        stateTime += dt;
        currentFrame.setU(stateTime);
        currentFrame.setV(stateTime);

    }

    @Override
    public void die() {

    }

    @Override
    public void resurrect() {

    }
}
