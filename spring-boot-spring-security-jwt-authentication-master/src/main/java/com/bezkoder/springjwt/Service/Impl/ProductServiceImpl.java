package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.ProductService;

import com.bezkoder.springjwt.Service.SizeService;
import com.bezkoder.springjwt.entities.*;
import com.bezkoder.springjwt.payload.response.ProductRespone;
import com.bezkoder.springjwt.payload.request.ProductRequest;
import com.bezkoder.springjwt.repository.CategoryRepository;
import com.bezkoder.springjwt.repository.ProductRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SizeService sizeService;


    @Override
    public List<Product> GetAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductRespone> getProductbyid(long id) {
        ProductRespone productDTO = new ProductRespone();
        return productRepository.findById(id)
                .map(item -> {
                    productDTO.setProductname(item.getProductname());
                    productDTO.setPrice(item.getPrice());
                    productDTO.setDescription(item.getDescription());
                    productDTO.setCreated_at(item.getCreated_at());
                    productDTO.setUpdated_at(item.getUpdated_at());
                    productDTO.setWarehouse(item.getWarehouse());
                    productDTO.setDiscount(item.getDiscount());
                    productDTO.setSize(item.getListSize());
                    productDTO.setBase64(item.getBase64());
                    productDTO.setCategory(item.getCategory().getCategoryname());
                    return productDTO;
                });

    }

    @Override
    public ArrayList<Product> getProductbyName(String query) {
        return productRepository.findProductbyname(query);}

