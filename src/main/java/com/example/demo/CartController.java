package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
	@RequestMapping("/addToCart/{pid}")
	public String addToCArt(@PathVariable long pid, HttpSession session, Model model) {
		ArrayList<Long> productsInCart = (ArrayList<Long>) session.getAttribute("productsInCart");
		if(productsInCart==null) {
			productsInCart = new ArrayList<Long>();
		}
		productsInCart.add(pid);
		session.setAttribute("productsInCart", productsInCart);
		model.addAttribute("productsInCart", productsInCart);
		return "redirect:/";
	}
	@RequestMapping("removeFromCart/{id}")
	public String removeFromCart(@PathVariable long id, HttpSession session) {
		List<Long> cart = (List<Long>) session.getAttribute("productsInCart");
		List<Long> newCart = new ArrayList<Long>();
 		for(Long i:cart) {
			if(i!=id) {
				newCart.add(i);
			}
		}
		session.setAttribute("productsInCart", newCart);
		return "redirect:/cart";
	}
	
}
