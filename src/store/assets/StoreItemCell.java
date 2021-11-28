/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.assets;

import javafx.scene.control.ListCell;
import store.StoresItemsController;

/**
 *
 * @author AHMED
 */
public class StoreItemCell extends ListCell<Stores>
{
    @Override
    public void updateItem(Stores string, boolean empty)
    {
        super.updateItem(string,empty);
        if(string != null)
        {
            StoresItemsController data = new StoresItemsController();
            data.setInfo(string,"0","0","0");
            setGraphic(data.getBox());
        }
    }
}
