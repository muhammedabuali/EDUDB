package transcations;

/**
 * Created by mohamed on 5/21/14.
 */
public class DBConfig {
    private static int MinimumEmptyBufferSlots;
    private static int MaximumUsedBufferSlots = 5;
    private static String LogFile;

    public static int getMaximumUsedBufferSlots() {
        return MaximumUsedBufferSlots;
    }

    public static int getMinimumEmptyBufferSlots() {
        return MinimumEmptyBufferSlots;
    }
}
