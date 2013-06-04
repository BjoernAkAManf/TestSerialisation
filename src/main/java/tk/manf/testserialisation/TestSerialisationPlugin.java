package tk.manf.testserialisation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import tk.manf.serialisation.ObjectSerialiser;
import tk.manf.serialisation.SerialisationException;

public class TestSerialisationPlugin extends JavaPlugin {
    private TestEntity entity;
    private ObjectSerialiser serial;

    @Override
    public void onEnable() {
        entity = new TestEntity("Björn");
        serial = new ObjectSerialiser(this);
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
