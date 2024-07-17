package com.example.visionapiproject;

import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.vision.v1.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class VisionController {

    @Autowired
    private ImageAnnotatorClient imageAnnotatorClient;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        ByteString imgBytes = ByteString.readFrom(file.getInputStream());

        List<AnnotateImageRequest> requests = List.of(
                AnnotateImageRequest.newBuilder()
                        .addFeatures(Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build())
                        .setImage(Image.newBuilder().setContent(imgBytes).build())
                        .build()
        );

        BatchAnnotateImagesResponse response = imageAnnotatorClient.batchAnnotateImages(requests);
        AnnotateImageResponse res = response.getResponsesList().get(0);
        if (res.hasError()) {
            return "Error: " + res.getError().getMessage();
        }
        return res.getTextAnnotationsList().get(0).getDescription();
    }
}