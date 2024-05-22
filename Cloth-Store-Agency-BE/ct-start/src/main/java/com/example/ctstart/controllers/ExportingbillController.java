package com.example.ctstart.controllers;

import com.example.ctapi.dtos.response.ExportingBillDto;
import com.example.ctapi.dtos.response.ExportingBillFullDto;
import com.example.ctapi.dtos.response.ResponseDto;
import com.example.ctapi.dtos.response.SocketMessage;
import com.example.ctapi.serviceImpl.IExportingServiceImpl;
import com.example.ctapi.services.IExportingbillService;
import com.example.ctcoremodel.ProductModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/Exportingbill")
public class ExportingbillController {
    private static final Logger logger = LoggerFactory.getLogger(IExportingServiceImpl.class);
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private IExportingbillService exportingbillService;

    @PostMapping("/create")
    public ResponseEntity<?> createBill(@RequestBody ExportingBillFullDto exportingBillFullDto) {
        try {
            exportingbillService.createExportingbill(exportingBillFullDto);
            return ResponseEntity.ok(new ResponseDto(List.of("create bill success"),
                    HttpStatus.CREATED.value(), exportingBillFullDto));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new ResponseDto(List.of("Can not created bill"),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExportingById(HttpServletRequest request, @PathVariable String id) {
        try {
            var result = exportingbillService.getExportingById(request, id);
            return ResponseEntity.ok(new ResponseDto(List.of("data get sucess"),
                    HttpStatus.OK.value(), result));
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error get Payment: " + e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deteleExporting(@PathVariable String id) {
        try {
            exportingbillService.deleteExportingFullByid(id);
            return ResponseEntity.ok(new ResponseDto(List.of("get all importing success"),
                    HttpStatus.OK.value(), "deleted : " + id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new ResponseDto(List.of("get all importing unsuccess"),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    @PostMapping("/getAllExportingBill")
    public ResponseEntity<?> getAllExportingbill(HttpServletRequest request) {
        try {
            List<ExportingBillFullDto> result = exportingbillService.getAllExportingbill(request);
            return ResponseEntity.ok(new ResponseDto(List.of("get all bill success"),
                    HttpStatus.OK.value(), result));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new ResponseDto(List.of("get all bill unsuccess"),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    @PostMapping("/findExportingAll")
    private ResponseEntity<?> seachAllExporting(HttpServletRequest request) {
        try {
            int a = 0;
            var result = exportingbillService.getAllExportingBillUseBaseSearch(request);
            return ResponseEntity.ok(new ResponseDto(List.of("Successful for find!"), HttpStatus.OK.value(), result));

        } catch (RuntimeException | IOException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new ResponseDto(List.of("get all bill unsuccess" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    @PostMapping("/updateExporting")
    public ResponseEntity<?> updateExporting(@RequestBody ExportingBillFullDto exportingReturnBillFull) {
        try {
            int a = 0;
            exportingbillService.updateImporting(exportingReturnBillFull);
            return ResponseEntity.ok(new ResponseDto(List.of("data Updatating success"),
                    HttpStatus.OK.value(), exportingReturnBillFull));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Payment: " + e.getMessage());
        }
    }

    @PostMapping("testSpr2")
    public void go() {
        messagingTemplate.convertAndSend("/topic/" + "billRealTimeSection",
                new SocketMessage("billRealTimeSection", "Wedsocket sending ...", new ExportingBillDto()));
    }

    @PostMapping("test")
    public ResponseEntity<?> testToWH(HttpServletRequest request) {
        try {
            List<ProductModel> result = exportingbillService.getTesTToWarehouse(request);
            return ResponseEntity.ok(new ResponseDto(List.of("get all bill success"),
                    HttpStatus.OK.value(), result));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new ResponseDto(List.of("get all bill unsuccess"),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }
}
