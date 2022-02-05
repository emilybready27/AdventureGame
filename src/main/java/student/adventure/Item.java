package student.adventure;

import com.google.gson.JsonParseException;

public class Item {
    // TODO: add banners, weapons, tools
    private String itemName;
    private String itemDescription;

    public Item() {
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public void printItem() {
        System.out.println(itemName);
        System.out.println(itemDescription);
    }

    public void checkNullItemField() throws JsonParseException {
        if (itemName == null || itemDescription == null) {
            throw new JsonParseException("Missing field");
        }
    }

    public void normalizeItem() {
        itemName = itemName.toLowerCase();
    }
}
