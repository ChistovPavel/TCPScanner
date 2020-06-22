package net.scanner;

import org.javatuples.Triplet;
import org.junit.Assert;
import org.junit.Test;

public class ScanTaskTest extends Assert {

    @Test(expected = NullPointerException.class)
    public void testNull()
    {
        ScanTask task = new ScanTask(null, null, null);
        Triplet<String, Integer, Boolean> result = task.call();
        Triplet<String, Integer, Boolean> expected = new Triplet<String, Integer, Boolean>(null, null, false);

        assertEquals(expected, result);
    }

    @Test
    public void testWrongHost()
    {
        ScanTask task = new ScanTask("", 0, 5000);
        Triplet<String, Integer, Boolean> result = task.call();
        Triplet<String, Integer, Boolean> expected = new Triplet<String, Integer, Boolean>("", 0, false);

        assertEquals(expected, result);
    }

    @Test
    public void testNotNull()
    {
        ScanTask task = new ScanTask("172.217.22.46", 80, 5000);
        Triplet<String, Integer, Boolean> result = task.call();
        Triplet<String, Integer, Boolean> expected = new Triplet<String, Integer, Boolean>("172.217.22.46", 80, true);

        assertEquals(expected, result);
    }

}
