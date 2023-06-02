## LocalStack + Bucket S3

## O que é o localstack?
 O localstack é um framework que simula os serviços da AWS em um ambiente local, permitindo que você teste suas 
 aplicações sem a necessidade de criar recursos na AWS, utilizando seus principais serviços, como S3, SQS, SNS, SecretsManager etc.


### Pré-requisitosa

- Docker Desktop
- AWS CLI
- Opcional -> Python 3.8

### Instalando

 Para instalar o Docker e o Docker Compose, siga os passos abaixo:

 Baixar o Docker : https://docs.docker.com/compose/install/

 Baixar o AWS CLI: https://docs.aws.amazon.com/pt_br/cli/latest/userguide/install-cliv2.html

 OPCIONAL -> Baixar o Python + PIP : https://phoenixnap.com/kb/install-pip-windows

### Executando o projeto

1. Clone o repositório
2. Execute o comando para subir a instância no docker: `docker-compose up -d`

#### Utilizando o AWS  CLI

3. Execute o comando para criar um bucket: `aws --endpoint-url=http://localhost:4566 --profile default s3 mb s3://bucket-name`
4. Execute o comando para listar os buckets dentro do S3 `aws --endpoint-url=http://localhost:4566 --profile default s3 ls`
5. Execute o comando para fazer upload de arquivos no bucket especificado `aws --endpoint http://localhost:4566 --profile default s3 cp helloworld.txt s3://bucket-name`
6. Execute o comando para listar os arquivos no bucket especificado `aws --endpoint http://localhost:4566 --profile default s3 ls s3://bucket-name`
7. Execute o comando para baixar os arquivos do bucket especificado `aws --endpoint http://localhost:4566 --profile default s3 cp s3://bucket-name/helloworld.txt .`
 
#### Utilizando o Java

3. Para criar um bucket, basta enviar uma requisição POST para este Endpoint com o nome do bucket-> `localhost:8080/buckets`
4. Para criar um arquivo no bucket, basta enviar uma requisição POST para este Endpoint -> `localhost:8080/data`
5. Para listar o conteúdo do arquivo dentro do bucket criado, basta enviar uma requisição GET para este Endpoint -> `localhost:8080/data`


### Links úteis

- Link com a documentação do LocalStack: https://docs.localstack.cloud/user-guide/

- Link com todos comandos da aws: https://docs.aws.amazon.com/cli/latest/reference/s3/index.html#cli-aws-s3

- Link repositório que tirei como base para o projeto : https://github.com/amrutprabhu/spring-boot-with-aws-s3
