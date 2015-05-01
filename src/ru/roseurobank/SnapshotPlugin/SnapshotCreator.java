/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roseurobank.SnapshotPlugin;

import com.sun.tools.visualvm.application.Application;
import com.sun.tools.visualvm.application.snapshot.ApplicationSnapshot;
import com.sun.tools.visualvm.application.snapshot.ApplicationSnapshotsSupport;
import com.sun.tools.visualvm.core.datasource.Storage;
import com.sun.tools.visualvm.core.datasource.descriptor.DataSourceDescriptor;
import com.sun.tools.visualvm.core.datasource.descriptor.DataSourceDescriptorFactory;
import com.sun.tools.visualvm.core.datasupport.Utils;
import com.sun.tools.visualvm.core.explorer.ExplorerSupport;
import com.sun.tools.visualvm.core.snapshot.Snapshot;
import com.sun.tools.visualvm.core.snapshot.SnapshotsContainer;
import com.sun.tools.visualvm.core.snapshot.SnapshotsSupport;
import com.sun.tools.visualvm.core.ui.DataSourceViewsManager;
import com.sun.tools.visualvm.core.ui.DataSourceWindowManager;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JTextArea;

/**
 * Class SnapshotCteator creates Java application snapshots in overrided method
 * run.
 *
 * @author evgeniy
 */
public class SnapshotCreator implements Runnable {

    private Application application;
    private String saveFolder;
    private int duration;
    private volatile boolean active;
    private static final long MILISECONDS_PER_MINUTE = 60000;
    private JTextArea console;

    /**
     * Constructor creates new SnapshotCreator instance and sets params to
     * default (saveFolder = ./, duration = 30 min, active = false).
     *
     * @param NewAllication Java application
     */
    public SnapshotCreator(Application NewAllication) {
        application = NewAllication;
        saveFolder = "./";
        duration = 30;
        active = false;
        console = null;
    }

    /**
     * Method creates snapshot, then sleep while plugin active.
     */
    @Override
    public void run() {
        try {
            long l = convertMinutesToMiliseconds();
            while (active) {
                createSnapshot(application, false);
                Thread.sleep(l);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    /**
     * This method creates Java application snashot and saves it to saveFolder
     * directory. If openSnapshot is true, then open snapshot in VisualVM.
     *
     *
     * @param application Java application for which we will make snapshot
     * @param openSnapshot Open apllication snapshot in VisualVM as new tab
     */
    private void createSnapshot(Application application, boolean openSnapshot) {
        Set<Snapshot> snapshots = application.getRepository().getDataSources(Snapshot.class);
        if (!snapshots.isEmpty() || DataSourceViewsManager.sharedInstance().canSaveViewsFor(application, ApplicationSnapshot.class)) {
            File tempSaveFolder = null;
            synchronized (this) {
                tempSaveFolder = Utils.getUniqueFile(application.getStorage().getDirectory(), ApplicationSnapshotsSupport.getInstance().getCategory().createFileName());
                if (!Utils.prepareDirectory(tempSaveFolder)) {
                    throw new IllegalStateException("Cannot save datasource snapshot " + tempSaveFolder);
                }
            }
            Iterator<Snapshot> descriptor = snapshots.iterator();
            while (descriptor.hasNext()) {
                Snapshot propNames = descriptor.next();

                try {
                    Storage propValues = propNames.getStorage();
                    String storage = "prop_preferred_position";
                    boolean snapshot = propValues.getCustomProperty(storage) != null;
                    if (!snapshot) {
                        int pos = ExplorerSupport.sharedInstance().getDataSourcePosition(propNames);
                        propValues.setCustomProperty(storage, Integer.toString(pos));
                    }

                    propNames.save(tempSaveFolder);
                    if (!snapshot) {
                        propValues.clearCustomProperty(storage);
                        if (!propValues.hasCustomProperties()) {
                            propValues.deleteCustomPropertiesStorage();
                        }
                    }
                } catch (Exception var11) {
                    console.append("[ERROR] " + new Date() + " Error saving snapshot to application snapshot. Stack trace: " + var11 + "\n");
                }
            }
            DataSourceDescriptor descriptor1 = DataSourceDescriptorFactory.getDescriptor(application);
            String[] propNames1 = new String[]{"snapshot_version", "prop_name", "prop_icon"};
            String[] propValues1 = new String[]{"1.0", descriptor1.getName() + getDisplayNameSuffix(application), Utils.imageToString(descriptor1.getIcon(), "png")};
            Storage storage1 = new Storage(tempSaveFolder, "application_snapshot.properties");
            storage1.setCustomProperties(propNames1, propValues1);
            ApplicationSnapshot snapshot1 = new ApplicationSnapshot(tempSaveFolder, storage1);
            DataSourceViewsManager.sharedInstance().saveViewsFor(application, snapshot1);
            SnapshotsContainer.sharedInstance().getRepository().addDataSource(snapshot1);
            if (openSnapshot && DataSourceWindowManager.sharedInstance().canOpenDataSource(snapshot1)) {
                DataSourceWindowManager.sharedInstance().openDataSource(snapshot1);
            }
            console.append("[INFO] " + new Date() + " Snapshot created.\n");
            snapshot1.save(new File(saveFolder));
            console.append("[INFO] " + new Date() + " Snapshot saved to folder " + saveFolder + ".\n");
        }
    }

    /**
     *
     * @return String Java application suffix name.
     */
    private static String getDisplayNameSuffix(Application application) {
        return ", " + SnapshotsSupport.getInstance().getTimeStamp(System.currentTimeMillis());
    }

    /**
     *
     * @return Java application instance
     */
    public Application getApplication() {
        return application;
    }

    /**
     * @param application Java application to set
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Method return directory path where to save snapshots.
     *
     * @return the SaveFolder
     */
    public String getSaveFolder() {
        return saveFolder;
    }

    /**
     * Method set new save folder path.
     *
     * @param saveFolder the saveFolder to set
     */
    public void setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
    }

    /**
     * @return the snapshot create duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Method set create snapshot duration in minutes.
     *
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return current plugin state (true - active)
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Method set plugin state.
     *
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return minutes in miliseconds.
     */
    private long convertMinutesToMiliseconds() {
        return duration * MILISECONDS_PER_MINUTE;
    }

    /**
     * @return the progress console
     */
    public JTextArea getConsole() {
        return console;
    }

    /**
     * @param console the progress console to set
     */
    public void setConsole(JTextArea console) {
        this.console = console;
    }
}
