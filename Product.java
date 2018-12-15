/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2.asm;

/**
 *
 * @author pc
 */
public class Product {
    private int id;
    private int idPP;
    private String name;
    private float price;
    private String inputDate;
    private String date;
    private String description;

    public Product() {
    }
    
    public Product(int idPP, String name) {
        this.name = name;
        this.idPP = idPP;
    }
    
    public Product(int idPP, String name, float price, String inputDate, String date, String description) {
        this.idPP = idPP;
        this.name = name;
        this.price = price;
        this.inputDate = inputDate;
        this.date = date;
        this.description = description;
    }

    public Product(int id, int idPP, String name, float price, String inputDate, String date, String description) {
        this.id = id;
        this.idPP = idPP;
        this.name = name;
        this.price = price;
        this.inputDate = inputDate;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPP() {
        return idPP;
    }

    public void setIdPP(int idPP) {
        this.idPP = idPP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
