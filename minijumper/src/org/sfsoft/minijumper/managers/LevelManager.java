package org.sfsoft.minijumper.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.sfsoft.minijumper.characters.*;
import org.sfsoft.minijumper.characters.enemies.BigStone;
import org.sfsoft.minijumper.characters.enemies.Enemy;
import org.sfsoft.minijumper.characters.enemies.GrayEnemy;
import org.sfsoft.minijumper.characters.enemies.GreenEnemy;
import org.sfsoft.minijumper.characters.enemies.YellowEnemy;
import org.sfsoft.minijumper.characters.items.Balloon;
import org.sfsoft.minijumper.characters.items.EnemyGenerator;
import org.sfsoft.minijumper.characters.items.Goal;
import org.sfsoft.minijumper.characters.items.Item;

import static org.sfsoft.minijumper.util.Constants.*;

/**
 * Gestor de niveles
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class LevelManager {

    TiledMap map;
    TiledMapTileLayer collisionLayer;
    MapLayer objectLayer;
    OrthogonalTiledMapRenderer mapRenderer;
    SpriteManager spriteManager;
    CameraManager cameraManager;
    public SpriteBatch batch;

    public LevelManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    /**
     * Carga el nivel actual
     */
    public void loadCurrentLevel() {

        map = new TmxMapLoader().load("levels/level1.tmx");
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("terrain");
        objectLayer = map.getLayers().get("objects");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        batch = mapRenderer.getSpriteBatch();

        // Crea el jugador y lo posiciona al inicio de la pantalla
        spriteManager.player = new Player(0, 0, 3, new Vector2(0, 0));
        // posición inicial del jugador
        spriteManager.player.position.set(0 * TILE_WIDTH, 10 * TILE_HEIGHT);

        loadEnemies();
        loadItems();
        playCurrentLevelMusic();
    }

    /**
     * Reinicia el nivel actual tras morir el jugador
     */
    public void restartCurrentLevel() {
        spriteManager.music.stop();
        cameraManager.init();

        spriteManager.enemies.clear();
        spriteManager.items.clear();
        spriteManager.animations.clear();
        spriteManager.tiles.clear();

        spriteManager.player.position.set(0 * TILE_WIDTH, 10 * TILE_HEIGHT);
        spriteManager.player.resurrect();
        loadEnemies();
        loadItems();
        playCurrentLevelMusic();
    }

    /**
     * Inicia la música del nivel actual
     */
    private void playCurrentLevelMusic() {
        // Musica de fondo durante el juego
        if (ConfigurationManager.isSoundEnabled()) {
            spriteManager.music = ResourceManager.getMusic(MUSIC + "bso.mp3");
            spriteManager.music.setLooping(true);
            spriteManager.music.setVolume(.2f);
            spriteManager.music.play();

            ResourceManager.getSound(SOUND + "game_begin.wav").play();
        }
    }

    /**
     * Carga los enemigos del TiledMap al juego
     */
    public void loadEnemies() {

        Enemy enemy = null;
        // Carga los objetos "enemigo" del TiledMap
        for (MapObject object : map.getLayers().get("objects").getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle rect = rectangleObject.getRectangle();
                if (rectangleObject.getProperties().containsKey("enemy")) {
                    String enemyType = (String) rectangleObject.getProperties().get("enemy");
                    switch (enemyType) {
                        case "green":
                            enemy = new GreenEnemy(rect.x, rect.y, 1, new Vector2(-ENEMY_SPEED, 0));
                            break;
                        case "yellow":
                            enemy = new YellowEnemy(rect.x, rect.y, 1, new Vector2(-ENEMY_SPEED, 0));
                            break;
                        case "gray":
                            enemy = new GrayEnemy(rect.x, rect.y, 1, new Vector2(-ENEMY_SPEED, 0));
                            break;
                        default:
                            enemy = new GreenEnemy(rect.x, rect.y, 1, new Vector2(-ENEMY_SPEED, 0));
                            break;
                    }
                    spriteManager.enemies.add(enemy);
                }
                else if (rectangleObject.getProperties().containsKey("generator")) {
                    String enemyType = (String) rectangleObject.getProperties().get("generator");
                    long rate = Long.parseLong((String) rectangleObject.getProperties().get("rate"));
                    spriteManager.items.add(new EnemyGenerator(rect.x, rect.y, enemyType, rate));
                }
                else if (rectangleObject.getProperties().containsKey("big_stone")) {
                    spriteManager.enemies.add(new BigStone(rect.x, rect.y, new Vector2(-3, -3)));
                }
            }
        }
    }

    /**
     * Carga los items del TiledMap al juego
     */
    private void loadItems() {

        Item item = null;
        // Carga los objetos "enemigo" del TiledMap
        for (MapObject object : map.getLayers().get("objects").getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                if (rectangleObject.getProperties().containsKey("item")) {
                    String itemType = (String) rectangleObject.getProperties().get("item");
                    if (itemType.equals("balloon")) {
                        int score = Integer.parseInt((String) rectangleObject.getProperties().get("score"));
                        Rectangle rect = rectangleObject.getRectangle();

                        item = new Balloon(rect.x, rect.y, score);
                        spriteManager.items.add(item);
                    }
                } else if (rectangleObject.getProperties().containsKey("goal")) {
                    Rectangle rect = rectangleObject.getRectangle();
                    spriteManager.goal = new Goal(rect.x, rect.y);
                }
            }
        }
    }

    public int getBalloonCount() {
        int count = 0;

        for (Item item : spriteManager.items) {
            if (item instanceof Balloon)
                count++;
        }

        return count;
    }
}
