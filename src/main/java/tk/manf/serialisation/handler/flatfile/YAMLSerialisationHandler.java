package tk.manf.serialisation.handler.flatfile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.manf.serialisation.SerialisationException;
import tk.manf.serialisation.annotations.InitiationConstructor;
import tk.manf.serialisation.annotations.Parameter;
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

    public <T> List<T> load(Class<T> c, Unit unit, File folder) throws IllegalAccessException, InstantiationException {
        FileConfiguration config;
        List<T> tmp;
        if (unit.isStatic()) {
            tmp = new ArrayList<T>(1);
            // THROW
            try {
                tmp.add(toObject(c, loadConfig(folder, unit.name(), unit.name())));
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(YAMLSerialisationHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(YAMLSerialisationHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialisationException ex) {
                Logger.getLogger(YAMLSerialisationHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            final File saves = new File(folder, unit.name());
            tmp = new ArrayList<T>(saves.listFiles().length);
            for (File f : saves.listFiles()) {
                //THROW
                try {
                    tmp.add(toObject(c, loadConfig(folder, f.getName(), unit.name())));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(YAMLSerialisationHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(YAMLSerialisationHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SerialisationException ex) {
                    Logger.getLogger(YAMLSerialisationHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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

    private static <T> T toObject(Class<T> c, FileConfiguration config) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SerialisationException {
        T o = null;
        for (Constructor<?> constr : c.getConstructors()) {
            if (constr.getAnnotation(InitiationConstructor.class) == null) {
                continue;
            }
            final List<Object> params = new ArrayList<Object>(constr.getParameterTypes().length);
            for (Class<?> type : constr.getParameterTypes()) {
                Parameter param = type.getAnnotation(Parameter.class);
                if (param == null) {
                    params.add(type.isPrimitive() ? type.newInstance() : null);
                    continue;
                }
                params.add(config.get(param.name()));
            }
            o = c.cast(constr.newInstance(params.toArray(new Object[params.size()])));
        }
        if (o == null) {
            throw new SerialisationException("No Object initiated");
        }
        for (Field f : c.getDeclaredFields()) {
            if (f.isAccessible()) {
                Property prop = f.getAnnotation(Property.class);
                if (prop == null) {
                    continue;
                }
                f.set(o, config.get(prop.name().length() == 0 ? f.getName() : prop.name()));
            }
        }
        return o;
    }
}