package org.example.Data;



public class Accessoires extends ProduitElectro {
    private String typeAccessoires;

    public Accessoires(int ID, String nom, String fabricant, long prix, String ram, String rom, String OS, String CPU, String typeAccessoires) {
        super(ID, nom, fabricant, prix, ram, rom, OS, CPU);
        this.typeAccessoires = typeAccessoires;
    }

    public Accessoires(int ID, String nom, String fabricant, long prix, String ram, String rom, String OS, String CPU) {
        super(ID, nom, fabricant, prix, ram, rom, OS, CPU);
    }

    public String getTypeAccessoires() {
        return typeAccessoires;
    }

    public void setTypeAccessoires(String typeAccessoires) {
        this.typeAccessoires = typeAccessoires;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type data.Accessoires: " + typeAccessoires;
    }
}

