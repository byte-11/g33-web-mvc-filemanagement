package uz.pdp.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.dto.FileDto;
import uz.pdp.model.FileEntity;

@Controller
@RequestMapping("/files")
public class FileController {

    @GetMapping
    public String filesPage() {
        return "files";
    }

    @PostMapping("/upload")
    public ModelAndView upload(@ModelAttribute FileDto fileDto, ModelAndView modelAndView) {
        var fileEntity = FileEntity.builder()
                .originalName(fileDto.getFile().getOriginalFilename())
                .name(fileDto.getName())
                .contentType(fileDto.getFile().getContentType())
                .size(fileDto.getFile().getSize()).build();
        modelAndView.addObject("file", fileEntity);
        modelAndView.setViewName("files");
        return modelAndView;
    }
}
