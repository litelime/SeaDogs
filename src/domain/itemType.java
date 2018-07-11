/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author syntel
 */
public class itemType {
    private String itemTypeId;
    private String itemType;

    public itemType() {
    }

    public itemType(String itemTypeId, String itemType) {
        this.itemTypeId = itemTypeId;
        this.itemType = itemType;
    }

    public String getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "itemType{" + "itemTypeId=" + itemTypeId + ", itemType=" + itemType + '}';
    }
    
    
    
}
