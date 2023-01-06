package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.entities.Product;
import com.example.demo.model.repos.ProductRepository;

@Controller
public class ViewController {
	@Autowired
	ProductRepository productRepo;
	
	@RequestMapping("/")
	public String index(Model model, HttpSession session) {
		getCartItems(model, session);
		Pageable page = PageRequest.of(0, 8);
		List<Product> products = productRepo.findByCategoryId(1, page);
		model.addAttribute("products", products);
		return "index";
	}
	
	private void getCartItems(Model model, HttpSession session) {
		List<Long> cart;
		cart = (List<Long>) session.getAttribute("productsInCart");
		ArrayList<Product> productsInCart = new ArrayList<>();
		if(cart!=null) {
			for(long i:cart) {
				Product p = productRepo.findById(i).get();
				productsInCart.add(p);
			}
		}
		model.addAttribute("productsInCart",productsInCart);
		
	}

	@ResponseBody
	@RequestMapping("/productByCategory/{id}")
	public List<Product> getProductByCategory(@PathVariable("id") long id){
		Pageable page = PageRequest.of(0, 8);
		return productRepo.findByCategoryId(id, page);
	}
	@RequestMapping("/cart")
	public String cart(Model model, HttpSession session) {
		getCartItems(model, session);
		return "cart";
	}
}
