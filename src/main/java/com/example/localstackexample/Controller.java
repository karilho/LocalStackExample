package com.example.localstackexample;

import io.awspring.cloud.s3.S3Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class Controller {

    // Injeção da propriedade s3ArquivoExemplo através da anotação @Value.
    // O valor "s3://bucketlucas/helloworld.txt" define o caminho do arquivo no serviço S3.
    // O Resource é uma abstração do Spring para representar um recurso, que pode ser um arquivo, um diretório, um URL, etc.
    @Value("s3://bucketlucas/helloworld.txt")
    private Resource s3ArquivoExemplo;

    //Injeta cliente S3 do SDK da AWS
    private final S3Client s3Client;

    //Construtor da classe Controller
    public Controller(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    //Cria um novo bucket no s3 do localhost.
    @PostMapping("/buckets")
    public ResponseEntity<String> newBucket(@RequestBody String bucketName) {
        CreateBucketRequest requisicao = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        //Cria o bucket com base na requisicao montada
        CreateBucketResponse resposta = s3Client.createBucket(requisicao);

        if (resposta.sdkHttpResponse().isSuccessful()) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Bucket Criado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha para criar o bucket");
    }

        //Retorna o conteudo do arquivo helloworld.txt
        @GetMapping("/data")
        public ResponseEntity<String> getData() {

            try {
                return ResponseEntity.ok(s3ArquivoExemplo.getContentAsString(StandardCharsets.UTF_8));
            } catch (Exception e) {
                return ResponseEntity.internalServerError()
                        .body("Could not fetch content");
            }
        }

        //Cria, escreve ou sobescreve o conteúdo no arquivo helloworld.txt
        @PostMapping("/data")
        public ResponseEntity<String> postData(@RequestBody String data) throws IOException {

            try (OutputStream outputStream = ((S3Resource) s3ArquivoExemplo).getOutputStream()) {
                outputStream.write(data.getBytes(StandardCharsets.UTF_8));
            }
            return ResponseEntity.ok(data);
        }


    }

