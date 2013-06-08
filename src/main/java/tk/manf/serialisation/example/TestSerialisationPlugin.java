package tk.manf.serialisation.example;

import java.util.List;
import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import tk.manf.serialisation.ObjectSerialiser;

/**
 * Example Plugin
 * 
 * @author Björn 'manf' Heinrichs
 */
public class TestSerialisationPlugin extends JavaPlugin {
    private TestEntity entity;
    private ObjectSerialiser serial;

    @Override
    public void onEnable() {
        try {
            serial = new ObjectSerialiser(this);
            List<TestEntity> o = serial.load(TestEntity.class);
            if(o == null || o.isEmpty()) {
                System.out.println("Loading failed!");
                entity = new TestEntity("Björn");
            } else {
                if(o.get(0) instanceof TestEntity) {
                    entity = (TestEntity) o.get(0);
                }
            }
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            try {
                serial.save(entity);
                sender.sendMessage("SAVED!");
                return true;
            } catch (Exception ex) {
                sender.sendMessage(ex.getLocalizedMessage());
                return true;
            }
        }

        if(args[0].equalsIgnoreCase("INFO")) {
            sender.sendMessage(entity.getName() + "->" + entity.getValue());
            return true;
        }
        
        if (args[0].equalsIgnoreCase("SET")) {
            if (args.length > 1) {
                try {
                    entity.setValue(Integer.valueOf(args[1]));
                    sender.sendMessage("SET!");
                    return true;
                } catch (Exception ex) {
                    sender.sendMessage(ex.getLocalizedMessage());
                    return true;
                }
            } else {
                sender.sendMessage("/" + label + " set [Value]");
                return true;
            }
        }
        return true;
    }
}
