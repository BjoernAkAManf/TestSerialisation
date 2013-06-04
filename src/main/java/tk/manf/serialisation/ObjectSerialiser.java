package tk.manf.serialisation;

import java.io.File;
import java.io.IOException;
import tk.manf.serialisation.annotations.Unit;
import tk.manf.serialisation.annotations.Property;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import tk.manf.serialisation.annotations.Identification;
import tk.manf.serialisation.handler.SerialisationHandler;

public class ObjectSerialiser {
    private File dataFolder;
    private Logger logger;
    
    public ObjectSerialiser(JavaPlugin plugin) {
        this.dataFolder = plugin.getDataFolder();
        this.logger = plugin.getLogger();
    }

    public void save(Object o) throws IllegalArgumentException, IllegalAccessException, IOException, SerialisationException {
        save(o, false);
    }

    public void save(Object o, boolean warn) throws IllegalArgumentException, IllegalAccessException, IOException, SerialisationException {
        Unit unit = o.getClass().getAnnotation(Unit.class);
        if (unit == null) {
            return;
        }
        SerialisationHandler handler = unit.handler().getHandler();
        String id = null;
        for (Field f : o.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (!unit.isStatic() && id == null) {
                if (f.getAnnotation(Identification.class) != null) {
                    id = f.get(o).toString();
                    if (!Modifier.isFinal(f.getModifiers())) {
                        if (warn) {
                            logger.log(Level.INFO, "Identifikation {0} is not final!", f.getName());
                        }
                    }
                } else {
                    throw new SerialisationException("Identification not found! Found " + f.getName());
                }
            }
            Property prop = f.getAnnotation(Property.class);
            /*
             * NASTY BLUB CODE
             */
            if (prop == null) {
                if (warn) {
                    logger.log(Level.INFO, "Field of {0} has no Properties({1})", new Object[]{f.getName(), f.getClass().getName()});
                }
                continue;
            }

            if (warn) {
                if (prop.name().length() == 0) {
                    logger.log(Level.INFO, "Field of {0} has no Name using Field name!", f.getName());
                }
            }
            /*
             * END NASTY CODE
             */

            handler.save(unit, dataFolder, id, prop.name().length() == 0 ? f.getName() : prop.name(), f.get(o));
            f.setAccessible(false);
        }
        handler.save(unit, dataFolder, id);
    }
}
