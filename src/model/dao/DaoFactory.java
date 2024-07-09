package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() { //a classe vai expor o método que retorna o tipo da interface mas internamente instancia uma implementação, deixa apenas a interface
		return new SellerDaoJDBC(DB.getConnection());
	}
}
