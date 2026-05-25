package com.project.example.repositories;

import com.project.example.model.Cart;
import com.project.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.user.email=?1")
    Cart findCartByEmail(String email);

    @Query("select c from Cart c where c.user.email=?1 AND c.id=?2")
    Cart findCartByEmailIdAndCartId(String emailId, Long cartId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Cart> findCartsByProductId(Long productId);
}
