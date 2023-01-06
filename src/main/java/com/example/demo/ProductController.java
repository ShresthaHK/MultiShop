package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.repos.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductRepository productRepo;
	
	@RequestMapping("/{pid}")
	public String getDetails(@PathVariable long pid, Model model) {
		model.addAttribute("product", productRepo.findById(pid).get());
		return "detail";
	}
}
