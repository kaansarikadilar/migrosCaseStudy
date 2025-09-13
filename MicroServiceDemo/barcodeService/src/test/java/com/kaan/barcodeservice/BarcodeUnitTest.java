package com.kaan.barcodeservice;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.barcodeTypes;
import com.kaan.barcode.entity.Barcode;
import com.kaan.barcode.feign.productInterface;
import com.kaan.barcode.repository.BarcodeRepository;
import com.kaan.barcode.service.ServiceImpl.BarcodeServiceImpl;
import static org.mockito.ArgumentMatchers.any;
import com.kaan.product.Entity.Product;
import com.kaan.product.Enums;
import com.kaan.product.ProductResponce.ProductResponce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BarcodeUnitTest {

    @Mock
    private BarcodeRepository barcodeRepository;

    private BarcodeServiceImpl barcodeService;

    @Mock
    private productInterface  productInterafce;

    @BeforeEach
    void setUp() {
        barcodeService = new BarcodeServiceImpl(barcodeRepository,productInterafce);
    }
    @Test
    void testSaveBarcode() {
        RequestBarcode requestBarcode1 = new RequestBarcode();
        requestBarcode1.setCode("testCode");
        requestBarcode1.setCategoryCode("1");
        requestBarcode1.setBarcodeType(barcodeTypes.valueOf("NORMAL"));
        requestBarcode1.setExtraBarcode(null);

        Barcode barcode = new Barcode();
        barcode.setCode("testCode");
        barcode.setProductId(1L);
        barcode.setExtraBarcode(null);
        barcode.setType(barcodeTypes.valueOf("NORMAL"));

        when(barcodeRepository.save(any(Barcode.class))).thenReturn(barcode);

        barcodeService.saveBarcode(requestBarcode1);

        assertNotNull(requestBarcode1,"barcode not saved");
        assertEquals("testCode",requestBarcode1.getCode(),"barcode code not saved");
        assertEquals("1",requestBarcode1.getCategoryCode(),"barcode category code not saved");
    }
    @Test
    void testGetBarcodeById() {
        Barcode barcode = new Barcode();
        barcode.setCode("testCode");
        barcode.setProductId(1L);
        barcode.setExtraBarcode(null);
        barcode.setType(barcodeTypes.valueOf("NORMAL"));
        when(barcodeRepository.findById(1L)).thenReturn(Optional.of(barcode));
        Barcode barcode1 = barcodeService.getBarcodeById(1L);

        assertNotNull(barcode1,"barcode not found");
        assertEquals("testCode",barcode1.getCode(),"barcode code not found");
        assertEquals(1,barcode1.getProductId(),"barcode product id not found");
        assertEquals(barcodeTypes.valueOf("NORMAL"),barcode1.getType(),"barcode extra code not found");
    }
    @Test
    void testGetAllBarcodes() {
        List<Barcode> barcode = Collections.singletonList(new Barcode(1L, "testCode", null, barcodeTypes.NORMAL, 1L));
        when(barcodeRepository.findAll()).thenReturn(barcode);

        List<Barcode> barcodeList = barcodeService.getAllBarcodes();
        assertNotNull(barcodeList,"barcode not found");
        assertEquals(1,barcodeList.size(),"barcode size not found");
        assertEquals("testCode",barcodeList.get(0).getCode(),"barcode code not found");
        verify(barcodeRepository).findAll();
    }
    @Test
    void testDeleteBarcodeById() {
        Barcode barcode = new Barcode(1L, "testCode", null, barcodeTypes.NORMAL, 1L);
        when(barcodeRepository.findById(1L)).thenReturn(Optional.of(barcode));
        barcodeService.deleteBarcodeById(1L);

        assertNotNull(barcode,"barcode not found");

    }
    @Test
    void testGetBarcodeByCode(){
        Barcode barcode = new Barcode(1L, "testCode", null, barcodeTypes.NORMAL, 1L);
        List<Barcode> barcodeList = List.of(barcode);

        when(barcodeRepository.findAll()).thenReturn(barcodeList);

        Barcode barcode1 = barcodeService.getBarcodeByCode("testCode");
        assertNotNull(barcode1,"barcode not found");
        assertEquals("testCode",barcode1.getCode(),"barcode code not found");
        assertEquals(1,barcode1.getProductId(),"barcode product id not found");
    }
    @Test
    @Disabled
    void testSaveBarcode_MY_Kilogram() {
        RequestBarcode request = new RequestBarcode();
        request.setProductId(1L);
        request.setCategoryCode("MY");

        ProductResponce product = new ProductResponce();
        product.setId(1L);
        product.setProductCode("MY");
        product.setUnit(Enums.valueOf("KILOGRAM"));

        ProductResponce product1;
//        product1 = productInterface.getProductById(request.getProductId());
        when(barcodeRepository.findByProductId(1L)).thenReturn(Optional.empty());
        when(barcodeRepository.save(any(Barcode.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponceBarcode result = barcodeService.updateBarcode(1L, request);

        assertNotNull(result);
        assertEquals(barcodeTypes.CASH, result.getType());
        assertNotNull(result.getExtraBarcode());
    }
    @Test
    @Disabled
    void testUpdateBarcode_MY_Kilogram() {
        RequestBarcode request = new RequestBarcode();
        request.setProductId(1L);
        request.setCategoryCode("MY");

        ProductResponce product = new ProductResponce();
        product.setId(1L);
        product.setProductCode("MY");
        product.setUnit(Enums.valueOf("KILOGRAM"));

        when(barcodeRepository.findByProductId(1L)).thenReturn(Optional.empty());
        when(barcodeRepository.save(any(Barcode.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponceBarcode result = barcodeService.updateBarcode(1L, request);

        ArgumentCaptor<Barcode> barcodeCaptor = ArgumentCaptor.forClass(Barcode.class);
        verify(barcodeRepository).save(barcodeCaptor.capture());
        Barcode savedBarcode = barcodeCaptor.getValue();

        assertNotNull(result);
        assertEquals(savedBarcode.getCode(), result.getCode(), "Kod response ile aynı olmalı");
        assertEquals(barcodeTypes.CASH, savedBarcode.getType(), "MY + KILOGRAM → CASH olmalı");
        assertNotNull(savedBarcode.getExtraBarcode(), "Extra barcode üretilmiş olmalı");
        assertEquals(1L, savedBarcode.getProductId());
    }
}
