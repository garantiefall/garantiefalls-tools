package de.garantiefall.sleevecreator.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.garantiefall.sleevecreator.entity.FileEntity;

@Controller
public class SleeveCreatorController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SleeveCreatorController.class);

	
	public static final String SESSION_KEY = "fileToEdit";
	@GetMapping("/sleevecreator")
	public String showSleevecreator() {
		System.out.println("SleeveCreatorController.showSleevecreator()");
		return "sleevecreator";
	}
	
	@PostMapping("/sleevecreator")
	public String showSleevecreator(@RequestParam(name = "fileSelector") MultipartFile file, HttpSession session) throws IOException {
		LOGGER.info("file: " + file);
		if(file != null && file.getBytes() != null) {
			FileEntity fileEntity = new FileEntity();
			fileEntity.setId(UUID.randomUUID().toString());
			fileEntity.setFilename(file.getName());
			fileEntity.setType(file.getContentType());
			fileEntity.setData(file.getBytes());
			
			session.setAttribute(SESSION_KEY, fileEntity);
			LOGGER.info("File in Session!");
			return "redirect:/sleevecreator/create";
		}
		return "redirect:/sleevecreator";
	}
	
	@GetMapping("/sleevecreator/create")
	public String showSleeveCreatorSite(Model model, HttpSession session) {
		if(session.getAttribute(SESSION_KEY) != null) {
			model.addAttribute("fileToCreate", (FileEntity) session.getAttribute(SESSION_KEY));
			return "sleevecreatorEditor";
		}
		return "redirect:/sleevecreator";
	}
	
	@ResponseBody
	@GetMapping("/sleevecreator/create/getFile")
	public byte[] getFile(HttpServletResponse response, HttpSession session) {
		FileEntity file = (FileEntity) session.getAttribute(SESSION_KEY);
		LOGGER.info("File Content getFile: " + file);
		if(file != null) {
			response.addHeader("Content-Type", file.getType());
			return file.getData();
		}
		return null;
	}
}
