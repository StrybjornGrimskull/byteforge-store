package com.byteforge.byteforge.dto.specifications;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type" // поле, которое будет хранить тип спецификации
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CaseSpecDTO.class, name = "case"),
        @JsonSubTypes.Type(value = CpuSpecDTO.class, name = "cpu"),
        @JsonSubTypes.Type(value = GpuSpecDTO.class, name = "gpu"),
        @JsonSubTypes.Type(value = MonitorSpecDTO.class, name = "monitor"),
        @JsonSubTypes.Type(value = MotherboardSpecDTO.class, name = "motherboard"),
        @JsonSubTypes.Type(value = PsuSpecDTO.class, name = "psu"),
        @JsonSubTypes.Type(value = RamSpecDTO.class, name = "ram"),
        @JsonSubTypes.Type(value = SsdSpecDTO.class, name = "ssd"),
        @JsonSubTypes.Type(value = WiredKeyboardSpecDTO.class, name = "wiredkbrd"),
        @JsonSubTypes.Type(value = WiredMouseSpecDTO.class, name = "wiredmouse"),
        @JsonSubTypes.Type(value = WirelessKeyboardSpecDTO.class, name = "wirelesskbrd"),
        @JsonSubTypes.Type(value = WirelessMouseSpecDTO.class, name = "wirelessmouse"),
})
public interface ProductSpecDTO {}