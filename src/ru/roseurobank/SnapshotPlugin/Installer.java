/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roseurobank.SnapshotPlugin;

import org.openide.modules.ModuleInstall;

/**
 * Class plugin installer.
 * 
 * @author evgeniy
 */
public class Installer extends ModuleInstall {
    public Installer() {
    }

    /**
     * This method install plugin into visualVM.
     */
    @Override
    public void restored() {
        SnapshotPluginViewProvider.initialize();
    }

    /**
     * This method uninstall pugin from VisualVM.
     */
    @Override
    public void uninstalled() {
        SnapshotPluginViewProvider.unregister();
    }
}
