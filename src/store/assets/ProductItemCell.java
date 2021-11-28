/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.assets;

import javafx.scene.control.ListCell;
import store.StockedItemController; 

/**
 *
 * @author AHMED
 */
public class ProductItemCell extends ListCell<StoreProducts>
{
    @Override
    public void updateItem(StoreProducts string, boolean empty)
    {
        super.updateItem(string,empty);
        if(string != null)
        {
            StockedItemController data = new StockedItemController();
            data.setInfo(string);
            setGraphic(data.getBox());
        }
    }
}
