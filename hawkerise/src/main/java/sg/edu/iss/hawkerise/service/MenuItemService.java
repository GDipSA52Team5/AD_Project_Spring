package sg.edu.iss.hawkerise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.hawkerise.model.MenuItem;
import sg.edu.iss.hawkerise.repo.MenuItemRepository;

@Service
public class MenuItemService implements MenuItemInterface {
	@Autowired
	MenuItemRepository mrepo;

	@Override
	public List<MenuItem> findByHawker(String userName) {
		return mrepo.findMenuItemByHawker(userName);
	}

	@Override
	public void saveMenuItem(MenuItem menuItem) {
		mrepo.save(menuItem);
	}

	@Override
	public MenuItem findById(int menuItemId) {
		return mrepo.findMenuItemById(menuItemId);
	}

	@Override
	public void deleteMenuItem(MenuItem menuItem) {
		mrepo.delete(menuItem);
	}

}
