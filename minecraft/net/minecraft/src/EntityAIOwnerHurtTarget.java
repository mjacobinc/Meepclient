package net.minecraft.src;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    EntityTameable theEntityTameable;
    EntityLiving field_48391_b;

    public EntityAIOwnerHurtTarget(EntityTameable par1EntityTameable)
    {
        super(par1EntityTameable, 32F, false);
        theEntityTameable = par1EntityTameable;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!theEntityTameable.isTamed())
        {
            return false;
        }

        EntityLiving entityliving = theEntityTameable.getOwner();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            field_48391_b = entityliving.getLastAttackingEntity();
            return isSuitableTarget(field_48391_b, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        taskOwner.setAttackTarget(field_48391_b);
        super.startExecuting();
    }
}
