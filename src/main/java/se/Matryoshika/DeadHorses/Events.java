package se.Matryoshika.DeadHorses;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events {
	
	private int horseList = -1;
	
	@SubscribeEvent
	public void killHorses(LivingUpdateEvent event){
	    	
    	if(event.getEntity() instanceof EntityHorse){
    		
    		EntityHorse horse = (EntityHorse) event.getEntity();
			
    		if(horse.worldObj.isRemote)
    			return;
			
			if(horse.isSkeletonTrap() || horse.getType() == HorseType.SKELETON){
				
				if(event.getEntity().worldObj.rand.nextInt(DeadHorses.randomScanChance) == 1){
					BlockPos pos = new BlockPos(horse.posX, horse.posY, horse.posZ);
					List<EntityHorse> list = horse.worldObj.getEntitiesWithinAABB(EntityHorse.class, new AxisAlignedBB(pos.getX()-DeadHorses.blockRange, 0, pos.getZ()-DeadHorses.blockRange, pos.getX()+DeadHorses.blockRange, 128, pos.getZ()+DeadHorses.blockRange));
					List<EntityHorse> skeletonList = new ArrayList<EntityHorse>();
				
					for(EntityHorse possibleSkeleton : list){
						if(possibleSkeleton.isSkeletonTrap() || possibleSkeleton.getType() == HorseType.SKELETON)
							skeletonList.add(possibleSkeleton);
					}
					
					if(!skeletonList.isEmpty() && skeletonList.size() > DeadHorses.maxLimit)
						horse.setDead();
				
					list.clear();
					skeletonList.clear();
				}
			}
    	}
    }
}
