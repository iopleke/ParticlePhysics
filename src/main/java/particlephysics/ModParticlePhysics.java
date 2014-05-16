package particlephysics;

import java.util.logging.Logger;

import net.minecraftforge.common.Configuration;
import particlephysics.gui.GuiHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = ModParticlePhysics.ID, name = ModParticlePhysics.NAME, version = "5.0.3", useMetadata = false, acceptedMinecraftVersions = "[1.6.4,)", dependencies = "required-after:Forge@[9.11.1.953,);after:BuildCraft|Energy;after:factorization;after:IC2;after:Railcraft;after:ThermalExpansion")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels =
{ ModParticlePhysics.CHANNEL_NAME }, packetHandler = PacketHandler.class)
public class ModParticlePhysics
{
    /** Internal mod name used for reference purposes and resource gathering. **/
    public static final String ID = "particlephysics";

    /** Network name that is used in the NetworkMod. **/
    public static final String CHANNEL_NAME = ID;

    /** User friendly version of our mods name. **/
    public static final String NAME = "Particle Physics";

    /** Reference to how many ticks make up a second in Minecraft. **/
    public static final int SECOND_IN_TICKS = 20;

    /** Provides standard logging from the Forge. **/
    public static Logger LOGGER;

    /** Creates blocks and items for Particle Physics. **/
    public static BetterLoader LOADER;

    public static ParticlePhysicsTab CREATIVE_TAB = new ParticlePhysicsTab();

    /** Provides standardized configuration file offered by the Forge. **/
    private static Configuration CONFIG;

    // The instance of your mod that Forge uses.
    @Instance(value = CHANNEL_NAME)
    public static ModParticlePhysics INSTANCE;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "particlephysics.ClientProxy", serverSide = "particlephysics.CommonProxy")
    public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Register instance.
        INSTANCE = this;

        // Setup logging.
        LOGGER = event.getModLog();
        LOGGER.setParent(FMLLog.getLogger());

        // Load configuration.
        LOGGER.info("Loading configuration...");
        CONFIG = new Configuration(event.getSuggestedConfigurationFile());
        Settings.load(CONFIG);

        LOGGER.info("Populating Particle List...");
        ParticleRegistry.populateParticleList();

        LOGGER.info("Creating Better Loader instance...");
        LOADER = new BetterLoader();
        LOADER.loadBlocks();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("Registering custom renderers...");
        PROXY.registerRenderers();

        LOGGER.info("Starting BetterLoader mainLoad()...");
        LOADER.mainload();

        LOGGER.info("Creating NetworkRegistry...");
        NetworkRegistry networkRegistry = NetworkRegistry.instance();

        LOGGER.info("Registering all Entities...");
        ParticleRegistry.registerEntities();

        LOGGER.info("Creating Custom GUI Handler...");
        networkRegistry.registerGuiHandler(this, new GuiHandler());
        
    }
}
