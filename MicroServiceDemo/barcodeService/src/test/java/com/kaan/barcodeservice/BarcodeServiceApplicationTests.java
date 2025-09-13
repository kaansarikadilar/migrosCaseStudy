package com.kaan.barcodeservice;


import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.BarcodeServiceApplication;
import com.kaan.barcode.barcodeMapper.BarcodeToRequest;
import com.kaan.barcode.barcodeTypes;
import com.kaan.barcode.entity.Barcode;
import com.kaan.barcode.service.IBarcodeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        List<ResponceBarcode> barcodes = barcodeService.getAllBarcodes();
        for (ResponceBarcode barcodeResponse : barcodes) {
            System.out.println(barcodeResponse);
        }
    }

    @Test
    public void testGetBarcodeById() {
        ResponceBarcode barcode = barcodeService.getBarcodeById(252L);
        System.out.println("barcode id :" + barcode.getCode());
    }

    @Test
    public void testGetBarcodeByCode() {
        ResponceBarcode barcode = barcodeService.getBarcodeByCode("478580335");
        System.out.println("barcode code :" + barcode.getCode());
        System.out.println("barcode type :" + barcode.getType());
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

        assertNotNull(savedBarcode, "Error saving barcode");
        assertEquals("Random", savedBarcode.getCode(), "Barcode code mismatch");
        assertEquals("extraBarcode random", savedBarcode.getExtraBarcode(), "Barcode code mismatch");

        ResponceBarcode fromDb = barcodeService.getBarcodeByCode(savedBarcode.getCode());
        assertEquals(savedBarcode.getCode(), fromDb.getCode(), "Barcode code mismatch");
        assertEquals(savedBarcode.getExtraBarcode(), fromDb.getExtraBarcode(), "Barcode ExtraCode mismatch");
    }
    @Test
    @Transactional
    void testDeleteBarcodeById() {
        Barcode barcode = new Barcode();
        RequestBarcode barcodeResponse = new RequestBarcode();
        barcode.setCode("Random");
        barcode.setId(1L);
        new BarcodeToRequest(barcode,barcodeResponse);
        barcodeService.saveBarcode(barcodeResponse);
        barcodeService.deleteBarcodeById(1L);
    }
}
