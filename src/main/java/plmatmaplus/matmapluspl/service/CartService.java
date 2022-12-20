package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.entity.Cart;
import plmatmaplus.matmapluspl.repository.CartRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public int getCartSize(HttpServletRequest httpServletRequest) {
        if (isNoSession(httpServletRequest)) return 0;
        else if (!areCoursesInCart(httpServletRequest)) return 0;
        return getCoursesInCartSize(httpServletRequest);
    }

    private boolean isNoSession(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getAttribute("user") == null;
    }

    private boolean areCoursesInCart(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().getAttribute("user");
       return cartRepository.findByUserId(Integer.parseInt(httpServletRequest.getSession().getAttribute("user").toString())).isPresent();
    }

    private int getCoursesInCartSize(HttpServletRequest httpServletRequest) {
        return cartRepository.findByUserId(Integer.parseInt(httpServletRequest.getSession().getAttribute("user").toString()))
                .get()
                .getCourses().size();
    }
}
