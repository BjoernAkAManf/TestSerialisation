package tk.manf.serialisation.handler.flatfile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.manf.serialisation.annotations.Unit;
import tk.manf.serialisation.handler.SerialisationHandler;

public class YAMLSerialisationHandler implements SerialisationHandler {
    private HashMap<String, FileConfiguration> staticCache;
    //private HashMap<String, String> cache;

    public YAMLSerialisationHandler(int opacity) {
        staticCache = new HashMap<String, FileConfiguration>(opacity);
        //cache = new HashMap<String, String>(opacity);
    }

    public void save(Unit unit, File dataFolder, String id) throws IOException {
        if (unit.isStatic()) {
            load(dataFolder, unit.name()).save(new File(dataFolder, unit.name()));
        } else {
            throw new UnsupportedOperationException("Non-Static saving is still impossible");
        }
    }

    public void save(Unit unit, File folder, String id, String name, Object value) {
        FileConfiguration config;
        if (unit.isStatic()) {
            config = load(folder, unit.name());
        } else {
            config = load(folder, unit.name(), id);
        }
        config.set(name, value);
        save(unit.name(), config);

    }

    private FileConfiguration load(File folder, String name) {
        if (staticCache.containsKey(name)) {
            return staticCache.get(name);
        }
        return YamlConfiguration.loadConfiguration(new File(folder, name));
    }

    private FileConfiguration load(File folder, String name, String id) {
        return load(new File(folder, name), id);
    }

    private void save(String name, FileConfiguration config) {
        if (staticCache.containsKey(name)) {
            return;
        }
        staticCache.put(name, config);
    }
}
