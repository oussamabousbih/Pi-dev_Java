/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author rbaih
 */
public class MyTableViewUtil {

    public static void autoAdjustTableColumn_basedOnSpace(TableView tableView) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    
}
