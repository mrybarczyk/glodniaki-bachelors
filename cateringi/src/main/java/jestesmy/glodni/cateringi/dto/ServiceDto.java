package jestesmy.glodni.cateringi.dto;

public class ServiceDto {
    private int id;
    private String name;
    private String description;
    private int companyId;

    public ServiceDto() {
    }

    public ServiceDto(int id, String name, String description, int companyId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}