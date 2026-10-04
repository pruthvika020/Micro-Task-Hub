package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	
	@Query("select U.role from Users U where U.fullname=:fullname and U.password=:pwd")
	public Object checkCredentials(@Param("fullname") String fullname, @Param("pwd") String password);
	
	@Query("select U.id from Users U where U.email=:email")
	public Object checkEmail(@Param("email") String email);
	
	public Users findByFullname(String fullname);
	//public Users findByFullnameAndPassword(String fullname, String password);
	
	@Query("select M from Menu M join RolesMapping R on M.mid = R.mid where R.role=:role order by M.menu ASC")
	public List<Object> getMenus(@Param("role") int role);
	
	@Query("select U,R from Users U left join Role R on U.role=R.role where U.fullname=:fullname")
	public Object getprofile(@Param("fullname") String fullname);
	
	@Query("select U from Users U where lower(U.fullname) like concat('%', lower(:key), '%')")
	public List<Users> searchKey (@Param("key") String key);
}
