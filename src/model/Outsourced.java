package model;

/**
 *This displays the outsourced part.
 *
 *
 *
 */
public class Outsourced extends Part{

    /**
     * This declares the company name.
     */
    private String companyName;

    /**
     * This is the getter for companyName.
     *
     * @return Retunrs the companyName.
     */

    public String getCompanyName()
        {
            return companyName;
        }

    /**
     * This is the setter for companyName
     *
     * @param companyName Company name.
     */
    public void setCompanyName(String companyName)
        {
            this.companyName = companyName;
         }

    /**
     * This is the constructor for Outsourced part.
     * @param id The ID for the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The inventory level of the part.
     * @param min The minimum inventory level of the part.
     * @param max The maximum inventory level of the part.
     * @param companyName Company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    }

