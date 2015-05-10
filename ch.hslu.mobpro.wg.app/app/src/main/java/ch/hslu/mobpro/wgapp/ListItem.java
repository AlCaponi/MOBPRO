package ch.hslu.mobpro.wgapp;

/**
 * Created by Dany on 10.05.2015.
 */
public class ListItem {
    private int listItemID;
    private String name;
    private String isChecked;
    private String createDate;
    private int listEntityListID;

    public ListItem(int listItemID, String name, String isChecked, String createDate, int listEntityListID) {
        this.listItemID = listItemID;
        this.name = name;
        this.isChecked = isChecked;
        this.createDate = createDate;
        this.listEntityListID = listEntityListID;
    }

    public ListItem() {
    }

    public int getListItemID() {
        return listItemID;
    }

    public void setListItemID(int listItemID) {
        this.listItemID = listItemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getListEntityListID() {
        return listEntityListID;
    }

    public void setListEntityListID(int listEntityListID) {
        this.listEntityListID = listEntityListID;
    }

    @Override
    public String toString() {
        if (this == null)
        {
            return null;
        }
        return this.getName();
    }
}
