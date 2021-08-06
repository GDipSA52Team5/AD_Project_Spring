package sg.edu.iss.hawkerise.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.hawkerise.model.Hawker;

public interface HawkerRepository extends JpaRepository<Hawker, Integer> {
	
	public Hawker findHawkerByUserNameAndPassword(String username, String password);
	
	public Hawker findHawkerByUserName(String username);
	

}
