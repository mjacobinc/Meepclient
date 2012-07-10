package net.minecraft.src;

import java.io.*;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase
{
    public int intArray[];

    public NBTTagIntArray(String par1Str)
    {
        super(par1Str);
    }

    public NBTTagIntArray(String par1Str, int par2ArrayOfInteger[])
    {
        super(par1Str);
        intArray = par2ArrayOfInteger;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeInt(intArray.length);

        for (int i = 0; i < intArray.length; i++)
        {
            par1DataOutput.writeInt(intArray[i]);
        }
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput) throws IOException
    {
        int i = par1DataInput.readInt();
        intArray = new int[i];

        for (int j = 0; j < i; j++)
        {
            intArray[j] = par1DataInput.readInt();
        }
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getId()
    {
        return 11;
    }

    public String toString()
    {
        return (new StringBuilder()).append("[").append(intArray.length).append(" bytes]").toString();
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy()
    {
        int ai[] = new int[intArray.length];
        System.arraycopy(intArray, 0, ai, 0, intArray.length);
        return new NBTTagIntArray(getName(), ai);
    }

    public boolean equals(Object par1Obj)
    {
        if (super.equals(par1Obj))
        {
            NBTTagIntArray nbttagintarray = (NBTTagIntArray)par1Obj;
            return intArray == null && nbttagintarray.intArray == null || intArray != null && intArray.equals(nbttagintarray.intArray);
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ Arrays.hashCode(intArray);
    }
}
