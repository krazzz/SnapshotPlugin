/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roseurobank.SnapshotPlugin;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class listener for JCheckBox. Main purposes are: 1.Change state of
 * SnapshotPlugin. 2.Read JTextField's and transfer values to SnapshotCreator.
 * Values are save directory path and duration.
 *
 * @author evgeniy
 */
public class StateListener implements ItemListener {
    
    private JTextField saveDirectoryField;
    private JTextField durationField;
    private JTextArea console;
    private final SnapshotCreator snapshotCreator;
    private Thread executeThread;
    
    public StateListener(JTextField saveFilePath, JTextField duration, SnapshotCreator creater, JTextArea textArea) {
        saveDirectoryField = saveFilePath;
        durationField = duration;
        snapshotCreator = creater;
        console = textArea;
        snapshotCreator.setConsole(console);
    }

    /**
     * Methods start and stop snapshot creator. If selected then start snapsho
     * creator.
     *
     * @param e - JCheckBox event.
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange(); //SELECTED = 1
        if (state == 1 && !snapshotCreator.isActive()) {
            console.append("[INFO] " + new Date() + " Activating snapshot plugin.\n");
            startSnapshotCreator();
        } else {
            console.append("[INFO] " + new Date() + " Deactivating snapshot plugin.\n");
            stopSnapshotCreator();
        }
    }

    /**
     * Method reads fields values and start snapshot creator in new Thread.
     */
    private void startSnapshotCreator() {
        try {
            String saveDirectory = saveDirectoryField.getText().trim();
            int duration = Integer.parseInt(durationField.getText().trim());
            snapshotCreator.setActive(true);
            if (!saveDirectory.equalsIgnoreCase("")) {
                snapshotCreator.setSaveFolder(saveDirectory);
            }
            if (duration != 0) {
                snapshotCreator.setDuration(duration);
            }
            executeThread = new Thread(snapshotCreator);
            executeThread.start();
        } catch (NumberFormatException ex) {
            console.append("[ERROR] " + new Date() + " Wrong number format in duration field.\n");
        }
    }

    /**
     * Method stops snapshot ctreator.
     */
    private void stopSnapshotCreator() {
        snapshotCreator.setActive(false);
    }

    /**
     * @return the save directory path field.
     */
    public JTextField getSaveDirectoryField() {
        return saveDirectoryField;
    }

    /**
     * @param saveDirectoryField the saveDirectoryField to set
     */
    public void setSaveDirectoryField(JTextField saveDirectoryField) {
        this.saveDirectoryField = saveDirectoryField;
    }

    /**
     * @return the duration field.
     */
    public JTextField getDurationField() {
        return durationField;
    }

    /**
     * @param durationField the durationField to set
     */
    public void setDurationField(JTextField durationField) {
        this.durationField = durationField;
    }
    
}
