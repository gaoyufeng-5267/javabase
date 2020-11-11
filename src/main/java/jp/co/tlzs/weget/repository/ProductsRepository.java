package jp.co.tlzs.weget.repository;

import jp.co.tlzs.weget.entity.Products;
import org.springframework.data.repository.CrudRepository;

public interface ProductsRepository extends CrudRepository<Products, String> {
//    @Async
//    @Query("select a from Products a ")
//    CompletableFuture<List<Products>> findAllProducts();
}
