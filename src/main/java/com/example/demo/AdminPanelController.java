package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.entities.Product;
import com.example.demo.model.entities.ProductCategory;
import com.example.demo.model.repos.ProductCategoryRepository;
import com.example.demo.model.repos.ProductRepository;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	ProductCategoryRepository categoryRepo;
	
	@RequestMapping("/")
	public String home() {
		return "admin";
	}
	
	@RequestMapping("/products")
	public String products() {
		return "admin/products";
	}
	
	@GetMapping("/products/addProduct")
	public String addProductForm(Model model) {
		List<ProductCategory> categories = categoryRepo.findAll();
		model.addAttribute("categories",categories);
		//System.out.println(categories);
		return "admin/addProductForm";
	}
	@ResponseBody
	@PostMapping("/products/addProduct")
	public String addProduct(@RequestParam("image") MultipartFile image,
							@RequestParam("categoryId") long categoryId,
							@RequestParam("name") String name,
							@RequestParam("sku") String sku,
							@RequestParam("description") String description,
							@RequestParam("unitPrice") BigDecimal price,
							@RequestParam("active") String active,
							@RequestParam("unitsInStock") int unitsInStock
			) throws IOException {
		ProductCategory category = categoryRepo.findById(categoryId).get();
		String destination =  new ClassPathResource("/static/assets/images/products").getFile().getAbsolutePath();
		Files.copy(image.getInputStream(), Paths.get(destination+"/"+category.getCategoryName().toLowerCase().replaceAll("\\s", "")+File.separator+image.getOriginalFilename()));
		//System.out.println(categoryId);
		
		Product product = new Product(
					category,
					sku,
					name,
					description,
					price,
					"assets/images/products/"+category.getCategoryName().toLowerCase().replaceAll("\\s", "")+"/"+image.getOriginalFilename(),
					active.equalsIgnoreCase("true"),
					unitsInStock
				);
		productRepo.save(product);
		return "redirect:/admin/products";
	}
}
