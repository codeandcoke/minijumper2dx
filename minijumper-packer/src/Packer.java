import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

/**
 * Clase para crear el textureatlas a partir de las texturas
 *
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Packer {

    public static void main(String[] args) {
        TexturePacker2.Settings settings = new TexturePacker2.Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        settings.filterMag = Texture.TextureFilter.Linear;
        settings.filterMin = Texture.TextureFilter.Linear;

        TexturePacker2.process(settings, "minijumper-desktop/assets/characters", "minijumper-desktop/assets/characters", "minijumper.pack");
    }
}
