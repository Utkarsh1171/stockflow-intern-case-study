@RestController
@RequestMapping("/api/companies")
public class AlertController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping("/{companyId}/alerts/low-stock")
    public ResponseEntity<?> getLowStockAlerts(@PathVariable Long companyId) {
        List<LowStockAlert> alerts = new ArrayList<>();
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        List<SaleSummary> recentSales = saleRepository.findRecentSales(companyId, cutoff);

        for (SaleSummary sale : recentSales) {
            Inventory inventory = inventoryRepository.findByProductIdAndWarehouseId(sale.getProductId(), sale.getWarehouseId());
            Product product = productRepository.findById(sale.getProductId()).orElse(null);
            Warehouse warehouse = warehouseRepository.findById(sale.getWarehouseId()).orElse(null);
            Supplier supplier = supplierRepository.findByProductId(sale.getProductId());

            if (inventory == null || product == null || warehouse == null) continue;

            int threshold = product.getLowStockThreshold() != null ? product.getLowStockThreshold() : 20;
            if (inventory.getQuantity() >= threshold) continue;

            double avgDailySales = sale.getTotalSold() / 30.0;
            Integer daysUntilStockout = avgDailySales > 0 ? (int)(inventory.getQuantity() / avgDailySales) : null;

            alerts.add(new LowStockAlert(
                product.getId(),
                product.getName(),
                product.getSku(),
                warehouse.getId(),
                warehouse.getName(),
                inventory.getQuantity(),
                threshold,
                daysUntilStockout,
                supplier != null ? new SupplierInfo(supplier) : null
            ));
        }

        return ResponseEntity.ok(Map.of("alerts", alerts, "total_alerts", alerts.size()));
    }
}
