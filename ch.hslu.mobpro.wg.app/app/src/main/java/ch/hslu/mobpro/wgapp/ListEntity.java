package ch.hslu.mobpro.wgapp;

/**
 * Created by Dany on 10.05.2015.
 */
public class ListEntity extends Object {
    private int ListID;
    private String ListName;
    private int GroupsGroupID;

    public ListEntity(int listID, String listName, int groupsGroupID) {
        this.ListID = listID;
        this.ListName = listName;
        this.GroupsGroupID = groupsGroupID;
    }

    public ListEntity()
    {
    }

    public int getListID() {
        return ListID;
    }

    public void setListID(int listID) {
        this.ListID = listID;
    }

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        this.ListName = listName;
    }

    public int getGroupsGroupID() {
        return GroupsGroupID;
    }

    public void setGroupsGroupID(int groupsGroupID) {
        this.GroupsGroupID = groupsGroupID;
    }
}
