package uz.pdp.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.dto.FileDto;
import uz.pdp.model.FileEntity;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
@RequestMapping("/files")
public class FileController {

    private static final Path PATH = Path.of("C:\\Users\\b.kambaraliev\\IdeaProjects\\g33\\g33-mvc-file-management\\src\\main\\resources\\files\\");

    @GetMapping
    public String filesPage() {
        return "files";
    }

    @PostMapping("/upload")
    public ModelAndView upload(@ModelAttribute FileDto fileDto, ModelAndView modelAndView) throws IOException {
        for (MultipartFile file : fileDto.getFile()) {
            Files.copy(
                    file.getInputStream(),
                    PATH.resolve(Objects.requireNonNull(file.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
        /*
        var fileEntity = FileEntity.builder()
                .originalName(fileDto.getFile().getOriginalFilename())
                .name(fileDto.getName())
                .contentType(fileDto.getFile().getContentType())
                .size(fileDto.getFile().getSize()).build();
        modelAndView.addObject("file", fileEntity);*/
        modelAndView.setViewName("files");
        return modelAndView;
    }

    @GetMapping("/download/{fileName:.*}")
    public ResponseEntity<Resource> download(
            @PathVariable("fileName") String fileName
    ) {
        FileSystemResource resource = new FileSystemResource(PATH.resolve(fileName));
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(URLConnection.guessContentTypeFromName(fileName)))
                .header("Content-Disposition", "attachment;filename=%s".formatted(fileName))
                .body(resource);
    }
}
