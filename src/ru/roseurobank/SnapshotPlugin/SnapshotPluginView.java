/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roseurobank.SnapshotPlugin;

import com.sun.tools.visualvm.application.Application;
import com.sun.tools.visualvm.core.ui.DataSourceView;
import com.sun.tools.visualvm.core.ui.components.DataViewComponent;
import java.awt.Color;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.openide.util.Utilities;

/**
 * Class view.
 * @author evgeniy
 */
public class SnapshotPluginView extends DataSourceView {

    private DataViewComponent dvc;
    private static final String IMAGE_PATH = "ru/roseurobank/SnapshotPlugin/snapshot.png";
    private final SnapshotCreator snapshotCreater;

    /**
     * Contructor call super class constructor with 5 arguments. 
     *
     * @param application Java application.
     */
    public SnapshotPluginView(Application application) {
        super(application, "Snapshot plugin", new ImageIcon(Utilities.loadImage(IMAGE_PATH, true)).getImage(), 60, false);
        snapshotCreater = new SnapshotCreator(application);
    }

    /**
     * In this method initialized and arranged all view components.
     * 
     * @return VisualVM View component. 
     */
    @Override
    protected DataViewComponent createComponent() {
        //Initialization of main panel elements
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        JLabel saveFilePath = new JLabel("File save path: ");
        JTextField saveFileText = new JTextField("./");
        JLabel saveDuration = new JLabel("Duration: ");
        JTextField saveDurationText = new JTextField("30");
        JCheckBox activateBox = new JCheckBox("Active");

        //Initialization of console panel elements
        JPanel consolePanel = new JPanel();
        JLabel progressLabel = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTextArea pluginInformationConsole = new JTextArea();
        jScrollPane1.setViewportView(pluginInformationConsole);

        ItemListener listener = new StateListener(saveFileText, saveDurationText, snapshotCreater, pluginInformationConsole);
        activateBox.addItemListener(listener);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(activateBox)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(saveDuration, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                .addComponent(saveFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(saveDurationText)
                                                .addComponent(saveFileText))))
                        .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(saveFilePath)
                                .addComponent(saveFileText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(saveDuration)
                                .addComponent(saveDurationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(activateBox)
                        .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout consolePanelLayout = new javax.swing.GroupLayout(consolePanel);
        consolePanel.setLayout(consolePanelLayout);
        consolePanelLayout.setHorizontalGroup(
                consolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(consolePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(consolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                .addGroup(consolePanelLayout.createSequentialGroup()
                                        .addComponent(progressLabel)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
        );
        consolePanelLayout.setVerticalGroup(
                consolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(consolePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(progressLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                        .addContainerGap())
        );

        //mainPanel.setBorder(BorderFactory.createEmptyBorder(14, 8, 14, 8));
        DataViewComponent.MasterView masterView = new DataViewComponent.MasterView("Snapshot plugin", null, mainPanel);
        DataViewComponent.MasterViewConfiguration masterConfiguration
                = new DataViewComponent.MasterViewConfiguration(false);
        dvc = new DataViewComponent(masterView, masterConfiguration);

        //Add detail views to the component:
        dvc.addDetailsView(new DataViewComponent.DetailsView(
                "Snapshot Plugin Progress Console", null, 10, consolePanel, null), DataViewComponent.BOTTOM_LEFT);

        return dvc;
    }
}
