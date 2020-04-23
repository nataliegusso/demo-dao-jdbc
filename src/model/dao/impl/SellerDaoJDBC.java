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

public class SellerDaoJDBC implements SellerDao {

	private Connection conn; //Chama o BD do MySQL por aqui
	
	public SellerDaoJDBC(Connection conn) { //for�ar a inje��o de independ�ncia
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
			st.setInt(1, id);
			rs = st.executeQuery();  //o resultado da opera��o ficar� guardado no rs
			//os dados est�o guardados no BD como tabela, mas preciso ter na mem�ria do comp os objetos associados instanciados em mem�ria (esqueminha java)
			//a tabela come�a em 1, mas a mem�ria do computador come�a em 0
			if (rs.next()) {  //testar se vem resultado, se n�o vier ele d� falso e pula p return null
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));  //pega do SQL a coluna DepartmentId e coloca no Id da classe department
				dep.setName(rs.getString("DepName"));
				
				Seller obj = new Seller();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
				obj.setBaseSalary(rs.getDouble("BaseSalary"));
				obj.setBirthDate(rs.getDate("BirthDate"));
				obj.setDepartment(dep);	//n�o pega da tabela pq j� foi feito acima, pega do obj Department
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally { 	//n�o fecha conex�o pq esse objeto pode ser usado p outra opera��o. Ideal � fechar no Program
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}