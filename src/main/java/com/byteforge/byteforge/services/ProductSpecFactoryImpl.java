package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.specifications.*;
import com.byteforge.byteforge.entities.*;
import com.byteforge.byteforge.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductSpecFactoryImpl implements ProductSpecFactory {

    private final CaseSpecRepository caseSpecRepository;
    private final CpuSpecRepository cpuSpecRepository;
    private final GpuSpecRepository gpuSpecRepository;
    private final MonitorSpecRepository monitorSpecRepository;
    private final MotherboardSpecRepository motherboardSpecRepository;
    private final PsuSpecRepository psuSpecRepository;
    private final RamSpecRepository ramSpecRepository;
    private final SsdSpecRepository ssdSpecRepository;
    private final WiredKeyboardSpecRepository wiredKeyboardSpecRepository;
    private final WiredMouseSpecRepository wiredMouseSpecRepository;
    private final WirelessKeyboardSpecRepository wirelessKeyboardSpecRepository;
    private final WirelessMouseSpecRepository wirelessMouseSpecRepository;

    @Override
    public void createAndAttachSpec(ProductSpecDTO specDTO, Product product, String type) {
        switch (type) {
            case "case" -> {
                CaseSpecDTO dto = (CaseSpecDTO) specDTO;
                CaseSpec spec = new CaseSpec();
                spec.setFormFactor(dto.formFactor());
                spec.setMotherboardSupport(dto.motherboardSupport());
                spec.setMaxGpuLength(dto.maxGpuLength());
                spec.setMaxCpuCoolerHeight(dto.maxCpuCoolerHeight());
                spec.setFansIncluded(dto.fansIncluded());
                spec.setRadiatorSupport(dto.radiatorSupport());
                spec.setProduct(product);
                caseSpecRepository.save(spec);
                product.setCaseSpec(spec);
            }
            case "cpu" -> {
                CpuSpecDTO dto = (CpuSpecDTO) specDTO;
                CpuSpec spec = new CpuSpec();
                spec.setCores(dto.cores());
                spec.setThreads(dto.threads());
                spec.setBaseClock(dto.baseClock());
                spec.setBoostClock(dto.boostClock());
                spec.setSocket(dto.socket());
                spec.setCacheSize(dto.cacheSize());
                spec.setTdp(dto.tdp());
                spec.setIntegratedGpu(dto.integratedGpu());
                spec.setProduct(product);
                cpuSpecRepository.save(spec);
                product.setCpuSpec(spec);
            }
            case "gpu" -> {
                GpuSpecDTO dto = (GpuSpecDTO) specDTO;
                GpuSpec spec = new GpuSpec();
                spec.setMemorySize(dto.memorySize());
                spec.setMemoryType(dto.memoryType());
                spec.setBusWidth(dto.busWidth());
                spec.setBaseClock(dto.baseClock());
                spec.setBoostClock(dto.boostClock());
                spec.setTdp(dto.tdp());
                spec.setLength(dto.length());
                spec.setDisplayOutputs(dto.displayOutputs());
                spec.setProduct(product);
                gpuSpecRepository.save(spec);
                product.setGpuSpec(spec);
            }
            case "monitor" -> {
                MonitorSpecDTO dto = (MonitorSpecDTO) specDTO;
                MonitorSpec spec = new MonitorSpec();
                spec.setScreenSize(dto.screenSize());
                spec.setResolution(dto.resolution());
                spec.setPanelType(dto.panelType());
                spec.setRefreshRate(dto.refreshRate());
                spec.setResponseTime(dto.responseTime());
                spec.setProduct(product);
                monitorSpecRepository.save(spec);
                product.setMonitorSpec(spec);
            }
            case "motherboard" -> {
                MotherboardSpecDTO dto = (MotherboardSpecDTO) specDTO;
                MotherboardSpec spec = new MotherboardSpec();
                spec.setSocket(dto.socket());
                spec.setChipset(dto.chipset());
                spec.setFormFactor(dto.formFactor());
                spec.setMemorySlots(dto.memorySlots());
                spec.setMaxMemory(dto.maxMemory());
                spec.setMemoryType(dto.memoryType());
                spec.setM2Slots(dto.m2Slots());
                spec.setSataPorts(dto.sataPorts());
                spec.setProduct(product);
                motherboardSpecRepository.save(spec);
                product.setMotherboardSpec(spec);
            }
            case "psu" -> {
                PsuSpecDTO dto = (PsuSpecDTO) specDTO;
                PsuSpec spec = new PsuSpec();
                spec.setWattage(dto.wattage());
                spec.setFormFactor(dto.formFactor());
                spec.setEfficiencyCert(dto.efficiencyCert());
                spec.setModularity(dto.modularity());
                spec.setPcie8pinConnectors(dto.pcie8pinConnectors());
                spec.setSataConnectors(dto.sataConnectors());
                spec.setProduct(product);
                psuSpecRepository.save(spec);
                product.setPsuSpec(spec);
            }
            case "ram" -> {
                RamSpecDTO dto = (RamSpecDTO) specDTO;
                RamSpec spec = new RamSpec();
                spec.setMemorySize(dto.memorySize());
                spec.setModulesCount(dto.modulesCount());
                spec.setSpeed(dto.speed());
                spec.setType(dto.type());
                spec.setTimings(dto.timings());
                spec.setVoltage(dto.voltage());
                spec.setProduct(product);
                ramSpecRepository.save(spec);
                product.setRamSpec(spec);
            }
            case "ssd" -> {
                SsdSpecDTO dto = (SsdSpecDTO) specDTO;
                SsdSpec spec = new SsdSpec();
                spec.setCapacity(dto.capacity());
                spec.setFormFactor(dto.formFactor());
                spec.setInterfaceType(dto.interfaceType());
                spec.setReadSpeed(dto.readSpeed());
                spec.setWriteSpeed(dto.writeSpeed());
                spec.setMemoryType(dto.memoryType());
                spec.setEnduranceTbw(dto.enduranceTbw());
                spec.setDramCache(dto.dramCache());
                spec.setEncryption(dto.encryption());
                spec.setThickness(dto.thickness());
                spec.setProduct(product);
                ssdSpecRepository.save(spec);
                product.setSsdSpec(spec);
            }
            case "wiredkbrd" -> {
                WiredKeyboardSpecDTO dto = (WiredKeyboardSpecDTO) specDTO;
                WiredKeyboardSpec spec = new WiredKeyboardSpec();
                spec.setLayout(dto.layout());
                spec.setSwitchType(dto.switchType());
                spec.setSwitchBrand(dto.switchBrand());
                spec.setSwitchModel(dto.switchModel());
                spec.setRgbLighting(dto.rgbLighting());
                spec.setHotSwappable(dto.hotSwappable());
                spec.setActuationForce(dto.actuationForce());
                spec.setTravelDistance(dto.travelDistance());
                spec.setWeight(dto.weight());
                spec.setCableLength(dto.cableLength());
                spec.setUsbPassthrough(dto.usbPassthrough());
                spec.setDetachableCable(dto.detachableCable());
                spec.setProduct(product);
                wiredKeyboardSpecRepository.save(spec);
                product.setWiredKeyboardSpec(spec);
            }
            case "wiredmouse" -> {
                WiredMouseSpecDTO dto = (WiredMouseSpecDTO) specDTO;
                WiredMouseSpec spec = new WiredMouseSpec();
                spec.setSensorType(dto.sensorType());
                spec.setSensorModel(dto.sensorModel());
                spec.setMaxDpi(dto.maxDpi());
                spec.setAdjustableDpi(dto.adjustableDpi());
                spec.setButtons(dto.buttons());
                spec.setCableLength(dto.cableLength());
                spec.setCableType(dto.cableType());
                spec.setUsbConnector(dto.usbConnector());
                spec.setWeight(dto.weight());
                spec.setRgbLighting(dto.rgbLighting());
                spec.setOnboardMemory(dto.onboardMemory());
                spec.setWarrantyMonths(dto.warrantyMonths());
                spec.setProduct(product);
                wiredMouseSpecRepository.save(spec);
                product.setWiredMouseSpec(spec);
            }
            case "wirelesskbrd" -> {
                WirelessKeyboardSpecDTO dto = (WirelessKeyboardSpecDTO) specDTO;
                WirelessKeyboardSpec spec = new WirelessKeyboardSpec();
                spec.setLayout(dto.layout());
                spec.setSwitchType(dto.switchType());
                spec.setSwitchBrand(dto.switchBrand());
                spec.setSwitchModel(dto.switchModel());
                spec.setWirelessTech(dto.wirelessTech());
                spec.setRgbLighting(dto.rgbLighting());
                spec.setHotSwappable(dto.hotSwappable());
                spec.setActuationForce(dto.actuationForce());
                spec.setTravelDistance(dto.travelDistance());
                spec.setWeight(dto.weight());
                spec.setBatteryLife(dto.batteryLife());
                spec.setChargingType(dto.chargingType());
                spec.setMultiDevicePairing(dto.multiDevicePairing());
                spec.setProduct(product);
                wirelessKeyboardSpecRepository.save(spec);
                product.setWirelessKeyboardSpec(spec);
            }
            case "wirelessmouse" -> {
                WirelessMouseSpecDTO dto = (WirelessMouseSpecDTO) specDTO;
                WirelessMouseSpec spec = new WirelessMouseSpec();
                spec.setSensorType(dto.sensorType());
                spec.setSensorModel(dto.sensorModel());
                spec.setMaxDpi(dto.maxDpi());
                spec.setButtons(dto.buttons());
                spec.setWirelessTech(dto.wirelessTech());
                spec.setPollingRate(dto.pollingRate());
                spec.setWeight(dto.weight());
                spec.setRgbLighting(dto.rgbLighting());
                spec.setBatteryType(dto.batteryType());
                spec.setBatteryLife(dto.batteryLife());
                spec.setStandbyBatteryLife(dto.standbyBatteryLife());
                spec.setChargingTime(dto.chargingTime());
                spec.setOnboardMemory(dto.onboardMemory());
                spec.setWarrantyMonths(dto.warrantyMonths());
                spec.setProduct(product);
                wirelessMouseSpecRepository.save(spec);
                product.setWirelessMouseSpec(spec);
            }
            default -> throw new IllegalArgumentException("Unsupported spec type: " + type);
        }
    }
}
