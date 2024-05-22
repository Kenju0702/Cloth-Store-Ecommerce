package com.example.ctapi.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportingReturnBillFullDto {
    ImportingReturnBIllDto importingReturn;
    List<ImportingReturnBillTransactionDto> importingReturnTransactionModels;
}
