package sg.edu.iss.hawkerise.controller;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.iss.hawkerise.model.Hawker;
import sg.edu.iss.hawkerise.model.MenuItem;
import sg.edu.iss.hawkerise.model.Tag;
import sg.edu.iss.hawkerise.service.MenuItemInterface;


@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	MenuItemInterface mservice;
	
	
	@RequestMapping(value = "/add")
	public String addMenu(Model model,HttpSession session) {
		if (session.getAttribute("hsession") == null) {
			return "forward:/hawker/login";
		} 
		
		MenuItem m = new MenuItem();
		model.addAttribute("newMenu", m);
		return "menu/itemform";
	}
	
	@RequestMapping(value = "/save")
	public String saveMenu(@ModelAttribute("newMenu") MenuItem newMenuItem
			, @RequestParam("filePhoto") MultipartFile multipartFile,HttpSession session) throws IOException {
		if (session.getAttribute("hsession") == null) {
			return "forward:/hawker/login";
		} 
		
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());		
		newMenuItem.setPhoto(fileName);
		Hawker h =(Hawker) session.getAttribute("hsession");
		newMenuItem.setHawker(h);
		
		mservice.saveMenuItem(newMenuItem);
		
		
		MenuItem savedMenuItem = newMenuItem;
		String uploadDir = "./item-photo/"+savedMenuItem.getId();
		
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Could not save uploaded file: " + fileName);
		}
		
		String localUrl = "http://localhost:8080" + newMenuItem.getPhotoImagePath();
		newMenuItem.setLocalUrl(localUrl);
		
		mservice.saveMenuItem(newMenuItem);
				
		return "forward:/menu/list";
	}
	
	@RequestMapping(value = "/list")
	public String listMenu(Model model,HttpSession session) {
		if (session.getAttribute("hsession") == null) {
			return "forward:/hawker/login";
		} 
		
		Hawker hawker = (Hawker)session.getAttribute("hsession");
		model.addAttribute("menuItems",mservice.findByHawker(hawker.getUserName()));
		return "menu/listmenu";
	}
	
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("menuId") int menuId,HttpSession session){
		if (session.getAttribute("hsession") == null) {
			return "hawker/hawkerlogin";
		}
    	
    	MenuItem menuToD = mservice.findById(menuId);
    	mservice.deleteMenuItem(menuToD);

    	return "forward:/menu/list";
    }
    
    @RequestMapping(value = "/update")
	public String update(@RequestParam("itemId") int itemId, Model model, HttpSession session) {
		if (session.getAttribute("hsession") == null) {
			return "forward:/menu/list";
		} else {
			MenuItem menuItem = mservice.findById(itemId);
			model.addAttribute("menuToUpdate", menuItem);
			return "menu/update";
		}
	}
	
}
