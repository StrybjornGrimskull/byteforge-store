package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.services.specifications.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecificationFactory {

    // Сервисы спецификаций
    private final CaseSpecService caseSpecService;
    private final CpuSpecService cpuSpecService;
    private final GpuSpecService gpuSpecService;
    private final MonitorSpecService monitorSpecService;
    private final MotherboardSpecService motherboardSpecService;
    private final PsuSpecService psuSpecService;
    private final RamSpecService ramSpecService;
    private final SsdSpecService ssdSpecService;
    private final WiredKeyboardSpecService wiredKeyboardSpecService;
    private final WirelessKeyboardSpecService wirelessKeyboardSpecService;
    private final WiredMouseSpecService wiredMouseSpecService;
    private final WirelessMouseSpecService wirelessMouseSpecService;

    /**
     * Создает спецификацию для продукта на основе ID категории
     */
    public void createSpecificationForProduct(Product product, Integer categoryId, ProductCreateRequestDto request) {
        switch (categoryId) {
            case 1: // Graphics cards
                gpuSpecService.createGpuSpec(product, request);
                break;
            case 2: // Central processing unit
                cpuSpecService.createCpuSpec(product, request);
                break;
            case 3: // Motherboard
                motherboardSpecService.createMotherboardSpec(product, request);
                break;
            case 4: // Random access memory
                ramSpecService.createRamSpec(product, request);
                break;
            case 5: // Power supply unit
                psuSpecService.createPsuSpec(product, request);
                break;
            case 6: // PC cases
                caseSpecService.createCaseSpec(product, request);
                break;
            case 7: // Monitors
                monitorSpecService.createMonitorSpec(product, request);
                break;
            case 8: // Solid state drive
                ssdSpecService.createSsdSpec(product, request);
                break;
            case 9: // Wired keyboard
                wiredKeyboardSpecService.createWiredKeyboardSpec(product, request);
                break;
            case 10: // Wireless keyboard
                wirelessKeyboardSpecService.createWirelessKeyboardSpec(product, request);
                break;
            case 11: // Wired mice
                wiredMouseSpecService.createWiredMouseSpec(product, request);
                break;
            case 12: // Wireless mice
                wirelessMouseSpecService.createWirelessMouseSpec(product, request);
                break;
            default:
                log.warn("No specification creator found for category ID: {}", categoryId);
        }
    }
}