//    @Override
//    public ArrayList<Product> getProdcuctbyBrand(String query) {
//        return productRepository.findProductbyBrand(query);}

    @Override
    public boolean saveProduct(ProductRequest productDTO) {
        if (ObjectUtils.isNotEmpty(productDTO)) {
            try{
                Optional<Category> categoryid = categoryRepository.findById(productDTO.getCategory());
                if(categoryid.isPresent()) {
                    Product product = new Product();
                    product.setProductname(productDTO.getProductname());
                    product.setPrice(productDTO.getPrice());
                    product.setDescription(productDTO.getDescription());
                    product.setCreated_at(LocalDate.now());
                    product.setUpdated_at(LocalDate.now());
                    product.setWarehouse(productDTO.getWarehouse());
                    product.setDiscount(productDTO.getDiscount());
                    Set<String> sizeinput = productDTO.getSize();
                    Set<Size> listsizes = new HashSet<>();

                    if (sizeinput == null) {
                        Size sizename = sizeService.findBySizeName(ESize.L)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        listsizes.add(sizename);
                    }
                    else
                    {
                        sizeinput.forEach(size -> {
                            switch(size) {
                                case "S":{
                                    Size sizeS = sizeService.findBySizeName(ESize.S)
                                            .orElseThrow(() -> new RuntimeException("Error: Size is not found."));
                                    listsizes.add(sizeS);
                                }
                                case "M": {
                                    Size sizeM = sizeService.findBySizeName(ESize.M)
                                            .orElseThrow(() -> new RuntimeException("Error: Size is not found."));
                                    listsizes.add(sizeM);
                                }
                                default:{
                                    Size sizeL = sizeService.findBySizeName(ESize.L)
                                            .orElseThrow(() -> new RuntimeException("Error: Size is not found."));
                                    listsizes.add(sizeL);
                                }
                            }
                        });
                    }
                    product.setListSize(listsizes);
                    product.setCategory(categoryid.get());
                    class4getpicture getpicture = new class4getpicture();
                    try
                    {
                        String base64 = getpicture.getbase64fromfolder(productDTO.getProductname());
                        product.setBase64(base64);
                    }
                    catch (IOException e)
                    {
                        product.setBase64(null);
                    }
                    productRepository.save(product);
                    return true;
                }
                return false;
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return false;
    }
    @Override
    public boolean updateProductbyid(Long id, ProductRequest productDTO) {
        if (ObjectUtils.isNotEmpty(productDTO)){
            Optional<Category> categoryid = categoryRepository.findById(productDTO.getCategory());
            if (categoryid.isPresent()) {
                try {
                Product product = productRepository.getById(id);
                    product.setProductname(productDTO.getProductname());
                    product.setPrice(productDTO.getPrice());
                    product.setDescription(productDTO.getDescription());
                    product.setCreated_at(LocalDate.now());
                    product.setUpdated_at(LocalDate.now());
                    product.setWarehouse(productDTO.getWarehouse());
                    product.setDiscount(productDTO.getDiscount());
                    Set<String> sizeinput = productDTO.getSize();
                    Set<Size> listsize = new HashSet<>();

                    if (sizeinput == null) {
                        Size sizename = sizeService.findBySizeName(ESize.L)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        listsize.add(sizename);
                    }
                    else
                    {
                        sizeinput.forEach(size -> {
                            switch(size) {
                                case "S":{
                                    Size sizeS = sizeService.findBySizeName(ESize.S)
                                            .orElseThrow(() -> new RuntimeException("Error: Size is not found."));
                                    listsize.add(sizeS);
                                }
                                case "M": {
                                    Size sizeM = sizeService.findBySizeName(ESize.M)
                                            .orElseThrow(() -> new RuntimeException("Error: Size is not found."));
                                    listsize.add(sizeM);
                                }
                                default:{
                                    Size sizeL = sizeService.findBySizeName(ESize.L)
                                            .orElseThrow(() -> new RuntimeException("Error: Size is not found."));
                                    listsize.add(sizeL);
                                }
                            }
                        });
                    }
                    product.setListSize(listsize);
                    product.setCategory(categoryid.get());
                    class4getpicture getpicture = new class4getpicture();
                    try {
                        String base64 = getpicture.getbase64fromfolder(productDTO.getProductname());
                        product.setBase64(base64);
                    } catch (IOException e) {
                        product.setBase64(null);
                    }
                    productRepository.save(product);
                    return true;
                    }
                    catch (Exception e){
                        return false;
                    }

                }
            return false;
        }
        return false;

    }

    @Override
    public ArrayList<Product> getproductbycategory(Long id) {
        ArrayList<Product> products = productRepository.findProductByStatusNative(id);
        return products;
    }

//    @Override
//    public ArrayList<Product> getproductbycategoryname(String query) {
//        ArrayList<Product> products = productRepository.Productbycategoryname(query);
//        return products;
//    }

    @Override
    public Product updatebypatch(long id, Map<String, Object> fields) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if(existingProduct.isPresent()){
        fields.forEach((key, value)->
        {
            Field field = ReflectionUtils.findField(Product.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingProduct.get(), value);
        });
            return productRepository.save(existingProduct.get());
        }
        return null;
    }

    @Override
    public List<Product> sortbyproductname(String direction) {
        if(direction.equals("asc")){
            return productRepository.findAll(Sort.by("productname").ascending());
        }
        else {
            return productRepository.findAll(Sort.by("productname").descending());
        }
    }

    @Override
    public List<Product> sortbyproductprice(String direction) {
        if (direction.equals("asc")){
            return productRepository.findAll(Sort.by("price").ascending());
        }
        else
            return productRepository.findAll(Sort.by("price").descending());
    }

    @Override
    public List<Product> sortbyproductnameandprice(String directionname, String directionprice) {
        if (directionname.equals("asc")){
            if (directionprice.equals("asc")){
                return productRepository.findAll(Sort.by("productname").and(Sort.by("price")));
            }
            else {
                return productRepository.findAll(Sort.by("productname").and(Sort.by("price")).descending());
            }
        }
        else {
            if(directionprice.equals("asc")){
                return productRepository.findAll(Sort.by("productname").descending().and(Sort.by("price")));
            }
            else {
                return productRepository.findAll(Sort.by("productname").descending().and(Sort.by("price").descending()));
            }
        }
    }

    @Override
    public List<Product> sortbyproductname_price_discount(String directionname, String directionprice, String directiondiscount) {
        List<Sort.Order> orderList = new ArrayList<>();
        if(directionname.equals("asc")){
            orderList.add(new Sort.Order(Sort.Direction.ASC,"productname"));
        }
        else {
            orderList.add(new Sort.Order(Sort.Direction.DESC,"productname"));
        }
        if(directionprice.equals("asc")){
            orderList.add(new Sort.Order(Sort.Direction.ASC,"price"));
        }
        else {
            orderList.add(new Sort.Order(Sort.Direction.DESC,"price"));
        }
        if(directiondiscount.equals("asc")){
            orderList.add(new Sort.Order(Sort.Direction.ASC,"discount"));
        }
        else {
            orderList.add(new Sort.Order(Sort.Direction.DESC,"discount"));
        }
        return productRepository.findAll(Sort.by(orderList));
    }

    @Override
    public Page<Product> getPagging(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public boolean deleteProductbyid(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.deleteById(id);
            return true;}
        return false;
    }

    @Override
    public boolean deleteallProduct() {
        productRepository.deleteAll();
        return true;
    }
}


