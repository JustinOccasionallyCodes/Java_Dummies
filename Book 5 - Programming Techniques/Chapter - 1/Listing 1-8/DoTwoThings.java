import java.util.concurrent.ScheduledThreadPoolExecutor;
public class DoTwoThings
{
    ScheduledThreadPoolExecutor pool =
        new ScheduledThreadPoolExecutor(2);
    CountDownClock clock = new CountDownClock();
    public static void main(String[] args)
    {
        new DoTwoThings();
    }
    DoTwoThings()
    {
        pool.execute(clock);
        pool.execute(clock);
        pool.shutdown();
    }
}
