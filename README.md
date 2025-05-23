
# Java + Spring

Sistema CRUD de produtos e fornecedores, onde para cada fornecedor, poderá haver diversos produtos.

Nesse projeto, um produto só poderá ser registrado se houver um fornecedor previamente registrado. Utilizando o mapeamento @ManyToOne e @OneToMany entre as classes de produto e fornecedor.

## Documentação:

![Swagger do projeto](swaggerimg.png)

### Desenvolvi testes unitários, com JUnit 5 + Mockito, para cada método das classes de serviço de product e supplier


Dependências Gradle:
- MapStruct
- Spring Web
- Spring JPA
- H2
- OpenAPI/Swagger
- 
### Schemas utilizados

#### SupplierAddDto
- **name**: string  
- **contact**: string  

#### SupplierUpdateDto
- **name**: string  
- **contact**: string  

#### SupplierDto
- **id**: integer (int64)  
- **name**: string  
- **contact**: string  
- **products**: array of ProductDto  

#### ProductAddDto
- **name**: string  
- **sku**: string  
- **description**: string  
- **price**: number  
- **quantity**: integer (int32)  
- **validity**: string (date)  
- **supplierId**: integer (int64)  

#### ProductUpdateDto
- **name**: string  
- **sku**: string  
- **description**: string  
- **price**: number  
- **quantity**: integer (int32)  
- **validity**: string (date)  
- **supplierId**: integer (int64)  

#### ProductDto
- **id**: integer (int64)  
- **name**: string  
- **sku**: string  
- **description**: string  
- **price**: number  
- **quantity**: integer (int32)  
- **validity**: string (date)  
- **supplierId**: integer (int64)  
