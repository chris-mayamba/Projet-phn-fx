package org.example.projet_phn_fx.dao;

import org.example.projet_phn_fx.Data.ProduitElectro;

import java.util.List;

public interface IProduitDAO {
    public void addProduits(ProduitElectro produitElectro);
    public List<ProduitElectro> getProduits();
    public void deleteProduits(ProduitElectro produitElectro);
    public void updateProduits(ProduitElectro produitElectro);

}
