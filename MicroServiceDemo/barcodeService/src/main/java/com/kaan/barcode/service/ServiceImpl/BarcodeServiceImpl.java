package com.kaan.barcode.service.ServiceImpl;

import com.kaan.barcode.BarcodeDto.RequestBarcode;
import com.kaan.barcode.BarcodeDto.ResponceBarcode;
import com.kaan.barcode.barcodeMapper.BarcodeToResponce;
import com.kaan.barcode.barcodeTypes;
import com.kaan.barcode.entity.Barcode;
import com.kaan.barcode.feign.productInterface;
import com.kaan.barcode.repository.BarcodeRepository;
import com.kaan.barcode.service.IBarcodeService;
import com.kaan.category.exeption.BaseException;
import com.kaan.category.exeption.ErrorMessage;
import com.kaan.category.exeption.messageType;
import com.kaan.product.ProductResponce.BarcodeResponce;
import com.kaan.product.ProductResponce.ProductResponce;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.kaan.barcode.barcodeMapper.requestToBarcode;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BarcodeServiceImpl implements IBarcodeService {
    private final BarcodeRepository barcodeRepository;
    private final productInterface productInterface;

    @Override
    @Transactional
    public ResponceBarcode generateBarcode(RequestBarcode request) {

        ProductResponce product = productInterface.getProductById(request.getProductId());

        Barcode barcode = new Barcode();
        ResponceBarcode responceBarcode = new ResponceBarcode();

        switch (Objects.requireNonNull(request.getCategoryCode())) {
            case "MY" -> {
                if ("KILOGRAM".equalsIgnoreCase(String.valueOf(product.getUnit()))) {
                    RequestBarcode cash = generateCashCode();
                    barcode.setType(barcodeTypes.CASH);
                    barcode.setExtraBarcode(cash.getCode());}}
            case "BL" -> {
                if ("ADET".equalsIgnoreCase(String.valueOf(product.getUnit()))) {
                    RequestBarcode cash = generateCashCode();
                    barcode.setType(barcodeTypes.CASH);
                    barcode.setExtraBarcode(cash.getCode());}
                if ("KILOGRAM".equalsIgnoreCase(String.valueOf(product.getUnit()))) {
                    RequestBarcode scale = generateScaleCode();
                    barcode.setType(barcodeTypes.SCALE);
                    String concat = product.getProductCode() + scale.getCode();
                    barcode.setExtraBarcode(concat);
                    responceBarcode.setExtraBarcode(concat);}}
            case "ET", "KE" -> {
                RequestBarcode scale = generateScaleCode();
                barcode.setType(barcodeTypes.SCALE);
                String concat = product.getProductCode() + scale.getCode();
                barcode.setExtraBarcode(concat);
                responceBarcode.setExtraBarcode(concat);}
            default -> {
                barcode.setType(barcodeTypes.NORMAL);
                barcode.setExtraBarcode(null);}}
        barcode.setProductId(product.getId());
        Random random = new Random();
        int number = random.nextInt(1_000_000_000);
        String code = String.format("%09d", number);
        barcode.setCode(code);
        barcodeRepository.save(barcode);
        new BarcodeToResponce(barcode, responceBarcode);
        return responceBarcode;
    }
    @Override
    public ResponceBarcode updateBarcode(Long id, RequestBarcode request) {

        ProductResponce product = productInterface.getProductById(request.getProductId());
        ResponceBarcode responceBarcode = new ResponceBarcode();
        if (product == null) {
            throw new BaseException(new ErrorMessage(messageType.NO_RECORD_EXIST, String.valueOf(request.getProductId())));
        }
        switch (Objects.requireNonNull(request.getCategoryCode())) {
            case "MY" -> {
                if ("KILOGRAM".equalsIgnoreCase(String.valueOf(product.getUnit()))) {
                    RequestBarcode cash = generateCashCode();
                    request.setBarcodeType(barcodeTypes.CASH);
                    request.setExtraBarcode(cash.getCode());}}
            case "BL" -> {
                if ("ADET".equalsIgnoreCase(String.valueOf(product.getUnit()))) {
                    RequestBarcode cash = generateCashCode();
                    request.setBarcodeType(barcodeTypes.CASH);
                    request.setExtraBarcode(cash.getCode());}
                if ("KILOGRAM".equalsIgnoreCase(String.valueOf(product.getUnit()))) {
                    RequestBarcode scale = generateScaleCode();
                    request.setBarcodeType(barcodeTypes.SCALE);
                    String concat = product.getProductCode() + scale.getCode();
                    request.setExtraBarcode(concat);
                    responceBarcode.setExtraBarcode(concat);}}
            case "ET", "KE" -> {
                RequestBarcode scale = generateScaleCode();
                request.setBarcodeType(barcodeTypes.SCALE);
                String concat = product.getProductCode() + scale.getCode();
                request.setExtraBarcode(concat);
                responceBarcode.setExtraBarcode(concat);}
            default -> {
                request.setBarcodeType(barcodeTypes.NORMAL);
                request.setExtraBarcode(null);}}
        request.setProductId(product.getId());
        Random random = new Random();
        int number = random.nextInt(1_000_000_000); // 0 - 999,999,999 arası sayı
        String code = String.format("%09d", number);
        request.setCode(code);
        Optional<Barcode> barcodeOpt = barcodeRepository.findByProductId(request.getProductId());
        Barcode barcode;
        if (barcodeOpt.isPresent()) {
            barcode = barcodeOpt.get();
        } else {
            barcode = new Barcode();}
        new requestToBarcode(request,barcode);
        barcodeRepository.save(barcode);
        new BarcodeToResponce(barcode, responceBarcode);
        return responceBarcode;
    }
    @Override
    public RequestBarcode generateBarcodeCode() {
        Random random = new Random();
        int number = random.nextInt(1_000_000_000); // 0 - 999,999,999 arası sayı
        String code = String.format("%09d", number);

        RequestBarcode responceBarcode = new RequestBarcode();
        responceBarcode.setCode(code);
        responceBarcode.setBarcodeType(barcodeTypes.NORMAL);
        return responceBarcode;
    }
    @Override
    public RequestBarcode generateCashCode() {
        Random random = new Random();
        int number = random.nextInt(1_000_0);
        String code = String.format("%04d", number);

        RequestBarcode responceBarcode = new RequestBarcode();
        responceBarcode.setCode(code);
        responceBarcode.setBarcodeType(barcodeTypes.CASH);
        return responceBarcode;
    }
    @Override
    public RequestBarcode generateScaleCode() {
        Random random = new Random();
        int number = random.nextInt(1_000);
        String code = String.format("%03d", number);

        RequestBarcode responceBarcode = new RequestBarcode();
        responceBarcode.setCode(code);
        responceBarcode.setBarcodeType(barcodeTypes.SCALE);
        return responceBarcode;
    }
    @Override
    public ResponceBarcode getBarcodeById(Long id) {
        Optional<Barcode> barcodeOptional = barcodeRepository.findById(id);
        if (barcodeOptional.isPresent()) {
            Barcode barcode = barcodeOptional.get();
            ResponceBarcode responceBarcode = new ResponceBarcode();
            new BarcodeToResponce (barcode, responceBarcode);
            return responceBarcode;
        }
        return null;}
    @Override
    public ResponceBarcode getBarcodeByCode(String Code) {
        List<Barcode> barcodeOptional = barcodeRepository.findAll();
        List<ResponceBarcode> responceBarcodeList = new ArrayList<>();
        for (Barcode barcode : barcodeOptional) {
            if (barcode.getCode().equals(Code)) {
                ResponceBarcode responceBarcode = new ResponceBarcode();
                new BarcodeToResponce (barcode, responceBarcode);
                responceBarcodeList.add(responceBarcode);
                return responceBarcode;}}
        return null;}
    @Override
    public boolean deleteBarcodeById(Long id) {
        Optional<Barcode> barcodeOptional = barcodeRepository.findById(id);
        if (barcodeOptional.isPresent()) {
            barcodeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public BarcodeResponce findByProductId(Long productId) {
        Optional<Barcode> barcodeOptional = barcodeRepository.findByProductId(productId);
        if (barcodeOptional.isPresent()) {
            Barcode barcode = barcodeOptional.get();
            BarcodeResponce responceBarcode = new BarcodeResponce();
            responceBarcode.setCode(barcode.getCode());
            responceBarcode.setType(barcode.getType());
            responceBarcode.setExtraBarcode(barcode.getExtraBarcode());
            responceBarcode.setId(barcode.getId());
            return responceBarcode;
        }
        return null;
    }
    @Override
    public List<ResponceBarcode> getAllBarcodes() {
        List<Barcode> barcodeOptional = barcodeRepository.findAll();
        List<ResponceBarcode> responceBarcodeList = new ArrayList<>();
        for (Barcode barcode : barcodeOptional) {
            ResponceBarcode responceBarcode = new ResponceBarcode();
            new BarcodeToResponce (barcode, responceBarcode);
            responceBarcodeList.add(responceBarcode);
        }
        return responceBarcodeList;
    }
    @Override
    public ResponceBarcode saveBarcode(@RequestBody RequestBarcode barcodeRequest) {

        Barcode barcode = new Barcode();
        new requestToBarcode(barcodeRequest,barcode);
        barcodeRepository.save(barcode);

        ResponceBarcode responceBarcode = new ResponceBarcode();
        new BarcodeToResponce (barcode, responceBarcode);

        return responceBarcode;
    }
}
















