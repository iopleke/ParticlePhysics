package particlephysics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;

import net.minecraft.launchwrapper.LogWrapper;
import net.minecraftforge.common.Configuration;
import particlephysics.utility.IDManager;

public class Settings
{
    // Modified from Source
    // http://www.minecraftforge.net/wiki/Reference_Mod_File

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgBool
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgId
    {
        public boolean block() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgInt
    {
    }

    // ** Auto-incrementing configuration IDs. Use this to make sure no config ID is the same. **/
    public static final IDManager idManager = new IDManager(3000, 5700);

    public static int getNextBlockID()
    {
        return idManager.getNextBlockID();
    }

    public static int getNextItemID()
    {
        return idManager.getNextItemID();
    }

    // -----
    // ITEMS
    // -----

    public static @CfgId
    int MinechemElement = getNextItemID();
    public static @CfgId
    int MinechemMolecule = getNextItemID();

    // ------
    // BLOCKS
    // ------

    public static @CfgId(block = true)
    int Emitter = getNextBlockID();
    public static @CfgId(block = true)
    int PolarizedGlass = getNextBlockID();
    public static @CfgId(block = true)
    int SeriesReceptor = getNextBlockID();
    public static @CfgId(block = true)
    int ControlGlass = getNextBlockID();
    public static @CfgId(block = true)
    int InfiniteEmitter = getNextBlockID();

    public static void load(Configuration config)
    {
        try
        {
            config.load();
            Field[] fields = Settings.class.getFields();
            for (Field field : fields)
            {
                CfgId annoBlock = field.getAnnotation(CfgId.class);
                CfgBool annoBool = field.getAnnotation(CfgBool.class);
                CfgInt annoInt = field.getAnnotation(CfgInt.class);

                // Config property is block or item.
                if (annoBlock != null && annoBool == null && annoInt == null)
                {
                    int id = field.getInt(null);
                    if (annoBlock.block())
                    {
                        id = config.getBlock(field.getName(), id).getInt();
                    }
                    else
                    {
                        id = config.getItem(field.getName(), id).getInt();
                    }
                    field.setInt(null, id);
                }
                else if (annoBool != null && annoBlock == null && annoInt == null)
                {
                    // Config property is bool.
                    if (field.isAnnotationPresent(CfgBool.class))
                    {
                        boolean bool = field.getBoolean(null);
                        bool = config.get(Configuration.CATEGORY_GENERAL, field.getName(), bool).getBoolean(bool);
                        field.setBoolean(null, bool);
                    }
                }
            }
        }
        catch (Exception e)
        {
            // failed to load config log
            LogWrapper.log(Level.WARNING, "Failed to load configuration file!");
        }
        finally
        {
            config.save();
        }
    }

}