package tk.manf.serialisation.handler.flatfile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import sun.misc.Unsafe;
import tk.manf.serialisation.annotations.Property;
import tk.manf.serialisation.annotations.Unit;
import tk.manf.serialisation.handler.SerialisationHandler;

public class YAMLSerialisationHandler implements SerialisationHandler {
    private static HashMap<String, FileConfiguration> cache;

    public YAMLSerialisationHandler(int opacity) {
        cache = new HashMap<String, FileConfiguration>(opacity);
    }

    public void save(Unit unit, File folder, String id) throws IOException {
        FileConfiguration config = loadConfig(unit, folder, id);
        File f = new File(folder, unit.name());
        if (!unit.isStatic()) {
            f = new File(f, id);
        }
        config.save(f);
        cache.remove(unit.isStatic() ? unit.name() : unit.name() + "-" + id);
    }

    public void save(Unit unit, File folder, String id, String key, Object value) {
        FileConfiguration config = loadConfig(unit, folder, id);
        config.set(key, value);
        cacheConfig(unit.isStatic(), id, unit.name(), config);
    }

    public Object[] load(Unit unit, File folder) throws IllegalAccessException, InstantiationException {
        FileConfiguration config;
        Object[] tmp;
        if (unit.isStatic()) {
            config = loadConfig(folder, unit.name(), unit.name());
            tmp = new Object[1];
            tmp[0] = toObject(unit.getClass(), config);
        } else {
            File f = new File(folder, unit.name());
            tmp = null;
        }
        return tmp;

    }

    /**
     * Loads the Config
     * <p/>
     * @param folder
     * @param id
     * @param name
     *               <p/>
     * @return
     */
    private static FileConfiguration loadConfig(Unit unit, File folder, String id) {
        if (unit.isStatic()) {
            return loadConfig(folder, unit.name(), unit.name());
        }
        return loadConfig(new File(folder, unit.name()), unit.name() + "-" + id, id);
    }

    /**
     * Loads given Config
     * <p/>
     * @param folder
     * @param id
     * @param name
     *               <p/>
     * @return
     */
    private static FileConfiguration loadConfig(File folder, String id, String name) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        return YamlConfiguration.loadConfiguration(new File(folder, name));
    }

    /**
     * Saves non-static Config
     * <p/>
     * @param id
     * @param name
     * @param config
     */
    private static void cacheConfig(boolean isStatic, String id, String name, FileConfiguration config) {
        if (isStatic) {
            cacheConfig(name, config);
        } else {
            cacheConfig(name + "-" + id, config);
        }
    }

    /**
     * Saves static Config
     * <p/>
     * @param id
     * @param config
     */
    private static void cacheConfig(String id, FileConfiguration config) {
        if (cache.containsKey(id)) {
            return;
        }
        cache.put(id, config);
    }

    private static Object toObject(Class<?> c, FileConfiguration config) throws InstantiationException, IllegalAccessException {
        Object o = Unsafe.getUnsafe().allocateInstance(c);
        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);
            Property prop = f.getAnnotation(Property.class);
            if (prop == null) {
                continue;
            }
            f.set(o, config.get(prop.name().length() == 0 ? f.getName() : prop.name()));
            f.setAccessible(false);
        }
        return null;
    }
}