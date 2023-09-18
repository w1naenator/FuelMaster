package lv.ami.fuelmaster.ApiControllers;

import java.net.InetAddress;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLServerSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.ami.fuelmaster.dtos.WarehouseDto;
import lv.ami.fuelmaster.models.Warehouse;
import lv.ami.fuelmaster.service.WarehouseService;

@RestController
@RequestMapping("/api")
public class StockApiController {

   @Autowired
    private WarehouseService warehouseService;
   
   @Autowired
   ServerProperties serverProperties;

   @GetMapping("/publickey")
   public ResponseEntity<Map<String, Object>> getPublicKey() throws Exception {
       SSLContext sslContext = SSLContext.getDefault();
       SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
       SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(0); // 0 means any available port
       int port = serverSocket.getLocalPort();
       String serverName = InetAddress.getLocalHost().getHostName();
       SSLSessionContext sessionContext = sslContext.getServerSessionContext();
       SSLSession session = sessionContext.getSession(serverName.getBytes()); // use the server name as the session ID
       X509Certificate cert = (X509Certificate) session.getPeerCertificates()[0];
       byte[] publicKey = cert.getPublicKey().getEncoded();
       String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey);
       Date expirationDate = cert.getNotAfter();
       Map<String, Object> response = new HashMap<>();
       response.put("publicKey", publicKeyBase64);
       response.put("expirationDate", expirationDate);
       
       return ResponseEntity.ok(response);
   }

   
   
   @GetMapping("/stocks")
   public ResponseEntity<List<WarehouseDto>> listProducts() {
       List<Warehouse> warehouses = warehouseService.findAll();
       List<WarehouseDto> stockdtos = new ArrayList<>();
       for (Warehouse warehouse : warehouses) {
           WarehouseDto dto = new WarehouseDto();
           dto.setId(warehouse.getId());
           dto.setName(warehouse.getName());
           stockdtos.add(dto);
       }
       return ResponseEntity.ok().body(stockdtos);
   }

   @GetMapping("/stocks/{id}")
   public ResponseEntity<WarehouseDto> getStockById(@PathVariable(value = "id") Long id) {
       Warehouse hydraulicRotatorTest = warehouseService.findById(id);
       if (hydraulicRotatorTest != null) {
           lv.ami.fuelmaster.dtos.WarehouseDto dto = warehouseService.convertWarehousetoDto(hydraulicRotatorTest);
           return ResponseEntity.ok().body(dto);
       } else {
           //return ResponseEntity.notFound().build();
    	   return ResponseEntity.ok().body(new WarehouseDto());
       }
   }

    @PostMapping("/stocks")
    public WarehouseDto createStock(@RequestBody WarehouseDto warehouseDto) {
    	Warehouse warehouse = warehouseService.convertDtoToWarehouse(warehouseDto);
        warehouseService.save(warehouse);
        warehouseDto = warehouseService.convertWarehousetoDto(warehouse);
        return warehouseDto;
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<Warehouse> updateStock(@PathVariable(value = "id") Long id, @RequestBody Warehouse warehouse) {
        Warehouse existingStock = warehouseService.findById(id);
        if (existingStock != null) {
            Warehouse updatedStock = existingStock;
            updatedStock.setName(warehouse.getName());
            updatedStock.setShelves(warehouse.getShelves());
            return ResponseEntity.ok().body(warehouseService.saveAndReturn(updatedStock));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable(value = "id") Long id) {
        Warehouse warehouse = warehouseService.findById(id);
        if (warehouse != null) {
            warehouseService.delete(warehouse);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<WarehouseDto> getAllStocks() {
        return warehouseService.convertStocksToDTOs(warehouseService.findAll());
    }
}
