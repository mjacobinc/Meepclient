package net.minecraft.src;

class PlayerUsageSnooperThread extends Thread
{
    /** The PlayerUsageSnooper object. */
    final PlayerUsageSnooper snooper;

    PlayerUsageSnooperThread(PlayerUsageSnooper par1PlayerUsageSnooper, String par2Str)
    {
        super(par2Str);
        snooper = par1PlayerUsageSnooper;
    }

    public void run()
    {
        PostHttp.sendPost(PlayerUsageSnooper.getServerURL(snooper), PlayerUsageSnooper.getDataMap(snooper), true);
    }
}
