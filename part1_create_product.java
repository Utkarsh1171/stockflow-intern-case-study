@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        try {
            if (request.getName() == null || request.getSku() == null || request.getPrice() == null ||
                request.getWarehouseId() == null || request.getInitialQuantity() == null) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }

            if (productRepository.findBySku(request.getSku()).isPresent()) {
                return ResponseEntity.badRequest().body("SKU must be unique");
            }

            Product product = new Product();
            product.setName(request.getName());
            product.setSku(request.getSku());
            product.setPrice(request.getPrice());

            productRepository.save(product);

            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setWarehouseId(request.getWarehouseId());
            inventory.setQuantity(request.getInitialQuantity());

            inventoryRepository.save(inventory);

            return ResponseEntity.ok(Map.of("message", "Product created", "product_id", product.getId()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
