package ch.hslu.mobpro.wgapp;

/**
 * Created by Dany on 10.05.2015.
 */
public class ListItem {
    private int ListItemID;
    private String Name;
    private String IsChecked;
    private String CreatedDate;
    private int ListEntityListID;

    public ListItem(int listItemID, String name, String isChecked, String createDate, int listEntityListID) {
        this.ListItemID = listItemID;
        this.Name = name;
        this.IsChecked = isChecked;
        this.CreatedDate = createDate;
        this.ListEntityListID = listEntityListID;
    }

    public ListItem() {
    }

    public int getListItemID() {
        return ListItemID;
    }

    public void setListItemID(int listItemID) {
        this.ListItemID = listItemID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getIsChecked() {
        return IsChecked;
    }

    public void setIsChecked(String isChecked) {
        this.IsChecked = isChecked;
    }

    public String getCreateDate() {
        return CreatedDate;
    }

    public void setCreateDate(String createDate) {
        this.CreatedDate = createDate;
    }

    public int getListEntityListID() {
        return ListEntityListID;
    }

    public void setListEntityListID(int listEntityListID) {
        this.ListEntityListID = listEntityListID;
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
