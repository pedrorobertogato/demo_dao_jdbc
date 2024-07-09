package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao(); //o programa não conhece a implementação apenas a interface
		
		System.out.println("=== TEST 1: sseller findById ====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
	}

}
