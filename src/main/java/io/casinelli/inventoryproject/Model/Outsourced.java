package io.casinelli.inventoryproject.Model;

/**
 * Part subclass used to store outsourced parts
 * @author Louis Casinelli Jr
 * @version 1.00
 */

public class Outsourced extends Part {

    private String companyName;

    /**
     * Constructor for outsourced part with the following attributes
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min,
                      int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * <p>Set company name of source of part</p>
     * @param companyName
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    /**
     * @return company name of manufacturer
     */
    public String getCompanyName() { return companyName; }
}