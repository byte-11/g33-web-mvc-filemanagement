package uz.pdp.model;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class FileEntity {
    private String originalName;
    private String name;
    private String contentType;
    private long size;
}
