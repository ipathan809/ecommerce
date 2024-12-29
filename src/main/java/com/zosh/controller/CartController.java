package com.zosh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.exception.ProductException;
import com.zosh.exception.UserException;
import com.zosh.model.Cart;
import com.zosh.model.CartItem;
import com.zosh.model.User;
import com.zosh.request.AddItemRequest;
import com.zosh.response.ApiResponse;
import com.zosh.service.CartService;
import com.zosh.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	private CartService cartService;
	private UserService userService;
	
	@Autowired
	public CartController(CartService cartService,UserService userService) {
		this.cartService=cartService;
		this.userService=userService;
	}
	
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {
	    User user = userService.findUserProfileByJwt(jwt);
	    Cart cart = cartService.findUserCart(user.getId());

	    if (cart != null) {
	        System.out.println("Cart found for user: " + user.getEmail());
	    } else {
	        System.out.println("No cart found for user: " + user.getEmail());
	    }

	    return new ResponseEntity<>(cart, HttpStatus.OK);
	}

//	@GetMapping("/")
//	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {
//	    // Ensure jwt token is parsed correctly
//	    User user = userService.findUserProfileByJwt(jwt);
//	    
//	    // Validate user before continuing
//	    if (user == null) {
//	        throw new UserException("User not found");
//	    }
//	    
//	    Cart cart = cartService.findUserCart(user.getId());
//	    return new ResponseEntity<>(cart, HttpStatus.OK);
//	}
	
//	@GetMapping("/")
//	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException{
//		
//		User user=userService.findUserProfileByJwt(jwt);
//		
//		Cart cart=cartService.findUserCart(user.getId());
//		
//		System.out.println("cart - "+cart.getUser().getEmail());
//		
//		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
//	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res= new ApiResponse("Item Added To Cart Successfully",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
		
	}
	

}
