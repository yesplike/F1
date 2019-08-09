package com.yesp.server.model;

public class Item {
    private Integer itemid;

    private String itemno;

    private Integer productid;

    private Float listprice;

    private String attrl;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private Product product;

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno == null ? null : itemno.trim();
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Float getListprice() {
        return listprice;
    }

    public void setListprice(Float listprice) {
        this.listprice = listprice;
    }

    public String getAttrl() {
        return attrl;
    }

    public void setAttrl(String attrl) {
        this.attrl = attrl == null ? null : attrl.trim();
    }
}