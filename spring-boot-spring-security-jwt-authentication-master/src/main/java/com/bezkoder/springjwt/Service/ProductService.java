package com.bezkoder.springjwt.Service;


import com.bezkoder.springjwt.dto.ProductDetailById;
import com.bezkoder.springjwt.dto.ProductListDTO;
import com.bezkoder.springjwt.dto.ProductSaveRequest;
import com.bezkoder.springjwt.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    //Get all Product
    List<ProductListDTO> GetAllProduct();

    //Get Product by id
    ProductDetailById getProductbyid(long id);

    //get Product by name
    ArrayList<Product> getProductbyName(String query);

    //get product by truong/brand ="DUE"
//    ArrayList<Product> getProdcuctbyBrand(String query);


    //delete Product by id
    boolean deleteProductbyid(long id);

    boolean saveProduct(ProductSaveRequest productDTO);

    //get product by category id
    ArrayList<ProductListDTO> getproductbycategory(Long id);

//    ArrayList<Product> getproductbycategoryname(String query);

    List<Product> getproductbycategoryname(String query);

    //update by patch
    Product updatebypatch(long id, Map<String, Object> fields);

    List<Product> sortbyproductname(String direction);

    List<Product> sortbyproductprice(String direction);

    List<Product> sortbyproductnameandprice(String directionname, String directionprice);

    List<Product> sortbyproductname_price_discount(String directionname, String directionprice, String directiondiscount);

    Page<Product> getPagging(Pageable pageable);

    List<ProductListDTO> GetAllProduct_IdOdd();

    List<ProductListDTO> getProductsRelation(long id);

    List<ProductListDTO> getProductSortByPrice();
}
