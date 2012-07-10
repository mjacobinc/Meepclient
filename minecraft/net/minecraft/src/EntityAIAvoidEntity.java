package net.minecraft.src;

import java.util.List;

public class EntityAIAvoidEntity extends EntityAIBase
{
    /** The entity we are attached to */
    private EntityCreature theEntity;
    private float field_48242_b;
    private float field_48243_c;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private PathEntity field_48238_f;

    /** The PathNavigate of our entity */
    private PathNavigate entityPathNavigate;

    /** The class of the entity we should avoid */
    private Class targetEntityClass;

    public EntityAIAvoidEntity(EntityCreature par1EntityCreature, Class par2Class, float par3, float par4, float par5)
    {
        theEntity = par1EntityCreature;
        targetEntityClass = par2Class;
        distanceFromEntity = par3;
        field_48242_b = par4;
        field_48243_c = par5;
        entityPathNavigate = par1EntityCreature.getNavigator();
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (targetEntityClass == (net.minecraft.src.EntityPlayer.class))
        {
            if ((theEntity instanceof EntityTameable) && ((EntityTameable)theEntity).isTamed())
            {
                return false;
            }

            closestLivingEntity = theEntity.worldObj.getClosestPlayerToEntity(theEntity, distanceFromEntity);

            if (closestLivingEntity == null)
            {
                return false;
            }
        }
        else
        {
            List list = theEntity.worldObj.getEntitiesWithinAABB(targetEntityClass, theEntity.boundingBox.expand(distanceFromEntity, 3D, distanceFromEntity));

            if (list.size() == 0)
            {
                return false;
            }

            closestLivingEntity = (Entity)list.get(0);
        }

        if (!theEntity.getEntitySenses().canSee(closestLivingEntity))
        {
            return false;
        }

        Vec3D vec3d = RandomPositionGenerator.func_48623_b(theEntity, 16, 7, Vec3D.createVector(closestLivingEntity.posX, closestLivingEntity.posY, closestLivingEntity.posZ));

        if (vec3d == null)
        {
            return false;
        }

        if (closestLivingEntity.getDistanceSq(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord) < closestLivingEntity.getDistanceSqToEntity(theEntity))
        {
            return false;
        }

        field_48238_f = entityPathNavigate.getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);

        if (field_48238_f == null)
        {
            return false;
        }

        return field_48238_f.isDestinationSame(vec3d);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !entityPathNavigate.noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        entityPathNavigate.setPath(field_48238_f, field_48242_b);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (theEntity.getDistanceSqToEntity(closestLivingEntity) < 49D)
        {
            theEntity.getNavigator().setSpeed(field_48243_c);
        }
        else
        {
            theEntity.getNavigator().setSpeed(field_48242_b);
        }
    }
}
