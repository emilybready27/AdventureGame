package student.adventure;

/** A class that handles the Item functionality of the Adventure Game. */
public class Item {
    private String itemName;
    private String itemDescription;

    public Item() {
    }

    public Item(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
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

    public boolean equals(Item other) {
        if (this == other) {
            return true;
        }
        return other != null && itemName.equals(other.getItemName())
                && itemDescription.equals(other.getItemDescription());
    }

    public static boolean isValidItem(String argument) {
        return (argument.equals("banner") || argument.equals("weapon")
                || argument.equals("tool") || argument.equals("crown"));
    }
}
