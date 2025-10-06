package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.services.specifications.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class SpecificationFactoryTest {

    @Mock
    private CaseSpecService caseSpecService;

    @Mock
    private CpuSpecService cpuSpecService;

    @Mock
    private GpuSpecService gpuSpecService;

    @Mock
    private MonitorSpecService monitorSpecService;

    @Mock
    private MotherboardSpecService motherboardSpecService;

    @Mock
    private PsuSpecService psuSpecService;

    @Mock
    private RamSpecService ramSpecService;

    @Mock
    private SsdSpecService ssdSpecService;

    @Mock
    private WiredKeyboardSpecService wiredKeyboardSpecService;

    @Mock
    private WirelessKeyboardSpecService wirelessKeyboardSpecService;

    @Mock
    private WiredMouseSpecService wiredMouseSpecService;

    @Mock
    private WirelessMouseSpecService wirelessMouseSpecService;

    @InjectMocks
    private SpecificationFactory specificationFactory;

    private Product testProduct;
    private ProductCreateRequestDto testRequest;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setName("Test Product");

        testRequest = new ProductCreateRequestDto(
                "Test Product",
                1,
                1,
                BigDecimal.valueOf(99.99),
                24,
                2023,
                "Test Description",
                null,
                10,
                null, null, null, null, null, null, null, null, null, null, null, null
        );
    }

    @Test
    void createSpecificationForProduct_ShouldCallGpuSpecService_WhenCategoryIdIs1() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 1, testRequest);

        // Assert
        verify(gpuSpecService).createGpuSpec(testProduct, testRequest);
        verifyNoInteractions(cpuSpecService, motherboardSpecService, ramSpecService, psuSpecService,
                caseSpecService, monitorSpecService, ssdSpecService, wiredKeyboardSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallCpuSpecService_WhenCategoryIdIs2() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 2, testRequest);

        // Assert
        verify(cpuSpecService).createCpuSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, motherboardSpecService, ramSpecService, psuSpecService,
                caseSpecService, monitorSpecService, ssdSpecService, wiredKeyboardSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallMotherboardSpecService_WhenCategoryIdIs3() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 3, testRequest);

        // Assert
        verify(motherboardSpecService).createMotherboardSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, ramSpecService, psuSpecService,
                caseSpecService, monitorSpecService, ssdSpecService, wiredKeyboardSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallRamSpecService_WhenCategoryIdIs4() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 4, testRequest);

        // Assert
        verify(ramSpecService).createRamSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, psuSpecService,
                caseSpecService, monitorSpecService, ssdSpecService, wiredKeyboardSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallPsuSpecService_WhenCategoryIdIs5() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 5, testRequest);

        // Assert
        verify(psuSpecService).createPsuSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                caseSpecService, monitorSpecService, ssdSpecService, wiredKeyboardSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallCaseSpecService_WhenCategoryIdIs6() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 6, testRequest);

        // Assert
        verify(caseSpecService).createCaseSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, monitorSpecService, ssdSpecService, wiredKeyboardSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallMonitorSpecService_WhenCategoryIdIs7() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 7, testRequest);

        // Assert
        verify(monitorSpecService).createMonitorSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, ssdSpecService, wiredKeyboardSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallSsdSpecService_WhenCategoryIdIs8() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 8, testRequest);

        // Assert
        verify(ssdSpecService).createSsdSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, monitorSpecService, wiredKeyboardSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallWiredKeyboardSpecService_WhenCategoryIdIs9() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 9, testRequest);

        // Assert
        verify(wiredKeyboardSpecService).createWiredKeyboardSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, monitorSpecService, ssdSpecService,
                wirelessKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallWirelessKeyboardSpecService_WhenCategoryIdIs10() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 10, testRequest);

        // Assert
        verify(wirelessKeyboardSpecService).createWirelessKeyboardSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, monitorSpecService, ssdSpecService,
                wiredKeyboardSpecService, wiredMouseSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallWiredMouseSpecService_WhenCategoryIdIs11() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 11, testRequest);

        // Assert
        verify(wiredMouseSpecService).createWiredMouseSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, monitorSpecService, ssdSpecService,
                wiredKeyboardSpecService, wirelessKeyboardSpecService, wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldCallWirelessMouseSpecService_WhenCategoryIdIs12() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 12, testRequest);

        // Assert
        verify(wirelessMouseSpecService).createWirelessMouseSpec(testProduct, testRequest);
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, monitorSpecService, ssdSpecService,
                wiredKeyboardSpecService, wirelessKeyboardSpecService, wiredMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldNotCallAnySpecService_WhenCategoryIdIsUnknown() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 999, testRequest);

        // Assert
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, monitorSpecService, ssdSpecService,
                wiredKeyboardSpecService, wirelessKeyboardSpecService, wiredMouseSpecService,
                wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldNotCallAnySpecService_WhenCategoryIdIsZero() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, 0, testRequest);

        // Assert
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, monitorSpecService, ssdSpecService,
                wiredKeyboardSpecService, wirelessKeyboardSpecService, wiredMouseSpecService,
                wirelessMouseSpecService);
    }

    @Test
    void createSpecificationForProduct_ShouldNotCallAnySpecService_WhenCategoryIdIsNegative() {
        // Act
        specificationFactory.createSpecificationForProduct(testProduct, -1, testRequest);

        // Assert
        verifyNoInteractions(gpuSpecService, cpuSpecService, motherboardSpecService, ramSpecService,
                psuSpecService, caseSpecService, monitorSpecService, ssdSpecService,
                wiredKeyboardSpecService, wirelessKeyboardSpecService, wiredMouseSpecService,
                wirelessMouseSpecService);
    }
}
