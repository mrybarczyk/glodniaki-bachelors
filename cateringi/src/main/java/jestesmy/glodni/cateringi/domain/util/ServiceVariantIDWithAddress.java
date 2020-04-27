package jestesmy.glodni.cateringi.domain.util;

public class ServiceVariantIDWithAddress {

    private int selectedServiceVariantID;

    private String address;

    private String diffAddress;

    public int getSelectedServiceVariantID() {
        return selectedServiceVariantID;
    }

    public void setSelectedServiceVariantID(int selectedServiceVariantID) {
        this.selectedServiceVariantID = selectedServiceVariantID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiffAddress() {
        return diffAddress;
    }

    public void setDiffAddress(String diffAddress) {
        this.diffAddress = diffAddress;
    }
}
