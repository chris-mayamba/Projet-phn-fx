package org.example.dao;

import org.example.Data.ProduitElectro;

import java.util.List;

public interface IProduitDAO {
    public void addProduits(ProduitElectro produitElectro);
    public List<ProduitElectro> getProduits();
    public void deleteProduits(ProduitElectro produitElectro);
    public void updateProduits(ProduitElectro produitElectro);

}
