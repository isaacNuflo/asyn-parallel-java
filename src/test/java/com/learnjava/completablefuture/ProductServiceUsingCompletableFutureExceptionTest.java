package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductOption;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    private ProductInfoService productInfoService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture;

    @Test
    void retrieveProductDetailsWithInventory_approach2() {
        String productId = "ABC123";

        when(productInfoService.retrieveProductInfo(anyString())).thenCallRealMethod();
        when(reviewService.retrieveReviews(anyString())).thenThrow(new RuntimeException("Exception Occurred"));
        when(inventoryService.addInventory(any(ProductOption.class))).thenCallRealMethod();

        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory_approach2(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
        assertEquals(0, product.getReview().getNoOfReviews());
        assertEquals(0.0, product.getReview().getOverallRating());
    }

    @Test
    void retrieveProductDetailsWithInventory_productInfoServiceError() {
        String productId = "ABC123";

        when(productInfoService.retrieveProductInfo(anyString())).thenThrow(new RuntimeException("Exception Occurred"));
        when(reviewService.retrieveReviews(anyString())).thenCallRealMethod();

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory_approach2(productId));
        assertEquals("Exception Occurred", runtimeException.getCause().getMessage());
    }

    @Test
    void retrieveProductDetailsWithInventory_inventoryServiceError() {
        String productId = "ABC123";

        when(productInfoService.retrieveProductInfo(anyString())).thenCallRealMethod();
        when(reviewService.retrieveReviews(anyString())).thenCallRealMethod();
        when(inventoryService.addInventory(any(ProductOption.class))).thenThrow(new RuntimeException("Exception Occurred"));

        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory_approach2(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
    }
}
