package com.ecommerce.project.controller;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/cart/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity
    ) {
        CartDTO cartDTO = cartService.addProductToCart(productId,quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById() {
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDTO = cartService.getCart(emailId,cartId);
        return ResponseEntity.ok(cartDTO);
    }

    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(Long productId,
                                                     String operation){
        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,  operation.equalsIgnoreCase("delete") ? -1 : 1);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteCartProductFromCart(@PathVariable Long cartId,
                                                             @PathVariable Long productId){
        String status = cartService.deleteProductFromCart(cartId,productId);
        return new ResponseEntity<String>(status,HttpStatus.OK);
    }
}
