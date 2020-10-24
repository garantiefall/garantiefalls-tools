package de.garantiefall.sleevecreator.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import de.garantiefall.sleevecreator.entity.FileEntity;

@Controller
public class SleeveCreatorController {
	
	@GetMapping("/sleevecreator")
	public String showSleevecreator() {
		return "sleevecreator";
	}
	
	@PostMapping("/sleevecreator/upload")
	public String showSleevecreator(@RequestParam("file") MultipartFile file) throws IOException {
		if(file != null && file.getBytes() != null) {
			FileEntity fileEntity = new FileEntity();
			fileEntity.setId(UUID.randomUUID().toString());
			fileEntity.setFilename(file.getName());
			fileEntity.setType(file.getContentType());
			fileEntity.setData(file.getBytes());
			
//			fileRepository.save(fileEntity);
			return "redirect:/sleevecreator/create/" + fileEntity.getId();
		}
		return "redirect:/sleevecreator";
	}
	
	@GetMapping("/sleevecreator/create/{id}")
	public String showSleeveCreatorSite(@PathVariable("id") String id, Model model) {
		model.addAttribute("fileId", id);
		return "sleevecratorEditor";
	}
	
	@GetMapping("/sleevecreator/getFile/{id}")
	public byte[] getFile(@PathVariable("id") String id, HttpServletResponse response) {
//		Optional<FileEntity> optFileEntity = fileRepository.findById(id);
//		if(optFileEntity.isPresent()) {
//			response.addHeader("Content-Type", optFileEntity.get().getType());
//			return optFileEntity.get().getData();
//		}
		return null;
	}
}