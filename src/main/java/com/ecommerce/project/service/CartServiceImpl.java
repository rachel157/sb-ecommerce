package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductRepository productRepository2;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
    Cart cart = createCart();

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

    CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(),productId);

    if(cartItem != null) {
        throw new APIException("Product " + product.getProductName() + " already exists in the cart");
    }

    if(product.getQuantity() == 0) {
        throw new APIException(product.getProductName() + " is not available");
    }
    if(quantity > product.getQuantity()) {
        throw new APIException("Please, make an order of the " + product.getProductName() + " less than or equal to " + product.getQuantity() + ".");
    }

    CartItem newCartItem = new CartItem();
    newCartItem.setProduct(product);
    newCartItem.setCart(cart);
    newCartItem.setQuantity(quantity);
    newCartItem.setDiscount(product.getDiscount());
    newCartItem.setProductPrice(product.getSpecialPrice());

    cartItemRepository.save(newCartItem);

    product.setQuantity(product.getQuantity());

    cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice()*quantity));

    cart.getCartItems().add(newCartItem);


    cartRepository.save(cart);


    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

    List<CartItem> cartItems = cart.getCartItems();

    Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
        ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
        map.setQuantity(item.getQuantity());
        return map;
            });

    cartDTO.setProducts(productStream.toList());
    return cartDTO;
   }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId,cartId);
        if(cart == null){
            throw new ResourceNotFoundException("Cart","cartId",cartId);
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(item -> {
                    ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
                    map.setQuantity(item.getQuantity());
                    return map;
                })
                .toList();
        cartDTO.setProducts(products);
        return cartDTO;
    }

    @Transactional
    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {

        String emailId=authUtil.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(emailId);
        Long cartId = userCart.getCartId();

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart","cartId",cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        if(product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }
        if(quantity > product.getQuantity()) {
            throw new APIException("Please, make an order of the " + product.getProductName() + " less than or equal to " + product.getQuantity() + ".");
        }

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId,productId);

        if(cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " does not exist in the cart");
        }

        int newQuantity = quantity + cartItem.getQuantity();

        if(newQuantity < 0) {
            throw new APIException("The resulting quantity cannot be negative.");
        }

        if(newQuantity > product.getQuantity()) {
            throw new APIException("The resulting quantity cannot be greater than the product quantity.");
        }

        if(newQuantity == 0) {
            deleteProductFromCart(cartId,productId);
        }
        else {
            cartItem.setProductPrice(product.getSpecialPrice());
            cartItem.setQuantity(newQuantity);
            cartItem.setDiscount(product.getDiscount());
            cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
            cartRepository.save(cart);
        }

        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        if(updatedCartItem.getQuantity() == 0){
            cartItemRepository.deleteById(updatedCartItem.getCartItemId());
        }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        List<ProductDTO> products = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        }).toList();

        cartDTO.setProducts(products);

        return cartDTO;
    }

    @Transactional
    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart","cartId",cartId));
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId,productId);
        if(cartItem == null) {
            throw new ResourceNotFoundException("Product","productId",productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice()*cartItem.getQuantity()));

        cartRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " has been deleted from the cart!!!";
    }

    @Transactional
    @Override
    public void updateProductInCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart","cartId",cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId,productId);

        if(cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " does not exist in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice()*cartItem.getQuantity());

        cartItem.setProductPrice(product.getSpecialPrice());

        cart.setTotalPrice(cartPrice + cartItem.getProductPrice()*cartItem.getQuantity());
        cartRepository.save(cart);
        cartItem.setProduct(product);

        cartRepository.save(cart);
        cartItemRepository.save(cartItem);


    }

    private Cart createCart() {
       Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
       if(userCart != null){
           return userCart;
       }

       Cart cart = new Cart();
       cart.setTotalPrice(0.00);
       cart.setUser(authUtil.loggedInUser());
       Cart newCart = cartRepository.save(cart);
       return newCart;
   }
}
