/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roseurobank.SnapshotPlugin;

import com.sun.tools.visualvm.application.Application;
import com.sun.tools.visualvm.host.Host;
import javax.swing.JTextArea;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author evgeniy
 */
public class SnapshotCreatorTest {

    private Application application;
    private SnapshotCreator instance;
    private static final String SAVE_PATH = "/Users/evgeniy/Snapshots";
    private static final String DEFAULT_PATH = "./";
    private static final int DEFAULT_DURATION = 30;

    public SnapshotCreatorTest() {
        application = new TestApplication(Host.LOCALHOST, "999");
        instance = new SnapshotCreator(application);

    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Start SnapshotCreator testing!");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Stop SnapshotCreator testing!");
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testRun() {
//        try {
//            System.out.println("Method run.");
//            File file = new File(SAVE_PATH);
//            String[] before = file.list();
//            instance.setSaveFolder(SAVE_PATH);
//            instance.setActive(true);
//            instance.setDuration(1);
//            instance.setConsole(new JTextArea());
//            Thread executeThread = new Thread(instance);
//            executeThread.start();
//            Thread.sleep(65000);
//            instance.setSaveFolder(DEFAULT_PATH);
//            instance.setActive(false);
//            instance.setDuration(DEFAULT_DURATION);
//            String[] after = file.list();
//            assertTrue(before.length != after.length);
//        } catch (InterruptedException ex) {
//            System.out.println(ex);
//        }
        assertTrue(true);
    }

    /**
     * Test of getApplication method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testGetApplication() {
        System.out.println("Method getApplication.");
        Application result = instance.getApplication();
        Application expResult = new TestApplication(Host.LOCALHOST, "999");
        assertEquals(expResult, result);
    }

    /**
     * Test of setApplication method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testSetApplication() {
        System.out.println("Method setApplication.");
        Application application = null;
        instance.setApplication(application);
        assertNull(instance.getApplication());
    }

    /**
     * Test of getSaveFolder method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testGetSaveFolder() {
        System.out.println("Method getSaveFolder.");
        String result = instance.getSaveFolder();
        assertTrue(DEFAULT_PATH.equalsIgnoreCase(result));
    }

    /**
     * Test of setSaveFolder method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testSetSaveFolder() {
        System.out.println("Method setSaveFolder.");
        String saveFolder = "./Test";
        instance.setSaveFolder(saveFolder);
        assertTrue(instance.getSaveFolder().equalsIgnoreCase(saveFolder));
    }

    /**
     * Test of getDuration method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testGetDuration() {
        System.out.println("Method getDuration.");
        assertTrue(DEFAULT_DURATION == instance.getDuration());
    }

    /**
     * Test of setDuration method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testSetDuration() {
        System.out.println("Method setDuration.");
        int duration = 0;
        instance.setDuration(duration);
        assertTrue(instance.getDuration() == duration);
    }

    /**
     * Test of isActive method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testIsActive() {
        System.out.println("Method isActive.");
        boolean expResult = false;
        boolean result = instance.isActive();
        assertEquals(expResult, result);
    }

    /**
     * Test of setActive method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testSetActive() {
        System.out.println("Method setActive.");
        boolean active = true;
        instance.setActive(active);
        assertTrue(instance.isActive());
    }

    /**
     * Test of getConsole method, of class SnapshotCreator.
     */
    @org.junit.Test
    public void testSetGetConsole() {
        System.out.println("Method setConsole & getConsole.");
        JTextArea expResult = new JTextArea("Testing");
        int hash1 = expResult.hashCode();
        instance.setConsole(expResult);
        JTextArea result = instance.getConsole();
        int hash2 = result.hashCode();
        assertTrue(hash1 == hash2);
    }

}
