package com.codeandcoke.minijumper.characters.items;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.codeandcoke.minijumper.managers.ResourceManager;

/**
 * A rainbow
 * @author Santiago Faci
 * @version August 2015
 */
public class Rainbow extends Item {

    Animation<TextureRegion> animation = null;
    private float stateTime;
    private boolean disappeared;

    public Rainbow(float x, float y) {
        super(x, y);
        currentFrame = ResourceManager.getRegion("rainbow", 1);
        animation = new Animation<TextureRegion>(0.2f, ResourceManager.getRegions("rainbow"));
        stateTime = 0;
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        currentFrame = animation.getKeyFrame(stateTime, false);

        if (stateTime > 2)
            disappeared = true;
    }

    public boolean hasDisappear() {
        return disappeared;
    }
}
