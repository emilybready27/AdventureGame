package student.adventure;

/** A class that handles the Item functionality of the Adventure Game. */
public class Item {
    private String itemName;
    private String itemDescription;

    /**
     * Constructs an Item object. Item name must be banner, weapon, tool, or crown.
     * The distribution of Items may not be uniform; some rooms may have none at all.
     * @param itemName String
     * @param itemDescription String
     */
    public Item(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    /**
     * Sets the Item name.
     * @param itemName String
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the Item name.
     * @return String
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * Sets the Item description.
     * @param itemDescription String
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * Gets the Item description.
     * @return String
     */
    public String getItemDescription() {
        return this.itemDescription;
    }

    /**
     * Overrides the equals function to compare two Items.
     * Because Item names are repeated, two items are defined as equal
     * if and only if both their Item name and Item description match.
     * @param other Item
     * @return boolean
     */
    public boolean equals(Item other) {
        if (this == other) {
            return true;
        }
        return other != null && itemName.equalsIgnoreCase(other.getItemName())
                && itemDescription.equalsIgnoreCase(other.getItemDescription());
    }

    /**
     * Determines if a given argument is a banner, weapon, tool, or crown.
     * @param argument String
     * @return boolean
     */
    public static boolean isValidItem(String argument) {
        return (argument.equalsIgnoreCase("banner") || argument.equalsIgnoreCase("weapon")
                || argument.equalsIgnoreCase("tool") || argument.equalsIgnoreCase("crown"));
    }
}
