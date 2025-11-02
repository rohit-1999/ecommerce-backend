package com.ecommerce.project.service;

import com.ecommerce.project.payload.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {


    String uploadImage(String path, MultipartFile file) throws IOException;
}
