package net.minecraft.src;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
    EntityTameable entityOwner;
    EntityLiving field_48393_b;

    public EntityAIOwnerHurtByTarget(EntityTameable par1EntityTameable)
    {
        super(par1EntityTameable, 32F, false);
        entityOwner = par1EntityTameable;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!entityOwner.isTamed())
        {
            return false;
        }

        EntityLiving entityliving = entityOwner.getOwner();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            field_48393_b = entityliving.getAITarget();
            return isSuitableTarget(field_48393_b, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        taskOwner.setAttackTarget(field_48393_b);
        super.startExecuting();
    }
}
