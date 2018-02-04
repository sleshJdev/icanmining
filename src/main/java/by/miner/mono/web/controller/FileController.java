package by.miner.mono.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller("/api")
public class FileController {
    @GetMapping(value = "/download/miner")
    public void downloadMiner() throws IOException {

    }
}
