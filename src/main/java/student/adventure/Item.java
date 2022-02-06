package student.adventure;

import com.google.gson.JsonParseException;

public class Item {
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

    public void checkForNull() throws JsonParseException {
        if (itemName == null || itemDescription == null) {
            throw new JsonParseException("Missing field.");
        }
    }

    public void normalizeItem() {
        itemName = itemName.toLowerCase();
    }

    public boolean equals(Item other) {
        if (this == other) {
            return true;
        }
        return other != null && itemName.equals(other.getItemName())
                && itemDescription.equals(other.getItemDescription());
    }

    public static boolean isValidItem(String argument) {
        return (argument.equals("banner") || argument.equals("weapon") || argument.equals("tool")
                || argument.equals("crown"));
    }
}
