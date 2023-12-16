package top.roozen.bangumi;

import org.springframework.stereotype.Component;
import run.halo.app.extension.SchemeManager;
import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 * @since 2023/7/29
 */
@Component
public class DoubanPlugin extends BasePlugin {

    private final SchemeManager schemeManager;

    public DoubanPlugin(PluginContext context, SchemeManager schemeManager) {
        super(context);
        this.schemeManager = schemeManager;
    }

    @Override
    public void start() {
        this.schemeManager.register(DoubanMovie.class);
    }

    @Override
    public void stop() {
        this.schemeManager.unregister(this.schemeManager.get(DoubanMovie.class));
    }

    @Override
    public void delete() {
        super.delete();
    }
}
