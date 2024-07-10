package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name ");
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>(); 
			Map<Integer, Department> map = new HashMap<>();  //o map vai mapear a lista dentro do while para que não seja criado outro department , o construtor aqui sera vazio para que o getInt Department entre nesse campó
			
			while (rs.next()) { //o while vai ser enquanto na leitura existir durtante a busca pelo vendedor
				
				Department dep = map.get(rs.getInt("DepartmentId")); //vai verificar se o departament não existe antes de gerar um novo, sendo assim ele vai buscar dentro de DepartmentID
				
				if (dep == null) { //caso não encontra o vendedor no departmentId que será vai instanciar o departamento como retorno
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); //vai salvar o departamento dentro do map para a próxima vez que for verificar ver que ele já existe
				}
				
				Seller obj = instatiateSeller(rs, dep);
				list.add(obj); //no final ele vai acrescentar o vendedor a lista
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}


	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name ");
			
			st.setInt(1, department.getId()); //vai buscar o Id no departament
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>(); 
			Map<Integer, Department> map = new HashMap<>();  //o map vai mapear a lista dentro do while para que não seja criado outro department , o construtor aqui sera vazio para que o getInt Department entre nesse campó
			
			while (rs.next()) { //o while vai ser enquanto na leitura existir durtante a busca pelo vendedor
				
				Department dep = map.get(rs.getInt("DepartmentId")); //vai verificar se o departament não existe antes de gerar um novo, sendo assim ele vai buscar dentro de DepartmentID
				
				if (dep == null) { //caso não encontra o vendedor no departmentId que será vai instanciar o departamento como retorno
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); //vai salvar o departamento dentro do map para a próxima vez que for verificar ver que ele já existe
				}
				
				Seller obj = instatiateSeller(rs, dep);
				list.add(obj); //no final ele vai acrescentar o vendedor a lista
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
