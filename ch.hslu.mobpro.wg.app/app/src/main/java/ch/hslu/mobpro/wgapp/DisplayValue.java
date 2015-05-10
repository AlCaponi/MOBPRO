package ch.hslu.mobpro.wgapp;

/**
 * Created by Dany on 10.05.2015.
 */
public class DisplayValue
{
    private String displayValue;
    private int id;

    public DisplayValue(int id, String displayValue)
    {
        this.displayValue = displayValue;
        this.id = id;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getDisplayValue();
    }
}
