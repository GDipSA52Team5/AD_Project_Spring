package sg.edu.iss.hawkerise.service;

import sg.edu.iss.hawkerise.model.Hawker;

public interface HawkerInterface {
	
	public boolean authenticate(Hawker hawker);
	
	public Hawker findByUserName(String username);
	
	public boolean checkExists(Hawker hawker);
	
	public void createHawker(Hawker hawker);
	
	public void update(Hawker hawker);

}
