package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn; //vai ter ele a disposição na classe para implementação
	
	public SellerDaoJDBC(Connection  conn) { //aqui gerou uma dependia do DAO com o banco de dados
		this.conn = conn;
	}
	
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			st.setInt(1, id); //o primeiro elemento vai retornar no id
			rs = st.executeQuery();
			if (rs.next()) { //o if vai testar se veio algum resultado
				Department dep = instantiateDepartment(rs);
				Seller obj = instatiateSeller(rs, dep);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException { //método auxiliar
		Seller obj = new Seller(); //até o final do if, esse código vai instaciar o vendedor
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}


	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(); //essa linha e as duas debaixo instanciam o departamento
		dep.setId(rs.getInt("DepartmentId")); //aqui ele vai procurar no banco de dados o DepartmentId que retorna o numero 1
		dep.setName(rs.getString("DepName")); //aqui ele vai procurar no banco de dados o DepartmentId que retorna o nome do departamento
		return dep;
	}


	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
