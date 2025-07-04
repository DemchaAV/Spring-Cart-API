package com.demcha.spring_cart_api.controlers;


import com.demcha.spring_cart_api.dtos.CheckoutRequest;
import com.demcha.spring_cart_api.dtos.CheckoutResponse;
import com.demcha.spring_cart_api.dtos.ErrorDto;
import com.demcha.spring_cart_api.exeptions.CartEmptyException;
import com.demcha.spring_cart_api.exeptions.CartNotFoundException;
import com.demcha.spring_cart_api.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;


    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        return checkoutService.checkout(checkoutRequest);
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
