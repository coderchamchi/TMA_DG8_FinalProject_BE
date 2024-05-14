package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.ProductService;

import com.bezkoder.springjwt.Service.SizeService;
import com.bezkoder.springjwt.dto.ProductDetailById;
import com.bezkoder.springjwt.dto.ProductListDTO;
import com.bezkoder.springjwt.dto.ProductSaveRequest;
import com.bezkoder.springjwt.entities.*;
import com.bezkoder.springjwt.repository.CategoryRepository;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.SizeRepository;
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

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<ProductListDTO> GetAllProduct() {
        List<ProductListDTO> listProducts = new ArrayList<>(); // Tạo danh sách rỗng

        productRepository.GetAllProduct().forEach(item -> {
            Optional<Size> size = sizeRepository.findByIdProduct(item.getIdProduct()).stream().findFirst();
            if (size.isPresent()) {
                ProductListDTO productListDTO = new ProductListDTO(); // Tạo đối tượng mới bên trong lambda
                productListDTO.setPrice(size.get().getPrice());
                productListDTO.setProductName(item.getProductName());
                productListDTO.setProductDescription(item.getProductDescription());
                productListDTO.setBase64(item.getBase64());
                productListDTO.setIdProduct(item.getIdProduct());
                productListDTO.setIdCategory(item.getCategory().getCategoryId());
                listProducts.add(productListDTO); // Thêm đối tượng vào danh sách
            }
        });

        return listProducts;
    }

    @Override
    public List<ProductListDTO> getProductsRelation(long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            Optional<Category> category = categoryRepository.findById(product.get().getCategory().getCategoryId());
            if(category.isPresent()){
                List<Product> products = productRepository.GetProductByCategory(category.get().getCategoryId());
                List<ProductListDTO> listProducts = new ArrayList<>(); // Tạo danh sách rỗng
                products.forEach(item ->
                {
                    Optional<Size> size = sizeRepository.findByIdProduct(item.getIdProduct()).stream().findFirst();
                    if (size.isPresent()) {
                        ProductListDTO productListDTO = new ProductListDTO(); // Tạo đối tượng mới bên trong lambda
                        productListDTO.setPrice(size.get().getPrice());
                        productListDTO.setProductName(item.getProductName());
                        productListDTO.setProductDescription(item.getProductDescription());
                        productListDTO.setBase64(item.getBase64());
                        productListDTO.setIdProduct(item.getIdProduct());
                        productListDTO.setIdCategory(item.getCategory().getCategoryId());
                        listProducts.add(productListDTO); // Thêm đối tượng vào danh sách
                    }
                });
                return listProducts;
            }
            return null;
        }
        return null;
    }

    @Override
    public List<ProductListDTO> getProductSortByPrice() {
        List<ProductListDTO> listProducts = new ArrayList<>(); // Tạo danh sách rỗng

        productRepository.GetAllProduct().forEach(item -> {
            Optional<Size> size = sizeRepository.findByIdProduct(item.getIdProduct()).stream().findFirst();
            if (size.isPresent()) {
                ProductListDTO productListDTO = new ProductListDTO(); // Tạo đối tượng mới bên trong lambda
                productListDTO.setPrice(size.get().getPrice());
                productListDTO.setProductName(item.getProductName());
                productListDTO.setProductDescription(item.getProductDescription());
                productListDTO.setBase64(item.getBase64());
                productListDTO.setIdProduct(item.getIdProduct());
                listProducts.add(productListDTO); // Thêm đối tượng vào danh sách
            }
        });
        Collections.sort(listProducts, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
        // function Collections.sort has 2 param, listProducts is list is needed sort and
        // parameter second (p1, p2) is interface to sort
        return listProducts;
    }


    @Override
    public ProductDetailById getProductbyid(long id) {
        Optional<Product> product = productRepository.findById(id);
        ProductDetailById productDetailById = new ProductDetailById();
        if(product.isPresent()){
            Product productData = product.get();
            List<Size> sizes = sizeRepository.findByIdProduct(productData.getIdProduct());
            productDetailById.setIdProduct(productData.getIdProduct());
            productDetailById.setProductName(productData.getProductName());
            productDetailById.setBase64(productData.getBase64());
            productDetailById.setProductDescription(productData.getProductDescription());
            productDetailById.setSizes(sizes);
        }
        return productDetailById;
    }


    @Override
    public ArrayList<Product> getProductbyName(String query) {
        return productRepository.findProductbyname(query);}

    @Override
    public boolean saveProduct(ProductSaveRequest productDTO) {
        if (ObjectUtils.isNotEmpty(productDTO)) {
            try{
                Optional<Category> idCategory = categoryRepository.findById(productDTO.getCategory());
                if(idCategory.isPresent()) {
                    Product product = new Product();
                    product.setProductName(productDTO.getProductName());
                    product.setProductDescription(productDTO.getProductDescription());
                    product.setCreateDate(LocalDate.now());
                    product.setUpdateDate(LocalDate.now());
                    product.setStatus(1);
                    product.setCategory(idCategory.get());
                    class4getpicture getPicture = new class4getpicture();
                    try
                    {
                        String base64 = getPicture.getbase64fromfolder(productDTO.getProductName());
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
    public ArrayList<ProductListDTO> getproductbycategory(Long id) {
        ArrayList<Product> products = productRepository.findProductByStatusNative(id);
        ArrayList<ProductListDTO> listProducts = new ArrayList<>();
        products.forEach(item ->
        {
            Optional<Size> size = sizeRepository.findByIdProduct(item.getIdProduct()).stream().findFirst();
            if (size.isPresent()) {
                ProductListDTO productListDTO = new ProductListDTO(); // Tạo đối tượng mới bên trong lambda
                productListDTO.setPrice(size.get().getPrice());
                productListDTO.setProductName(item.getProductName());
                productListDTO.setProductDescription(item.getProductDescription());
                productListDTO.setBase64(item.getBase64());
                productListDTO.setIdProduct(item.getIdProduct());
                productListDTO.setIdCategory(item.getCategory().getCategoryId());
                listProducts.add(productListDTO); // Thêm đối tượng vào danh sách
            }
        });
        return listProducts;
    }

    @Override
    public List<Product> getproductbycategoryname(String query) {
        Category category = categoryRepository.getCategoryByName(query);
        return productRepository.GetProductByCategory(category.getCategoryId());
    }

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
            Product productUpdate = product.get();
            productUpdate.setStatus(0);
            productRepository.save(productUpdate);
            return true;
        }
        return false;
    }
}


