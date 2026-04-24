package com.huffman.compressor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class FileValidationService {

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
        "application/octet-stream",
        "text/plain",
        "application/pdf",
        "image/jpeg",
        "image/png",
        "image/gif",
        "application/zip",
        "application/x-zip-compressed",
        "application/x-compressed",
        "application/x-gzip",
        "application/gzip",
        "application/x-tar",
        "application/x-rar-compressed",
        "application/x-7z-compressed",
        "application/x-bzip2",
        "application/x-lzma",
        "application/x-xz",
        "application/java-archive",
        "application/x-msdownload",
        "application/x-msdos-program",
        "application/x-shockwave-flash",
        "application/x-msi",
        "application/xml",
        "text/xml",
        "application/json",
        "text/csv",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation"
    );

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB

    public boolean isValidFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }

        String contentType = file.getContentType();
        return contentType != null && ALLOWED_MIME_TYPES.contains(contentType);
    }

    public boolean isValidCompressedFile(MultipartFile file) {
        if (!isValidFile(file)) {
            return false;
        }

        String filename = file.getOriginalFilename();
        if (filename == null) {
            return false;
        }

        String lowerFilename = filename.toLowerCase();
        return lowerFilename.endsWith(".huf") || 
               lowerFilename.endsWith(".txt") || 
               lowerFilename.endsWith(".zip") || 
               lowerFilename.endsWith(".gz") || 
               lowerFilename.endsWith(".tar") || 
               lowerFilename.endsWith(".rar") || 
               lowerFilename.endsWith(".7z") || 
               lowerFilename.endsWith(".bz2") || 
               lowerFilename.endsWith(".xz") || 
               lowerFilename.endsWith(".lzma") || 
               lowerFilename.endsWith(".jar") || 
               lowerFilename.endsWith(".exe") || 
               lowerFilename.endsWith(".com") || 
               lowerFilename.endsWith(".swf") || 
               lowerFilename.endsWith(".msi") || 
               lowerFilename.endsWith(".xml") || 
               lowerFilename.endsWith(".json") || 
               lowerFilename.endsWith(".csv") || 
               lowerFilename.endsWith(".doc") || 
               lowerFilename.endsWith(".docx") || 
               lowerFilename.endsWith(".xls") || 
               lowerFilename.endsWith(".xlsx") || 
               lowerFilename.endsWith(".ppt") || 
               lowerFilename.endsWith(".pptx");
    }

    public String getValidationErrorMessage(MultipartFile file, boolean isCompressed) {
        if (file == null || file.isEmpty()) {
            return "Please select a file";
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return "File size exceeds maximum limit of 50MB";
        }

        if (isCompressed) {
            String filename = file.getOriginalFilename();
            if (filename == null || !isValidCompressedFile(file)) {
                return "Please select a valid compressed file. Supported formats: .huf, .txt, .zip, .gz, .tar, .rar, .7z, .bz2, .xz, .lzma, .jar, .exe, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .xml, .json, .csv and more";
            }
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            return "File type not supported. Please use common file formats.";
        }

        return "Unknown validation error";
    }
}
