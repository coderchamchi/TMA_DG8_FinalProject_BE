package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.PaymentService;
import com.bezkoder.springjwt.Service.ShoppingCartService;
import com.bezkoder.springjwt.Service.UserService;
import com.bezkoder.springjwt.dto.PaymentDTO;
import com.bezkoder.springjwt.dto.ProductListDTO;
import com.bezkoder.springjwt.entities.*;
import com.bezkoder.springjwt.repository.PaymentMethodRepository;
import com.bezkoder.springjwt.repository.PaymentRepository;
import com.bezkoder.springjwt.repository.ShoppingCartRepository;
import com.bezkoder.springjwt.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Override
    public boolean savePayment(long user, PaymentDTO paymentDTO) {
        ShoppingCart cart = shoppingCartRepository.getShoppingCartByUser(user);
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(paymentDTO.getPaymentMethod());
        Optional<Voucher> voucher = voucherRepository.findById(paymentDTO.getVoucher());
        if (ObjectUtils.isEmpty(paymentDTO) || ObjectUtils.isEmpty(cart) || paymentMethod.isEmpty() || voucher.isEmpty()) {
            return false;
        }
        Payment payment = new Payment();
        payment.setVoucher(voucher.get());
        payment.setPaymentMethod(paymentMethod.get());
        payment.setEmail(paymentDTO.getEmail());
        payment.setStatus(0);
        payment.setAddress(paymentDTO.getAddress());
        payment.setCreateDate(LocalDate.now());
        payment.setTransportFee(paymentDTO.getTransportFee());
        payment.setShoppingCart(cart);
        paymentRepository.save(payment);
        return true;
    }

    @Override
    public Payment updatePayment(long id, Map<String, Object> fields) {
        Optional<Payment> existingPayment = paymentRepository.findById(id);

        if (existingPayment.isPresent()) {
            fields.forEach((key, value) ->
            {
                Field field = ReflectionUtils.findField(Product.class, key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingPayment.get(), value);
            });
            return paymentRepository.save(existingPayment.get());
        }
        return null;
    }

    @Override
    public PaymentResponse findById(long shoppingCart) {
        PaymentResponse paymentResponse = new PaymentResponse();

        Payment payment = paymentRepository.GetPayment(shoppingCart);
        if (ObjectUtils.isEmpty(payment)){
            return null;
        }
        List<ProductListDTO> productListDTO = shoppingCartService.getAllItem(userService.findUserByUserName().getUserId());
        paymentResponse.setVoucher(payment.getVoucher().getDescription());
        paymentResponse.setPaymentMethod(payment.getPaymentMethod().getDescription());
        paymentResponse.setEmail(payment.getEmail());
        paymentResponse.setAddress(payment.getAddress());
        paymentResponse.setTransportFee(payment.getTransportFee());
        paymentResponse.setProductListDTOS(productListDTO);
        return paymentResponse;
    }

}
