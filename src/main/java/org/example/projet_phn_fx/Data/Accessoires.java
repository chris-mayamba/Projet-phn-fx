package org.example.projet_phn_fx.Data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Accessoires extends ProduitElectro {
    private final StringProperty typeAccessoires = new SimpleStringProperty();

    public Accessoires(int ID, String nom, String fabricant, long prix,
                       String ram, String rom, String OS, String CPU,
                       String typeAccessoires) {
        super(ID, nom, fabricant, prix, ram, rom, OS, CPU);
        setTypeAccessoires(typeAccessoires);
    }

    // Property getter
    public StringProperty typeAccessoiresProperty() { return typeAccessoires; }

    // Getter/Setter normaux
    public String getTypeAccessoires() { return typeAccessoires.get(); }
    public void setTypeAccessoires(String typeAccessoires) {
        this.typeAccessoires.set(typeAccessoires);
    }

    @Override
    public String toString() {
        return super.toString() + ", Type Accessoires: " + getTypeAccessoires();
    }
}