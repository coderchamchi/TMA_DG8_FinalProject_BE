package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.CategoryService;
import com.bezkoder.springjwt.Service.UserService;
import com.bezkoder.springjwt.config.jwt.AuthEntryPointJwt;
import com.bezkoder.springjwt.dto.*;
import com.bezkoder.springjwt.entities.Product;
import com.bezkoder.springjwt.Service.ProductService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.bezkoder.springjwt.entities.ERole.ROLE_ADMIN;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("ProjectSJ/Product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @GetMapping("/all")
    public ResponseEntity<List<ProductListDTO>> getAllProduct() {
        List<ProductListDTO> listProduct = productService.GetAllProduct();
        return new ResponseEntity<List<ProductListDTO>>(listProduct, HttpStatus.OK);
    }

    @GetMapping("/listProductIdIsOdd")
    public ResponseEntity<List<ProductListDTO>> getAllProductIdIsOdd(){
        List<ProductListDTO> productListDTOS = productService.GetAllProduct_IdOdd();
        return new ResponseEntity<List<ProductListDTO>>(productListDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailById> getProductById(@PathVariable("id") long id) {
        ProductDetailById productDetailById = productService.getProductbyid(id);

        if (ObjectUtils.isNotEmpty(productDetailById)) {
            return new ResponseEntity<>(productDetailById, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/proOutstanding")
    public ResponseEntity<List<ProductListDTO>> getProOutstanding(){
        List<ProductListDTO> listProduct = productService.getProductSortByPrice()
                .stream()
                .limit(5)
                .collect(Collectors.toList());
        return new ResponseEntity<List<ProductListDTO>>(listProduct, HttpStatus.OK);
    }

    @GetMapping("/relation/{id}")
    public ResponseEntity<List<ProductListDTO>> getProductRelation(@PathVariable("id") long id) {
        List<ProductListDTO> listProduct =  productService.getProductsRelation(id);
        return new ResponseEntity<List<ProductListDTO>>(listProduct, HttpStatus.OK);
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<ArrayList<ProductListDTO>> getProductsByCategory(@PathVariable("id") long id){
        Optional<CategoryDTO> optional = categoryService.getCategorybyid(id);
        if (ObjectUtils.isNotEmpty(optional)){
            ArrayList<ProductListDTO> listProduct = productService.getproductbycategory(id);
            return new ResponseEntity<ArrayList<ProductListDTO>>(listProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categoryname")
    public ResponseEntity<List<Product>> getproductsbycategoryname(@RequestParam("query") String query){
        try {
            List<Product> products = productService.getproductbycategoryname(query);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/searchbyname")
    public ResponseEntity<ArrayList<Product>> getproductbyname(@RequestParam("query") String query){
        try {
            ArrayList<Product> product = productService.getProductbyName(query);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseJson<Boolean>> addProduct(@Validated @RequestBody ProductSaveRequest productRequest)
                                                            //sử dụng @RBody để nó tự động convert JSON thành đối tượng DTO
                                                            //sử dụng @Validated để check xem những trường bên entity cấu hình
                                                            //so với data lúc nhân được có phù hợp hay không
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean checkAuth = false;
        for(GrantedAuthority role : auth.getAuthorities()){
            if(role.toString().equals(String.valueOf(ROLE_ADMIN))){
                checkAuth = true;
            }
        }
        if (!checkAuth) {
            logger.error("Error: User has not Permission");
            return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.FORBIDDEN, "Error: User has not Permission"));
        }
        boolean check = productService.saveProduct(productRequest);
        if(!check){
            logger.error("Error: Can not add the product");
            return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Error: Can not add the product"));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Add Successed"));
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseJson<Boolean>> updateProduct(@PathVariable("id") Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean checkAuth = false;
        for(GrantedAuthority role : auth.getAuthorities()){
            if(role.toString().equals(String.valueOf(ROLE_ADMIN))){
                checkAuth = true;
            }
        }
        if (!checkAuth) {
            return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.FORBIDDEN, "Error: User has not Permission"));
        }
        boolean updateProductbyid = productService.deleteProductbyid(id);
        if (!updateProductbyid){
            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Not Found The Product"));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Deleted Success"));
    }

//    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<ResponseJson<Boolean>> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest){
//
//        boolean updateProductbyid = productService.updateProductbyid(id, productRequest);
//        if (!updateProductbyid){
//            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Not Found The Product"));
//        }
//        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Update Success"));
//    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateproductbypatch(@PathVariable long id, @RequestBody Map<String,Object> fields){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean checkAuth = false;
        for(GrantedAuthority role : auth.getAuthorities()){
            if(role.toString().equals(String.valueOf(ROLE_ADMIN))){
                checkAuth = true;
            }
        }
        if (!checkAuth) {
            return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.FORBIDDEN, "Error: User has not Permission"));
        }
        Product product= productService.updatebypatch(id, fields);
        if(product != null){
            return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<ResponseJson<Boolean>> deleteProductbyid(@PathVariable("id") long id) {
//        Boolean check = productService.deleteProductbyid(id);
//        if (!check){
//            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.NOT_FOUND, "Not Found The Product"));
//        }
//        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Delete Success"));
//    }

//    @GetMapping("/sortbyname")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_USER')")
//    public ResponseEntity<List<Product>> sortproductsbyname(@RequestParam("direction") String direction)
//    {
//        List<Product> products = productService.sortbyproductname(direction);
//        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
//    }

//    @GetMapping("/sortbyprice")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_USER')")
//    public ResponseEntity<List<Product>> sortproductsbyprice(@RequestParam("direction") String direction)
//    {
//        List<Product> products = productService.sortbyproductprice(direction);
//        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
//    }

//    @GetMapping("/sortbynameandprice")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_USER')")
//    public ResponseEntity<List<Product>> sortproductsbynameandprice(@RequestParam("directionname") String directionname,
//                                                                    @RequestParam("directionprice") String directionprice)
//    {
//        List<Product> products = productService.sortbyproductnameandprice(directionname, directionprice);
//        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
//    }

//    @GetMapping("/sortbynameandpriceanddiscount")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_USER')")
//    public ResponseEntity<List<Product>> sortproductsbynameandpriceanddiscount( @RequestParam("directionname") String directionname,
//                                                                                @RequestParam("directionprice") String directionprice,
//                                                                                @RequestParam("directiondiscount") String directiondiscount)
//    {
//        List<Product> products = productService.sortbyproductname_price_discount(directionname, directionprice, directiondiscount);
//        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
//    }

    @GetMapping("/pagination")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Map<String, Object>> getPagging(@RequestParam(defaultValue = "0") int page, //page là số trang muốn phân
                                                          @RequestParam(defaultValue = "9") int size) // size là só sp muốn hiển thị ở 1 trang
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> pageproducts = productService.getPagging(pageable);
        Map <String,Object> data = new HashMap<>();
        data.put("product",pageproducts.getContent());
        data.put("total",pageproducts.getSize());
        data.put("totalItems",pageproducts.getTotalElements());
        data.put("totalPages",pageproducts.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

//    @GetMapping("/getpaggingandsortbyname")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_USER')")
//    public ResponseEntity<Map<String, Object>> getPaggingandsortbyname(
//            @RequestParam(defaultValue = "0") int page, //page là số trang muốn phân
//            @RequestParam(defaultValue = "8") int size,// size là só sp muốn hiển thị ở 1 trang
//            @RequestParam String directionname)
//    {
//        Sort.Order order;
//        if(directionname.equals("asc")){
//            order = new Sort.Order(Sort.Direction.ASC,"productname");
//        }
//        else {
//            order = new Sort.Order(Sort.Direction.DESC,"productname");
//        }
//        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
//        Page<Product> pageproducts = productService.getPagging(pageable);
//        Map <String, Object> data = new HashMap<>();
//        data.put("product", pageproducts.getContent());
//        data.put("total", pageproducts.getSize());
//        data.put("totalItems", pageproducts.getTotalElements());
//        data.put("totalPages", pageproducts.getTotalPages());
//        return new ResponseEntity<>(data, HttpStatus.OK);
//    }

}


