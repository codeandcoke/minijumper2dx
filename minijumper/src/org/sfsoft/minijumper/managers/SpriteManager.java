package org.sfsoft.minijumper.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import org.sfsoft.minijumper.MiniJumper;
import org.sfsoft.minijumper.characters.*;
import org.sfsoft.minijumper.characters.enemies.BigStone;
import org.sfsoft.minijumper.characters.animations.Explosion;
import org.sfsoft.minijumper.characters.SpriteAnimation;
import org.sfsoft.minijumper.characters.animations.Stone;
import org.sfsoft.minijumper.characters.enemies.Enemy;
import org.sfsoft.minijumper.characters.enemies.GrayEnemy;
import org.sfsoft.minijumper.characters.enemies.GreenEnemy;
import org.sfsoft.minijumper.characters.enemies.YellowEnemy;
import org.sfsoft.minijumper.characters.items.*;
import org.sfsoft.minijumper.screens.GameOverScreen;

import java.util.Iterator;

import static org.sfsoft.minijumper.util.Constants.*;
import static org.sfsoft.minijumper.util.Constants.ENEMY_HEIGHT;

/**
 * SpriteManager manages logic during the game
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class SpriteManager implements InputProcessor {

    MiniJumper game;
    Player player;
    Array<Enemy> enemies;
    public Array<Item> items;
    Array<SpriteAnimation> animations;
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };
    public Array<Rectangle> tiles;
    Music music;
    LevelManager levelManager;
    CameraManager cameraManager;
    Goal goal = null;

    public SpriteManager(MiniJumper game) {
        this.game = game;

        enemies = new Array<>();
        items = new Array<>();
        animations = new Array<>();
        // Se utiliza para almacenar en cada frame los rectangles de los tiles
        // con los que colisiona el jugador
        tiles = new Array<>();

        Gdx.input.setInputProcessor(this);
    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void update(float dt) {

        if (!player.isDead())
            handleInput();

        updateCharacters(dt);
    }

    /**
     * Controla la entrada por teclado del usuario
     */
    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.velocity.x = PLAYER_SPEED;
            player.state = Player.State.RIGHT;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.velocity.x = - PLAYER_SPEED;
            player.state = Player.State.LEFT;
        }
        else {
            player.velocity.x = 0;
            if (player.state == Player.State.LEFT)
                player.state = Player.State.IDLE_LEFT;
            else if (player.state == Player.State.RIGHT)
                player.state = Player.State.IDLE_RIGHT;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            int xCell = (int) player.position.x / TILE_WIDTH;
            int yCell = (int) player.position.y / TILE_HEIGHT;
            TiledMapTileLayer.Cell cell = levelManager.collisionLayer.getCell(xCell, yCell);
            if ((cell != null) && (cell.getTile().getProperties().containsKey("creeper"))) {
                player.velocity.y = 1f;
                player.state = Player.State.CLIMBING;
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) {
            if (!player.isJumping) {
                player.jump();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.fire();
        }
    }

    /**
     * Actualiza la lógica del juego
     * @param dt
     */
    private void updateCharacters(float dt) {

        updateEnemies(dt);
        updateAnimations(dt);
        updateItems(dt);
        updatePlayer(dt);
    }

    private void updateEnemies(float dt) {

        Enemy enemy;
        Iterator<Enemy> iterEnemies = enemies.iterator();
        while (iterEnemies.hasNext()) {
            enemy = iterEnemies.next();
            // Si la cámara no lo enfoca y aún no ha salido me lo salto
            if (!cameraManager.camera.frustum.pointInFrustum(new Vector3(enemy.position.x, enemy.position.y, 0)) && (enemy.stateTime == 0))
                continue;

            enemy.update(dt, this);
            // An enemy fall
            if (enemy.position.y <= 0) {
                iterEnemies.remove();
                continue;
            }

            // An enemy reach left side of the screen
            if (enemy.position.x <= -ENEMY_WIDTH) {
                iterEnemies.remove();
                continue;
            }

            // El jugador cae encima de un enemigo
            if (!player.isDead()) {
                if (enemy.rect.overlaps(player.rect)) {
                    if (enemy instanceof BigStone) {
                        music.stop();
                        player.die();
                    }
                    else {
                        if (player.position.y > enemy.position.y + ENEMY_HEIGHT / 2) {
                            enemy.lives--;
                            if (enemy.lives == 0) {
                                killEnemy(enemy);
                                iterEnemies.remove();
                            }
                            player.jump();
                        } else {
                            music.stop();
                            player.die();
                        }
                    }
                }
            }
        }
    }

    /**
     * Update animations during the game
     * @param dt
     */
    private void updateAnimations(float dt) {
        SpriteAnimation animation;
        Iterator<SpriteAnimation> iterAnimations = animations.iterator();
        while (iterAnimations.hasNext()) {
            animation = iterAnimations.next();
            animation.update(dt);
            if (animation instanceof Explosion) {
                Explosion explosion = (Explosion) animation;
                if (explosion.isDead())
                    iterAnimations.remove();

            }
            else if (animation instanceof Stone) {
                Enemy enemy = null;
                Stone stone = (Stone) animation;
                boolean enemyKilled = false;

                Iterator<Enemy> iterEnemies = enemies.iterator();
                while (iterEnemies.hasNext()) {
                    enemy = iterEnemies.next();
                    if (enemy.rect.overlaps(stone.rect)) {
                        killEnemy(enemy);
                        iterEnemies.remove();
                        enemyKilled = true;
                    }
                }

                // Because of a stone can kill several enemies, stone is removed after checking all the enemies
                if (enemyKilled)
                    iterAnimations.remove();
            }
        }
    }

    /**
     * Kill an enemy after the player throw a stone or jump over him
     * @param enemy
     */
    private void killEnemy(Enemy enemy) {

        String explosionType = null;
        if (enemy instanceof GreenEnemy)
            explosionType = "green_pop";
        else if (enemy instanceof YellowEnemy)
            explosionType = "yellow_pop";
        else if (enemy instanceof GrayEnemy)
            explosionType = "gray_pop";

        Explosion explosion = new Explosion(enemy.position.x, enemy.position.y, explosionType);
        animations.add(explosion);
        ResourceManager.getSound(SOUND + "shot.mp3").play();
        // Randomly, a rainbow can appear when the player kills an enemy
        int random = MathUtils.random(0, 10);
        if (random < 2) {
            Rainbow rainbow = new Rainbow(enemy.position.x, enemy.position.y);
            items.add(rainbow);
        }
    }

    /**
     * Actualiza el jugador en la partida
     * @param dt
     */
    private void updateItems(float dt) {

        Item item;
        Iterator<Item> iterItems = items.iterator();
        while (iterItems.hasNext()) {
            item = iterItems.next();
            if (item instanceof Balloon) {
                if (item.rect.overlaps(player.rect)) {
                    player.score += item.score;
                    iterItems.remove();
                    if (ConfigurationManager.isSoundEnabled())
                        ResourceManager.getSound(SOUND + "blop.mp3").play();
                }
            }
            else if (item instanceof EnemyGenerator) {
                // EnemyGenerator only generate enemies if camera is showing it
                if (cameraManager.camera.frustum.pointInFrustum(new Vector3(item.position.x, item.position.y, 0))) {
                    EnemyGenerator enemyGenerator = (EnemyGenerator) item;
                    if ((TimeUtils.millis() - enemyGenerator.lastEnemy) > enemyGenerator.rate) {
                        enemies.add(enemyGenerator.generate());
                    }
                }
            }
            else if (item instanceof Rainbow) {
                item.update(dt);
                if (((Rainbow) item).hasDisappear())
                    iterItems.remove();
            }
        }
    }

    private void updatePlayer(float dt) {
        player.update(dt, this);

        if (goal != null) {
            if (player.rect.overlaps(goal.rect)) {
                // TODO player ends current level (if he has take all the balloons)
                if (levelManager.getBalloonCount() == 0) {

                }
            }
        }

        // Comprueba cuando reiniciar la partida después de morir el jugador
        if ((player.isDead()) && (player.position.y < -PLAYER_HEIGHT))
            if (player.lives > 0)
                levelManager.restartCurrentLevel();
            else
                game.setScreen(new GameOverScreen(game));
    }

    /**
     * Genera los rectángulos de colisión de los tiles con los que colisiona un personaje en cada momento
     * @param startX
     * @param endX
     * @param startY
     * @param endY
     */
    public void getCollisionTiles(org.sfsoft.minijumper.characters.Character character, int startX, int endX, int startY, int endY) {

        tiles.clear();

        int xCell, yCell;
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                xCell = x / TILE_WIDTH;
                yCell = y / TILE_HEIGHT;
                TiledMapTileLayer.Cell cell = levelManager.collisionLayer.getCell(xCell, yCell);
                if ((cell != null) && (cell.getTile().getProperties().containsKey("blocked"))) {
                    Rectangle rect = rectPool.obtain();
                    if (character.velocity.y > 0) {
                        if (!(cell.getTile().getProperties().get("blocked").equals("down")))
                            rect.set(x, y, 1, 1);
                    }
                    else
                        rect.set((int) (Math.ceil(x / TILE_WIDTH) * TILE_WIDTH), (int) (Math.ceil(y / TILE_HEIGHT) * TILE_HEIGHT), TILE_WIDTH, TILE_WIDTH);

                    tiles.add(rect);

                    if (cell.getTile().getProperties().containsKey("death")) {
                        if (character instanceof Player) {
                            character.die();
                            break;
                        }
                    }
                }
                else if ((cell != null) && (cell.getTile().getProperties().containsKey("spring"))) {
                    if (character.velocity.y < 0) {
                        if (character instanceof Player) {
                            Player player = (Player) character;
                            player.superJump();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.CONTROL_LEFT) {
            if (!player.firing) {
                player.loadFire();
            }
            return true;
        }

        if (keycode == Input.Keys.SHIFT_LEFT) {
            player.throwBird();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.CONTROL_LEFT) {

            if (player.firing) {
                Stone stone = player.fire();
                if (stone != null)
                    animations.add(stone);
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
