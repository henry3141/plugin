import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowTNTTask extends BukkitRunnable {
    private final ArrowTNTPlugin plugin;

    public ArrowTNTTask(ArrowTNTPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // Get a list of all arrow entities in the world
        Collection<Arrow> arrows = plugin.getServer().getWorlds().stream()
                .flatMap(world -> world.getEntitiesByClass(Arrow.class).stream())
                .collect(Collectors.toList());

        // Spawn TNT at the location of each arrow
        arrows.forEach(arrow -> {
            Location arrowLocation = arrow.getLocation();
            arrowLocation.getWorld().spawn(arrowLocation, TNTPrimed.class);
        });
    }
}

public class ArrowTNTPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Schedule the repeating task to run every tick
        new ArrowTNTTask(this).runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        // Unregister any events or commands here
    }
}