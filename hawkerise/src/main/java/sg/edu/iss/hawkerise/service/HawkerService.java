package sg.edu.iss.hawkerise.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import sg.edu.iss.hawkerise.model.Hawker;
import sg.edu.iss.hawkerise.repo.HawkerRepository;

@Service
public class HawkerService implements HawkerInterface {
	@Autowired
	HawkerRepository hrepo;

	@Transactional
	public boolean authenticate(Hawker hawker) {
		Hawker fromDb = hrepo.findHawkerByUserNameAndPassword(hawker.getUserName(), hawker.getPassword());
		if(fromDb != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Hawker findByUserName(String username) {
		return hrepo.findHawkerByUserName(username);
	}

	@Override
	public boolean checkExists(Hawker hawker) {
		if (hrepo.findAll().contains(hawker)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void createHawker(Hawker hawker) {
		hrepo.saveAndFlush(hawker);
	}

	@Override
	public void update(Hawker hawker) {
	Hawker h = hrepo.findHawkerByUserName(hawker.getUserName());
	h.setStallName(hawker.getStallName());
	h.setContactNumber(hawker.getContactNumber());
	h.setPassword(hawker.getPassword());
	h.setOperatingHours(hawker.getOperatingHours());
	h.setTags(hawker.getTags());
	hrepo.saveAndFlush(h);

	}

}
