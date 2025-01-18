package com.scm.arjun.scm20.controller;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class TestController {


    public static final String filePath = "D:\\data\\dod\\IRLA\\IRLA_Data_71012.txt";

    @GetMapping("/test/data")
    public List<String> getData() {

        return Arrays.asList("test1", "test2", "test3", "test4");
    }


    /* Download Zip File From SpringBoot  */
    @GetMapping("/downloadZipFile")
    public ResponseEntity<InputStreamResource> downloadFileInZip() throws IOException {
        final String filePath = "D:\\data\\dod\\IRLA\\IRLA_Data_71012.txt";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            addFilesToZip(zipOutputStream, Path.of(filePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Prepare the response as file Zip
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = IRLAData.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    private void addFilesToZip(ZipOutputStream zipOutputStream, Path filePath) {

        try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
            ZipEntry zipEntry = new ZipEntry(filePath.getFileName().toString());
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, length);
            }
            zipOutputStream.closeEntry();
        } catch (IOException exception) {
            throw new RuntimeException("Error while zipping the file :: " + filePath.getFileName(), exception);
        }
    }


    // Modern Approch to Deal With Zip File Using Java 21 and SpringBoot 3

    @GetMapping("/downloadZipFileNew")
    public ResponseEntity<InputStreamResource> downloadZipFileNew() throws IOException {
        final String filePathNew = "D:\\data\\dod\\IRLA\\IRLA_Data_710151.txt";
        Path filePath = Path.of(filePathNew);
        String originalFileName= filePath.getFileName().toString();
        String zipFileName = originalFileName.substring(0,originalFileName.lastIndexOf('.')) + ".zip";

        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
            throw new IOException("File not found or unreadable File ... " + filePath);
        }

        //Create ZIP File
        byte[] zipData = createZip(filePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+zipFileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(new ByteArrayInputStream(zipData)));
    }

    private byte[] createZip(Path filePath) throws IOException {

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.putNextEntry(new ZipEntry(filePath.getFileName().toString()));
            Files.copy(filePath, zos); //write the file content to zip
            zos.closeEntry();
            zos.finish();
            return bos.toByteArray();
        }
    }


}
