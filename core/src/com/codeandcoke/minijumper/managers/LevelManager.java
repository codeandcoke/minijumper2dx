package com.codeandcoke.minijumper.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.codeandcoke.minijumper.characters.Player;
import com.codeandcoke.minijumper.characters.items.Balloon;
import com.codeandcoke.minijumper.characters.items.EnemyGenerator;
import com.codeandcoke.minijumper.util.Constants;
import com.codeandcoke.minijumper.characters.enemies.BigStone;
import com.codeandcoke.minijumper.characters.enemies.Enemy;
import com.codeandcoke.minijumper.characters.enemies.GrayEnemy;
import com.codeandcoke.minijumper.characters.enemies.GreenEnemy;
import com.codeandcoke.minijumper.characters.enemies.YellowEnemy;
import com.codeandcoke.minijumper.characters.items.Goal;
import com.codeandcoke.minijumper.characters.items.Item;

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
    public Batch batch;

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
        batch = mapRenderer.getBatch();

        // Crea el jugador y lo posiciona al inicio de la pantalla
        spriteManager.player = new Player(0, 0, 3, new Vector2(0, 0));
        // posición inicial del jugador
        spriteManager.player.position.set(0 * Constants.TILE_WIDTH, 10 * Constants.TILE_HEIGHT);

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

        spriteManager.player.position.set(0 * Constants.TILE_WIDTH, 10 * Constants.TILE_HEIGHT);
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
            spriteManager.music = ResourceManager.getMusic(Constants.MUSIC + "bso.mp3");
            spriteManager.music.setLooping(true);
            spriteManager.music.setVolume(.2f);
            spriteManager.music.play();

            ResourceManager.getSound(Constants.SOUND + "game_begin.wav").play();
        }
    }

    /**
     * Carga los enemigos del TiledMap al juego
     */
    public void loadEnemies() {

        Enemy enemy = null;
        // Carga los objetos "enemigo" del TiledMap
        for (MapObject object : map.getLayers().get("objects").getObjects()) {
            if (object instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tile = (TiledMapTileMapObject) object;
                if (tile.getProperties().containsKey("enemy")) {
                    String enemyType = (String) tile.getProperties().get("enemy");
                    switch (enemyType) {
                        case "green":
                            enemy = new GreenEnemy(tile.getX(), tile.getY(), 1, new Vector2(-Constants.ENEMY_SPEED, 0));
                            break;
                        case "yellow":
                            enemy = new YellowEnemy(tile.getX(), tile.getY(), 1, new Vector2(-Constants.ENEMY_SPEED, 0));
                            break;
                        case "gray":
                            enemy = new GrayEnemy(tile.getX(), tile.getY(), 1, new Vector2(-Constants.ENEMY_SPEED, 0));
                            break;
                        default:
                            enemy = new GreenEnemy(tile.getX(), tile.getY(), 1, new Vector2(-Constants.ENEMY_SPEED, 0));
                            break;
                    }
                    spriteManager.enemies.add(enemy);
                }
                else if (tile.getProperties().containsKey("generator")) {
                    String enemyType = (String) tile.getProperties().get("generator");
                    long rate = Long.parseLong((String) tile.getProperties().get("rate"));
                    spriteManager.items.add(new EnemyGenerator(tile.getX(), tile.getY(), enemyType, rate));
                }
                else if (tile.getProperties().containsKey("big_stone")) {
                    spriteManager.enemies.add(new BigStone(tile.getX(), tile.getY(), new Vector2(-3, -3)));
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
            if (object instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tile = (TiledMapTileMapObject) object;
                if (tile.getProperties().containsKey("item")) {
                    String itemType = (String) tile.getProperties().get("item");
                    if (itemType.equals("balloon")) {
                        int score = Integer.parseInt((String) tile.getProperties().get("score"));

                        item = new Balloon(tile.getX(), tile.getY(), score);
                        spriteManager.items.add(item);
                    }
                } else if (tile.getProperties().containsKey("goal")) {
                    spriteManager.goal = new Goal(tile.getX(), tile.getY());
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
