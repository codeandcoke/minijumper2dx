package org.sfsoft.minijumper.characters.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import org.sfsoft.minijumper.characters.SpriteAnimation;
import org.sfsoft.minijumper.managers.ResourceManager;

/**
 * Clase para las explosiones de cualquier tipo
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Explosion extends SpriteAnimation {

    private Animation animation;

    public Explosion(float x, float y, String name) {
        super(x, y);
        animation = new Animation(0.15f, ResourceManager.getRegions(name));
    }

    @Override
    public void update(float dt) {

        stateTime += dt;
        currentFrame = animation.getKeyFrame(stateTime);
        if (animation.isAnimationFinished(stateTime))
            die();
    }

    @Override
    public void die() {
        dead = true;
    }

    @Override
    public void resurrect() {
        dead = false;
    }
}
