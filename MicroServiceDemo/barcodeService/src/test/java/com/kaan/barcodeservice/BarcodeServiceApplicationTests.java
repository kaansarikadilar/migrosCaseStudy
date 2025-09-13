package com.kaan.barcodeservice;


import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeServiceApplication;
import com.kaan.barcode.barcodeTypes;
import com.kaan.barcode.entity.Barcode;
import com.kaan.barcode.service.IBarcodeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = BarcodeServiceApplication.class)
class BarcodeServiceApplicationTests {


    private final IBarcodeService barcodeService;
    @Autowired
    public BarcodeServiceApplicationTests(IBarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    @BeforeEach
    void contextLoads() {
        System.out.println("Working");
    }
    @Test
    public void testGetAllBarcodes() {
        List<Barcode> barcodes = barcodeService.getAllBarcodes();
        for(Barcode barcodeResponse :barcodes){
            System.out.println(barcodeResponse);
        }
    }
    @Test
    public void testGetBarcodeById() {
        Barcode barcode = barcodeService.getBarcodeById(252L);
        System.out.println("barcode id :" +barcode.getId());
        System.out.println("product id :" +barcode.getProductId());
    }
    @Test
    public void testGetBarcodeByCode() {
        Barcode barcode = barcodeService.getBarcodeByCode("478580335");
        System.out.println("barcode id :" +barcode.getId());
        System.out.println("product id :" +barcode.getProductId());
    }
    @Test
    @Transactional
    public void testSaveBarcode() {
        RequestBarcode requestBarcode = new RequestBarcode();
        requestBarcode.setCode("Random");
        requestBarcode.setExtraBarcode("extraBarcode random");
        requestBarcode.setProductId(252L);
        requestBarcode.setBarcodeType(barcodeTypes.CASH);
        var savedBarcode = barcodeService.saveBarcode(requestBarcode);

        assertNotNull(savedBarcode,"Error saving barcode");
        assertEquals("Random",savedBarcode.getCode(),"Barcode code mismatch");
        assertEquals("extraBarcode random",savedBarcode.getExtraBarcode(),"Barcode code mismatch");

        Barcode fromDb = barcodeService.getBarcodeByCode(savedBarcode.getCode());
        assertEquals(savedBarcode.getCode(),fromDb.getCode(),"Barcode code mismatch");
        assertEquals(savedBarcode.getProductId(),fromDb.getProductId(),"Barcode productId mismatch");
        assertEquals(savedBarcode.getExtraBarcode(),fromDb.getExtraBarcode(),"Barcode ExtraCode mismatch");
        assertEquals(savedBarcode.getBarcodeType(),fromDb.getType(),"Barcode Type mismatch");
    }
    @Test
    @Transactional
    public void testDeleteBarcodeById() {
        Barcode barcode = barcodeService.getBarcodeById(252L);
        barcodeService.deleteBarcodeById(barcode.getId());
        assertNotNull(barcode,"Barcode not found");
    }


}
