package com.example.telecare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/.well-known/assetlinks.json")
public class GoogleAssetLinkController {
    @GetMapping(value = "")
    public ResponseEntity<String> createProduct() {
        return ResponseEntity.ok().body("[\n" +

                "{\n" +
                "    \"relation\": [\"delegate_permission/common.handle_all_urls\"],\n" +
                "    \"target\": {\n" +
                "        \"namespace\": \"android_app\",\n" +
                "        \"package_name\": \"com.example.healthcare_app\",\n" +
                "        \"sha256_cert_fingerprints\": [\n" +
                "            \"D8:58:4A:96:B8:5D:E2:DA:F2:21:25:72:19:7F:35:59:5F:52:EE:5B:96:8B:A9:D3:96:91:00:C5:13:F0:0F:CA\"\n" +
                "        ]\n" +
                "    }\n" +
                "}\n" +
                "]");
    }
}
