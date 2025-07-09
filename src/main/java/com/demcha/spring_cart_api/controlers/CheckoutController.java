package com.demcha.spring_cart_api.controlers;


import com.demcha.spring_cart_api.dtos.CheckoutRequest;
import com.demcha.spring_cart_api.dtos.ErrorDto;
import com.demcha.spring_cart_api.exeptions.CartEmptyException;
import com.demcha.spring_cart_api.exeptions.CartNotFoundException;
import com.demcha.spring_cart_api.services.CheckoutService;
import com.stripe.exception.StripeException;
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
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) throws StripeException {
        try {
            return ResponseEntity.ok(checkoutService.checkout(checkoutRequest));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error creating session"));
        }
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
