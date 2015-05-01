/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roseurobank.SnapshotPlugin;

import com.sun.tools.visualvm.application.Application;
import com.sun.tools.visualvm.core.ui.DataSourceView;
import com.sun.tools.visualvm.core.ui.DataSourceViewProvider;
import com.sun.tools.visualvm.core.ui.DataSourceViewsManager;

/**
 * Simple provider for Application DataSource.
 * 
 * @author evgeniy
 */
public class SnapshotPluginViewProvider extends DataSourceViewProvider<Application> {

    private static DataSourceViewProvider<Application> instance = new SnapshotPluginViewProvider();

    /**
     * 
     * @param application - Java application
     * @return View state. True - always shown.
     */
    @Override
    public boolean supportsViewFor(Application application) {
        //Always shown:
        return true;
    }

    /**
     * Method creates view instance.
     * 
     * @param application - Java application.
     * @return cteated SnapshotPluginView.
     */
    @Override
    public synchronized DataSourceView createView(final Application application) {
        return new SnapshotPluginView(application);

    }

    /**
     * Method initialize plugin.
     */
    static void initialize() {
        DataSourceViewsManager.sharedInstance().addViewProvider(instance, Application.class);
    }

    /**
     * Method uninstall plugin.
     */
    static void unregister() {
        DataSourceViewsManager.sharedInstance().removeViewProvider(instance);
    }

}
