package com.app.src.service;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class XmlService {

    public String detectEncoding(byte[] xmlBytes) {
        String header = new String(xmlBytes, 0, Math.min(xmlBytes.length, 100), StandardCharsets.US_ASCII);
        Pattern pattern = Pattern.compile("encoding=[\"'](.*?)[\"']");
        Matcher matcher = pattern.matcher(header);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "UTF-8"; // fallback padrão
    }
}
