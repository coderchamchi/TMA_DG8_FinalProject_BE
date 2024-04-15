package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.SizeService;
import com.bezkoder.springjwt.dto.SizeRequestDTO;
import com.bezkoder.springjwt.entities.ESize;
import com.bezkoder.springjwt.entities.Product;
import com.bezkoder.springjwt.entities.Size;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SizeserviceImpl implements SizeService {
    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Optional<Size> findBySizeName(ESize sizename) {
        return sizeRepository.findBySizeName(sizename);
    }

    @Override
    public boolean saveSize(SizeRequestDTO sizeRequestDTO) {
        if(ObjectUtils.isEmpty(sizeRequestDTO)){
            return false;
        }
        Optional<Product> product = productRepository.findById(sizeRequestDTO.getProduct());
        if (product.isEmpty()){
            return false;
        }
        Size size = new Size();
        size.setSizeName(sizeRequestDTO.getSizeName());
        size.setPrice(sizeRequestDTO.getPrice());
        size.setUpdateDate(LocalDate.now());
        size.setProduct(product.get());
        sizeRepository.save(size);
        return true;
    }

    @Override
    public List<Size> findByIdProduct(long product) {
        return sizeRepository.findByIdProduct(product);
    }

    @Override
    public Optional<Size> findByIdSize(long size) {
        return sizeRepository.findById(size);
    }

}
