package net.minecraft.src;

import java.util.Random;

public class EntityAIOcelotSit extends EntityAIBase
{
    private final EntityOcelot theOcelot;
    private final float field_50083_b;
    private int field_50084_c;
    private int field_52011_h;
    private int field_50081_d;
    private int field_50082_e;
    private int field_50079_f;
    private int field_50080_g;

    public EntityAIOcelotSit(EntityOcelot par1EntityOcelot, float par2)
    {
        field_50084_c = 0;
        field_52011_h = 0;
        field_50081_d = 0;
        field_50082_e = 0;
        field_50079_f = 0;
        field_50080_g = 0;
        theOcelot = par1EntityOcelot;
        field_50083_b = par2;
        setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return theOcelot.isTamed() && !theOcelot.isSitting() && theOcelot.getRNG().nextDouble() <= 0.0065000001341104507D && func_50077_h();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return field_50084_c <= field_50081_d && field_52011_h <= 60 && func_50078_a(theOcelot.worldObj, field_50082_e, field_50079_f, field_50080_g);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theOcelot.getNavigator().tryMoveToXYZ((double)(float)field_50082_e + 0.5D, field_50079_f + 1, (double)(float)field_50080_g + 0.5D, field_50083_b);
        field_50084_c = 0;
        field_52011_h = 0;
        field_50081_d = theOcelot.getRNG().nextInt(theOcelot.getRNG().nextInt(1200) + 1200) + 1200;
        theOcelot.func_50008_ai().setIsSitting(false);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theOcelot.setSitting(false);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        field_50084_c++;
        theOcelot.func_50008_ai().setIsSitting(false);

        if (theOcelot.getDistanceSq(field_50082_e, field_50079_f + 1, field_50080_g) > 1.0D)
        {
            theOcelot.setSitting(false);
            theOcelot.getNavigator().tryMoveToXYZ((double)(float)field_50082_e + 0.5D, field_50079_f + 1, (double)(float)field_50080_g + 0.5D, field_50083_b);
            field_52011_h++;
        }
        else if (!theOcelot.isSitting())
        {
            theOcelot.setSitting(true);
        }
        else
        {
            field_52011_h--;
        }
    }

    private boolean func_50077_h()
    {
        int i = (int)theOcelot.posY;
        double d = 2147483647D;

        for (int j = (int)theOcelot.posX - 8; (double)j < theOcelot.posX + 8D; j++)
        {
            for (int k = (int)theOcelot.posZ - 8; (double)k < theOcelot.posZ + 8D; k++)
            {
                if (!func_50078_a(theOcelot.worldObj, j, i, k) || !theOcelot.worldObj.isAirBlock(j, i + 1, k))
                {
                    continue;
                }

                double d1 = theOcelot.getDistanceSq(j, i, k);

                if (d1 < d)
                {
                    field_50082_e = j;
                    field_50079_f = i;
                    field_50080_g = k;
                    d = d1;
                }
            }
        }

        return d < 2147483647D;
    }

    private boolean func_50078_a(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockId(par2, par3, par4);
        int j = par1World.getBlockMetadata(par2, par3, par4);

        if (i == Block.chest.blockID)
        {
            TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);

            if (tileentitychest.numUsingPlayers < 1)
            {
                return true;
            }
        }
        else
        {
            if (i == Block.stoneOvenActive.blockID)
            {
                return true;
            }

            if (i == Block.bed.blockID && !BlockBed.isBlockFootOfBed(j))
            {
                return true;
            }
        }

        return false;
    }
}
