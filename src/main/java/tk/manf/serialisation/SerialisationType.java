package tk.manf.serialisation;

import tk.manf.serialisation.handler.SerialisationHandler;
import tk.manf.serialisation.handler.flatfile.YAMLSerialisationHandler;
/**
 * 
 * 
 */
public enum SerialisationType {
    FLATFILE_YAML(new YAMLSerialisationHandler(3));

    private SerialisationType(SerialisationHandler handler) {
        this.handler = handler;
    }

    public SerialisationHandler getHandler() {
        return handler;
    }
    
    public void setSerialisationHandler(SerialisationHandler handler) {
        this.handler = handler;
    }  
    
    private SerialisationHandler handler;
}
