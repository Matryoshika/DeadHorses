package se.Matryoshika.DeadHorses;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DeadHorses.MODID, version = DeadHorses.VERSION, name = DeadHorses.MODID, acceptableRemoteVersions = "*")
public class DeadHorses{
	
    public static final String MODID = "deadhorses";
    public static final String VERSION = "1.0";
    
    private final Events HORSE_KILLING_EVENT = new Events();
    
    public File configDir;
    public Configuration config;
    
    public static int blockRange;
    public static int maxLimit;
    public static int randomScanChance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	configDir = new File(event.getModConfigurationDirectory(), "DeadHorses");
    	readConfigs();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(HORSE_KILLING_EVENT);
    }
    
    
    public void readConfigs(){
    	config = new Configuration(new File(configDir, MODID+".cfg"));
    	
    	config.load();
    	
    	config.addCustomCategoryComment("DeadHorses Configs", "The variables of which this mod will read, and base it's actions upon");
    	blockRange = config.getInt("scanBlockRange", "DeadHorses Configs", 32,  1, 96, "How far out from each Skeleton Horse this mod will scan for more Skeleton Horses");
    	maxLimit = config.getInt("SkeletonHorseMaxLimit", "DeadHorses Configs", 2, 1, 64, "The max amount of Skeleton Horses that may exist within the scanned area");
    	randomScanChance = config.getInt("ChanceToFireScan", "DeadHorses Configs", 1000, 1, 24000, "The chance per tick to scan the area for more Skeleton Horses");
    	
    	if(config.hasChanged())
    		config.save();
    }
    
}
