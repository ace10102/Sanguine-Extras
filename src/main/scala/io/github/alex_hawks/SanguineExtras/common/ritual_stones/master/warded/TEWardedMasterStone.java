package io.github.alex_hawks.SanguineExtras.common.ritual_stones.master.warded;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.common.tileEntity.TEMasterStone;

public class TEWardedMasterStone extends TEMasterStone
{
    private UUID blockOwner;
    
    public UUID getBlockOwner()
    {
        return blockOwner;
    }
    
    public void setBlockOwner(UUID owner)
    {
        this.blockOwner = owner;
    }
    
    public boolean canBreak(EntityPlayer player)
    {
        if (blockOwner == null)
            return true;
        if (player.getPersistentID().equals(blockOwner))
            return true;
        if (this.getOwner().equals(""))
            return true;
        return false;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        if (blockOwner != null) 
        {
            tag.setLong("OwnerMost", blockOwner.getMostSignificantBits());
            tag.setLong("OwnerLeast", blockOwner.getLeastSignificantBits());
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        if (tag.hasKey("OwnerMost") && tag.hasKey("OwnerLeast"))
        {
            this.blockOwner = new UUID(tag.getLong("OwnerMost"), tag.getLong("OwnerLeast"));
        }
    }
    
    @Override
    public void activateRitual(World w, int crystalLevel, ItemStack activationCrystal, EntityPlayer player, String crystalOwner)
    {
        super.activateRitual(w, crystalLevel, activationCrystal, player, crystalOwner);
        
        WMRSHandler.wardRitual(this, player);
    }
}
