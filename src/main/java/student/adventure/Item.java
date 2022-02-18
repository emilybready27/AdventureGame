package student.adventure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** A class that handles the Item functionality of the Adventure Game. */
public class Item {
    private static final Set<String> validItems = new HashSet(Arrays.asList("banner","weapon","tool","crown"));
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
     * Determines if a given argument is a valid Item.
     * @param argument String
     * @return boolean
     */
    public static boolean isValidItem(String argument) {
        return validItems.contains(argument);
    }

    /**
     * Checks if any of the attributes of a valid Item are missing.
     * @return boolean
     */
    public boolean isEmpty() {
        return itemName == null || itemDescription == null;
    }
}
