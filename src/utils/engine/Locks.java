package utils.engine;

/**
 * @author BingoIO_OI233
 */
public class Locks
{
    private static Locks locks = new Locks();
    private SimpleLock[] lockSet;

    private Locks()
    {
        lockSet = new SimpleLock[5];
        for(int i=0;i<5;i++)
        {
            lockSet[i] = new SimpleLock();
        }
    }

    public static Locks getLockSet()
    {
        return locks;
    }

    public SimpleLock getLock(int id)
    {
        return lockSet[id];
    }
}


